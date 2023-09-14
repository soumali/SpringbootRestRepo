package com.sb.kfk.main.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.sb.kfk.main.constants.AppConstants;

@Service
public class KafkaConsumer {
	private Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

	@KafkaListener(topics = { AppConstants.TOPIC_NAME1, AppConstants.TOPIC_NAME2 }, groupId = AppConstants.GROUP_ID)
	public void consume1(String message) {
		LOGGER.info("message received" + message);
	}
}
	