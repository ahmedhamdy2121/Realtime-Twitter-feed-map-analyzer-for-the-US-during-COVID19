package bdt.streamdata.hbase;

import java.io.IOException;
import java.io.Serializable;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.io.compress.Compression.Algorithm;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.spark.api.java.JavaRDD;

import bdt.streamdata.model.Tweet;
import lombok.extern.log4j.Log4j;

@Log4j
public class HBaseEngine implements Serializable {

	private static final long serialVersionUID = 1L;

	private static HBaseEngine instance;

	private HBaseEngine() {}

	public static HBaseEngine getInstance() {
		if (instance == null) {
			instance = new HBaseEngine();
		}
		return instance;
	}

	public void createTable(Connection connection) {
		try (Admin admin = connection.getAdmin()) {
			HTableDescriptor table = new HTableDescriptor(TableName.valueOf(HBaseConstant.TABLE_NAME));
			table.addFamily(new HColumnDescriptor(HBaseConstant.TWEET_F).setCompressionType(Algorithm.NONE));
			table.addFamily(new HColumnDescriptor(HBaseConstant.COUNTRY_F).setCompressionType(Algorithm.NONE));
			if (!admin.tableExists(table.getTableName())) {
				log.info("Creating table ");
				admin.createTable(table);
				log.info("Table created");
			}
		} catch (IOException ex) {
			log.error(ex.getMessage());
		}
	}

	public void put(Connection connection, TweetRecord tw) throws IOException {
		try (Table tb = connection.getTable(TableName.valueOf(HBaseConstant.TABLE_NAME))) {
			tb.put(putObject(tw.getId(), tw));
		}
	}

	private Put putObject(String key, TweetRecord obj) {
		Put p = new Put(Bytes.toBytes(key));
		p.addColumn(Bytes.toBytes(HBaseConstant.TWEET_F), Bytes.toBytes(HBaseConstant.id), Bytes.toBytes(obj.getId()));
		p.addColumn(Bytes.toBytes(HBaseConstant.TWEET_F), Bytes.toBytes(HBaseConstant.createdAt), Bytes.toBytes(obj.getCreatedAt()));
		p.addColumn(Bytes.toBytes(HBaseConstant.COUNTRY_F), Bytes.toBytes(HBaseConstant.location), Bytes.toBytes(obj.getLocation()));
		p.addColumn(Bytes.toBytes(HBaseConstant.COUNTRY_F), Bytes.toBytes(HBaseConstant.country), Bytes.toBytes(obj.getCountry()));
		return p;
	}

	public void save(JavaRDD<Tweet> rdd) {		
		rdd.foreach(record -> {			
			Connection connection = HbaseConnection.getInstance();
			TweetRecord tw = TweetRecord.of(record);
			try {
				put(connection, tw);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			});
		
	}
	public TweetRecord get(Connection connection, String key) throws IOException {
		try (Table tb = connection.getTable(TableName.valueOf(HBaseConstant.TABLE_NAME))) {
			Get g = new Get(Bytes.toBytes(key));
			Result result = tb.get(g);
			if (result.isEmpty()) {
				return null;
			}
			byte [] id = result.getValue(Bytes.toBytes(HBaseConstant.TWEET_F),Bytes.toBytes(HBaseConstant.id));			
			byte [] createdAt = result.getValue(Bytes.toBytes(HBaseConstant.TWEET_F),Bytes.toBytes(HBaseConstant.createdAt));
			byte [] location = result.getValue(Bytes.toBytes(HBaseConstant.COUNTRY_F),Bytes.toBytes(HBaseConstant.location));
			byte [] country = result.getValue(Bytes.toBytes(HBaseConstant.COUNTRY_F),Bytes.toBytes(HBaseConstant.country));

			return TweetRecord.of(
				Bytes.toString(id), 
				Bytes.toString(location),
				Bytes.toString(createdAt),
				Bytes.toString(country)
			);
		}
	}

}
