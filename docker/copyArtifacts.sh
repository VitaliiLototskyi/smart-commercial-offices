#!/bin/sh
cp ../data-generator-service/target/data-generator-service-0.0.1-SNAPSHOT.jar data-generator-service.jar
docker build -t data-generator .
echo "Copied"
#docker run -p 8000:8000 data-generator
