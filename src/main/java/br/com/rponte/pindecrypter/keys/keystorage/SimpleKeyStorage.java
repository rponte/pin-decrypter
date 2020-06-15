package br.com.rponte.pindecrypter.keys.keystorage;

import br.com.rponte.pindecrypter.KeyStorage;
import br.com.rponte.pindecrypter.keys.EncryptedZpk;
import br.com.rponte.pindecrypter.keys.MasterKey;

/**
 * Simple In-Memory Key Storage
 */
public class SimpleKeyStorage implements KeyStorage {

	private MasterKey masterKey;
	private EncryptedZpk encryptedZpk;

	@Override
	public MasterKey getMasterKey() {
		return masterKey;
	}

	@Override
	public EncryptedZpk getEncryptedZpk() {
		return encryptedZpk;
	}

	public SimpleKeyStorage withMasterKey(MasterKey masterKey) {
		this.masterKey = masterKey;
		return this;
	}
	
	public SimpleKeyStorage withEncryptedZpk(EncryptedZpk encryptedZpk) {
		this.encryptedZpk = encryptedZpk;
		return this;
	}
	
}
