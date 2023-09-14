package com.sb.kfk.main.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.sb.kfk.main.constants.AppConstants;

@Service
public class KafkaProducer {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void sendMessage(String topic, String message1, String message2) {
		LOGGER.info(String.format("Message sent -> %s", message1));
		if (topic.equals(AppConstants.TOPIC_NAME1))
			kafkaTemplate.send(AppConstants.TOPIC_NAME1, message1);
		if (topic.equals(AppConstants.TOPIC_NAME2))
			kafkaTemplate.send(AppConstants.TOPIC_NAME1, message2);
	}
}
