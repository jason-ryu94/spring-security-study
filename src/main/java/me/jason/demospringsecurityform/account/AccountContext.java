package me.jason.demospringsecurityform.account;


/**
 * ThreadLocal 에 넣고 뺄 수 있는 간단한 구조를 만든다.
 * SecurityContextHolder가 SecurityContext를 어떻게 가지고 있을거냐에 대한 기본적인 전략인다.
 */
public class AccountContext {

    private static final ThreadLocal<Account> ACCOUNT_THREAD_LOCAL
            = new ThreadLocal<>();

    public static void setAccount(Account account) {
        ACCOUNT_THREAD_LOCAL.set(account);
    }

    public static Account getAccount() {
        return ACCOUNT_THREAD_LOCAL.get();
    }
}
