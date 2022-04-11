do_install:append:bcm-2xxx-rpi4() {
	cd ${D}/${nonarch_base_libdir}/firmware/brcm/
	ln -sf brcmfmac43455-sdio.bin brcmfmac43455-sdio.raspberrypi,4-model-b.bin
	cd -
}