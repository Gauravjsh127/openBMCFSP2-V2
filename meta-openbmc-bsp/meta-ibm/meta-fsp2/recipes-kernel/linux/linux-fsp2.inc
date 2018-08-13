DESCRIPTION = "Linux kernel for the FSP2 system on a chip"
SECTION = "kernel"
LICENSE = "GPLv2"

PROVIDES += "virtual/kernel"

KCONFIG_MODE="--alldefconfig"

KSRC ?= "git://github.com/openbmc/linux;protocol=git;branch=${KBRANCH}"
SRC_URI = "${KSRC}"

LINUX_VERSION_EXTENSION ?= "-${SRCREV}"

PV = "${LINUX_VERSION}+git${SRCPV}"

COMPATIBLE_MACHINE_${MACHINE} = "^${MACHINE}$"
KERNEL_CLASSES ?= "kernel-uimage"


DEPENDS = "u-boot-mkimage-native"

inherit kernel
require recipes-kernel/linux/linux-yocto.inc

KERNEL_FEATURES_remove = "phosphor-gpio-keys"
INSANE_SKIP_kernel-vmlinux = "ldflags"


# From 4.16+ the COPYING file changed
LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"
