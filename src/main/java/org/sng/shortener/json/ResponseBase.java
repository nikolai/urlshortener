package org.sng.shortener.json;

public abstract class ResponseBase {

    private final boolean success;
    private final String description;

    public ResponseBase(boolean success, String description) {
        this.success = success;
        this.description = description;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getDescription() {
        return description;
    }
}