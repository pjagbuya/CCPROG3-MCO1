import java.util.LinkedHashMap;
import java.util.Map;


/**
 * The class VM_StockedInfo represents inventory records of VM_Regular,
 * which in this design means storing copies of VM_Slot and
 * their corresponding stock counts
 *
 * @author Paul Josef P. Agbuya
 * @author Vince Kenneth D. Rojo
 * @version 1.0
 */
public class VM_StockedInfo {

    /**
     * Stores a copy of all slots of VM,
     * as well as the stock counts of each slot
     * 
     * @param vmMachine the VM_Regular whose inventory is to be recorded
     **/
    public VM_StockedInfo(VM_Regular vmMachine)
    {
        int i;
        int stock;
        money = new Money();
        money.acceptDenominations(vmMachine.getCurrentMoney());
        VM_Slot[] slots = vmMachine.getSlotsCopy();

        
        VM_Slot slot;
        itemSlotsAndStock = new LinkedHashMap<VM_Slot, Integer>(); 


        // Makes the default a negative int
        stock = -1;
        // Iterates through all slots of the vmMachine
        for(i = 0; i < vmMachine.getSlots().length; i++)
        {
            // set default that slot is empty
            slot = null;

            if(slots[i] != null)
            {
                // Identify the slot and stock
                slot = slots[i];
                stock = slot.getSlotItemStock();
            }

            // If the slot stored is not empty
            if(slot != null && stock >= 0)
                // Stores info of name and stock
                itemSlotsAndStock.put(slot, stock);
                
        }

    }
	
	
	/**
	 * Checks whether cash reserves and slots are either null or practically empty
	 *
	 * @return true if neither of the above conditions are met
	 */
    public boolean isEmptyData()
    {
        if(money == null || itemSlotsAndStock.isEmpty())
            return true;
        for(Map.Entry<VM_Slot, Integer> stockAndSlotEntry : itemSlotsAndStock.entrySet())
            if(stockAndSlotEntry.getKey() != null && !stockAndSlotEntry.getKey().getSlotItemName().equalsIgnoreCase(""))
                return false;

        return true;
    }
	
	
	/**
     * Gets the money object of this inventory record
     * 
     * @return the money tray of this inventory record
     */
    public Money getMoney() {
        return money;
    }
	
	
	/**
     * Gets the list containing copies of the slots and their corresponding stock counts at the time of recording
     * 
     * 
     * @return the list of slots and their stock counts
     */
    public LinkedHashMap<VM_Slot, Integer> getItemSlotsAndStock() {
        return itemSlotsAndStock;
    }
	
	
	/** the list containing copies of the slots and their corresponding stock counts at the time of recording */
    private LinkedHashMap<VM_Slot, Integer> itemSlotsAndStock;
    /** the money object, contains a record of the VM's cash reserves at the time of recording */
    private Money money;
}
