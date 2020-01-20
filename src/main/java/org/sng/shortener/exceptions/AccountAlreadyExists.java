package org.sng.shortener.exceptions;

public class AccountAlreadyExists extends BusinessFastException {
    public AccountAlreadyExists(String accountId) {
        super(BusinessError.ACCOUNT_ALREADY_EXISTS, accountId);
    }
}
