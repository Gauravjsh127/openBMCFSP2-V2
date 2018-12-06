FILESEXTRAPATHS_prepend := "${THISDIR}/uboot-fsp2:" 
SRC_URI += " \
	    file://uboot-dhcp-tftp-option.patch \
	    file://uboot-config_ip_defrag.patch \
	    file://uboot-disable-eth1.patch \
	    file://uboot-fixup-sfc-warnings.patch \
           "
