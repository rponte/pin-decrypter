package br.com.rponte.pindecrypter.keys;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class EncryptedZpkTest {

	/**
	 * https://emvlab.org/descalc/?key=5AD57D95BED009C0D22718602C881CF2&iv=0000000000000000&input=B3EF806FC201CFCBD2FC1A29A86F167B&mode=ecb&action=Encrypt&output=
	 */
	@Test
	public void shouldDecryptAnEncryptedZpk_usingMasterKey() {
		// scenario
		MasterKey ZMK = MasterKey.of("5AD57D95BED009C0D22718602C881CF2");
		String zpkEncryptedUnderZmk = "A5FFF148D005286C2CAA46B785FCDD02";
		
		// action
		EncryptedZpk eZpk = new EncryptedZpk(zpkEncryptedUnderZmk);
		Zpk zpk = eZpk.decrypt(ZMK);
		
		// validation
		assertEquals("clear zpk", "B3EF806FC201CFCBD2FC1A29A86F167B", zpk.getKey());
		assertEquals("clear zpk - KCV", "09BE48", zpk.getKcv());
	}
	
	@Test
	public void shouldNotDecryptAnEncryptedZpk_whenUsingAnotherMasterKey() {
		// scenario
		MasterKey anotherZmk = MasterKey.of("FFFFFFFFFFFFFFFF0000000000000000");
		String zpkEncryptedUnderZmk = "A5FFF148D005286C2CAA46B785FCDD02";
		
		// action
		EncryptedZpk eZpk = new EncryptedZpk(zpkEncryptedUnderZmk);
		Zpk zpk = eZpk.decrypt(anotherZmk);
		
		// validation
		assertNotEquals("clear zpk", "B3EF806FC201CFCBD2FC1A29A86F167B", zpk.getKey());
		assertNotEquals("clear zpk - KCV", "09BE48", zpk.getKcv());
	}

}
