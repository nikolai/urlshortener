package org.sng.shortener.exceptions;

public class InvalidRedirectType extends BusinessFastException {
    public InvalidRedirectType(int redirectType) {
        super(BusinessError.INVALID_REDIRECT_TYPE, redirectType);
    }
}
