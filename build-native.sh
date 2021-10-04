#!/bin/bash


export VERSION="3.1-SNAPSHOT"
native-image -jar "target/authlete-java-common-${VERSION}-jar-with-dependencies.jar" \
  --allow-incomplete-classpath \
  --enable-url-protocols=https \
  -H:ReflectionConfigurationFiles=reflect-config.json,authlete-reflect-config.json
mv "authlete-java-common-${VERSION}-jar-with-dependencies" authlete

