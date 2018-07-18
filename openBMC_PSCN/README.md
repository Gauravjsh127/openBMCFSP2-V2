# OpenBMCFsp2 #

 To Build PSCN application via openBMC there are two ways:
   - Using Docker - No dependency on host system  
   - Using Host system
   
   Note: Currently the build environment is tested on ubuntu 16:04 for other Linux distribution it may or may not work.
   
## Using Docker ##   
   
   - Install docker in your local system
  
  - Run the docker setup script
  	./openBMC_PSCN/openbmc-build-setup.sh.sh.
     This script execution will take appox 2 hour and will generate final docker images which will be used for PSCN build..

   - checkout PSCN code.

   - Change the image name inside dockerbuild-imagename file to openbmc/ubuntu:latest-fsp2-x86_64-2328916236   

   - Now run the docker script to build the PSCN code.[3 steps]
  	./dockerbuild fsp 
	./dockerbuild fsp:package_rpm
	./dockerbuild fsp:package_ffdc 

   - Switch to the PSCN output directory ie cd build
   
   - scp the RPMS to SE generated inside build directory and install them. 
   
   - Reboot the FSPs and SE.
   
## Using Host system ##

  Generating the cross-toolchain via BitBake and kernel images ie vmlinux,dtb,rootfs and uboot in your host system and using it to build PSCN code. 
  Make sure your system has libxerces-c-dev,cmake and rpm packages installed.

  - Populate the code : ./git clone git@github.com:Gauravjsh127/openBMCFsp2-V2.git
	cd openBMCFsp2-V2
    
  - Run the configurations steps-
        export TEMPLATECONF=meta-openbmc-machines/meta-openpower/meta-ibm/meta-z-fsp2-ppc/conf/
 	./openBMC_PSCN/clone-meta-fsp2-ibm-internal.sh
	. openbmc-env

  - Build kernel images ie vmlinux,dtb,rootfs and uboot.
        bitbake core-image-minimal 
  
  - Build the SDK 
  	bitbake core-image-minimal -c populate_sdk 
	
  - Install the SDK.
        .tmp/deploy/sdk/fsp2/*.sh

  - Delete the conf folder and return to previous directory.
  	rm -rf conf/
	cd ..
  
  - Run the configurations steps-
        export TEMPLATECONF=meta-openbmc-machines/meta-openpower/meta-ibm/meta-z-fsp2-x86/conf/
	. openbmc-env

  - Build kernel images ie vmlinux,dtb,rootfs and uboot.
        bitbake core-image-minimal-x86 
	
  - checkout PSCN code(dev/master).
	cd scsn/
             
  - run the prepOpenBMCHostbuild.pl script and pass the openBMC project path(Make sure you have build the openBMC project for fsp2-x86 and fsp2-ppc target and also install the fsp2-ppc application sdk in the local system).
  	./prepOpenBMCHostbuild.pl /home/gaurav/openBMC/github/11th_July/openBMCFSP2-V2
  
  - Build the PSCN code 
  	./buildctl.cm fsp2
 
  - Switch to the PSCN output directory ie cd build
   
  - scp the RPMS to SE generated inside build directory and install them. 
   
  - Reboot the FSPs and SE.
   
  
