package org.sng.shortener.services;

import org.sng.shortener.exceptions.InvalidLongUrlException;
import org.sng.shortener.exceptions.InvalidRedirectType;
import org.sng.shortener.model.AllowedRedirect;
import org.sng.shortener.model.Redirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class ShortenerService {
    private static final int URL_KEY_SIZE = 6;
    private final Map<String, Redirection> redirectionMap = new ConcurrentHashMap<>();

    @Autowired
    private RandomGeneratorService generatorService;

    @Autowired
    private ConfigurationService configurationService;

    public String shorten(String longUrl, int redirectType, String accountId) throws InvalidRedirectType, InvalidLongUrlException{
        try {
            Redirection redirection = new Redirection(new URL(longUrl), AllowedRedirect.fromCode(redirectType), accountId);
            return configurationService.getServiceUrl() + shorten(URL_KEY_SIZE, redirection);
        } catch (MalformedURLException e) {
            throw new InvalidLongUrlException(longUrl);
        }
    }

    private String shorten(int urlKeySize, Redirection redirection) {
        String shortUrlKey = generatorService.generateString(urlKeySize);
        if (redirectionMap.putIfAbsent(shortUrlKey, redirection) == null) {
            return shortUrlKey;
        }
        return shorten(urlKeySize+1, redirection);
    }

    public Redirection find(String shortUrl) {
        Redirection redirection = redirectionMap.get(shortUrl);
        if (redirection != null) {
            redirection.incrementHit();
        }
        return redirection;
    }

    public Map<String, Long> getStats(String accountId) {
        return redirectionMap.values().stream()
                .filter(r->r.getAccountId().equals(accountId))
                .collect(Collectors.groupingBy(Redirection::getLongUrl, Collectors.summingLong(Redirection::getHitCount)));
    }
}
