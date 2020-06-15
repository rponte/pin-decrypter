package br.com.rponte.pindecrypter.pin.pinblock;

import br.com.rponte.pindecrypter.util.codec.Hex;

/**
 * Represents an PIN block
 */
public class PinBlock {
	
	private final String encodedPin;
	
	/**
	 * Creates a pinblock with the specifed PIN
	 * 
	 * @param encodedPin hex-encoded PIN block
	 */
	public PinBlock(String encodedPin) {
		this.encodedPin = encodedPin;
	}
	
	/**
	 * Returns the clear pinblock (hex-encoded PIN block)
	 */
	public String getClearPinBlock() {
		return this.encodedPin;
	}
	
	/**
	 * Returns the clear pinblock as byte array
	 */
	public byte[] getClearPinBlockAsByteArray() {
		byte[] pinblock = Hex.decode(encodedPin);
		return pinblock;
	}
	
	/**
	 * Extracts the PIN from this pinblock using the specified <code>pan</code>
	 * 
	 * @param pan the card number
	 * @return the plain-text PIN
	 */
	public String getPin(String pan) {
		PinBlockEncoder encoder = new PinBlockEncoder(PinBlockFormat.ISO_FORMAT_00);
		return encoder.decode(getClearPinBlockAsByteArray(), pan);
	}
	
}
