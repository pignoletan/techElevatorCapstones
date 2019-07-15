package com.techelevator;

import java.util.ArrayList;
import java.util.Stack;

public class VendingSlot {
	public final int startQuant;
	//public final int capacity;
	private int curQuant;
	private Stack<VendingItem> items = new Stack<VendingItem>();
	
	//Ctor for one kind of item in a slot
	public VendingSlot(VendingItem item, int startQuant) {
		this.startQuant = startQuant;
		this.curQuant = startQuant;
		for (int i = 0; i < startQuant; i++) {
			VendingItem newItem = item.clone();
			this.items.add(newItem);
		}
	}
	
	//Ctor for more than one kind of item in a slot
	public VendingSlot(Stack<VendingItem> items) {
		this.items = items;
		this.startQuant = items.size();
		this.curQuant = startQuant;
	}
	
	public int removeItem(int quant) {
		for (int i = 0; i < quant; i++) {
			curQuant -= 1;
			items.pop();
		}
		return curQuant;
	}
	
	public void printQuant() {
		if (curQuant == 0) {
			System.out.print("SOLD OUT");
		} else {
			System.out.print(curQuant);
		}
	}

	public int getCurQuant() {
		return curQuant;
	}
	
	public int getStartQuant() {
		return startQuant;
	}

	public Stack<VendingItem> getItems() {
		return items;
	}
	
	//returns the item that is next to be purchased
	public VendingItem getNextItem() {
		return items.peek();
	}
}
