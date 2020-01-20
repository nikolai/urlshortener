package org.sng.shortener.exceptions;

public class InvalidLongUrlException extends BusinessFastException {
    public InvalidLongUrlException(String longUrl) {
        super(BusinessError.INVALID_LONG_URL, longUrl);
    }
}
