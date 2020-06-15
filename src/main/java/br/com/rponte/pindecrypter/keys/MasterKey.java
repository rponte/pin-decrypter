package br.com.rponte.pindecrypter.keys;

import br.com.rponte.pindecrypter.util.Xor;
import br.com.rponte.pindecrypter.util.codec.Hex;

/**
 * Represents a ZMK (Zone Master Key) that will be used to decrypt an encrypted
 * ZPK (Zone PIN Key)
 * 
 * @author rponte
 */
public class MasterKey {
	
	private final String masterKey;
	
	private MasterKey(String masterKey) {
		this.masterKey = masterKey;
	}
	
	private MasterKey(String key1, String key2, String key3) {
		this.masterKey = combineKeys(key1, key2, key3);
	}
	
	/**
	 * Generates an instance of {@link MasterKey} using the <code>masterKey</code>
	 * specified
	 * 
	 * @param masterKey the master key (ZPK)
	 * @return an instance of <b>MasterKey</b>
	 */
	public static MasterKey of(String masterKey) {
		return new MasterKey(masterKey);
	}
	
	/**
	 * Generates an instance of {@link MasterKey} using the combination of the 3
	 * component keys specified
	 * 
	 * @param key1 key component one
	 * @param key2 key component two
	 * @param key3 key component three
	 * @return an instance of <b>MasterKey</b>
	 */
	public static MasterKey combined(String key1, String key2, String key3) {
		return new MasterKey(key1, key2, key3);
	}
	
	/**
	 * Returns this master key (ZPK)
	 */
	public String getMasterKey() {
		return this.masterKey;
	}
	
	/**
	 * Returns the Key Check Value (KCV) for this key
	 */
	public String getKcv() {
		Kcv kcv = new Kcv(masterKey);
		return kcv.calculate();
	}
	
	@Override
	public String toString() {
		return "MasterKey [key=" + masterKey + ", kcv=" + getKcv() + "]";
	}

	/**
	 * Returns the combination of the three component keys
	 */
	private String combineKeys(String key1, String key2, String key3) {
		byte[] keyData = buildMasterKey(key1, key2, key3);
		String masterKey = Hex.encodeString(keyData);
		return masterKey;
	}

	/**
	 * Basically does a XOR with the three component keys
	 */
	private byte[] buildMasterKey(String key1, String key2, String key3) {
		
		byte[] ck1 = Hex.decode(key1);
		byte[] ck2 = Hex.decode(key2);
		byte[] ck3 = Hex.decode(key3);
		
		return Xor.xor(ck1, ck2, ck3);
	}

}
