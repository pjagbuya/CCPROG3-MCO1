import java.util.Scanner;
import java.util.LinkedHashMap;

public class VM_Regular {
	public VM_Regular( int nOfSlots, int item_max) {
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


	// I think this function won't encompass, slots index currently, and will be hard to track once there are more slots
	// How about add the name of the product and it searches for the name instead
	// Before we release stock, or even allow such, we must
	/**
	 * looks for the slot associated with a given item name, and tells that slot to sell a specified amount of that item
	 * has no input validation (use the hasEnoughStock() and hasEnoughChange() methods prior to this)
	 *
	 */
	public void releaseStock(int[] quantities) {
		int i;
		for(i = 0; i < slots.length; i++)
			slots[i].releaseStock(quantities[i]);
	}
	
	public boolean hasEnoughStock(int[] quantities) {
		int i = 0;
		
		boolean stockHasRequiredQuantities = true; // initially true
		while(i < slots.length) {
			// if the current slot does not hold the required quantity of its item
			if( !(slots[i].hasEnoughStock(quantities[i])) )
				stockHasRequiredQuantities = false;
			i++;
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
	
	public double getTotalOfMoneyReserves() {
		return currentMoney.getTotalMoney();
	}
	
	public boolean canGiveChange(double amt, LinkedHashMap<String, Integer> duplicateOfDenomMap) {
		return execGiveChange(amt, duplicateOfDenomMap);
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

	private boolean execGiveChange(double amt, LinkedHashMap<String, Integer> duplicateOfDenomMap)
	{
		LinkedHashMap<String, Integer> denominations = currentMoney.getDenominations();

		if(amt >= 1000.0 && denominations.get("One Thousand Bill") > 0) {
			duplicateOfDenomMap.put("One Thousand Bill", duplicateOfDenomMap.get("One Thousand Bill") - 1);
			return canGiveChange(amt-1000.0, duplicateOfDenomMap);
		}
		else if(amt >= 500.0 && denominations.get("") > 0) {
			duplicateOfDenomMap.put("Five Hundred Bill", duplicateOfDenomMap.get("Five Hundred Bill") - 1);
			return canGiveChange(amt-500.0, duplicateOfDenomMap);
		}
		else if(amt >= 200.0 && denominations.get("Two Hundred Bill") > 0) {
			duplicateOfDenomMap.put("Two Hundred Bill", duplicateOfDenomMap.get("Two Hundred Bill") - 1);
			return canGiveChange(amt-200.0, duplicateOfDenomMap);
		}
		else if(amt >= 100.0 && denominations.get("One Hundred Bill") > 0) {
			duplicateOfDenomMap.put("One Hundred Bill", duplicateOfDenomMap.get("One Hundred Bill") - 1);
			return canGiveChange(amt-100.0, duplicateOfDenomMap);
		}
		else if(amt >= 50.0 && denominations.get("Fifty Bill") > 0) {
			duplicateOfDenomMap.put("Fifty Bill", duplicateOfDenomMap.get("Fifty Bill") - 1);
			return canGiveChange(amt-50.0, duplicateOfDenomMap);
		}
		else if(amt >= 20.0 && denominations.get("Twenty Bill") > 0) {
			duplicateOfDenomMap.put("Twenty Bill", duplicateOfDenomMap.get("Twenty Bill") - 1);
			return canGiveChange(amt-20.0, duplicateOfDenomMap);
		}
		else if(amt >= 20.0 && denominations.get("Twenty Coin") > 0) {
			duplicateOfDenomMap.put("Twenty Coin", duplicateOfDenomMap.get("Twenty Coin") - 1);
			return canGiveChange(amt-20.0, duplicateOfDenomMap);
		}
		else if(amt >= 10.0 && denominations.get("Ten Coin") > 0) {
			duplicateOfDenomMap.put("Ten Coin", duplicateOfDenomMap.get("Ten Coin") - 1);
			return canGiveChange(amt-10.0, duplicateOfDenomMap);
		}
		else if(amt >= 5.0 && denominations.get("Five Coin") > 0) {
			duplicateOfDenomMap.put("Five Coin", duplicateOfDenomMap.get("Five Coin") - 1);
			return canGiveChange(amt-5.0, duplicateOfDenomMap);
		}
		else if(amt >= 1.0 && denominations.get("One Coin") > 0) {
			duplicateOfDenomMap.put("One Coin", duplicateOfDenomMap.get("One Coin") - 1);
			return canGiveChange(amt-1.0, duplicateOfDenomMap);
		}
		else if(amt >= 0.25 && denominations.get("Twenty Five Cents") > 0) {
			duplicateOfDenomMap.put("Twenty Five Cents", duplicateOfDenomMap.get("Twenty Five Cents") - 1);
			return canGiveChange(amt-0.25, duplicateOfDenomMap);
		}
		else if(amt >= 0.05 && denominations.get("Five Cents") > 0) {
			duplicateOfDenomMap.put("Five Cents", duplicateOfDenomMap.get("Five Cents") - 1);
			return canGiveChange(amt-0.05, duplicateOfDenomMap);
		}
		else if(amt >= 0.01 && denominations.get("One Cent") > 0) {
			duplicateOfDenomMap.put("One Cent", duplicateOfDenomMap.get("One Cent") - 1);
			return canGiveChange(amt-0.01, duplicateOfDenomMap);
		}
		else if(amt == 0)
			return true;
		else
			return false;		
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