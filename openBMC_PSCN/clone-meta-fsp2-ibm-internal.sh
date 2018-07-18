#!/bin/sh

# Merge meta-fsp2-drivers/adals/apps layers inside the meta-openbmc-bsp/meta-ibm layer
THIS_SCRIPT=$BASH_SOURCE

echo " Change directory to meta-openbmc-bsp/meta-ibm/ for cloning fsp2 drivers/adals/apps layer" 

cd meta-openbmc-bsp/meta-ibm/

git clone git@github.ibm.com:XXPETRI/meta-fsp2-ibm-internal.git

echo " Change directory to OE root directory " 

cd ../../

