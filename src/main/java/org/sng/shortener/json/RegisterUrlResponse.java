package org.sng.shortener.json;

public class RegisterUrlResponse {
    private final String shortUrl;

    public RegisterUrlResponse(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }
}
