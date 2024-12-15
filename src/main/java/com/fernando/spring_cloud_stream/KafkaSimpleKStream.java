package com.fernando.spring_cloud_stream;


import com.fernando.spring_cloud_stream.events.ClientCreatedEvent;
import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class KafkaSimpleKStream {

    private static final Logger logger = LoggerFactory.getLogger(KafkaSimpleKStream.class);

    @Bean
    public Function<KStream<String, ClientCreatedEvent>, KStream<String, ClientCreatedEvent>[]> processClientEvents() {
        return inputStream -> {
            System.out.println("[KafkaSimpleKStream] - processing client events...");
            KStream<String, ClientCreatedEvent>[] branches = inputStream.branch(
                    (key, event) -> event != null && "C".equals(event.getProduct().toString()), // Consignado
                    (key, event) -> event != null && "P".equals(event.getProduct().toString())  // Private
            );

            branches[0].peek((key, event) ->
                    logger.info("Consignado Event: Key={}, Value={}", key, event));
            branches[1].peek((key, event) ->
                    logger.info("Private Event: Key={}, Value={}", key, event));

            return branches;
        };
    }
}