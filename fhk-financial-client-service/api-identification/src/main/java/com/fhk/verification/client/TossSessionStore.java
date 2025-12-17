package com.fhk.verification.client;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TossSessionStore {

    private final Map<String, String> store = new ConcurrentHashMap<>();

    public void save(String txId, String sessionKey) {
        store.put(txId, sessionKey);
    }

    public String get(String txId) {
        return store.get(txId);
    }
}
