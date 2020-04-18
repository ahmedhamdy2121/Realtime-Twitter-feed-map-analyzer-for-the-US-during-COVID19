package bdt.streamdata;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Connection;

import bdt.streamdata.hbase.HBaseEngine;
import bdt.streamdata.hbase.HbaseConnection;

public class SetupData {

	public static void main(String[] args) throws IOException {
		Connection connection = HbaseConnection.getInstance();
		HBaseEngine engine = HBaseEngine.getInstance();
		engine.createTable(connection);
		connection.close();
	}

}
