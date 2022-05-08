package br.com.jean.kafka_consumer;

import br.com.jean.Service.KafkaService;

public class App {
    public static void main( String[] args ) {
      KafkaService.reader(System.getenv("KAFKA_GROUP_ID_READER"));
    }
}
