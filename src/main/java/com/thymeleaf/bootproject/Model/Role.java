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

    @ManyToMany(mappedBy = "roles")  //RoleVO와 연결
    private List<UserVO> users;
}
