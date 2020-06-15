package br.com.rponte.pindecrypter.keys;

/**
 * Represents a Zone PIN Key (ZPK)
 */
public class Zpk {

	private String key;

	public Zpk(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
	
	/**
	 * Returns the Key Check Value (KCV) for this key
	 */
	public String getKcv() {
		Kcv kcv = new Kcv(key);
		return kcv.calculate();
	}

	@Override
	public String toString() {
		return "Zpk [key=" + key + ", kcv=" + getKcv() + "]";
	}

}
