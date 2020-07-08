DESCRIPTION = "Closed source binary files to help boot the ARM on the BCM2835."
LICENSE = "Broadcom-RPi"

LIC_FILES_CHKSUM = "file://LICENCE.broadcom;md5=c403841ff2837657b2ed8e5bb474ac8d"

inherit deploy

include recipes-bsp/common/raspberrypi-firmware.inc

INHIBIT_DEFAULT_DEPS = "1"

COMPATIBLE_MACHINE = "^rpi$"

S = "${RPIFW_S}/boot"

PR = "r3"

PACKAGE = "${PN}"
FILES_${PN} = "/boot/${PN}"
INSANE_SKIP += "src-uri-bad arch"
do_install() {
    install -d ${D}/boot/${PN}

    for i in ${S}/*.elf ; do
        cp $i ${D}/boot/${PN}
    done
    for i in ${S}/*.dat ; do
        cp $i ${D}/boot/${PN}
    done
    for i in ${S}/*.bin ; do
        cp $i ${D}/boot/${PN}
    done

    # Add stamp
    touch ${D}/boot/${PN}/${PN}-${PV}.stamp

    # Add LICENSE file with disclaimer
    (cd ${S} ; ls -C -w 80 *.bin *.dat *.elf) > ${D}/boot/${PN}/LICENSE.broadcom
    cat<<EOF>> ${D}/boot/${PN}/LICENSE.broadcom

========================================================================
The following applies to the files found in this directory listed above.
========================================================================

EOF
    cat ${S}/LICENCE.broadcom >> ${D}/boot/${PN}/LICENSE.broadcom
}

do_deploy() {
    install -d ${DEPLOYDIR}/
    cp -rf ${D}/boot/${PN} ${DEPLOYDIR}/
}

addtask deploy before do_build after do_install
do_deploy[dirs] += "${DEPLOYDIR}/${PN}"

PACKAGE_ARCH = "${MACHINE_ARCH}"

