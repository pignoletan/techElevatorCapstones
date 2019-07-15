package com.techelevator;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

public class TestFormatMoney {

	@Test
	public void testFormatMoney() throws FileNotFoundException, IOException {
		//File testFile = new File("vendingmachine.csv");
		//VendingMachine testMachine = new VendingMachine(testFile);
		int dollaBills = 5000000;
		String rightAmount = "$50000.00";
		boolean isEqual = rightAmount.equals(VendingMachine.formatMoney(dollaBills));
		assertTrue("Wrong format oh noes!",isEqual);
		dollaBills = 69;
		rightAmount = "$0.69";
		isEqual = rightAmount.equals(VendingMachine.formatMoney(dollaBills));
		assertTrue("No zero oh noes!",isEqual);
		dollaBills = 4;
		rightAmount = "$0.04";
		isEqual = rightAmount.equals(VendingMachine.formatMoney(dollaBills));
		assertTrue("Can't handle less than ten cents.",isEqual);
		dollaBills = 0;
		rightAmount = "$0.00";
		isEqual = rightAmount.equals(VendingMachine.formatMoney(dollaBills));
		assertTrue("Can't handle zero cents... makes no sense.",isEqual);
	}

}
