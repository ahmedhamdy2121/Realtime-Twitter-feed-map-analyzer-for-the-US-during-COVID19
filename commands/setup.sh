#!/bin/bash

# Clear the topic covid-tweets in kafka
kafka-topics --delete  --zookeeper quickstart.cloudera:2181  --topic covid-tweets

# Create the topic covid-tweets in kafka
kafka-topics --create --zookeeper quickstart.cloudera:2181 --replication-factor 1 --partitions 3 --topic covid-tweets


