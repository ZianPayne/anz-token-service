package com.zianpayne.tokenization.testutil;

import com.zianpayne.tokenization.domain.model.AccountNumber;
import com.zianpayne.tokenization.domain.model.AccountNumbers;
import com.zianpayne.tokenization.domain.model.Token;
import com.zianpayne.tokenization.domain.model.Tokens;

import java.util.Arrays;
import java.util.List;

public final class TokenHelper {
    public static final String DEFAULT_TOKEN = "A1234567890123456789012345678901";
    public static final long DEFAULT_ACCOUNT = 4_111_111_111_111_111L;

    private TokenHelper() {
    }

    public static Tokens domainTokens(String... values) {
        return new Tokens(Arrays.stream(values)
                .map(Token::new)
                .toList());
    }

    public static AccountNumbers domainAccountNumbers(long... values) {
        List<AccountNumber> accounts = Arrays.stream(values)
                .mapToObj(AccountNumber::new)
                .toList();
        return new AccountNumbers(accounts);
    }
}
