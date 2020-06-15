package br.com.rponte.pindecrypter.util.codec;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.Test;

public class HexTest {

	@Test
	public void shouldEncodeByteArrayIntoHex() {
		assertEquals("empty", "", Hex.encodeString("".getBytes()));
		assertEquals("chars only", "616263", Hex.encodeString("abc".getBytes()));
		assertEquals("numbers only", "31323334", Hex.encodeString("1234".getBytes()));
		assertEquals("special chars", "4072706F6E7465", Hex.encodeString("@rponte".getBytes()));
		assertEquals("ascii chars", "63616E73613F6F203F3F3F", Hex.encodeString("cansaço éíá".getBytes(StandardCharsets.US_ASCII)));
		assertEquals("iso8859-1 chars", "63616E7361E76F20E9EDE1", Hex.encodeString("cansaço éíá".getBytes(StandardCharsets.ISO_8859_1)));
		assertEquals("utf-8 chars", "63616E7361C3A76F20C3A9C3ADC3A1", Hex.encodeString("cansaço éíá".getBytes(StandardCharsets.UTF_8)));
	}
	
	@Test
	public void shouldDecodeHexIntoByteArray() {
		assertArrayEquals("empty", "".getBytes(), Hex.decode(""));
		assertArrayEquals("chars only", "abc".getBytes(), Hex.decode("616263"));
		assertArrayEquals("numbers only", "1234".getBytes(), Hex.decode("31323334"));
		assertArrayEquals("special chars", "@rponte".getBytes(), Hex.decode("4072706F6E7465"));
		assertArrayEquals("ascii chars", "cansaço éíá".getBytes(StandardCharsets.US_ASCII), Hex.decode("63616E73613F6F203F3F3F"));
		assertArrayEquals("iso8859-1 chars", "cansaço éíá".getBytes(StandardCharsets.ISO_8859_1), Hex.decode("63616E7361E76F20E9EDE1"));
		assertArrayEquals("utf-8 chars", "cansaço éíá".getBytes(StandardCharsets.UTF_8), Hex.decode("63616E7361C3A76F20C3A9C3ADC3A1"));
	}

}
