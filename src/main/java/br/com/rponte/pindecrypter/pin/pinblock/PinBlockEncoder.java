package br.com.rponte.pindecrypter.pin.pinblock;

import static br.com.rponte.pindecrypter.util.StringUtils.isNumeric;

import java.util.Arrays;

import br.com.rponte.pindecrypter.util.Xor;
import br.com.rponte.pindecrypter.util.codec.Hex;

/**
 * PIN block Encoder and Decoder
 * 
 * https://en.wikipedia.org/wiki/ISO_9564
 * https://neapay.com/online-tools/calculate-pin-block.html
 * https://gist.github.com/Gilmor/de2ba62407cb14b9b569
 * https://gist.github.com/rponte/336772302d73a08849fb7581c1ce9348 (forked from Gilmor)
 * http://stick2code.blogspot.com/2014/07/3des-encryption-of-credit-card-pin.html
 */
public class PinBlockEncoder {
	
	/**
     * The minimum length of the PIN
     */
    private static final short MIN_PIN_LENGTH = 4;
    /**
     * The maximum length of the PIN
     */
    private static final short MAX_PIN_LENGTH = 12;
    /**
     * a 64-bit block of ones used when calculating pin blocks
     */
    private static final byte[] PIN_PADDING_BLOCK = Hex.decode("FFFFFFFFFFFFFFFF");
    
	private final PinBlockFormat format;
    
    public PinBlockEncoder(PinBlockFormat format) {
		this.format = format;
	}
    
    /**
     * Decodifica PIN Block para PIN em texto plano
     */
    public String decode(byte[] clearPinBlock, String pan) {
		try {
			String pan12RightMostDigits = get12RigthMostPosition(pan);
			String plainTextPin = calculatePIN(clearPinBlock, format.getCode(), pan12RightMostDigits);
			return plainTextPin;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
	}

    /**
     * Encoda PIN para PIN Block para formato hexadecimal
     */
	public String encodeHex(String plainTextPin, String pan) {
    	byte[] pinBlock = encode(plainTextPin, pan);
    	return Hex.encodeString(pinBlock);
    }
    
	/**
     * Encoda PIN para PIN Block para array de bytes
     */
    public byte[] encode(String plainTextPin, String pan) {
    	try {
    		String pan12RightMostDigits = get12RigthMostPosition(pan);
    		byte[] pinBlock = calculatePINBlock(plainTextPin, format.getCode(), pan12RightMostDigits);
    		return pinBlock;
    	} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
    }
	
	private byte[] calculatePINBlock(String pin, int pinBlockFormat, String accountNumber) throws Exception {
		
		if (pin.length() < MIN_PIN_LENGTH || pin.length() > MAX_PIN_LENGTH)
            throw new RuntimeException("Invalid PIN length: " + pin.length());
		
        if (!isNumeric(pin))
            throw new RuntimeException("Invalid PIN decimal digits: " + pin);
        
        if (accountNumber.length() != 12)
            throw  new RuntimeException("Invalid Account Number: " + accountNumber + ". The length of the account number must be 12 (the 12 right-most digits of the account number excluding the check digit)");
        
    	// Block 1
	    byte[] block1 = Hex.decode(new String(formatPINBlock(pin, 0x0)));
	
	    // Block 2
	    byte[] block2 = Hex.decode("0000" + accountNumber);
	    
	    // pinBlock
	    byte[] pinBlock = Xor.xor(block1, block2);
	    return pinBlock;
	}

	private char[] formatPINBlock(String pin, int checkDigit){
		char[] block = Hex.encode(PIN_PADDING_BLOCK);
		char[] pinLenHex = String.format("%02X", pin.length()).toCharArray();
		pinLenHex[0] = (char)('0' + checkDigit);

		// pin length then pad with 'F'
		System.arraycopy(pinLenHex, 0, block, 0, pinLenHex.length);
		System.arraycopy(pin.toCharArray(), 0 ,block, pinLenHex.length, pin.length());
		return block;
	}
	
	private String calculatePIN(byte[] pinBlock, int pinBlockFormat, String accountNumber) throws Exception {
		byte[] bl2 = Hex.decode("0000" + accountNumber);
        // get Block1
        byte[] bl1 = Xor.xor(pinBlock, bl2);
        int pinLength = bl1[0] & 0x0f;
        char[] block1 = Hex.encodeString(bl1).toCharArray();
        int offset = 2;
        int checkDigit = 0x0;
        int padidx = pinLength + offset;
        // test pin block
        validatePinBlock(block1,checkDigit,padidx,offset);
        // get pin
        String pin = new String(Arrays.copyOfRange(block1, offset, padidx));
		return pin;
	}
	
	private void validatePinBlock(char[] pblock, int checkDigit, int padidx, int offset)
            throws RuntimeException {
      validatePinBlock(pblock, checkDigit, padidx, offset, 'F');
    }
	
	private void validatePinBlock(char[] pblock, int checkDigit, int padidx, int offset, char padDigit) throws RuntimeException {
		// test pin block check digit
		if (checkDigit >= 0 && pblock[0] - '0' != checkDigit)
			throw new RuntimeException("PIN Block Error - invalid check digit");
		// test pin block pdding
		int i = pblock.length - 1;
		while (i >= padidx)
			if (pblock[i--] != padDigit && padDigit > 0)
				throw new RuntimeException("PIN Block Error - invalid padding");
		// test pin block digits
		while (i >= offset)
			if (pblock[i--] >= 'A')
				throw new RuntimeException("PIN Block Error - illegal pin digit");
		// test pin length
		int pinLength = padidx - offset;
		if (pinLength < MIN_PIN_LENGTH || pinLength > MAX_PIN_LENGTH)
			throw new RuntimeException("PIN Block Error - invalid pin length: " + pinLength);
	}
	
	private String get12RigthMostPosition(String pan) {
		int LENGHT = pan.length() ;
		return pan.substring( LENGHT - 12 - 1  , LENGHT - 1 ) ;
	}
	
}
