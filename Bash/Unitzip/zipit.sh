#!/bin/bash

zip contract.txt.zip contract.txt
rm contract.txt
xz contract.txt.zip
bzip2 contract.txt.zip.xz
gzip contract.txt.zip.xz.bz2