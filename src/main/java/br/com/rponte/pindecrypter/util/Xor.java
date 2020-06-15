package br.com.rponte.pindecrypter.util;

public class Xor {

	/**
	 * Bitwise XOR between two corresponding bytes
	 */
	public static byte[] xor(byte[] op1, byte[] op2) {
        // Use the smallest array
		int length = (op2.length > op1.length) ? op1.length : op2.length;
		byte[] result = new byte[length];
        for (int i = 0; i < result.length; i++) {
            result[i] = (byte)(op1[i] ^ op2[i]);
        }
        return  result;
	}
	
	/**
	 * Bitwise XOR between two corresponding bytes
	 */
	public static byte[] xor(byte[] op1, byte[] op2, byte[] op3) {
		byte[] result = new byte[op1.length];
		int i = 0;
		for (byte b1 : op1) {
			byte b2 = op2[i];
			byte b3 = op3[i];
			result[i] = (byte) (b1 ^ b2 ^ b3);
			i++;
		}
		return result;
	}
}
