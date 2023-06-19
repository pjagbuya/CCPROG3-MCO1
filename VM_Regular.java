import java.util.Scanner;

public class VM_Regular {
	public VM_Regular( int nOfSlots, Money money ) {
		slots = new VM_Slot[nOfSlots];
		currentMoney = new Money();
	}

	/**
	 * looks for the slot associated with a given item name, and tells that slot to sell a specified amount of that item
	 * has no input validation (use the hasEnoughStock() and hasEnoughChange() methods prior to this)
	 *
	 */
	public void sell(String name, int quantity) {
		int i = 0;
		while(i < slots.length && !(slots[i].getItemName().equalsIgnoreCase(name)))
			i++;
	
		if(i < slots.length)
			slot[i].sell(quantity);
	}
	
	public boolean hasEnoughStock(int[] quantities) {
		int i = 0;
		boolean stockHasRequiredQuantities = true; // initially true
		while(i < slots.length)
			// if the current slot does not hold the required quantity of its item
			if( !(slot[i].hasEnoughStock(quantities[i])) )
				stockHasRequiredQuantities = false;
		
		return stockHasRequiredQuantities;
	}
	
	public double computeTotalCost(int[] quantities) {
		int i = 0;
		double totalCost = 0.0;
		while(i < slots.length)
			totalCost += slots[i].computePartialCost(quantities[i]);
		return totalCost;
	}
	
	public boolean canGiveChange(double amt, HashMap<String, Integer> duplicateOfDenomMap) {
		return currentMoney.canGiveChange(amt, duplicateOfDenomMap);
	}
	
	public VM_Slot[] getSlots() {
		return slots;
	}
	
	public void setSlot(VM_Slot slot, int slotId) {
		slots[slotId] = slot;
	}
	

	private VM_Slot[] slots;
	private Money currentMoney;
}