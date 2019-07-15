package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class VendingMachine {
	private Map<String,VendingSlot> inventory = new TreeMap<String,VendingSlot>();
	private LinkedList<VendingItem> currentOrder = new LinkedList<VendingItem>(); //keeps track of items sold
	private int totalSales = 0; //total money deposited
	private int curBalance = 0;
	private File auditLog = new File("Log.txt");
	private File salesReport = new File("report.txt");
	public final static DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	private final PrintWriter writer = new PrintWriter(auditLog); //private so people can't touch our file
	
	public int getCurBalance() {
		return curBalance;
	}
	
	public VendingSlot getSlotByKey(String key) {
		return inventory.get(key);
	}
	
	public int getNumOfOrders() {
		return currentOrder.size();
	}
	
	public boolean hasKey(String key) {
		return inventory.containsKey(key);
	}
	
	public int getTotalSales() {
		return totalSales;
	}
	

	public VendingMachine(File allItems) throws FileNotFoundException, IOException {
		Scanner reader = new Scanner(allItems);
		while (reader.hasNextLine()) {
			String curLine = reader.nextLine();
			String[] information = curLine.split("\\|");
			try {
				for (int i = 0; i < 4; i++) {
					if (information[i].equals("")) {
						System.out.println("Hey man you're missing info in your vendingmachine.csv, tell us about your chips yo.");
						System.exit(1);
					}
				}
				String location = information[0];
				String itemName = information[1];
				String itemType = information[3];
				if (!itemType.equals("Gum")
						&&!itemType.equals("Candy")
						&&!itemType.equals("Drink")
						&&!itemType.equals("Chip")) {
					System.out.println("Error: Wrong snack type listed in vendingmachine.csv.");
					System.exit(1);
				}
				int itemPrice = (int)(Double.parseDouble(information[2]) * 100);
			
				VendingItem curItem = new VendingItem(itemName,itemType,itemPrice);
				VendingSlot curSlot = new VendingSlot(curItem,5);
				inventory.put(location,curSlot);
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("Error: Incomplete product information in vendingmachine.csv.");
				System.exit(1);
			} catch (NumberFormatException e) {
				System.out.println("Error: Price of items is improperly formatted in vendingmachine.csv.");
				System.exit(1);
			}
			
			try {
				auditLog.createNewFile();
			}
			catch (FileNotFoundException e) {
				
			}
		}
	}
	
	public void printInventory() {
		for (Map.Entry<String,VendingSlot> curEntry : inventory.entrySet()) {
			String productCode = curEntry.getKey();
			VendingSlot slotToPrint = curEntry.getValue();
			VendingItem itemToPrint = slotToPrint.getNextItem();
			String curName = itemToPrint.getProductName();
			int curPrice = itemToPrint.getPrice();
			System.out.print(productCode + " " + curName + " " + formatMoney(curPrice) + " ");
			slotToPrint.printQuant();
			System.out.println();
		}
	}
	
	public static String formatMoney(int money) {
		String result = "$";
		int dollars = money / 100;
		int cents = money - (dollars*100);
		
		result += dollars + ".";
		if(cents<10) {
			result += "0";
		}
		
		return result + cents;
	}
	
	public boolean feedMoney(String money) throws FileNotFoundException, IOException {
		
		if(money.substring(0,1).equals("$")) {
			money = money.substring(1);
		}
		
		int cents = 0;
		
		try {
			cents = Integer.parseInt(money) * 100; 
		}
		catch(NumberFormatException e) {
			System.out.println("Please do not input letters, decimal numbers, or special characters.");
			return false;
		}
		
		if(cents == 100 || cents == 200 || cents == 500 || cents == 1000 || cents == 0) {
			curBalance += cents;
			updateLog("FEED MONEY",cents,curBalance);
			return true;
		}
		
		System.out.println("Please input only 1, 2, 5, or 10.");
		return false;
		
	}
	
	public int chargeMoney(int price) {
		curBalance -= price;
		totalSales += price;
		return curBalance;
	}
	
	public void addItem(VendingItem item) {
		currentOrder.add(item);
	}
	
	public VendingItem popOrder() {
		return currentOrder.pop();
	}
	
	public void printChange() throws FileNotFoundException, IOException {
		int numOfQuarters = 0;
		int numOfDimes = 0;
		int numOfNickels = 0;
		updateLog("GIVE CHANGE",curBalance,0);
		while (curBalance > 4) {
			if (curBalance >= 25) {
				numOfQuarters++;
				curBalance -= 25;
			} else if (curBalance >= 10) {
				numOfDimes++;
				curBalance -= 10;
			} else if (curBalance >= 5) {
				numOfNickels++;
				curBalance -= 5;
			}
		}
		if (numOfQuarters > 0) {System.out.println(numOfQuarters + " quarters.");}
		if (numOfDimes > 0) {System.out.println(numOfDimes + " dimes.");}
		if (numOfNickels > 0) {System.out.println(numOfNickels + " nickels.");}
	}
	
	public void updateLog(String command,int firstValue,int secondValue) throws FileNotFoundException {
		Date date = new Date();
		String firstStr = formatMoney(firstValue);
		String secondStr = formatMoney(secondValue);
		String concat = String.format("%20s: %22s: %8s: %8s", dateFormat.format(date), command, firstStr, secondStr);
		writer.println(concat);
		//writer.println(dateFormat.format(date) + " " + command + ":\t" + firstStr + "\t" + secondStr + "\n");
	}
	
	public void generateSalesReport() throws IOException {
		
		PrintWriter salesWriter = new PrintWriter(salesReport);
		salesReport.createNewFile();
		
		for(Map.Entry<String,VendingSlot> entry : inventory.entrySet()) {
			
			VendingSlot slotRecord = entry.getValue();
			String prodName = slotRecord.getNextItem().getProductName();
			int numSold = (slotRecord.getStartQuant() - slotRecord.getCurQuant());
			
			salesWriter.println(prodName + " | " + numSold);
		}
		
		salesWriter.println("***Total Sales*** " + formatMoney(totalSales));
		
		salesWriter.flush();
		salesWriter.close();
			
	}
	
	public void closeWriter() {
		writer.flush();
		writer.close();
	}
	
}
