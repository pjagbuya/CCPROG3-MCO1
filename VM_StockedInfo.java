import java.util.LinkedHashMap;


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


        slot = null;
        stock = -1;
        // Iterates through all slots of the vmMachine
        for(i = 0; i < vmMachine.getSlots().length; i++)
        {
            if(slots[i] != null)
            {
                slot = slots[i];
                stock = slot.getSlotItemStock();
            }
            if(slot != null && stock >= 0)
                // Stores infor of name and stock
                itemSlotsAndStock.put(slot, stock);
                
        }

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
