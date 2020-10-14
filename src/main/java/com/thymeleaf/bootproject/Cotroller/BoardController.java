package com.thymeleaf.bootproject.Cotroller;

import com.thymeleaf.bootproject.Model.BoardVO;
import com.thymeleaf.bootproject.repository.BoardRepository;
import com.thymeleaf.bootproject.validator.BoardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BoardValidator boardValidator;

    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 2) Pageable pageable,
                       @RequestParam(required = false,defaultValue = "") String searchText){
        /*Page<BoardVO> boards = boardRepository.findAll(pageable);*/ //페이징한 데이터를 다 가져올수 있다.
        Page<BoardVO> boards = boardRepository.findByTitleContainingOrContentContaining(searchText,searchText,pageable);
        int startPage = Math.max(1,boards.getPageable().getPageNumber() - 4);//최솟값을0으로 만든다.,현재페이지로부터4개를 더보여줌
        int endPage = Math.min(boards.getTotalPages(),boards.getPageable().getPageNumber() + 4);//최댓값을모든페이지수,현재페이지로부터4개를 더보여줌
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("boards",boards);
        return "board/list";
    }
    @GetMapping("/form")
    public  String form(Model model, @RequestParam(required = false) Long id){
        if(id == null){
            model.addAttribute("board",new BoardVO());
        }else{
            BoardVO bvo = boardRepository.findById(id).orElse(null);
            model.addAttribute("board",bvo);
        }

        return "board/form";
    }
    @PostMapping("/form")
    public String formPost(@Valid @ModelAttribute("board") BoardVO board, BindingResult bindingResult){ //@Size에 부합되는 조건
        boardValidator.validate(board,bindingResult);
        if(bindingResult.hasErrors()){
            return "board/form"; //form으로 돌아갈때 form object에 board가 줄수없기때문에 @ModelAttribute로 board를 지정해줘야함
        }
        boardRepository.save(board);
        return "redirect:/board/list";
    }
}
