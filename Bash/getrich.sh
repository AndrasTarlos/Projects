#!/bin/bash

echo "Enter your age>"
read age

agewhenmillionaire=$(($RANDOM % 14 + $age))

echo "You will be $agewhenmillionaire old when you will become a mill."


