package org.sng.shortener.exceptions;

import java.util.HashSet;
import java.util.Set;

public class RegisteredErrorCodes {
    private static final Set<Integer> REGISTERED_ERROR_CODES = new HashSet<>();
    public static void checkDuplicated(int code) {
        if (!REGISTERED_ERROR_CODES.add(code)) {
            throw new IllegalStateException("Duplicated error code " + code);
        }
    }
}
