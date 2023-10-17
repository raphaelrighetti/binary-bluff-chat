package com.raphaelrighetti.websocket.models;

import java.util.concurrent.ConcurrentHashMap;

public class ChatSubscriptionCounter {

	private ConcurrentHashMap<String, Integer> topicCounts = new ConcurrentHashMap<>();
	
	public void increment(String chat) {
        topicCounts.put(chat, topicCounts.getOrDefault(chat, 0) + 1);
    }
	
	public void decrement(String chat) {
        topicCounts.put(chat, topicCounts.getOrDefault(chat, 0) - 1);
    }

    public int getCount(String chat) {
        return topicCounts.getOrDefault(chat, 0);
    }

	
}
