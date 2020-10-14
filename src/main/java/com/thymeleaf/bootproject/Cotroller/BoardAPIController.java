package com.thymeleaf.bootproject.Cotroller;


import java.util.List;

import com.thymeleaf.bootproject.Model.BoardVO;
import com.thymeleaf.bootproject.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
class BoardAPIController {
    @Autowired
    private BoardRepository repository;


    @GetMapping("/boards")
    List<BoardVO> all(@RequestParam(required = false, defaultValue = "") String title,
                      @RequestParam(required = false, defaultValue = "") String content) {
        if(StringUtils.isEmpty(title) && StringUtils.isEmpty(content)){
            return repository.findAll(); //둘다 비었다면 모든 리스트 뿌림
        }else{
            return repository.findByTitleOrContent(title,content); //제목또는 내용이 들어오면 해당하는 모든리스트 뿌림
        }
    }

    @PostMapping("/boards")
    BoardVO newBoardVO(@RequestBody BoardVO newBoardVO) {
        return repository.save(newBoardVO);
    }

    // Single item

    @GetMapping("/boards/{id}")
    BoardVO one(@PathVariable Long id) {
        return repository.findById(id).orElse(null);

    }

    @PutMapping("/boards/{id}")
    BoardVO replaceBoardVO(@RequestBody BoardVO newBoardVO, @PathVariable Long id) {

        return repository.findById(id)
                .map(BoardVO -> {
                    BoardVO.setTitle(newBoardVO.getTitle());
                    BoardVO.setContent(newBoardVO.getContent());
                    return repository.save(BoardVO);
                })
                .orElseGet(() -> {
                    newBoardVO.setId(id);
                    return repository.save(newBoardVO);
                });
    }

    @DeleteMapping("/boards/{id}")
    void deleteBoardVO(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
