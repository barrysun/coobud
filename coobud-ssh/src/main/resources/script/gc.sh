#!/bin/bash

ps_name="java"

pid_num=($(ps -ef | grep $ps_name | grep -v grep | awk '{print $2}'))

for i in ${pid_num[@]};do
        jstat -gc $i
done