package br.com.rponte.pindecrypter.pin.pinblock;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.com.rponte.pindecrypter.util.codec.Hex;

public class PinBlockEncoderTest {
	
	private PinBlockEncoder encoder;
	
	@Before
	public void init() {
		this.encoder = new PinBlockEncoder(PinBlockFormat.ISO_FORMAT_00);
	}

	@Test
	public void shouldDecodePinBlock_usingPan() {
		// scenario
		String pan = "5531889002744913";
		byte[] clearPinBlock = Hex.decode("04203876FFD8BB6E");
		
		// action
		String pin = encoder.decode(clearPinBlock, pan);
		
		// validation
		assertEquals("plain text pin", "2020", pin);
	}
	
	@Test
	public void shouldEncodePinIntoPinBlock_usingPan() {
		// scenario
		String pan = "5531889002744913";
		String pin = "2020";
		
		// action
		byte[] pinblock = encoder.encode(pin, pan);
		
		// validation
		byte[] expectedPinBlock = Hex.decode("04203876FFD8BB6E");
		assertArrayEquals("encoded pin", expectedPinBlock, pinblock);
	}
	
	@Test
	public void shouldEncodePinIntoPinBlockWithHexFormat_usingPan() {
		// scenario
		String pan = "5531889002744913";
		String pin = "2020";
		
		// action
		String hexPinblock = encoder.encodeHex(pin, pan);
		
		// validation
		String expectedHexPinBlock = "04203876FFD8BB6E";
		assertEquals("hex encoded pin ", expectedHexPinBlock, hexPinblock);
	}
	
	@Test
	public void shouldEncodeAndDecodeSamePinBlock_whenUsingTheSamePan() {
		
		// scenario
		String pan = "5531889002744913";
		String pin = "2020";
		
		// action
		byte[] pinblock = encoder.encode(pin, pan);
		String decodedPin = encoder.decode(pinblock, pan);
	
		assertEquals("encoded and decoded pin", decodedPin, decodedPin);
	}

}
