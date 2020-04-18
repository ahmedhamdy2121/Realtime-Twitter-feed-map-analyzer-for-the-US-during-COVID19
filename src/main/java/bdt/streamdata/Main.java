package bdt.streamdata;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;

public class Main {

	public static void main(String[] args) throws IOException {
		final JavaSparkContext sc = new JavaSparkContext(new SparkConf().setAppName("project").setMaster("local"));

		SQLContext sqlContext = new org.apache.spark.sql.SQLContext(sc);

		List<String> jsonData = Arrays
				.asList("{\"name\":\"Yin\",\"address\":{\"city\":\"Columbus\",\"state\":\"Ohio\"}}");
		JavaRDD<String> anotherPeopleRDD = sc.parallelize(jsonData);
		DataFrame anotherPeople = sqlContext.read().json(anotherPeopleRDD);
		anotherPeople.show();
		sc.close();

	}

}
