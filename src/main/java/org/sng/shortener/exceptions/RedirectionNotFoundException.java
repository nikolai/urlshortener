package org.sng.shortener.exceptions;

public class RedirectionNotFoundException extends BusinessFastException {
    public RedirectionNotFoundException() {
        super(BusinessError.REDIRECTION_NOT_FOUND);
    }
}
