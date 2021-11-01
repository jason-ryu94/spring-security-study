package me.jason.demospringsecurityform.form;

import me.jason.demospringsecurityform.account.Account;
import me.jason.demospringsecurityform.account.AccountContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SampleService {

    public void dashboard() {
        // AccountContext에 들어있는 것을 사용할 수 있다.
        // 아래의 SecurityContextHolder가 ThreadLocal 을 사용해서 같은 인스턴스를 사용하는 방법이다.
//        Account account = AccountContext.getAccount();
//        System.out.println("==================");
//        System.out.println("account = " + account);


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        System.out.println("=============================");
        System.out.println("authentication = " + authentication);
        System.out.println(userDetails.getUsername());
        Object credentials = authentication.getCredentials();
        boolean authenticated = authentication.isAuthenticated();
    }
}
