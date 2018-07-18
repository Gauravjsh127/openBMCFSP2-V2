DESCRIPTION = "Linux kernel for the FSP2 system on a chip 4.10"
SECTION = "kernel"
LICENSE = "GPLv2"

LINUX_VERSION ?= "4.10"

SRCREV_machine="e4eaca0332d5178a8dcb1d30f3f5b6c370a7132a"

KCONFIG_MODE="--alldefconfig"

# Modify SRCREV to a different commit hash in a copy of this recipe to
# build a different release of the Linux kernel.
# tag: v4.10-rc7 ba5c00e0627e4d735b36e0353744c6c3c4690454
###   https://github.com/fr0st61te/linux
SRC_URI = "git://github.com/fr0st61te/linux;protocol=git;name=machine"

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

LINUX_VERSION_EXTENSION_append = "-custom"

PV = "${LINUX_VERSION}+git${SRCPV}"

COMPATIBLE_MACHINE_${MACHINE} = "^${MACHINE}$"

do_patch_append() {
        for DTB in "${KERNEL_DEVICETREE}"; do
               DT=`basename ${DTB} .dtb`
                if [ -r "${WORKDIR}/${DT}.dts" ]; then
                        cp ${WORKDIR}/${DT}.dts \
                                ${STAGING_KERNEL_DIR}/arch/${ARCH}/boot/dts
               fi
       done
}

DEPENDS = "u-boot-mkimage-native"

inherit kernel
require recipes-kernel/linux/linux-yocto.inc

KERNEL_FEATURES_remove = "phosphor-gpio-keys"
INSANE_SKIP_kernel-vmlinux = "ldflags"


