package com.sb.kfk.main.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfig {

	private static final String JAVAGUIDES_1 = "javaguides1";
	private static final String JAVAGUIDES_2 = "javaguides2";

	@Bean
	public NewTopic javaguidesTopic1() {
		return TopicBuilder.name(JAVAGUIDES_1).build();
	}
	
	@Bean
	public NewTopic javaguidesTopic2() {
		return TopicBuilder.name(JAVAGUIDES_2).build();
	}
}
