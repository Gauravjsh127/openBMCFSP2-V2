KBRANCH ?= "dev-4.17"
LINUX_VERSION ?= "4.17.11"

SRCREV="db64579331df4ff9d9c2706601fdb41978192a09"

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
#SRC_URI += "file://7_ibm-emac.patch"

#### Segmentation fault Kernel panic patch
SRC_URI += "file://8_SEGFAULT_panic.patch"

#### Segmentation fault Kernel panic patch
SRC_URI += "file://9_compiler_types.patch"
