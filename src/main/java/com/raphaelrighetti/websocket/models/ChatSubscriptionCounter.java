package com.raphaelrighetti.websocket.models;

import java.util.concurrent.ConcurrentHashMap;

public class ChatSubscriptionCounter {

	private ConcurrentHashMap<String, Integer> chatCounts = new ConcurrentHashMap<>();
	
	public void increment(String chat) {
        chatCounts.put(chat, chatCounts.getOrDefault(chat, 0) + 1);
    }
	
	public void decrement(String chat) {
		int count = chatCounts.getOrDefault(chat, 0);
		
		if (count == 0) {
			chatCounts.remove(chat);
		} else {
			chatCounts.put(chat, count - 1);
		}
    }

    public int getCount(String chat) {
        return chatCounts.getOrDefault(chat, 0);
    }

	
}
