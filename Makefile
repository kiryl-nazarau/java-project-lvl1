.DEFAULT_GOAL := build-run
run:
	java -jar ./target/casino-1.0-SNAPSHOT-jar-with-dependencies.jar
build-run: build run
build:
	./mvnw verify package
update:
	./mvnw versions:update-properties versions:display-plugin-updates