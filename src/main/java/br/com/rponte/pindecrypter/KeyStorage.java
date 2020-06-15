package br.com.rponte.pindecrypter;

import br.com.rponte.pindecrypter.keys.EncryptedZpk;
import br.com.rponte.pindecrypter.keys.MasterKey;

/**
 * Represents a place in the system where all critical keys may be restored from
 */
public interface KeyStorage {

	/**
	 * Returns the Master Key (ZMK-Zone Master Key)
	 */
	public MasterKey getMasterKey();
	
	/**
	 * Returns the ZPK (Zone PIN Key) encrypted under ZMK
	 */
	public EncryptedZpk getEncryptedZpk();
	
}
