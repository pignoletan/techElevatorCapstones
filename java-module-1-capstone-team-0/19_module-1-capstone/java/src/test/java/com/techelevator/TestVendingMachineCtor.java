package com.techelevator;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

public class TestVendingMachineCtor {

	@Test
	public void ctorTest() throws FileNotFoundException, IOException {
		File input = new File("vendingmachine.csv");
		VendingMachine testMachine = new VendingMachine(input);
		assertTrue("Key A1 not found.",testMachine.hasKey("A1"));
		assertTrue("Key A2 not found.",testMachine.hasKey("A2"));
		assertTrue("Key A3 not found.",testMachine.hasKey("A3"));
		assertTrue("Key A4 not found.",testMachine.hasKey("A4"));
		assertTrue("Key B1 not found.",testMachine.hasKey("B1"));
		assertTrue("Key B4 not found.",testMachine.hasKey("B4"));
		assertTrue("Key C1 not found.",testMachine.hasKey("C1"));
		assertTrue("Key C4 not found.",testMachine.hasKey("C4"));
		assertTrue("Key D1 not found.",testMachine.hasKey("D1"));
		assertTrue("Key D4 not found.",testMachine.hasKey("D4"));
		assertFalse("Key E1 found.",testMachine.hasKey("E1"));
		assertFalse("Key Skynet found.",testMachine.hasKey("Skynet"));
		assertEquals("Total sales not initialized to 0.",0,testMachine.getTotalSales());
		assertEquals("curBalance not initialized to 0.",0,testMachine.getCurBalance());
		VendingSlot testSlot = testMachine.getSlotByKey("A2");
		assertEquals("Slots not initialized to quantity 5.",5,testSlot.getCurQuant());
		assertEquals("Slots not given startQuant of 5.",5,testSlot.getStartQuant());
		VendingItem testItem = testSlot.getNextItem();
		assertEquals("A2 does not return Stackers for name.","Stackers",testItem.getProductName());
		assertEquals("A2 does not return 145 for price.",145,testItem.getPrice());
		assertEquals("A2 does not return Chip for type.","Chip",testItem.getType());
		assertEquals("Current Order not initialized to 0.",0,testMachine.getNumOfOrders());
	}

}
