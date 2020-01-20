package org.sng.shortener.json;

import org.sng.shortener.exceptions.BusinessError;

public class ErrorResponse extends ResponseBase {

    private final int code;
    private final String level;
    private final int intLevel;

    public ErrorResponse(BusinessError be, String description) {
        super(false, description);
        this.code = be.getCode();
        this.level = be.getLevel().getLevel();
        this.intLevel = be.getLevel().getIntLevel();
    }

    public int getCode() {
        return code;
    }

    public String getLevel() {
        return level;
    }

    public int getIntLevel() {
        return intLevel;
    }
}
