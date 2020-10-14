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
        String encodedPassword = passwordEncoder.encode((user.getUserpassword())); //인코딩된 패스워드를 객체에 넣는다.
        user.setUserpassword(encodedPassword); //유저 패스워드에 인코딩된 패스워드를 db에 넣는다.
        user.setEnabled(true); //true면 1 false면 0(활성화된 상태 1)
        
        Role role = new Role();
        role.setId(1l); //user_role 에 role_id에 하드코딩으로 저장이 됨
        user.getRoles().add(role); //어떤 권한을 줄껀지 ,UserVO에서 roels를 양방향 매핑해서 role테이블에 있는 권한을 user_role에 저장
        return userRepository.save(user);
    }
}
