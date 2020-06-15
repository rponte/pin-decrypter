package br.com.rponte.pindecrypter.util;

public class StringUtils {

	/**
	 * Return true if the string represent a number in the specified radix
	 */
	public static boolean isNumeric(String text) {
		int radix = 10;
		int i = 0, len = text.length();
        while ( i < len && Character.digit(text.charAt(i), radix) > -1  ){
            i++;
        }
        return i >= len && len > 0;
	}
}
