package com.techelevator;

public class VendingItem {

	private String productName = "";
	private String type = "Gum";
	private int price = 50;
	
	//Three-param ctor. Price should be int in cents
	public VendingItem(String name,String type,int price) {
		if (!type.equals("Candy") //ensures only a valid type can be put in
				&&!type.equals("Chip")
				&&!type.equals("Drink")
				&&!type.equals("Gum")) {
				System.out.println("Be advised: " + type + " is an unsupported product type.");
				this.type = "Gum";
			}
		this.productName = name;
		this.type = type;
		this.price = price;
	}
	
	public String getSound() {
		switch(type) {
			case "Candy":
				return "Munch Munch, Yum!";
			case "Chip":
				return "Crunch Crunch, Yum!";
			case "Drink":
				return "Glug Glug, Yum!";
			case "Gum":
				return "Chew Chew, Yum!";
		}
		return "Skynet has become self-aware"; //this make Eclipse happy, unreachable code
	}
	
	public String getProductName() {
		return productName;
	}
	public String getType() {
		return type;
	}
	public int getPrice() {
		return price;
	}

	public VendingItem clone() {
		String newProductName = productName;
		String newType = type;
		int newPrice = price;
		VendingItem newVendingItem = new VendingItem(newProductName,newType,newPrice);
		return newVendingItem;
	}
	
	
}
