package com.thymeleaf.bootproject.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AndRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource; //스프링 내장 객체

    @Override
    public void configure(WebSecurity web) { // 5
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**"); //3개의 경로는 get접근이 불가
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { //보안설정
        http
                .authorizeRequests()
                    .antMatchers( "/user/register","/board/list" ,"/").permitAll() // 누구나 접근 허용
                    .antMatchers("/board/detail").hasRole("ROLE_USER") // USER, ADMIN만 접근 가능
                    .antMatchers("/admin").hasRole("ROLE_ADMIN") // ADMIN만 접근 가능
                    .anyRequest().authenticated() //위에를 제외한 모든 요청은 반드시 로그인을 해야만 페이지를 볼 수있다.
                    .and()
                .formLogin()//위에 설정한 url빼고는 로그인 페이지로 이동하고 로그인이 되면 다시 들어가려는 페이지로 들어감
                    .loginPage("/user/login")//포스트로 입력값으로 날라옴
                    .defaultSuccessUrl("/")
                    .permitAll()
                    .and()
                .logout()
                    .logoutSuccessUrl("/user/login") // 로그아웃 성공시 리다이렉트 주소
                    .invalidateHttpSession(true); // 세션 날리기

    }
    //인증처리를 알아서 해준다.
    //Authentication 로그인
    //Authroization 권한
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource) //application.properties에 설정된 데이터 소스
                .passwordEncoder(passwordEncoder()) //로그인처리를 할때 비밀번호 암호화를 해서 db와 비교
                .usersByUsernameQuery("select username,userpassword,enabled "  //인증처리(로그인)
                        + " from uservo "
                        + " where username = ?")
                .authoritiesByUsernameQuery("select u.username,r.authname " //권한 처리(로그인을 해도 그 사용자의 권한)
                        + " from user_role ur inner join uservo u on ur.user_id = u.id "
                        + " inner join role r on ur.role_id = r.id "
                        + " where u.username = ?");
    }
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); //비밀번호를 암호화하는 객체
    }
}
