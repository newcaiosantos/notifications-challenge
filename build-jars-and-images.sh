#!/bin/bash
./user-settings-ms/gradlew build -p user-settings-ms
./notifications-manager-ms/gradlew build -p notifications-manager-ms
./notifications-sender-ms/gradlew build -p notifications-sender-ms
docker-compose build --no-cache