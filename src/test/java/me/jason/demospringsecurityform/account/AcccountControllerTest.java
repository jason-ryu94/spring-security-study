package me.jason.demospringsecurityform.account;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AcccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountService accountService;

    /**
     * with 으로 접근하는 방법
     */

    @Test
    public void index_annoymous() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void index_user() throws Exception {
        mockMvc.perform(get("/").with(user("jason").roles("USER")))
                .andDo(print())
                .andExpect(status().isOk());
    }

    /**
     * 어노테이션으로 유저 정보 설정
     */

    @Test
    @WithAnonymousUser
    public void index_annoymous2() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "jason", roles = "USER")
    public void index_user2() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    /**
     * custom 한 어노테이션으로 만들어서 쓰기
     */


    @Test
    @WtihUser
    public void index_annoymous3() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());

    }


    @Test
    @Transactional
    public void login_success() throws Exception {
        Account user = this.createUser();
        mockMvc.perform(formLogin().user(user.getUsername()).password("123"))
                .andExpect(authenticated());

    }

    @Test
    @Transactional
    public void login_fail() throws Exception {
        Account user = this.createUser();
        mockMvc.perform(formLogin().user(user.getUsername()).password("12345"))
                .andExpect(unauthenticated());

    }

    private Account createUser() {
        Account account = new Account();
        account.setUsername("jason");
        account.setPassword("123");
        account.setRole("USER");
        accountService.createAccount(account);
        return account;
    }
}