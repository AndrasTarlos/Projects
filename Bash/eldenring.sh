#!/bin/bash

echo "Enter your next move (0/1): "
read move

beast=$(( $RANDOM % 10 ))
echo "$beast"
if [[ $beast == $move ]]; then
	echo "You win man!"
else 
	echo "You are a loser!"
fi
