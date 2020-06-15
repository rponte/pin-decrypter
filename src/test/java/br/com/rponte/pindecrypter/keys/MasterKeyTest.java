package br.com.rponte.pindecrypter.keys;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * We can generate Master Key and Componentes keys on this site:
 * https://emvlab.org/keyshares/
 */
public class MasterKeyTest {

	@Test
	public void shouldCreateMasterKey_usingInformedKey() {
		
		String masterKey = "4FEED46341790E844B1633FA6EBF193F";
		MasterKey zmk = MasterKey.of(masterKey);
		
		assertEquals("master key", masterKey, zmk.getMasterKey());
		assertEquals("master key - KCV", "797F85", zmk.getKcv());
	}
	
	@Test
	public void shouldCreateMasterKey_usingThreeComponentKeys() {
		
		MasterKey zmk = MasterKey.combined(
					"B4089E65CF3ED07DF4D9EB31334B3494", 
					"E2E9668244CE6E673040711F4B851420", 
					"190F2C84CA89B09E8F8FA9D41671398B");
		
		String expectedMasterKey = "4FEED46341790E844B1633FA6EBF193F";
		assertEquals("master key", expectedMasterKey, zmk.getMasterKey());
		assertEquals("master key - KCV", "797F85", zmk.getKcv());
	}

}
