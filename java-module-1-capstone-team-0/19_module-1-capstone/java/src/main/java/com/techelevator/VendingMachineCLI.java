package com.techelevator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**************************************************************************************************************************
*  This is your Vending Machine Command Line Interface (CLI) class
*  
*  It is instantiated and invoked from the VendingMachineApp (main() application)
*  
*  Your code should be placed in here
***************************************************************************************************************************/
import com.techelevator.view.Menu;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE      = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT          = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS,
													    MAIN_MENU_OPTION_PURCHASE,
													    MAIN_MENU_OPTION_EXIT
													    };
	
	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT      = "Select Product";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION          = "Finish Transaction";
	private static final String[] PURCHASE_MENU_OPTIONS = { PURCHASE_MENU_OPTION_FEED_MONEY,
															PURCHASE_MENU_OPTION_SELECT_PRODUCT,
															PURCHASE_MENU_OPTION_FINISH_TRANSACTION
													    };
	private static VendingMachine theVendingMachine;
	
	private Menu vendingMenu;              // Menu object to be used by an instance of this class
	
	public VendingMachineCLI(Menu menu) throws FileNotFoundException, IOException {  // Constructor - user will pas a menu for this class to use
		this.vendingMenu = menu; // Make the Menu the user object passed, our Menu
		
		try {
			File inventoryList = new File("vendingmachine.csv");
			theVendingMachine = new VendingMachine(inventoryList);
		}
		catch (FileNotFoundException e) {
			System.out.println("Error: The input file doesn't exist. Please make sure vendingmachine.csv is properly configured.");
			System.exit(1);
		}
	}
	/**************************************************************************************************************************
	*  VendingMachineCLI main processing loop
	*  
	*  Display the main menu and process option chosen
	***************************************************************************************************************************/

	public void run() throws FileNotFoundException, IOException {

		boolean shouldProcess = true;         // Loop control variable
		
		while(shouldProcess) {                // Loop until user indicates they want to exit
			
			String choice = (String)vendingMenu.getChoiceFromOptions(MAIN_MENU_OPTIONS);  // Display menu and get choice
			
			switch(choice) {                  // Process based on user menu choice
			
				case MAIN_MENU_OPTION_DISPLAY_ITEMS:
					displayItems();           // invoke method to display items in Vending Machine
					break;                    // Exit switch statement
			
				case MAIN_MENU_OPTION_PURCHASE:
					purchaseItems();          // invoke method to purchase items from Vending Machine
					break;                    // Exit switch statement
			
				case MAIN_MENU_OPTION_EXIT:
					endMethodProcessing();    // Invoke method to perform end of method processing
					shouldProcess = false;    // Set variable to end loop
					break;                    // Exit switch statement
			}	
		}
		return;                               // End method and return to caller
	}
/********************************************************************************************************
 * Methods used to perform MAIN MENU processing
 ********************************************************************************************************/
	public /*static*/ void displayItems() {      // static attribute used as method is not associated with specific object instance
		theVendingMachine.printInventory();
	}
	
	
	public /*static*/ void purchaseItems() throws FileNotFoundException, IOException {	 // static attribute used as method is not associated with specific object instance

			boolean shouldProcess = true;         // Loop control variable
			
			while(shouldProcess) {                // Loop until user indicates they want to exit
				
				System.out.println();
				String dollaBills = VendingMachine.formatMoney(theVendingMachine.getCurBalance());
				System.out.print("Current balance: " + dollaBills);
				
				String choice = (String)vendingMenu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);  // Display menu and get choice
				
				switch(choice) {                  // Process based on user menu choice
				
					case PURCHASE_MENU_OPTION_FEED_MONEY:
						askForMoney();
						//theVendingMachine.relevantMethod;           // invoke method to feed money
						break;                    // Exit switch statement
				
					case PURCHASE_MENU_OPTION_SELECT_PRODUCT:
						askForItem();          // invoke method to select product
						break;                    // Exit switch statement
				
					case PURCHASE_MENU_OPTION_FINISH_TRANSACTION:
						//theVendingMachine.relevantMethod;    // Invoke method to finish transaction
						eatItems();
						shouldProcess = false;    // Set variable to end loop
						break;                    // Exit switch statement

				}
				
				
			}
			
			return;                               // End method and return to caller
		}
		
	public static void endMethodProcessing() throws IOException { // static attribute used as method is not associated with specific object instance
		System.out.println("Skynet thanks you for using the Vendo-Matic 500!");
		theVendingMachine.closeWriter();
		theVendingMachine.generateSalesReport();
	}
	
/********************************************************************************************************
 * * Methods used to perform PURCHASE MENU processing
********************************************************************************************************/
	
	public static void askForMoney() throws FileNotFoundException, IOException {
		
		boolean finished = false;
		
		while(!finished) {
		
			System.out.print("Enter the amount you are depositing ($1 $2 $5 $10): ");
			Scanner reader  = new Scanner(System.in);
			String input = reader.nextLine();
			
			finished = theVendingMachine.feedMoney(input);
			
		}
	}
	
	public static void askForItem() throws FileNotFoundException, IOException {
		boolean finished = false;
		
		while (!finished) {
			System.out.print("Enter the code for the desired item (or type \"exit\"): ");
			Scanner reader = new Scanner(System.in);
			String input = reader.nextLine();
			input = input.toUpperCase();
			
			if(input.equals("EXIT")) {
				finished = true;
				continue;
			}
			
			finished = theVendingMachine.hasKey(input);
			int temp = theVendingMachine.getCurBalance(); //remembers the current balance we started with so we can log it
			if (!finished) {
				System.out.println("Product code not found.");
				continue;
			}
			VendingSlot slot = theVendingMachine.getSlotByKey(input);
			if (slot.getCurQuant() == 0) {
				finished = false;
				System.out.println("Sold out :(");
				continue;
			}
			
			VendingItem item = slot.getNextItem();
			int price = item.getPrice();
			if (theVendingMachine.getCurBalance() < price) {
				System.out.println("Insufficient funds.");
				continue;
				//System.out.println("Your ass is broke!");
			}
			
			String itemName = item.getProductName();
			System.out.println("You successfully purchased a " + itemName + ".");
			System.out.println("Thank you! :D");
			
			theVendingMachine.chargeMoney(price);
			theVendingMachine.addItem(item);
			theVendingMachine.updateLog(itemName + " " + input,temp,theVendingMachine.getCurBalance());
			slot.removeItem(1);
		}
	}
	
	public void eatItems() throws FileNotFoundException, IOException { //displays the disgusting sounds the user makes when eating their orders
		int maxIter = theVendingMachine.getNumOfOrders();
		for (int i = 0; i < maxIter; i++) {
			VendingItem item = theVendingMachine.popOrder();
			String sound = item.getSound();
			System.out.println(sound);
		}
		if (theVendingMachine.getCurBalance() != 0) {
			String formattedMoney = VendingMachine.formatMoney(theVendingMachine.getCurBalance());
			System.out.println("Your total change is " + formattedMoney + ", dispensed as:");
			theVendingMachine.printChange();
		} else {
			System.out.println("You are owed no change.");
		}
	}

}
