//package com.fernando.spring_cloud_stream;
//
//import org.apache.kafka.common.serialization.Serdes;
//import org.apache.kafka.streams.KeyValue;
//import org.apache.kafka.streams.kstream.KStream;
//import org.apache.kafka.streams.kstream.KTable;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//
//@Component("process")
//public class SimpleConsumer implements java.util.function.Consumer<KStream<Object, String>> {
//
//    @Override
//    public void accept(KStream<Object, String> input) {
////        input
////                .flatMapValues(value -> Arrays.asList(value.toLowerCase().split("\\W+")))
////                .map((key, value) -> new KeyValue<>(value, value))
////                .groupByKey(Serialized.with(Serdes.String(), Serdes.String()))
////                .windowedBy(TimeWindows.of(5000))
////                .count(Materialized.as("word-counts-state-store"))
////                .toStream()
////                .map((key, value) -> new KeyValue<>(key.key(), new WordCount(key.key(), value,
////                        new Date(key.window().start()), new Date(key.window().end()))));
//    }
//
////
////    @Bean
////    public BiFunction<KStream<String, Long>, KTable<String, String>, KStream<String, Long>> process() {
////        return (userClicksStream, userRegionsTable) -> (userClicksStream
////                .leftJoin(userRegionsTable, (clicks, region) -> new RegionWithClicks(region == null ?
////                                "UNKNOWN" : region, clicks),
////                        Joined.with(Serdes.String(), Serdes.Long(), null))
////                .map((user, regionWithClicks) -> new KeyValue<>(regionWithClicks.getRegion(),
////                        regionWithClicks.getClicks()))
////                .groupByKey(Grouped.with(Serdes.String(), Serdes.Long()))
////                .reduce(Long::sum)
////                .toStream());
////    }
//
//
//
////    @Bean
////    public Function<KStream<Long, Order>,
////            Function<GlobalKTable<Long, Customer>,
////                    Function<GlobalKTable<Long, Product>, KStream<Long, EnrichedOrder>>>> enrichOrder() {
////
////        return orders -> (
////                customers -> (
////                        products -> (
////                                orders.join(customers,
////                                                (orderId, order) -> order.getCustomerId(),
////                                                (order, customer) -> new CustomerOrder(customer, order))
////                                        .join(products,
////                                                (orderId, customerOrder) -> customerOrder
////                                                        .productId(),
////                                                (customerOrder, product) -> {
////                                                    EnrichedOrder enrichedOrder = new EnrichedOrder();
////                                                    enrichedOrder.setProduct(product);
////                                                    enrichedOrder.setCustomer(customerOrder.customer);
////                                                    enrichedOrder.setOrder(customerOrder.order);
////                                                    return enrichedOrder;
////                                                })
////                        )
////                )
////        );
////    }
//
//
//
//
//    // multiplas saidas
////    @Bean
////    public Function<KStream<Object, String>, KStream<?, WordCount>[]> process() {
////
////        Predicate<Object, WordCount> isEnglish = (k, v) -> v.word.equals("english");
////        Predicate<Object, WordCount> isFrench = (k, v) -> v.word.equals("french");
////        Predicate<Object, WordCount> isSpanish = (k, v) -> v.word.equals("spanish");
////
////        return input -> {
////            final Map<String, KStream<Object, WordCount>> stringKStreamMap = input
////                    .flatMapValues(value -> Arrays.asList(value.toLowerCase().split("\\W+")))
////                    .groupBy((key, value) -> value)
////                    .windowedBy(TimeWindows.of(Duration.ofSeconds(5)))
////                    .count(Materialized.as("WordCounts-branch"))
////                    .toStream()
////                    .map((key, value) -> new KeyValue<>(null, new WordCount(key.key(), value,
////                            new Date(key.window().start()), new Date(key.window().end()))))
////                    .split()
////                    .branch(isEnglish)
////                    .branch(isFrench)
////                    .branch(isSpanish)
////                    .noDefaultBranch();
////
////            return stringKStreamMap.values().toArray(new KStream[0]);
////        };
////    }
//}