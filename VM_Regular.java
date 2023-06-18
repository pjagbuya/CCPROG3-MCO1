import java.util.Scanner;

public class VM_Regular {
	public VM_Regular( int nOfSlots, Money money ) {
		slots = new VM_Slot[nOfSlots];
		currentMoney = new Money();
	}

	/**
	 * looks for the slot associated with a given item name, and tells that slot to sell a specified amount of that item
	 *
	 *
	 */
	public void sell(String name, int quantity) {
		int i = 0;
		while(i < slots.length && !(slots[i].getItemName().equalsIgnoreCase(name)))
			i++;
	
		if(i < slots.length)
			slot[i].sell(quantity);
	}
	
	public VM_Slot[] getSlots() {
		return slots;
	}
	
	public void setSlot(VM_Slot slot, int slotId) {
		slots[slotId] = slot;
	}

	public void setSlot
	
	

	private VM_Slot[] slots;
	private Money currentMoney;
}