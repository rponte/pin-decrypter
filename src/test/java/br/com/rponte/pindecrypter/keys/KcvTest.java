package br.com.rponte.pindecrypter.keys;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * You can manually validate a KCV on this site:
 * https://neapay.com/online-tools/hsm-keys-compose.html
 */
public class KcvTest {

	@Test
	public void shouldCalculateTheKcv_forA3DesKey() {
		
		// scenario
		String key = "B3EF806FC201CFCBD2FC1A29A86F167B";
		
		// action
		Kcv kcv = new Kcv(key);
		String calculatedValue = kcv.calculate();
		
		// validation
		assertEquals("kcv", "09BE48", calculatedValue);
	}

}
