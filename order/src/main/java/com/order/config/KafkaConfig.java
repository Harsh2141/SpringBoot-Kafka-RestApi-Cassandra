package com.order.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;


@Configuration
@EnableScheduling
@EnableKafka
public class KafkaConfig {

	@Value("${kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Bean
	public Map<String, Object> producerConfig() {
		Map<String, Object> props = new HashMap<>();
		
		//list of server
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		
		//default byte array
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		
		//0 1 -1 
		//0 only sent message not wait for acknolage met
		//1 leader write kare and directly sent response
		//-1 wait for all replicate respose 
		props.put(ProducerConfig.ACKS_CONFIG,"1");
		
		props.put(ProducerConfig.RETRIES_CONFIG,3);
		props.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG,5000);
		
		//set client id ProducerConfig.CLIENT_ID_CONFIG
		//set time out for socket connection ProducerConfig.CONNECTIONS_MAX_IDLE_MS_CONFIG
		
		//set compression  type by set ProducerConfig.COMPRESSION_TYPE_CONFIG (gzip,lz4,snappy,gstd) full batch compression
		//set batch size ProducerConfig.BATCH_SIZE_CONFIG sent message by batch
		//set buffer memory ProducerConfig.BUFFER_MEMORY_CONFIG it is use for make batch and all thing stiore in ram it is called buffer memory
		//set ProducerConfig.LINGER_MS_CONFIG wait for make batch
		//set ProducerConfig.MAX_REQUEST_SIZE_CONFIG
		
		return props;
	}

	@Bean
	public ProducerFactory<String, String> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfig());
	}

	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}
}
