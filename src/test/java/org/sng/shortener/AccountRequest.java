package org.sng.shortener;

public class AccountRequest {
    private final String accountId;

    public AccountRequest(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountId() {
        return accountId;
    }
}
