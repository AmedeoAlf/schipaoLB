#!/usr/bin/env sh

./gradlew build
scp -P 10169 build/libs/schipaoLB-1.0-SNAPSHOT-all.jar schiantabasti@schipao.ftp.sh:luckyblock-server/plugins/schipaoLB-1.0-SNAPSHOT-all.jar