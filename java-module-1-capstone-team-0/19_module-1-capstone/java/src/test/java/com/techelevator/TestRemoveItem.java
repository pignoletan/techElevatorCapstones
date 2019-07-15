package com.techelevator;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestRemoveItem {

	@Test
	public void testRemoveItem() {
		VendingItem testItem = new VendingItem("Frank Fella","Drink",20000);
		VendingSlot testSlot = new VendingSlot(testItem,5);
		testSlot.removeItem(1);
		assertEquals("Did not remove 1 item.",4,testSlot.getCurQuant());
		testSlot.removeItem(4);
		assertEquals("Did not remove 4 items.",0,testSlot.getCurQuant());
	}

}
