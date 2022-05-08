#!/bin/bash

export KAFKA_HOST="localhost:9092"
export KAFKA_TOPIC="SEND_EMAIL"
export KAFKA_GROUP_ID_READER="consomer_1"

export className=App
echo "## Running $className..."
shift
mvn exec:java -Dexec.mainClass="br.com.jean.kafka_consumer.$className"