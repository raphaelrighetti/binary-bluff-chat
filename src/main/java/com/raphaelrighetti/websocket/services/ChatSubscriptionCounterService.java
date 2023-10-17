package com.raphaelrighetti.websocket.services;

import org.springframework.stereotype.Service;

import com.raphaelrighetti.websocket.models.ChatSubscriptionCounter;

@Service
public class ChatSubscriptionCounterService {
	
	private ChatSubscriptionCounter chatSubscriptionCounter = new ChatSubscriptionCounter();
	
	public void increment(String chat) {
        chatSubscriptionCounter.increment(chat);
    }
	
	public void decrement(String chat) {
        chatSubscriptionCounter.decrement(chat);
    }

    public int getCount(String chat) {
        return chatSubscriptionCounter.getCount(chat);
    }

}
