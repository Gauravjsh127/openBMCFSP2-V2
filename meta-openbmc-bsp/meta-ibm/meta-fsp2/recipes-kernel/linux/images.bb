DESCRIPTION = " images folder that contains mif files and ipl binaries for ddr and ddr4 cards"
LICENSE = "IBM-LICENSE"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/IBM-LICENSE;md5=f7b4ffd4b82a8971026b3fccf3d426bc"

SRC_URI += "file://images \
 	  "	
FILES_${PN} += "/images" 	  	   

S = "${WORKDIR}"

do_install() {
    install -d ${D}/images ${D}/images/ddr3_ipl ${D}/images/ddr4_ipl

    install -m 0755 ${S}/images/sunray* ${D}/images  	

    install -m 0755 ${S}/images/ddr3_ipl/* ${D}/images/ddr3_ipl 
    install -m 0755 ${S}/images/ddr4_ipl/* ${D}/images/ddr4_ipl   
}

