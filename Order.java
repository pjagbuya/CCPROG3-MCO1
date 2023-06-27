import java.util.LinkedHashMap;
public class Order
{
    public Order()
    {
        pendingOrder = new LinkedHashMap<String, Integer>();
        totalCostOfOrder = 0;

    }

    public boolean addOrder(VM_Slot slot, int itemQty)
    {
        if(slot == null)
            return false;
        else if( slot.getSlotItemStock() == 0 || itemQty > slot.getSlotItemStock() || itemQty <= 0)
            return false;
        else
        {
            pendingOrder.put(slot.getSlotItemName(), itemQty);
            totalCostOfOrder += slot.getItem().getItemPrice() * itemQty;
            return true;
        }
    }

    public LinkedHashMap<String, Integer> getPendingOrder() {
        return pendingOrder;
    }

    public double getTotalCost()
    {  
        return totalCostOfOrder;

    }
	

    public void clearOrder()
    {
        pendingOrder.clear();
        totalCostOfOrder = 0;
    }


    private LinkedHashMap<String, Integer> pendingOrder;
    private int totalCaloriesOfOrder;
    private double totalCostOfOrder;
}