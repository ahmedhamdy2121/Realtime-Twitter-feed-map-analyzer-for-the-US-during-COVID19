package bdt.streamdata;


import java.io.IOException;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.hive.HiveContext;

public class TwitterAnalysisApp {
	public static void main(String[] args) throws IOException, InterruptedException {
		System.out.println("TwitterAnalysisApp is running");
		SparkConf sparkConf = new SparkConf().setAppName("TwitterAnalysisApp").setMaster("local[*]");
		JavaSparkContext sc = new JavaSparkContext(sparkConf);

		HiveContext hiveContext = new HiveContext(sc);
		hiveContext.sql("show tables").show();
	    DataFrame df1 = hiveContext.sql("SELECT * FROM hb_tweet LIMIT 10");
	    df1.show();
		
		DataFrame df2 = hiveContext.sql("SELECT state.code, count(tweet.id) AS TweetsCount FROM hb_tweet AS tweet join state  AS state on (tweet.location = state.name) group by state.code");
	    df2.show();
		
//		DataFrame df3 = hiveContext.sql("SELECT state.code, cast(count(tweet.id) as decimal)/(SELECT count(*) FROM hb_tweet),2) AS TweetsPercent FROM hb_tweet AS tweet join state on (tweet.location = state.name) group by state.code");
//	    df3.show();
	 }
}