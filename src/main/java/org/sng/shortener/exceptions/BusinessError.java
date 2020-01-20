package org.sng.shortener.exceptions;


import org.springframework.http.HttpStatus;

import java.util.logging.Level;

public enum BusinessError {
    ACCOUNT_ALREADY_EXISTS  (153, ErrorLevel.WARN,  HttpStatus.CONFLICT, "Account %s already exists. Try another name."),
    INVALID_LONG_URL        (154, ErrorLevel.FATAL, HttpStatus.UNPROCESSABLE_ENTITY, "Invalid URL %s"),
    INVALID_REDIRECT_TYPE   (155, ErrorLevel.FATAL, HttpStatus.UNPROCESSABLE_ENTITY, "Invalid redirect type %d."),
    INVALID_AUTH            (156, ErrorLevel.FATAL, HttpStatus.UNAUTHORIZED, "Invalid login or password"),
    REDIRECTION_NOT_FOUND   (157, ErrorLevel.WARN,  HttpStatus.NOT_FOUND, "Redirection not found. Sorry."),
    ACCESS_DENIED           (158, ErrorLevel.WARN,  HttpStatus.FORBIDDEN, "Access denied. You are not allowed to see this info."),
    SYSTEM_ERROR            (333, ErrorLevel.FATAL, HttpStatus.INTERNAL_SERVER_ERROR, "System error (%d). Try again later."),
    ;

    private int code;
    private HttpStatus httpStatus;
    private final ErrorLevel level;
    private final String template;

    BusinessError(int code, ErrorLevel level, HttpStatus httpStatus, String template) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.level = level;
        this.template = template;

        RegisteredErrorCodes.checkDuplicated(code);
    }

    public String format(Object... messageArgs) {
        return String.format(template, messageArgs);
    }

    public ErrorLevel getLevel() {
        return level;
    }

    public int getCode() {
        return code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public enum ErrorLevel {
        FATAL(Level.SEVERE.intValue()),
        WARN(Level.WARNING.intValue()),
        ;
        private int intLevel;
        ErrorLevel(int intLevel) {
            this.intLevel = intLevel;
        }

        public int getIntLevel() {
            return intLevel;
        }

        public String getLevel() {
            return this.name();
        }
    }
}
