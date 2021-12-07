.PHONY: all run clean

all:
	javac -XDignore.symbol.file -d ./ src/main/java/Main.java
	jar cfe Main.jar Main Main.class MySignalHandler.class

clean:
	rm -rf *.class *.jar

run:
	java -jar Main.jar
