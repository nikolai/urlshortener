package org.sng.shortener.services;

import org.springframework.stereotype.Service;

@Service
public class RandomGeneratorService {
    private final char[] allowedSymbols = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

    public String generateString(int size) {
        char[] passw = new char[size];
        int left = 0;
        int right = allowedSymbols.length;
        for (int i=0; i<size; i++) {
            passw[i] = allowedSymbols[(int)(left + Math.random() * (right - left))];
        }
        return new String(passw);
    }

}
