#!/bin/sh
javac -classpath src/ src/reseau/Online.java
java -classpath src/ reseau/Online
find . -type f -path "./src/*" -name "*.class" -delete

