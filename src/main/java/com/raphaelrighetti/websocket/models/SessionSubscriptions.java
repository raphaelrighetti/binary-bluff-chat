package com.raphaelrighetti.websocket.models;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class SessionSubscriptions {

	private ConcurrentHashMap<String, List<String>> sessionSubscriptions = new ConcurrentHashMap<>();
	
	public void add(String sessionId, String chat) {
		if (getSubscriptions(sessionId).isEmpty()) {
			sessionSubscriptions.put(sessionId, List.of(chat));
		} else {
			getSubscriptions(sessionId).add(chat);
		}
    }
	
	public List<String> remove(String sessionId) {
		if (!getSubscriptions(sessionId).isEmpty()) {
			return sessionSubscriptions.remove(sessionId);
		} else {
			return new ArrayList<>();
		}
    }

    public List<String> getSubscriptions(String sessionId) {
        return sessionSubscriptions.getOrDefault(sessionId, new ArrayList<>());
    }
	
}
