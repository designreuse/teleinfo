#! /usr/bin/sh
sudo killall -9 java
cd ~/teleinfo/
git pull
mvn package
/usr/lib/jvm/java-7-openjdk-amd64/jre/bin/java -jar ~/teleinfo/target/ekito-teleinfo.jar &