package com.shipment.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.scheduling.annotation.EnableScheduling;


@Configuration
@EnableScheduling
@EnableKafka
public class KafkaConfig {

	@Value("${kafka.bootstrap-servers}")
	private String bootstrapServers;
	

	@Bean
	public Map<String, Object> consumerConfig() {
		Map<String, Object> props = new HashMap<>();
		//lsit of server
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		
		//value DESERIALIZER stream of byte into object
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		
		//set client id ConsumerConfig.CLIENT_ID_CONFIG use to trace request mainly use to debug
		//ConsumerConfig.GROUP_ID_CONFIG uniquely identify
		//ConsumerConfig.FETCH_MIN_BYTES_CONFIG fetch minimal data if that data not present then wait for it
		// default 3s ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG send consumer hart beat  to sent coordinator (broker) it means that consumer is alive
		// default 10s ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG time out hart beat not set consumer will be daed and consumer sent out from group 
		// ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG maximum byte fetch karvana
		// ConsumerConfig.DEFAULT_FETCH_MAX_BYTES max ketala kari sake
		
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		return props;
	}

	@Bean
	public ConsumerFactory<String, String> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>(consumerConfig());
	}

	@Bean
	public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}
	
}
