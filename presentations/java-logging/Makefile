.PHONY: all run

all:
	javac -d ./out -cp dependencies/slf4j-api-1.7.36.jar com/clpsz/Main.java com/clpsz/MyApp.java

run-logback:
	@java -cp ./out/:./configs:dependencies/slf4j-api-1.7.36.jar:\
	dependencies/logback-core-1.2.11.jar:\
	dependencies/logback-classic-1.2.11.jar \
	com.clpsz.Main

run-log4j:
	@java -cp ./out/:./configs:dependencies/slf4j-api-1.7.36.jar:\
	dependencies/slf4j-log4j12-1.7.25.jar:\
	dependencies/log4j-1.2.17.jar \
	com.clpsz.Main

run-none:
	@java -cp ./out/:dependencies/slf4j-api-1.7.36.jar \
	com.clpsz.Main
