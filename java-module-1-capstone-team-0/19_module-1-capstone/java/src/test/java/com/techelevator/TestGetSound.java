package com.techelevator;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestGetSound {

	@Test
	public void testGetSound() {
		VendingItem chip = new VendingItem("Skynet","Chip",50);
		VendingItem candy = new VendingItem("Skynet","Candy",50);
		VendingItem drink = new VendingItem("Skynet","Drink",50);
		VendingItem gum = new VendingItem("Skynet","Gum",50);
		String chipSound = "Crunch Crunch, Yum!";
		String candySound = "Munch Munch, Yum!";
		String drinkSound = "Glug Glug, Yum!";
		String gumSound = "Chew Chew, Yum!";
		assertEquals("Chip sound is wrong.",chipSound,chip.getSound());
		assertEquals("Candy sound is wrong.",candySound,candy.getSound());
		assertEquals("Drink sound is wrong.",drinkSound,drink.getSound());
		assertEquals("Gum sound is wrong.",gumSound,gum.getSound());
	}

}
