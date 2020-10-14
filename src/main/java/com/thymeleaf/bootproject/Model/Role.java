package com.thymeleaf.bootproject.Model;

import lombok.Data;


import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String authname;

    @ManyToMany(mappedBy = "roles")  //UserVO에 객체와 연결 상대방 조인되는 테이블까지 양방향 매핑
    private List<UserVO> users;
}
