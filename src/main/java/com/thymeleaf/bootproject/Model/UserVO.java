package com.thymeleaf.bootproject.Model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class UserVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String userpassword;
    private Boolean enabled;

    @ManyToMany
    @JoinTable( //조인되는 테이블을 설정해준다.
        name = "user_role",  //테이블
        joinColumns = @JoinColumn(name = "user_id"),  //컬럼1
        inverseJoinColumns = @JoinColumn(name = "role_id") //컬럼2
    )
    private List<Role> roles = new ArrayList<>();
}
