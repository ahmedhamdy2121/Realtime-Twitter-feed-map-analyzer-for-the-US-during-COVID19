package bdt.streamdata;


import java.io.IOException;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

import bdt.streamdata.consumer.TwitterKafkaConsumer;


/**
 * Hello world!
 */
public class TwitterKafkaConsumerApp {
	public static void main(String[] args) throws IOException, InterruptedException {
		System.out.println("TwitterKafkaConsumerApp is running");
		SparkConf sparkConf = new SparkConf().setAppName("TwitterKafkaConsumerApp").setMaster("local[*]");
		JavaSparkContext sc = new JavaSparkContext(sparkConf);
		TwitterKafkaConsumer comsumer = new TwitterKafkaConsumer();
        comsumer.run(sc);
	}	

}
