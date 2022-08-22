#!/bin/bash

echo "Enter the place>"
read name

curl wttr.in/$name
