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

    // Move this to VM_Regular
    // private void displayAllInfo()
    // {
    //     for(Map.Entry<String,Integer> tempNamesAndStock: itemNamesAndStock.entrySet())
    //     {
    //         System.out.printf("\t| %20s | %20s | %11s | %20s | %20s \n", " Item Name ", "Item Init Stock ", "Items Sold", " Items in Stock", "Profit Collected");
    //         System.out.printf("\t| %20s | %20s | %11s | %20s | %20s \n", tempNamesAndStock.getKey(), tempNamesAndStock.getKey().toString());
    //         System.out.printf("\t| %20s | %20s | %11s | %20s | %20s \n", tempNamesAndStock.getKey(), tempNamesAndStock.getKey().toString());
    //     }
    // }

    private LinkedHashMap<VM_Slot, Integer> itemSlotsAndStock;
    
    private Money money;
}
