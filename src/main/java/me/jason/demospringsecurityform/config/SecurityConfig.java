package me.jason.demospringsecurityform.config;

import me.jason.demospringsecurityform.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AccountService accountService;

    // securityConfig가 하나의 필터 역할을 한다.
    // antMatchers 가 체인을 만드는 역할을 한다.
    // @Order 를 통해 순서를 줄 수 있다.
    // filterChainProxy까지 들어오는 과정에 대한 부분

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 요청에 대한 인가 설정
        http.authorizeRequests()
                .mvcMatchers("/", "/info", "/account/**").permitAll()
                .mvcMatchers("/admin").hasRole("ADMIN")
                .anyRequest().authenticated();

        // 로그인 폼을 사용하겠다.
        http.formLogin();

        // 기본적인 http 형식을 사용하겠다.
        http.httpBasic();
    }

    // 명시적으로 유저 정보 가져와서 accountService를 쓰라고 알려주는 것
    // accountService 가 빈으로 등록만 되어 있으면 이것이 없어도 알아서 쓴다.
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountService);
    }

    // 유저를 등록하는 방법
    // noop 는 인코딩 방식을 쓰는 곳인데 그 중 사용하지 않겠다는 것이다.
    // 인메모리 방식이다.
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("jason").password("{noop}123").roles("USER").and()
//                .withUser("admin").password("{noop}111").roles("ADMIN");
//    }
}
