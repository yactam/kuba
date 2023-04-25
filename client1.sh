#!/bin/sh
javac -classpath src/ src/reseau/Client.java
java -classpath src/ reseau/Client
find . -type f -path "./src/*" -name "*.class" -delete