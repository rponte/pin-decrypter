package br.com.rponte.pindecrypter.pin.pinblock;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.rponte.pindecrypter.util.codec.Hex;

/**
 * https://emvlab.org/descalc/?key=B3EF806FC201CFCBD2FC1A29A86F167B&iv=0000000000000000&input=7D261F74AA07DD81&mode=ecb&action=Decrypt&output=ADB52E4658966002
 */
public class PinBlockTest {

	@Test
	public void shouldDecodePinBlock_usingInformedPan() {
		// scenario
		String pan = "4000340000000500";
		String encodedPin = "040665BFFFFFFFAF";
		
		// action
		PinBlock pinblock = new PinBlock(encodedPin);
		String pin = pinblock.getPin(pan);
		
		// validation
		assertEquals("plain text pin", "0666", pin);
		assertEquals("clear pinblock", encodedPin, pinblock.getClearPinBlock());
		assertArrayEquals("clear pinblock byte array", Hex.decode(encodedPin), pinblock.getClearPinBlockAsByteArray());
	}
	
	@Test
	public void shouldDecodePinBlockWith32Bytes_usingInformedPan() {
		// scenario
		String pan = "4000340000000500";
		String encodedPinWith32Bytes = "040665BFFFFFFFAF5379717320020C16";
		
		// action
		PinBlock pinblock = new PinBlock(encodedPinWith32Bytes);
		String pin = pinblock.getPin(pan);
		
		// validation
		assertEquals("plain text pin", "0666", pin);
		assertEquals("clear pinblock", encodedPinWith32Bytes, pinblock.getClearPinBlock());
		assertArrayEquals("clear pinblock byte array", Hex.decode(encodedPinWith32Bytes), pinblock.getClearPinBlockAsByteArray());
	}

}
