package com.techelevator;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestGetClone {

	@Test
	public void testClone() {
		VendingItem theOriginal = new VendingItem("The Real One","Candy",1984);
		VendingItem theImposter = theOriginal.clone();
		assertEquals("Price not cloning correctly.",theImposter.getPrice(),theOriginal.getPrice());
		boolean isEqual = theOriginal.getProductName().equals(theImposter.getProductName());
		assertTrue("Product name not copying correctly.",isEqual);
		isEqual = theOriginal.getType().equals(theImposter.getType());
		assertTrue("Type not copying correctly.",isEqual);
	}

}
