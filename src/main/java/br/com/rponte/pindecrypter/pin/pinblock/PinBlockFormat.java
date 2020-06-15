package br.com.rponte.pindecrypter.pin.pinblock;

/**
 * PIN block formats
 */
public enum PinBlockFormat {

	ISO_FORMAT_00(0);
	
	private int code;

	private PinBlockFormat(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
}
