#!/bin/sh
cp ../target/data-generator-service-0.0.1-SNAPSHOT.jar data-generator-service.jar
docker build -t data-generator .
echo "built"
docker run -p 8000:8000 data-generator
