#!/bin/bash
if ! [[ -d Desktop-Goose--Not-my-project- ]]; then
	git clone https://github.com/AndrasTarlos/Desktop-Goose--Not-my-project-.git
	wait
fi

for i in $(seq 1 5)
do 
	Desktop-Goose--Not-my-project-/GooseDesktop.exe
done
