import java.util.LinkedHashMap;
import java.util.Map;

public class VM_StockedInfo {

    
    public VM_StockedInfo(VM_Regular vmMachine)
    {
        int i;
        int stock;
        money = new Money();
        money.acceptDenominations(vmMachine.getCurrentMoney());
        VM_Slot[] slots = vmMachine.getSlotsCopy();

        
        VM_Slot slot;
        itemSlotsAndStock = new LinkedHashMap<VM_Slot, Integer>(); 


        
        stock = -1;
        // Iterates through all slots of the vmMachine
        for(i = 0; i < vmMachine.getSlots().length; i++)
        {
            // set defaultly that slot is empty
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

    public boolean isEmptyData()
    {
        if(money == null || itemSlotsAndStock.isEmpty())
        {
            return true;
        }
        for(Map.Entry<VM_Slot, Integer> stockAndSlotEntry : itemSlotsAndStock.entrySet())
        {
            if(stockAndSlotEntry.getKey() != null && !stockAndSlotEntry.getKey().getSlotItemName().equalsIgnoreCase(""))
            {
                return false;
            }
                
        }

        return true;
    }

    public Money getMoney() {
        return money;
    }

    public LinkedHashMap<VM_Slot, Integer> getItemSlotsAndStock() {
        return itemSlotsAndStock;
    }


    private LinkedHashMap<VM_Slot, Integer> itemSlotsAndStock;
    
    private Money money;
}
