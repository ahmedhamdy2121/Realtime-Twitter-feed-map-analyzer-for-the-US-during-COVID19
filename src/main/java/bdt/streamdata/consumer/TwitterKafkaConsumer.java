package bdt.streamdata.consumer;


import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;

import com.google.gson.Gson;

import bdt.streamdata.config.KafkaConfiguration;
import bdt.streamdata.hbase.HBaseEngine;
import bdt.streamdata.model.Tweet;
import kafka.serializer.StringDecoder;

public class TwitterKafkaConsumer  implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Gson gson = new Gson();

	public void run(JavaSparkContext sc) throws InterruptedException {
		
		Set<String> topics = Collections.singleton(KafkaConfiguration.TOPIC);
        Map<String, String> kafkaParams = new HashMap<String, String>();
        kafkaParams.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfiguration.KAFKA_BROKERS);
		kafkaParams.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, String.valueOf(KafkaConfiguration.MESSAGE_SIZE));

		HBaseEngine engine = HBaseEngine.getInstance();
		try (JavaStreamingContext streamingContext = new JavaStreamingContext(sc, new Duration(5000))) {
			JavaPairInputDStream<String, String> directKafkaStream = KafkaUtils.createDirectStream(
				streamingContext, String.class,
				String.class, StringDecoder.class, StringDecoder.class, kafkaParams, topics);
			JavaDStream<Tweet> tweets = directKafkaStream.map(rdd -> gson.fromJson(rdd._2, Tweet.class));
			tweets.foreachRDD(rdd -> {
				if (!rdd.isEmpty()) {
					engine.save(rdd);
					System.out.println("Saving to :" + gson.toJson(rdd));
				}
			});
			streamingContext.start();
			streamingContext.awaitTermination();
		}
				
	}
	
	

}
