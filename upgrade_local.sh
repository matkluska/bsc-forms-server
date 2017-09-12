#!/usr/bin/env bash

mvn clean compile package &&
docker-compose -f docker-compose.yml -f docker-compose.dev.yml build &&
docker-compose -f docker-compose.yml -f docker-compose.dev.yml up -d
