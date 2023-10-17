package com.raphaelrighetti.websocket.interceptors;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import com.raphaelrighetti.websocket.services.SessionSubscriptionsService;
import com.raphaelrighetti.websocket.services.ChatSubscriptionCounterService;

@Component
public class MessageInterceptor implements ChannelInterceptor {
	
	@Autowired
	private ChatSubscriptionCounterService topicSubscriptionCounterService;
	
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
			sessionSubscriptionsService.add(accessor.getSessionId(), accessor.getDestination());
			topicSubscriptionCounterService.increment(accessor.getDestination());
		}
	}
	
	private void handleDisconnect(StompHeaderAccessor accessor, StompCommand command) {
		if (command.name().equals(StompCommand.DISCONNECT.name())) {
			List<String> topics = sessionSubscriptionsService.remove(accessor.getSessionId());
			
			topics.forEach(topic -> {
	        	topicSubscriptionCounterService.decrement(topic);
	        });
		}
	}
	
}
