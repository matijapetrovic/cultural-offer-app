package cultureapp.common;

import cultureapp.domain.account.Account;
import cultureapp.domain.authority.Authority;
import cultureapp.domain.user.RegularUser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class RegularUserTestData {

    public static final Long VALID_REGULAR_USER_ID = 1L;
    public static final String VALID_REGULAR_USER_FIRST_NAME = "John";
    public static final String VALID_REGULAR_USER_LAST_NAME = "Doe";

    public static final Long VALID_REGULAR_USER_ACCOUNT_ID = 1L;
    public static final String VALID_REGULAR_USER_ACCOUNT_EMAIL = "user1@gmail.com";
    public static final String VALID_REGULAR_USER_ACCOUNT_PASSWORD = "abcabc123";

    public static Account validRegularUserAccount() {
        return Account.withId(
                VALID_REGULAR_USER_ACCOUNT_ID,
                VALID_REGULAR_USER_ACCOUNT_EMAIL,
                VALID_REGULAR_USER_ACCOUNT_PASSWORD,
                true,
                List.of(Authority.of("ROLE_USER")));
    }

    public static RegularUser validRegularUserWithAccount(Account account) {
        return RegularUser.withId(
                VALID_REGULAR_USER_ID,
                VALID_REGULAR_USER_FIRST_NAME,
                VALID_REGULAR_USER_LAST_NAME,
                account,
                new HashSet<>());
    }
}
