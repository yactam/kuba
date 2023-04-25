#!/bin/sh
javac -classpath src/ src/reseau/Server.java
java -classpath src/ reseau/Server
find . -type f -path "./src/*" -name "*.class" -delete

