package org.sng.shortener.exceptions;

import static org.sng.shortener.exceptions.BusinessError.ACCESS_DENIED;

public class AccessDenied extends BusinessFastException {
    public AccessDenied() {
        super(ACCESS_DENIED,ACCESS_DENIED.format());
    }
}
