#!/bin/bash

# Author: Andras Tarlos
# Copyright (c) itguy

# -u unzip any type of compressed file
# -gz  compress the selected file with gzip
# -zip compress the selected file with zip
# -bzip2 compress the selected file with bzip2
# -xz compress the selected file with bzip

valid_args=("-u", "-gz", "-zip", "-bz2", "-xz")
args=$@
help="--help"
option=$1
filename=$2

# Text response functions ------------------------------------------------------------------------

error_message() {
	echo "unitzip: For help, type \"./unitzip.sh --help\""
	exit 1
}

help_message() {
	echo "No Copyright (c) 2022 - Type \"unitzip -L\" for non-existing software license."
	echo "Unitzip 1.0 (August 23th 2022)."
	echo "------------------------------------------------------------------------------"
	echo "Usage:"
	echo "	./unitzip [-options] [FILENAME]"
	echo "Options:"
	echo "	-u  unzip any type of compressed file"
	echo "	-gz  compress the selected file with gzip"
	echo "	-zip  compress the selected file with zip"
	echo "	-bzip2  compress the selected file with bzip2"
	echo "	-xz  compress the selected file with bzip"
	echo "FILENAME:"
	echo "	The name of the file in the same directory"
	echo "Examples:"
	echo "	\"./unitzip -gz contract.txt\" -> Compresses file"
	echo "	\"./unitzip -u contract.txt.xz\" -> Decompresses file"
	echo "------------------------------------------------------------------------------"

	exit 0
}

# Text response functions ------------------------------------------------------------------------

# Logic functions --------------------------------------------------------------------------------

# If option -u
unzip_file() {
	echo "Unzipping..."

	# Check if file exists
	# Unzip until file is unzipped
	while [ true ]
	do
		if [[ $filename == *.xz ]]; then
			xz -d $filename
			filename=${filename::-3}
		elif [[ $filename == *.bz2 ]]; then
			bunzip2 $filename
			filename=${filename::-4}
		elif [[ $filename == *.zip ]]; then
			unzip $filename -d .
			rm $filename
			filename=${filename::-4}
		elif [[ $filename == *.gz ]]; then
			gzip -d $filename
			filename=${filename::-3}
		else
			break
		fi
	done

}

# If a zip option is selected
# ("-u", "-gz", "-zip", "-bz2", "-xz")
zip_file() {
	echo "Zipping.."

	if [[ $option == "-gz" ]]; then
		gzip $filename
	elif [[ $option == "-zip" ]]; then
		zip ${filename}.zip $filename
		rm $filename
	elif [[ $option == "-bz2" ]]; then
		bzip2 $filename
	else
		xz -f $filename
	fi
}

# Logic functions --------------------------------------------------------------------------------

# Start

if [ $# -eq 0 ]; then
	echo "unitzip: No arguments were given.."
	error_message

# Check if the arguments contain "--help"
elif [[ " ${args[*]} " =~ "--help" ]]; then
	help_message

elif [ $# -eq 1 ]; then
	echo "unitzip: Only one argument was given.."
	error_message

elif [ $# -gt 2 ]; then
	echo "unitzip: Too many arguments.."
	error_message

elif [[ ! " ${valid_args[*]} " =~ "$1" ]]; then
	echo "unitzip: Invalid option given.."
	error_message

elif [ ! -f $filename ]; then
	echo "unitzip: File \"${filename}\" doesn't exist.. "
	error_message
fi


if [ $1 == "-u" ]; then
	unzip_file
else
	zip_file
fi

echo "Done"
exit 0