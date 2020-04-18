package bdt.streamdata;

import bdt.streamdata.producer.TwitterKafkaProducer;

/**
 * Hello world!
 */
public class TwitterKafkaProducerApp {
    public static void main(String[] args) {
        System.out.println("TwitterKafkaProducerApp is running");
        TwitterKafkaProducer producer = new TwitterKafkaProducer();
        producer.run();
    }
}
