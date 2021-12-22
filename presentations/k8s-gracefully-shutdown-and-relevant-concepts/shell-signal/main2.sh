#!/bin/sh


echo "begin"

stop=0


handle_sigterm() {
  echo "[INFO] Received INT"
  stop=1
}
trap handle_sigterm INT # 捕获 SIGINT 信号并回调 handle_sigterm 函数


for a in `seq 10000`
do
  if [ "$stop" = "1" ]; then
      break
  fi

  echo "hello world."
  sleep 3
  echo "hello shopee."
  sleep 3
done

echo "end"

