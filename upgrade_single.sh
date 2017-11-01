#!/usr/bin/env bash

if [ -n "$1" ]; then
  mvn clean compile package &&
  docker-compose up -d --no-deps --build ${1}
else
  echo "Service name argument is required"
fi