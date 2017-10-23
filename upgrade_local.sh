#!/usr/bin/env bash

mvn clean compile package &&
docker-compose build &&
docker-compose up -d
