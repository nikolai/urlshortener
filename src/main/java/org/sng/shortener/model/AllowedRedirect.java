package org.sng.shortener.model;

import org.sng.shortener.exceptions.InvalidRedirectType;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Optional;

public enum AllowedRedirect {
    PERMANENTLY_301(HttpStatus.MOVED_PERMANENTLY.value()),
    @SuppressWarnings("deprecation")
    TEMPORARILY_302(HttpStatus.MOVED_TEMPORARILY.value()),
    ;

    private int code;

    AllowedRedirect(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static AllowedRedirect fromCode(int code) throws InvalidRedirectType {
        Optional<AllowedRedirect> found = Arrays.stream(values()).filter(r -> r.code == code).findFirst();
        if (found.isPresent()) {
            return found.get();
        }
        throw new InvalidRedirectType(code);
    }

}
