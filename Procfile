web: java -Dserver.port=$PORT $JAVA_OPTS -jar build/libs/pdw-0.0.1-SNAPSHOT.jar
release: ./gradlew flywayClean && ./gradlew flywayMigrate
