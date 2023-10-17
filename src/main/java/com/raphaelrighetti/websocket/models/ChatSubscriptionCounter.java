package com.raphaelrighetti.websocket.models;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.raphaelrighetti.websocket.models.records.AvailableChat;

public class ChatSubscriptionCounter {

	private ConcurrentHashMap<String, Integer> chatCounts = new ConcurrentHashMap<>();
	
	public void increment(String chat) {
        chatCounts.put(chat, chatCounts.getOrDefault(chat, 0) + 1);
    }
	
	public void decrement(String chat) {
		chatCounts.put(chat, chatCounts.getOrDefault(chat, 0) - 1);
		
		if (chatCounts.getOrDefault(chat, 0) <= 0) {
			chatCounts.remove(chat);
		}
    }

    public int getCount(String chat) {
        return chatCounts.getOrDefault(chat, 0);
    }
    
    public List<AvailableChat> getAvailableChats() {
    	List<AvailableChat> availableChats = new ArrayList<>();
    	
    	chatCounts.forEach((chat, count) -> {
    		AvailableChat availableChat = new AvailableChat(chat, count);
    		
    		availableChats.add(availableChat);
    	});
    	
    	List<AvailableChat> sortedChats = availableChats.stream()
    		.filter(chat -> chat.subscriptionCount() < 2)
    		.filter(chat -> {
    			Pattern pattern = Pattern.compile("/bot");
    			Matcher matcher = pattern.matcher(chat.url());
    			
    			return !matcher.find();
    		})
    		.sorted((c1, c2) -> {
    			return Integer.compare(c1.subscriptionCount(), c2.subscriptionCount());
    		})
    		.toList();
    	
    	System.out.println(sortedChats);
    	
    	return sortedChats;
    }
	
}
