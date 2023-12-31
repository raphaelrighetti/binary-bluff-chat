package com.raphaelrighetti.binarybluff.chat.interceptors;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import com.raphaelrighetti.binarybluff.chat.services.ChatSubscriptionCounterService;
import com.raphaelrighetti.binarybluff.chat.services.SessionSubscriptionsService;

@Component
public class MessageInterceptor implements ChannelInterceptor {
	
	@Autowired
	private ChatSubscriptionCounterService chatSubscriptionCounterService;
	
	@Autowired
	private SessionSubscriptionsService sessionSubscriptionsService;
	
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
			System.out.println(accessor.getSessionId() + 
					" se inscrevendo em: " + accessor.getDestination());
			
			sessionSubscriptionsService.add(accessor.getSessionId(), accessor.getDestination());
			chatSubscriptionCounterService.increment(accessor.getDestination());
		}
	}
	
	private void handleDisconnect(StompHeaderAccessor accessor, StompCommand command) {
		if (command.name().equals(StompCommand.DISCONNECT.name())) {
			System.out.println(accessor.getSessionId() + " desconectando!");
			
			List<String> chats = sessionSubscriptionsService.remove(accessor.getSessionId());
			
			chats.forEach(chat -> {
	        	chatSubscriptionCounterService.decrement(chat);
	        });
		}
	}
	
}
