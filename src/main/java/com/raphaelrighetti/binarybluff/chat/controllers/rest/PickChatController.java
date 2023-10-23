package com.raphaelrighetti.binarybluff.chat.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raphaelrighetti.binarybluff.chat.dto.ChosenChatDTO;
import com.raphaelrighetti.binarybluff.chat.services.PickChatService;

@RestController
@RequestMapping("/rest/pick-chat")
public class PickChatController {

	@Autowired
	private PickChatService service;
	
	@GetMapping
	public ResponseEntity<ChosenChatDTO> pickAvailableChat() {
		return ResponseEntity.ok(service.pickChat());
	}
	
}
