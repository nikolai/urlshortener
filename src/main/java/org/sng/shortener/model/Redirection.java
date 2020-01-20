package org.sng.shortener.model;

import java.net.URL;
import java.util.concurrent.atomic.AtomicLong;

public class Redirection {
    private final URL longUrl;
    private final AllowedRedirect redirectType;
    private String accountId;
    private final AtomicLong hitCount = new AtomicLong();

    public Redirection(URL longUrl, AllowedRedirect redirectType, String accountId) {
        this.longUrl = longUrl;
        this.redirectType = redirectType;
        this.accountId = accountId;
    }

    public String getLongUrl() {
        return longUrl.toString();
    }

    public int getRedirectType() {
        return redirectType.getCode();
    }

    public void incrementHit() {
        hitCount.incrementAndGet();
    }

    public long getHitCount() {
        return hitCount.get();
    }

    public String getAccountId() {
        return accountId;
    }
}