package com.raphaelrighetti.binarybluff.chat.services;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raphaelrighetti.binarybluff.chat.dto.AvailableChatDTO;
import com.raphaelrighetti.binarybluff.chat.dto.ChosenChatDTO;

@Service
public class PickChatService {
	
	@Autowired
	private ChatSubscriptionCounterService chatSubscriptionCounterService;
	
	private Random random = new Random();
	private int randomIntRange = 1;
	
	public ChosenChatDTO pickChat() {
		boolean chatWithBot = random.nextInt(randomIntRange) > 0;
		String chatUrlTemplate = "/chat/" + UUID.randomUUID();
		
		if (!chatWithBot) {
			List<AvailableChatDTO> availableChats = chatSubscriptionCounterService.getAvailableChats();
			
			if (!availableChats.isEmpty()) {
				return new ChosenChatDTO(availableChats.get(0).url() + "/b");
			} else {
				return new ChosenChatDTO(chatUrlTemplate + "/a");
			}
		} else {
			System.out.println("Vai conversar com robô!");
			
			return new ChosenChatDTO(chatUrlTemplate + "/bot");
		}
	}

}
