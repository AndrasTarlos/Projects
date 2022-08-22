#!/bin/bash

name=$1
compliment=$2
user=$(whoami)
date=$(date)
whereami=$(pwd)
echo "You are currently logged in as $user, the current date is $date and you are in $whereami"
