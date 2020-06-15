package br.com.rponte.pindecrypter.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StringUtilsTest {

	@Test
	public void shouldBeNumeric() {
		assertTrue(StringUtils.isNumeric("0"));
		assertTrue(StringUtils.isNumeric("01"));
		assertTrue(StringUtils.isNumeric("000000002"));
		assertTrue(StringUtils.isNumeric("3837093"));
		assertTrue(StringUtils.isNumeric("94764896458957985645897645"));
	}
	
	@Test
	public void shouldNotBeNumeric() {
		assertFalse(StringUtils.isNumeric(""));
		assertFalse(StringUtils.isNumeric("a"));
		assertFalse(StringUtils.isNumeric("1a"));
		assertFalse(StringUtils.isNumeric("12a34"));
		assertFalse(StringUtils.isNumeric("c4"));
		assertFalse(StringUtils.isNumeric("   "));
	}

}
