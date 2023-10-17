package com.raphaelrighetti.websocket.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.raphaelrighetti.websocket.models.SessionSubscriptions;

@Service
public class SessionSubscriptionsService {
	
	private SessionSubscriptions sessionSubscriptions = new SessionSubscriptions();
	
	public void add(String sessionId, String chat) {
		sessionSubscriptions.add(sessionId, chat);
    }
	
	public List<String> remove(String sessionId) {
		return sessionSubscriptions.remove(sessionId);
    }

    public List<String> getSubscriptions(String sessionId) {
        return sessionSubscriptions.getSubscriptions(sessionId);
    }

}
