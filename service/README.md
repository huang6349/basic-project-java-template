English | [简体中文](./README_zh-CN.md)

# service

[![](https://img.shields.io/badge/java-1.8.0-fb9d40.svg?style=flat-square)](https://www.oracle.com/technetwork/java/javase/downloads/index.html)

This is the service part of the java project

# deployment scripts

#### linux

```sh
#!/bin/sh
APP=basic-0.0.1-SNAPSHOT
APP_NAME=${APP}".jar"
command=$1

function start() 
{
  rm -f tpid
  nohup java -jar ${APP_NAME} > log.txt 2>err.txt &
  echo $! > tpid
  check
}

function stop() {
  tpid=`ps -ef|grep $APP_NAME|grep -v grep|grep -v kill|awk '{print $2}'`
  if [ ${tpid} ]; then
    echo 'Stop Process...'
    kill -15 $tpid
  fi
    sleep 5
    tpid=`ps -ef|grep $APP_NAME|grep -v grep|grep -v kill|awk '{print $2}'`
  if [ ${tpid} ]; then
    echo 'Kill Process!'
    kill -9 $tpid
  else
    echo 'Stop Success!'
  fi
}

function check() 
{
  tpid=`ps -ef|grep $APP_NAME|grep -v grep|grep -v kill|awk '{print $2}'`
  if [ ${tpid} ]; then
    echo 'App is running.'
  else
    echo 'App is NOT running.'
  fi
}

function forcekill() 
{
  tpid=`ps -ef|grep $APP_NAME|grep -v grep|grep -v kill|awk '{print $2}'`
  if [ ${tpid} ]; then
    echo 'Kill Process!'
    kill -9 $tpid
  fi
}

if [ "${command}" ==  "start" ]; then
  start
elif [ "${command}" ==  "stop" ]; then
  stop
elif [ "${command}" ==  "check" ]; then
  check
elif [ "${command}" ==  "kill" ]; then
  forcekill
else
  echo "Unknow argument...."
fi
```
