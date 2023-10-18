package com.raphaelrighetti.websocket.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.raphaelrighetti.websocket.models.records.Message;
import com.raphaelrighetti.websocket.services.SessionSubscriptionsService;

@Controller
public class UserChatController {
	
	@Autowired
	private SimpMessagingTemplate brokerMessagingTemplate;
	
	@Autowired
	private SessionSubscriptionsService sessionSubscriptionsService;
	
	@MessageMapping("/message")
	public Message sendMessage(Message message, StompHeaderAccessor accessor) {
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
