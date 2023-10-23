package com.raphaelrighetti.binarybluff.chat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.raphaelrighetti.binarybluff.chat.dto.MessageDTO;
import com.raphaelrighetti.binarybluff.chat.services.SessionSubscriptionsService;

import jakarta.validation.Valid;

@Controller
public class UserChatController {
	
	@Autowired
	private SimpMessagingTemplate brokerMessagingTemplate;
	
	@Autowired
	private SessionSubscriptionsService sessionSubscriptionsService;
	
	@MessageMapping("/message")
	public MessageDTO sendMessage(@Valid MessageDTO message, StompHeaderAccessor accessor) {
		String chat = 
				sessionSubscriptionsService.getSubscriptions(accessor.getSessionId()).get(0);
		
		if (chat.endsWith("/a")) {
			chat = chat.replace("/a", "/b");
		} else {
			chat = chat.replace("/b", "/a");
		}
		
		System.out.println(message.content());
		System.out.println(chat);
		
		brokerMessagingTemplate.convertAndSend(chat, message);
		
		return message;
	}

}
