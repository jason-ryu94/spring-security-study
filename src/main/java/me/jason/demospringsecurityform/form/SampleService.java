package me.jason.demospringsecurityform.form;

import me.jason.demospringsecurityform.account.Account;
import me.jason.demospringsecurityform.account.AccountContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SampleService {

    public void dashboard() {
        // AccountContext에 들어있는 것을 사용할 수 있다.
        Account account = AccountContext.getAccount();
        System.out.println("==================");
        System.out.println("account = " + account);


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        // 사용자의 권한을 나타낸다.
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        Object credentials = authentication.getCredentials();
        boolean authenticated = authentication.isAuthenticated();
    }
}
