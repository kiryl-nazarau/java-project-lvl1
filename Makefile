.DEFAULT_GOAL := build-run
run:
	java -jar ./target/casino-1.0-SNAPSHOT-jar-with-dependencies.jar
clean:
	rm -rf ./target
build-run: build run
build: clean
	./mvnw clean package
update:
	./mvnw versions:update-properties
	./mvnw versions:display-plugin-updates