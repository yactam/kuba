#!/bin/sh
javac -classpath src/ src/com/kuba/GameOnline.java
java -classpath src/ com/kuba/GameOnline
find . -type f -path "./src/*" -name "*.class" -delete