# OpenBMC : FSP2 support

[![Build Status](https://openpower.xyz/buildStatus/icon?job=openbmc-build)](https://openpower.xyz/job/openbmc-build/)

The OpenBMC project can be described as a Linux distribution for embedded
devices that have a BMC; typically, but not limited to, things like servers,
top of rack switches or RAID appliances. The OpenBMC stack uses technologies
such as [Yocto](https://www.yoctoproject.org/),
[OpenEmbedded](https://www.openembedded.org/wiki/Main_Page),
[systemd](https://www.freedesktop.org/wiki/Software/systemd/), and
[D-Bus](https://www.freedesktop.org/wiki/Software/dbus/) to allow easy
customization for your server platform.


## Setting up your OpenBMC project

### 1) Prerequisite
- Ubuntu 14.04

```
sudo apt-get install -y git build-essential libsdl1.2-dev texinfo gawk chrpath diffstat
```

- Fedora 23

```
sudo dnf install -y git patch diffstat texinfo chrpath SDL-devel bitbake
sudo dnf groupinstall "C Development Tools and Libraries"
```
### 2) Download the source
```
git clone https://github.com/Gauravjsh127/openBMCFSP2-V2.git
cd openBMCFSP2-V2
```

### 3) Target your hardware
Any build requires an environment variable known as `TEMPLATECONF` to be set
to a hardware target.  OpenBMC has placed all known hardware targets in a
standard directory structure
`meta-openbmc-machines/meta-[architecture]/meta-[company]/meta-[target]`.
You can see all of the known targets with
`find meta-openbmc-machines -type d -name conf`. Choose the hardware target and
then move to the next step. Additional examples can be found in the
[OpenBMC Cheatsheet](https://github.com/openbmc/docs/blob/master/cheatsheet.md)

Machine | TEMPLATECONF
--------|---------
fsp2-ppc | ```meta-openbmc-bsp/meta-ibm/meta-fsp2-ibm-internal/meta-ibm-machines/meta-z-fsp2-ppc/conf/```
fsp2-x86| ```meta-openbmc-bsp/meta-ibm/meta-fsp2-ibm-internal/meta-ibm-machines/meta-z-fsp2-x86/conf/```
qemu-ppc| ```meta-openbmc-bsp/meta-ibm/meta-fsp2-ibm-internal/meta-ibm-machines/meta-z-qemu-ppc/conf/```

As an example target fsp2-ppc
```
export TEMPLATECONF=meta-openbmc-bsp/meta-ibm/meta-fsp2-ibm-internal/meta-ibm-machines/meta-z-fsp2-ppc/conf/
```

### 4) Build Images

```
./openBMC_PSCN/clone-meta-fsp2-ibm-internal.sh
. openbmc-env
bitbake core-image-minimal


Note 
  - For fsp2-x86 build the core-image-minimal-x86 image.
  - For cloning meta-fsp2-ibm-internal layer you need special access to github.ibm.com(Contact the repository owners for access).
  - when switching targets you need to do a rm -r -f build/conf
```

### 4) Build Application sdk
```
bitbake core-image-minimal -c populate_sdk 

Note 
 - To install SDK in host system run the script tmp/deploy/sdk/openbmc-phosphor-glibc-x86_64-core-image-minimal-powerpc-toolchain-2.5.sh -y
 - SDK is installed in the path /opt/openbmc-phosphor/2.4.2 by default.
 - Always set the build environment variables defined in the file /opt/openbmc-phosphor/2.4.2/environment-setup-powerpc-openbmc-linux before using the c++/c openbmc compilers.
```

### 5) Run  Qemu Target
```
runqemu qemuppc
```


### 5) Steps to generate UBOOT for z
```
- git clone  git@github.com:Gauravjsh127/openBMCFSP2-V2.git
- ./openBMC_PSCN/clone-meta-fsp2-ibm-internal.sh
- export TEMPLATECONF=meta-openbmc-bsp/meta-ibm/meta-fsp2-ibm-internal/meta-ibm-machines/meta-z-fsp2-ppc/conf/
- . openbmc-env
- bitbake core-image-minimal
 
Now build the x-86 target which will use the new uboot build and generate the mif files
- rm -rf conf
- cd ..
- export TEMPLATECONF=meta-openbmc-bsp/meta-ibm/meta-fsp2-ibm-internal/meta-ibm-machines/meta-z-fsp2-x86/conf/
- . openbmc-env
- bitbake core-image-minimal-x86
- bitbake mif-fsp2-ddr3-x86
- bitbake mif-fsp2-ddr4-x86

The new mif files for ddr3 and ddr4 will be generated inside the build directory
ddr3 : /build/tmp/work/x86_64-openbmc-linux/mif-fsp2-ddr3-x86/1.0-r0/mif-fsp2-ddr3-x86-code/*.mif
ddr4 : /build/tmp/work/x86_64-openbmc-linux/mif-fsp2-ddr4-x86/1.0-r0/mif-fsp2-ddr4-x86-code/*.mif
```
