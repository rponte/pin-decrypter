package br.com.rponte.pindecrypter.pin;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.rponte.pindecrypter.keys.Zpk;
import br.com.rponte.pindecrypter.pin.pinblock.PinBlock;
import br.com.rponte.pindecrypter.util.codec.Hex;

public class EncryptedPinTest {

	@Test
	public void shouldDecryptAnEncryptedPin_usingPanAndZpk() {
		// scenario
		String pan = "4000340000000500";
		Zpk zpk = new Zpk("B3EF806FC201CFCBD2FC1A29A86F167B");
		String encryptedPin = "8D215B31E3C8DA64";
		
		// action
		EncryptedPin epin = new EncryptedPin(encryptedPin);
		PinBlock pinblock = epin.decrypt(zpk);
		
		// validation
		String expectedPinBlock = "044012BFFFFFFFAF";
		assertEquals("plain text pin", "4011", pinblock.getPin(pan));
		assertEquals("clear pinblock", expectedPinBlock, pinblock.getClearPinBlock());
		assertArrayEquals("clear pinblock byte array", Hex.decode(expectedPinBlock), pinblock.getClearPinBlockAsByteArray());
	}

}
