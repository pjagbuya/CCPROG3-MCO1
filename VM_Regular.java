public class VM_Regular {
	public VM_Regular( int nOfSlots, Money money ) {
		slots = new VM_Slot[nOfSlots];
		currentMoney = new Money();
	}


	public double returnChange(Money actualCost, Money moneyReceived) {
		
	}

	public void updateMoney() {
	
	}
	
	public void viewSummaryTransac() {
		
	}
	
	public void setSlot(VM_Slot slot, int slotId) {
		slots[slotId] = slot;
	}
	
	

	private VM_Slot[] slots;
	private Money currentMoney;
	private VM_StockedInfo stockedInfo;
}