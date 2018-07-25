LINUX_VERSION ?= "4.13.16"

SRCREV="61dd33b93ddde2f996d087c1129e4309c97822a2"

require linux-fsp2.inc

##### FSP2 Kernel config file

SRC_URI += "file://fsp2_defconfig.cfg"

#### FSP Header files added
SRC_URI += "file://1_headerfiles.patch"
#### FSP fsptrace device driver patch added
SRC_URI += "file://2_fsptrace.patch"
#### FSP NGFSI device driver patch added
SRC_URI += "file://3_ngfsi.patch"
#### FSP NGFSI device driver patch added
SRC_URI += "file://4_pra.patch"

#### FSP Device tree patch
SRC_URI += "file://5_device_tree.patch"

#### FSP pabend patch
SRC_URI += "file://6_pabend.patch"

#### FSP Ethernet device driver patch
SRC_URI += "file://7_ibm-emac.patch"

#### Segmentation fault Kernel panic patch
SRC_URI += "file://8_SEGFAULT_panic.patch"
