package org.sng.shortener.exceptions;

public class BusinessFastException extends Exception {

    private BusinessError businessError;

    public BusinessFastException(BusinessError error, Object... messageArgs) {
        super(error.format(messageArgs), null, false, false);
        businessError = error;
    }

    public BusinessError getBusinessError() {
        return businessError;
    }
}
