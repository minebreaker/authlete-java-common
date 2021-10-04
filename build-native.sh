native-image -jar target/authlete-java-common-3.1-SNAPSHOT-jar-with-dependencies.jar \
  --allow-incomplete-classpath \
  --enable-url-protocols=https \
  -H:ReflectionConfigurationFiles=reflect-config.json
