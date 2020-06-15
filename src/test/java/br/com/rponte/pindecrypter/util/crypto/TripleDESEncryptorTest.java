package br.com.rponte.pindecrypter.util.crypto;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.com.rponte.pindecrypter.util.codec.Hex;

public class TripleDESEncryptorTest {
	
	private TripleDESEncryptor encrypter;

	@Before
	public void init() {
		this.encrypter = new TripleDESEncryptor();
	}
	
	@Test
	public void shouldEncryptAndDecryptText_usingSameKey() {
		
		// scenario
		String key = "D9BFEA0E3B648A20FBC8673DE38A91C7";
		String clearValue = "@rponte";
		String hexClearValue = Hex.encodeString(clearValue.getBytes());
		
		// action
		String hexEncryptedText = encrypter.encrypt(hexClearValue, key);
		String hexDecryptedText = encrypter.decrypt(hexEncryptedText, key);
		
		// validation
		String plainTextValue = new String(Hex.decode(hexDecryptedText)).trim();
		assertEquals("decrypted text", clearValue, plainTextValue);
	}

	@Test
	public void shouldDecryptAnEncryptedText_usingAKey() {
		
		// scenario
		String key = "D9BFEA0E3B648A20FBC8673DE38A91C7";
		String hexEncryptedText = "8572B6972C12BA8A";
		
		// action
		String value = encrypter.decrypt(hexEncryptedText, key);
		
		// validation
		String plainTextValue = new String(Hex.decode(value)).trim();
		assertEquals("decrypted text", "@rponte", plainTextValue);
	}

}
