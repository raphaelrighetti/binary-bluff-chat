package com.raphaelrighetti.websocket.interceptors;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import com.raphaelrighetti.websocket.services.SessionSubscriptionsService;
import com.raphaelrighetti.websocket.models.records.AvailableChat;
import com.raphaelrighetti.websocket.services.ChatSubscriptionCounterService;

@Component
public class MessageInterceptor implements ChannelInterceptor {
	
	@Autowired
	private ChatSubscriptionCounterService chatSubscriptionCounterService;
	
	@Autowired
	private SessionSubscriptionsService sessionSubscriptionsService;
	
	private int randomIntRange = 2;
	
	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
		StompCommand command = accessor.getCommand();
		
		handleSubscribe(accessor, command);
		handleDisconnect(accessor, command);
		
		return message;
	}
	
	private void handleSubscribe(StompHeaderAccessor accessor, StompCommand command) {
		if (command.name().equals(StompCommand.SUBSCRIBE.name())) {
			Random random = new Random();
			
			boolean chatWithBot = random.nextInt(randomIntRange) > 0;
			
			if (chatWithBot) {
				System.out.println("Vai conversar com robô!");
				
				accessor.setDestination(accessor.getDestination() + "/" + accessor.getSessionId() + "/bot");
			} else {
				List<AvailableChat> availableChats = chatSubscriptionCounterService.getAvailableChats();
				
				if (!availableChats.isEmpty()) {
					AvailableChat chat = availableChats.get(0);
					String destination = chat.url() + "/b";
					
					accessor.setDestination(destination);
				} else {
					accessor.setDestination(accessor.getDestination() + "/" + UUID.randomUUID() + "/a");
				}
			}
			
			System.out.println(accessor.getDestination());
			
			sessionSubscriptionsService.add(accessor.getSessionId(), accessor.getDestination());
			chatSubscriptionCounterService.increment(accessor.getDestination());
		}
	}
	
	private void handleDisconnect(StompHeaderAccessor accessor, StompCommand command) {
		if (command.name().equals(StompCommand.DISCONNECT.name())) {
			System.out.println("Vai desconectar!");
			
			List<String> chats = sessionSubscriptionsService.remove(accessor.getSessionId());
			
			chats.forEach(chat -> {
	        	chatSubscriptionCounterService.decrement(chat);
	        });
		}
	}
	
}
