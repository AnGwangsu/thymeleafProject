package com.thymeleaf.bootproject.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource; //스프링 내장 객체

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/","/user/register","/css/**").permitAll()  //연결할 link의 경로를 지정해준다
                    .anyRequest().authenticated() //인증
                    .and()
                .formLogin()
                    .loginPage("/user/login")
                    .permitAll()
                    .and()
                .logout()
                    .permitAll();
    }
    //인증처리를 알아서 해준다.
    //Authentication 로그인
    //Authroization 권한
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder()) //인코더를 이용해서 알아서 비밀번호를 암호화함
                .usersByUsernameQuery("select username,userpassword,enabled "  //인증처리
                        + " from uservo "
                        + " where username = ?")
                .authoritiesByUsernameQuery("select u.username,r.authname " //권한 처리
                        + " from user_role ur inner join uservo u on ur.user_id = u.id "
                        + " inner join role r on ur.role_id = r.id "
                        + " where u.username = ?");
    }
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); //비밀번호를 암호화하는 객체
    }
}
