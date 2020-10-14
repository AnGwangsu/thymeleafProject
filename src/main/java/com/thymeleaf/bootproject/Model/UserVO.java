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
    @JoinTable( //조인되는 테이블(매핑테이블)을 설정해준다.
        name = "user_role",  //테이블
        joinColumns = @JoinColumn(name = "user_id"),  //컬럼1
        inverseJoinColumns = @JoinColumn(name = "role_id") //join된 상대 테이블 role의 조인되는 컬럼
    )
    private List<Role> roles = new ArrayList<>(); //role과 매핑한다.
}
