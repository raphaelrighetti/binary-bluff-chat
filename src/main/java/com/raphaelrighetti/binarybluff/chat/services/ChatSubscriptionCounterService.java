package com.raphaelrighetti.binarybluff.chat.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.raphaelrighetti.binarybluff.chat.models.ChatSubscriptionCounter;
import com.raphaelrighetti.binarybluff.chat.models.records.AvailableChat;

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
    
    public List<AvailableChat> getAvailableChats() {
    	return chatSubscriptionCounter.getAvailableChats();
    }

}
