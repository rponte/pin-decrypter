package br.com.rponte.pindecrypter.util.codec;

public final class Hex {

	private static final char[] HEX = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'A', 'B', 'C', 'D', 'E', 'F' };

	public static String encodeString(byte[] bytes) {
		return new String(encode(bytes));
	}
	
	public static char[] encode(byte[] bytes) {
		final int nBytes = bytes.length;
		char[] result = new char[2 * nBytes];

		int j = 0;
		for (byte aByte : bytes) {
			// Char for top 4 bits
			result[j++] = HEX[(0xF0 & aByte) >>> 4];
			// Bottom 4
			result[j++] = HEX[(0x0F & aByte)];
		}

		return result;
	}

	public static byte[] decode(CharSequence s) {
		int nChars = s.length();

		if (nChars % 2 != 0) {
			throw new IllegalArgumentException(
					"Hex-encoded string must have an even number of characters");
		}

		byte[] result = new byte[nChars / 2];

		for (int i = 0; i < nChars; i += 2) {
			int msb = Character.digit(s.charAt(i), 16);
			int lsb = Character.digit(s.charAt(i + 1), 16);

			if (msb < 0 || lsb < 0) {
				throw new IllegalArgumentException(
					"Detected a Non-hex character at " + (i + 1) + " or " + (i + 2) + " position");
			}
			result[i / 2] = (byte) ((msb << 4) | lsb);
		}
		return result;
	}

}
