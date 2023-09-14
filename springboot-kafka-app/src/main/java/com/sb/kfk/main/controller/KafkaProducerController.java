package com.sb.kfk.main.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sb.kfk.main.producer.KafkaProducer;

public class KafkaProducerController {
	private KafkaProducer kafkaProducer;

	public KafkaProducerController(KafkaProducer producer) {
		this.kafkaProducer = producer;
	}

	@GetMapping("/publish")
	public ResponseEntity<String> publish(@RequestParam("topic") String topic,
			@RequestParam("message1") String message1, @RequestParam("message2") String messag2) {
		kafkaProducer.sendMessage(topic, message1, messag2);
		return ResponseEntity.ok("Message sent to topic");
	}

}
