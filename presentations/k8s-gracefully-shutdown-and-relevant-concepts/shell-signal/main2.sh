#!/bin/sh


echo "begin"

#sh /app/scripts/start1.sh &
#echo "start1.sh executed"

handle_sigterm() {
  echo "[INFO] Received INT"
  sleep 30
}
trap handle_sigterm INT # 捕获 SIGINT 信号并回调 handle_sigterm 函数


for a in `seq 10000`
do
   echo "hello world."
   sleep 3
   echo "hello shopee."
   sleep 3
done

echo "end"

