package br.com.rponte.pindecrypter.keys;

import br.com.rponte.pindecrypter.util.crypto.TripleDESEncryptor;

/**
 * Represents an encrypted Zone PIN Key (ZPK)
 */
public class EncryptedZpk {
	
	private static TripleDESEncryptor TRIPLE_DES = new TripleDESEncryptor();

	private final String zpkEncryptedUnderZmk;

	public EncryptedZpk(String zpkEncryptedUnderZmk) {
		this.zpkEncryptedUnderZmk = zpkEncryptedUnderZmk;
	}

	/**
	 * Decrypts this encrypted ZPK using the specified <code>ZMK</code>
	 * 
	 * @param ZMK the master key that will be used to decrypts this encrypted ZPK
	 * @return the <code>Zpk</code> instance containing the decryped ZPK
	 */
	public Zpk decrypt(MasterKey ZMK) {
		String zpk = TRIPLE_DES.decrypt(zpkEncryptedUnderZmk, ZMK.getMasterKey());
		return new Zpk(zpk);
	}
	
	@Override
	public String toString() {
		return this.zpkEncryptedUnderZmk;
	}
	
}
