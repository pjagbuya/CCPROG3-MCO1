import java.util.Scanner;
import java.util.HashMap;

public class VM_Regular {
	public VM_Regular( int nOfSlots ) {
		slots = new VM_Slot[nOfSlots];
		currentMoney = new Money();
	}

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
		System.out.println("Total cost: " + totalCost);
		return totalCost;
	}
	
	public double getTotalOfMoneyReserves() {
		return currentMoney.getTotalMoney();
	}
	
	public boolean canGiveChange(double amt, HashMap<String, Integer> duplicateOfDenomMap) {
		return currentMoney.canGiveChange(amt, duplicateOfDenomMap);
	}
	
	public void displayAllItems() {
		int i;
		for(i = 0; i < slots.length; i++)
			slots[i].displayAllItems();
	}
	
	public void setSlot(VM_Slot slot, int slotId) {
		slots[slotId] = slot;
	}
	
	public Money getCurrentMoney() {
			return currentMoney;
	}
	

	private VM_Slot[] slots;
	private Money currentMoney;
}