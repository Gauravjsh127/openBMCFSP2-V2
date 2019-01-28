DESCRIPTION = " Copy Mif files inside rootfs"
LICENSE = "IBM-LICENSE"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/IBM-LICENSE;md5=f7b4ffd4b82a8971026b3fccf3d426bc"

SRC_URI = "file://sunray2_nor8M_network.mif"
SRC_URI += "file://sunray2_nor8M_network_ecc_n.mif"
SRC_URI += "file://sunray4_nor8M_network.mif"
SRC_URI += "file://sunray4_nor8M_network_ecc_n.mif"

S = "${WORKDIR}"

INSANE_SKIP_${PN} = "already-stripped ldflags"

FILES_${PN} += "/images" 

do_install() {
    install -d ${D}/images 
    install -m 0755 ${WORKDIR}/sunray* ${D}/images  
}

