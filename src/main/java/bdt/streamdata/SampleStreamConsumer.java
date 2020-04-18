package bdt.streamdata;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;

import com.google.gson.Gson;

import bdt.streamdata.config.KafkaConfiguration;
import bdt.streamdata.model.Tweet;
import kafka.serializer.StringDecoder;

public class SampleStreamConsumer {
	private static Gson gson = new Gson();
	public static void main(String[] args) throws InterruptedException {

        SparkConf conf = new SparkConf()
                .setAppName("spark-streaming-consumer")
                .setMaster("local[*]");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaStreamingContext ssc = new JavaStreamingContext(sc, new Duration(2000));

        Set<String> topics = Collections.singleton(KafkaConfiguration.TOPIC);
        Map<String, String> kafkaParams = new HashMap<String, String>();
        kafkaParams.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, args[0]);
        kafkaParams.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, String.valueOf(KafkaConfiguration.MESSAGE_SIZE));

        JavaPairInputDStream<String, String> directKafkaStream = KafkaUtils.createDirectStream(ssc,
                String.class, String.class, StringDecoder.class, StringDecoder.class, kafkaParams, topics);
        JavaDStream<Tweet> tweets = directKafkaStream.map(rdd -> gson.fromJson(rdd._2, Tweet.class));
        tweets.foreachRDD(rdd -> {
            System.out.println("--- New RDD with " + rdd.partitions().size()
                    + " partitions and " + rdd.count() + " records");
            rdd.foreach(record -> System.out.println(gson.toJson(record)));
        });

        ssc.start();
        ssc.awaitTermination();
    }

}
