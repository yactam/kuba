#!/bin/sh
javac -classpath src/ src/Main.java
java -classpath src/ Main
find . -type f -path "./src/*" -name "*.class" -delete
