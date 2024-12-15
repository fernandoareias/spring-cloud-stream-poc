package com.fernando.spring_cloud_stream;

import com.fernando.spring_cloud_stream.events.ClientCreatedEvent;
import com.fernando.spring_cloud_stream.events.CreditCardCreated;
import com.fernando.spring_cloud_stream.events.ProposalCreatedEvent;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.Joined;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.function.BiFunction;

@Configuration
public class KafkaSimpleKTable {

    private static final Logger logger = LoggerFactory.getLogger(KafkaSimpleKTable.class);

    @Bean
    public BiFunction<KTable<String, com.fernando.spring_cloud_stream.events.ProposalCreatedEvent>, KStream<String, com.fernando.spring_cloud_stream.events.CreditCardCreated>, KStream<String, ClientCreatedEvent>> joinStreams() {
        return (proposalTable, creditCardStream) -> {


            System.out.println("[KafkaSimpleKTable] - join streams events...");

            Serde<com.fernando.spring_cloud_stream.events.CreditCardCreated> creditCardSerde = new SpecificAvroSerde<>();
            Serde<com.fernando.spring_cloud_stream.events.ProposalCreatedEvent> proposalSerde = new SpecificAvroSerde<>();
            Serde<ClientCreatedEvent> clientCreatedSerde = new SpecificAvroSerde<>();

            Map<String, String> serdeConfig = Map.of("schema.registry.url", "http://localhost:8081");
            creditCardSerde.configure(serdeConfig, false);
            proposalSerde.configure(serdeConfig, false);
            clientCreatedSerde.configure(serdeConfig, false);

            // Realizar o join
            return creditCardStream.leftJoin(
                    proposalTable,
                    (creditCard, proposal) -> {
                        logger.info("Joining CreditCard {} with Proposal {}", creditCard, proposal);
                        if (creditCard != null && proposal != null) {
                            ClientCreatedEvent event = new ClientCreatedEvent();
                            event.setKey(creditCard.getPortadorDocument().toString());
                            event.setCardNumber(creditCard.getCardNumber().toString());
                            event.setProposalNumber(proposal.getProposalNumber().toString());
                            event.setDocument(creditCard.getPortadorDocument().toString());
                            event.setProduct(proposal.getProduct().toString());
                            return event;
                        }
                        return null;
                    },
                    Joined.with(
                            Serdes.String(),  // Serde para a chave
                            creditCardSerde,  // Serde para os valores do stream
                            proposalSerde     // Serde para os valores da tabela
                    )
            );
        };
    }
}
