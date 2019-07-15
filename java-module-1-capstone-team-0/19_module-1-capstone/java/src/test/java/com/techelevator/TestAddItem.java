package com.techelevator;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

public class TestAddItem {

	@Test
	public void testAddItem() throws FileNotFoundException, IOException {
		File testFile = new File("vendingmachine.csv");
		VendingMachine testMachine = new VendingMachine(testFile);
		VendingItem testItem = new VendingItem("A Sicilian","Drink",40000);
		testMachine.addItem(testItem);
		VendingItem itemToRead = testMachine.popOrder();
		boolean isEqual = testItem.getProductName().equals(itemToRead.getProductName());
		assertTrue("Did not pop the item you added.",isEqual);
		VendingItem otherTestItem = new VendingItem("Andrews keys","Chip",40);
		testMachine.addItem(testItem);
		testMachine.addItem(otherTestItem);
		itemToRead = testMachine.popOrder();
		isEqual = testItem.getProductName().equals(itemToRead.getProductName());
		assertTrue("Not popping in the right order.",isEqual);
		itemToRead = testMachine.popOrder();
		isEqual = otherTestItem.getProductName().equals(itemToRead.getProductName());
		assertTrue("Not popping in the right order.",isEqual);
	}

}
