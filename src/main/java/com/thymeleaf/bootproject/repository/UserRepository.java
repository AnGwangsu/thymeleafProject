package com.thymeleaf.bootproject.repository;

import com.thymeleaf.bootproject.Model.UserVO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserVO,Long> {
}
