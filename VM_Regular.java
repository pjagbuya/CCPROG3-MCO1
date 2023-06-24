

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class VM_Regular {
	public VM_Regular( int nOfSlots, int item_max) 
	{
		slots = new VM_Slot[nOfSlots];

		for (int i = 0; i < nOfSlots; i++)
		{
			slots[i] = new VM_Slot(item_max);
		}
		currentMoney = new Money();
	}


	// Double check for some unrecalled conditions
	public void setSlot(VM_Item item, int stock, int ind)
	{
		for (int i = 0; i < slots.length; i++)
			if(ind == i && slots[i].getItem() == null)
				slots[i].addItemStock(item, stock);
		
	}
	
	public boolean acceptOrder(Order order, Money money)
	{
		ArrayList<Integer> quantities;
		int slotInd;
		if(order.getPendingOrder() != null && order.getPendingOrder().size() > 0)
		{
			
			quantities = new ArrayList<>(order.getPendingOrder().size());
			for(String tempOrder : order.getPendingOrder().keySet())
			{	
				slotInd = findSlotInd(tempOrder);
				if(slotInd >= 0)
				{
					return releaseStock(order.getPendingOrder().get(tempOrder), findSlotInd(tempOrder));
				}
			}

			
		}
		return false;
	}	
	public void subtractBillsOrCoins(Money money)
	{
		Money tempMoney = new Money(money);

		for(String tempString : money.getDenominations().keySet())
		{
			tempMoney.subtractBillsOrCoins(Money.moneyStringToValue(tempString), 
					currentMoney.getDenominations().get(tempString) - money.getDenominations().get(tempString));
			
		}
		currentMoney = tempMoney;

	}

	// I think this function won't encompass, slots index currently, and will be hard to track once there are more slots
	// How about add the name of the product and it searches for the name instead
	// Before we release stock, or even allow such, we must
	/**
	 * looks for the slot associated with a given item name, and tells that slot to sell a specified amount of that item
	 * has no input validation (use the hasEnoughStock() and hasEnoughChange() methods prior to this)
	 *
	 */
	public boolean releaseStock(int quantities, int ind) {
	
		if(quantities > 0 && ind >= 0)
		{
			slots[ind].releaseStock(quantities);
			return true;
		}

		return false;
			
	}
	
	public boolean hasEnoughStock(Order order) {
		int i = 0;
		
		boolean stockHasRequiredQuantities = true; // initially true
		
		for(String tempItemName : order.getPendingOrder().keySet())
		{

			i = findSlotInd(tempItemName);
			if(i >= 0)
			
			// if the current slot does not hold the required quantity of its item
				if( !(slots[i].hasEnoughStock(order.getPendingOrder().get(tempItemName))) )
					stockHasRequiredQuantities = false;
		}
			


		
		
		return stockHasRequiredQuantities;
	}
	
	public double computeTotalCost(int[] quantities) {
		int i = 0;
		double totalCost = 0.0;
		while(i < slots.length) {
			totalCost += slots[i].computePartialCost(quantities[i]);
			i++;
		}
		return totalCost;
	}

	public int findSlotInd(String slotName)
	{
		VM_Slot temp;
		int i;
		if(slotName.length() > 0)
		{
			for(i = 0; i < slots.length; i++)
				if(slots[i].getItem() != null)
					if(slots[i].getSlotItemName().equalsIgnoreCase(slotName))
						return i;
		}
		return -1;
	}
	public VM_Slot getSlot(String slotName)
	{

		VM_Slot temp;
		temp = null;
		if(slotName.length() > 0)
		{
			for(int i = 0; i < slots.length; i++)
				if(slots[i].getItem() != null)
					if(slots[i].getSlotItemName().equalsIgnoreCase(slotName))
						return slots[i];
		}
		return temp;
	}
	public VM_Slot getSlot(int ind)
	{

		VM_Slot temp;
		temp = null;
		if(ind >= 0 && ind < slots.length)
	
			return slots[ind];
		
		return temp;
	}

	public Money getCurrentMoney() {
		return currentMoney;
	}
	
	public double getTotalOfMoneyReserves() {
		return currentMoney.getTotalMoney();
	}
	
	public boolean canGiveChange(Order order, Money money) {
		return execGiveChange(order, money);
	}
	
	public void displayAllItems() {
		int i;
		for(i = 0; i < slots.length; i++)
			slots[i].displayAllItems();
	}
	
	public void addBillsOrCoins(double givenValue, int amt) 
	{
		currentMoney.addBillsOrCoins(givenValue, amt);
	}
	public LinkedHashMap<String, Integer> getDenominations() {
		return currentMoney.getDenominations();
	}
	
	public void addDenominations(LinkedHashMap<String, Integer> denominations) {
		currentMoney.addDenominations(denominations);
	}

	
	public void setDenominations(LinkedHashMap<String, Integer> denominations) {
		currentMoney.setDenominations(denominations);
	}


	public void updateMoneyWithChange(Order order, Money money)
	{
		double amt = money.getTotalMoney() - order.getTotalCost();
		
		Money tempMoney = new Money();
		LinkedHashMap<String, Integer>  denominations = currentMoney.getDenominations();
		LinkedHashMap<String, Integer>  duplicateOfDenomMap = currentMoney.getDenominations();

		
		while(amt != 0)
		{

			if(amt >= 1000.0 && denominations.get("One Thousand Bill") > 0) {
				duplicateOfDenomMap.put("One Thousand Bill", duplicateOfDenomMap.get("One Thousand Bill") - 1);
				amt -= 1000;
			}
			else if(amt >= 500.0 && denominations.get("") > 0) {
				duplicateOfDenomMap.put("Five Hundred Bill", duplicateOfDenomMap.get("Five Hundred Bill") - 1);
				amt-= 500;
			}
			else if(amt >= 200.0 && denominations.get("Two Hundred Bill") > 0) {
				duplicateOfDenomMap.put("Two Hundred Bill", duplicateOfDenomMap.get("Two Hundred Bill") - 1);
				amt -= 200;
			}
			else if(amt >= 100.0 && denominations.get("One Hundred Bill") > 0) {
				duplicateOfDenomMap.put("One Hundred Bill", duplicateOfDenomMap.get("One Hundred Bill") - 1);
				amt -= 100;
			}
			else if(amt >= 50.0 && denominations.get("Fifty Bill") > 0) {
				duplicateOfDenomMap.put("Fifty Bill", duplicateOfDenomMap.get("Fifty Bill") - 1);
				amt -= 50;
			}
			else if(amt >= 20.0 && denominations.get("Twenty Bill") > 0) {
				duplicateOfDenomMap.put("Twenty Bill", duplicateOfDenomMap.get("Twenty Bill") - 1);
				amt-= 20;
			}
			else if(amt >= 20.0 && denominations.get("Twenty Coin") > 0) {
				duplicateOfDenomMap.put("Twenty Coin", duplicateOfDenomMap.get("Twenty Coin") - 1);
				amt -= 20;
			}
			else if(amt >= 10.0 && denominations.get("Ten Coin") > 0) {
				
				duplicateOfDenomMap.put("Ten Coin", duplicateOfDenomMap.get("Ten Coin") - 1);
				amt -= 10;
			}
			else if(amt >= 5.0 && denominations.get("Five Coin") > 0) {
				duplicateOfDenomMap.put("Five Coin", duplicateOfDenomMap.get("Five Coin") - 1);
				amt -= 5;
			}
			else if(amt >= 1.0 && denominations.get("One Coin") > 0) {
				duplicateOfDenomMap.put("One Coin", duplicateOfDenomMap.get("One Coin") - 1);
				amt -= 1;
			}
			else if(amt >= 0.25 && denominations.get("Twenty Five Cents") > 0) {
				duplicateOfDenomMap.put("Twenty Five Cents", duplicateOfDenomMap.get("Twenty Five Cents") - 1);
				amt -= 0.25;
			}
			else if(amt >= 0.05 && denominations.get("Five Cents") > 0) {
				duplicateOfDenomMap.put("Five Cents", duplicateOfDenomMap.get("Five Cents") - 1);
				amt -= 0.05;
			}
			else if(amt >= 0.01 && denominations.get("One Cent") > 0) {
				duplicateOfDenomMap.put("One Cent", duplicateOfDenomMap.get("One Cent") - 1);
				amt -= 0.01;
			}
		}

		currentMoney.setDenominations(duplicateOfDenomMap);
		// currentMoney.addDenominations(tempMoney.getDenominations());

	}

	private boolean execGiveChange(Order order, Money money)
	{
		double amt = money.getTotalMoney() - order.getTotalCost();
		

		Money origMoney = money;
		LinkedHashMap<String, Integer>  denominations = currentMoney.getDenominations();
		LinkedHashMap<String, Integer>  duplicateOfDenomMap = currentMoney.getDenominations();

		
		while(amt != 0)
		{

			if(amt >= 1000.0 && denominations.get("One Thousand Bill") > 0) {
				duplicateOfDenomMap.put("One Thousand Bill", duplicateOfDenomMap.get("One Thousand Bill") - 1);
				amt -= 1000;
			}
			else if(amt >= 500.0 && denominations.get("") > 0) {
				duplicateOfDenomMap.put("Five Hundred Bill", duplicateOfDenomMap.get("Five Hundred Bill") - 1);
				amt-= 500;
			}
			else if(amt >= 200.0 && denominations.get("Two Hundred Bill") > 0) 
			{
				duplicateOfDenomMap.put("Two Hundred Bill", duplicateOfDenomMap.get("Two Hundred Bill") - 1);
				amt -= 200;
			}
			else if(amt >= 100.0 && denominations.get("One Hundred Bill") > 0) {
				duplicateOfDenomMap.put("One Hundred Bill", duplicateOfDenomMap.get("One Hundred Bill") - 1);
				amt -= 100;
			}
			else if(amt >= 50.0 && denominations.get("Fifty Bill") > 0) {
				duplicateOfDenomMap.put("Fifty Bill", duplicateOfDenomMap.get("Fifty Bill") - 1);
				amt -= 50;
			}
			else if(amt >= 20.0 && denominations.get("Twenty Bill") > 0) {
				duplicateOfDenomMap.put("Twenty Bill", duplicateOfDenomMap.get("Twenty Bill") - 1);
				amt-= 20;
			}
			else if(amt >= 20.0 && denominations.get("Twenty Coin") > 0) {
				duplicateOfDenomMap.put("Twenty Coin", duplicateOfDenomMap.get("Twenty Coin") - 1);
				amt -= 20;
			}
			else if(amt >= 10.0 && denominations.get("Ten Coin") > 0) {
				
				duplicateOfDenomMap.put("Ten Coin", duplicateOfDenomMap.get("Ten Coin") - 1);
				amt -= 10;
			}
			else if(amt >= 5.0 && denominations.get("Five Coin") > 0) {
				duplicateOfDenomMap.put("Five Coin", duplicateOfDenomMap.get("Five Coin") - 1);
				amt -= 5;
			}
			else if(amt >= 1.0 && denominations.get("One Coin") > 0) {
				duplicateOfDenomMap.put("One Coin", duplicateOfDenomMap.get("One Coin") - 1);
				amt -= 1;
			}
			else if(amt >= 0.25 && denominations.get("Twenty Five Cents") > 0) {
				duplicateOfDenomMap.put("Twenty Five Cents", duplicateOfDenomMap.get("Twenty Five Cents") - 1);
				amt -= 0.25;
			}
			else if(amt >= 0.05 && denominations.get("Five Cents") > 0) {
				duplicateOfDenomMap.put("Five Cents", duplicateOfDenomMap.get("Five Cents") - 1);
				amt -= 0.05;
			}
			else if(amt >= 0.01 && denominations.get("One Cent") > 0) {
				duplicateOfDenomMap.put("One Cent", duplicateOfDenomMap.get("One Cent") - 1);
				amt -= 0.01;
			}
			else
			{
				currentMoney.setDenominations(origMoney.getDenominations());
				return false;
			}


				
		}


		return true;
	}





	/**
	 * This method checks if each slot of the vending machine is filled with an item. If not
	 * it will return false
	 * 
	 * @return true if all slots contain an item with more than 0 in stock, otherwise false
	 */
	public boolean isThisValid()
	{
		for(VM_Slot tempSlot : slots)
		{
			if(tempSlot.getItem() == null || tempSlot.getSlotItemStock() == 0)
				return false;
		}
		return true;
	}

	private VM_Slot[] slots;
	private Money currentMoney;

}