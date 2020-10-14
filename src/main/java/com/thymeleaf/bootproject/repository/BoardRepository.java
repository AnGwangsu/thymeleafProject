package com.thymeleaf.bootproject.repository;

import com.thymeleaf.bootproject.Model.BoardVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//mapper에 해당함
public interface BoardRepository extends JpaRepository<BoardVO,Long> { //interface만 정의되면 쿼리가 자동 생성
    List<BoardVO> findByTitle(String title);
    List<BoardVO> findByTitleOrContent(String title,String content); //Or조건문

    Page<BoardVO> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
}
