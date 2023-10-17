package com.raphaelrighetti.websocket.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import com.raphaelrighetti.websocket.models.records.Message;

@Controller
public class GreetingController {
	
	@MessageMapping("/hello")
	@SendTo("/chat/greetings")
	public Message greeting(Message message) throws Exception {
		return new Message(HtmlUtils.htmlEscape(message.content()));
	}

}
