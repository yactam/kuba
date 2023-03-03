#!/bin/sh
javac -classpath src/ src/com/kuba/Main.java
java -classpath src/ com.kuba.Main
find . -type f -path "./src/*" -name "*.class" -delete

