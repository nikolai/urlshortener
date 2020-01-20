package org.sng.shortener.json;

import java.util.HashMap;
import java.util.Map;

public class GetStatisticResponse extends HashMap<String, Long> implements Map<String, Long> {
    public GetStatisticResponse(){}

    public GetStatisticResponse(Map<String, Long> accountStats) {
        putAll(accountStats);
    }
}
