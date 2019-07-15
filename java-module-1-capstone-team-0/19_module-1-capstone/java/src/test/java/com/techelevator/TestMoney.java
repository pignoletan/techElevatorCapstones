package com.techelevator;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

public class TestMoney {

	@Test
	public void testMoney() throws FileNotFoundException, IOException {
		File testFile = new File("vendingmachine.csv");
		VendingMachine testMachine = new VendingMachine(testFile);
		testMachine.feedMoney("10");
		assertEquals("Can't take money.",1000,testMachine.getCurBalance());
		testMachine.feedMoney("$10");
		assertEquals("Can't accept $.",2000,testMachine.getCurBalance());
		testMachine.chargeMoney(777);
		assertEquals("Can't charge money.",1223,testMachine.getCurBalance());
		testMachine.chargeMoney(1223);
		assertEquals("Can't charge down to zero.",0,testMachine.getCurBalance());
	}

}
