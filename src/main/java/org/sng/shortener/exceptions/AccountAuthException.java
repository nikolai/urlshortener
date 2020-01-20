package org.sng.shortener.exceptions;

import static org.sng.shortener.exceptions.BusinessError.INVALID_AUTH;

public class AccountAuthException extends BusinessFastException {
    public AccountAuthException() {
        super(INVALID_AUTH);
    }
}
