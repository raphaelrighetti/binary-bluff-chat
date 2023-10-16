package com.raphaelrighetti.websocket.services;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class TopicSubscriptionCounterService {
	
	private ConcurrentHashMap<String, Integer> topicCounts = new ConcurrentHashMap<>();
	
	public void increment(String topic) {
        topicCounts.put(topic, topicCounts.getOrDefault(topic, 0) + 1);
        
        System.out.println(getCount(topic));
    }
	
	public void decrement(String topic) {
        topicCounts.put(topic, topicCounts.getOrDefault(topic, 0) - 1);
        
        System.out.println(getCount(topic));
    }

    public int getCount(String topic) {
        return topicCounts.getOrDefault(topic, 0);
    }

}
