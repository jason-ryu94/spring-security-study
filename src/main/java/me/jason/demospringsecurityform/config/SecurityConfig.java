package me.jason.demospringsecurityform.config;

import me.jason.demospringsecurityform.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AccountService accountService;

    // securityConfig가 하나의 필터 역할을 한다.
    // antMatchers 가 체인을 만드는 역할을 한다.
    // @Order 를 통해 순서를 줄 수 있다.
    // filterChainProxy까지 들어오는 과정에 대한 부분

    // 처음 요청이 들어오면 servletContainer가 잡는다.

    /**
     * 서블릿필터 처리를 스프링에 있는 빈으로 위임하고 싶을 때 사용하는 서블릿 필터
     * 스프링 부트는 자동으로 등록된다.
     * 스프링 부트 없이는 AbstractSecurityWebApplicationInitializer 를 사용해서 등록
     *
     * 권한 사용을 담당하는 부분은 : AccessDecisionManager(인가)
     *
     * */

    /**
     * role의 hirerachy 를 지정하기 위한 방법
     */
    public AccessDecisionManager accessDecisionManager() {

        // 롤의 계층을 설정해주는 것
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");

        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setRoleHierarchy(roleHierarchy);


        /**
         * accessDecisionManager를 커스텀하는 방식이고,
         * ExpressionHandler를 커스텀하는 방식은 아래의 과정이 필요없다.
         * configure에 .expressionHandler를 넣어주면된다.
         * voter가 사용하는 핸들러만 커스텀 해준 것이다.
         */
        WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
        webExpressionVoter.setExpressionHandler(handler);

        List<AccessDecisionVoter<? extends Object>> voters = Arrays.asList(webExpressionVoter);

        return new AffirmativeBased(voters);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 요청에 대한 인가 설정
        http.authorizeRequests()
                .mvcMatchers("/", "/info", "/account/**").permitAll()
                .mvcMatchers("/admin").hasRole("ADMIN")
                .mvcMatchers("/user").hasRole("USER")
                .accessDecisionManager(accessDecisionManager())
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
