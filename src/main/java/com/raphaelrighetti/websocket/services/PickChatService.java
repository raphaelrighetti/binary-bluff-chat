package com.raphaelrighetti.websocket.services;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raphaelrighetti.websocket.models.records.AvailableChat;
import com.raphaelrighetti.websocket.models.records.ChosenChat;

@Service
public class PickChatService {
	
	@Autowired
	private ChatSubscriptionCounterService chatSubscriptionCounterService;
	
	private Random random = new Random();
	private int randomIntRange = 1;
	
	public ChosenChat pickChat() {
		boolean chatWithBot = random.nextInt(randomIntRange) > 0;
		String chatUrlTemplate = "/chat/" + UUID.randomUUID();
		
		if (!chatWithBot) {
			List<AvailableChat> availableChats = chatSubscriptionCounterService.getAvailableChats();
			
			if (!availableChats.isEmpty()) {
				return new ChosenChat(availableChats.get(0).url() + "/b");
			} else {
				return new ChosenChat(chatUrlTemplate + "/a");
			}
		} else {
			System.out.println("Vai conversar com robô!");
			
			return new ChosenChat(chatUrlTemplate + "/bot");
		}
	}

}
