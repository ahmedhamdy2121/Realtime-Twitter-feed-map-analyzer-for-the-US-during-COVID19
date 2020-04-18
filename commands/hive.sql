/*
ROW FORMAT SERDE
  'org.apache.hadoop.hive.hbase.HBaseSerDe'

Specifies the name of a Java class in the Big SQL server CLASSPATH 
that implements the Hive Serde interface org.apache.hadoop.hive.serde2.SerDe.
*/

/*
STORED BY
  'org.apache.hadoop.hive.hbase.HBaseStorageHandler'

Specifies the name of a Java class in the Big SQL server CLASSPATH 
that implements the Hive storage handler interface org.apache.hadoop.hive.ql.metadata.HiveStorageHandler.
*/

/*
'serialization.format'='1'

is the field delimiter character in file between two column fields of the table
when the file is serialized 
*/

/*
TBLPROPERTIES

Table properties defines job properties that can configure input or output formats. 
They can be used to specify more detailed information about the table. 
You can use table properties to include an Avro file format, such as avro.schema.literal, or avro.schema.url.
*/

DROP TABLE IF EXISTS state;
CREATE TABLE state (name STRING, code String)
ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t';

LOAD DATA LOCAL INPATH 'state.txt'
OVERWRITE INTO TABLE state;

DROP TABLE IF EXISTS hb_tweet;
CREATE TABLE hb_tweet(
  id string COMMENT 'from deserializer',
  createdAt string COMMENT 'from deserializer',
  location string COMMENT 'from deserializer',  
  country string COMMENT 'from deserializer'
)
ROW FORMAT SERDE
  'org.apache.hadoop.hive.hbase.HBaseSerDe'
STORED BY
  'org.apache.hadoop.hive.hbase.HBaseStorageHandler'
WITH SERDEPROPERTIES (
  'hbase.columns.mapping'=':key,tweet_f:createdAt,country_f:location, country_f:country',
  'serialization.format'='1')
TBLPROPERTIES (
  'hbase.table.name'='hb_tweet'
);