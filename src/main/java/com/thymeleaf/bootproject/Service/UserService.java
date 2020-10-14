package com.thymeleaf.bootproject.Service;

import com.thymeleaf.bootproject.Model.Role;
import com.thymeleaf.bootproject.Model.UserVO;
import com.thymeleaf.bootproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //유저가 가입할때 권한과 비밀번호 암호화에대한 로직
    public UserVO save(UserVO user){//로직이들어가므로 Service메소드로 빼버림
        String encodedPassword = passwordEncoder.encode((user.getUserpassword()));
        user.setUserpassword(encodedPassword);
        user.setEnabled(true);

        Role role = new Role();
        role.setId(1l); //role테이블에 권한을 준다.
        user.getRoles().add(role); //어떤 권한을 줄껀지
        return userRepository.save(user);
    }
}
