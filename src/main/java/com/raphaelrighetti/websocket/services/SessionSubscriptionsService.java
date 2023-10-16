package com.raphaelrighetti.websocket.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class SessionSubscriptionsService {
	
	private ConcurrentHashMap<String, List<String>> sessionSubscriptions = new ConcurrentHashMap<>();
	
	public void add(String sessionId, String topic) {
		if (getSubscriptions(sessionId).isEmpty()) {
			sessionSubscriptions.put(sessionId, List.of(topic));
		} else {
			getSubscriptions(sessionId).add(topic);
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
