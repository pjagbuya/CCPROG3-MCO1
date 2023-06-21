import java.util.HashMap;
public class Order
{
    public Order()
    {
        pendingOrder = new HashMap<String, Integer>();
        totalCostOfOrder = 0;

    }

    public boolean addOrder(VM_Slot slot, int itemQty)
    {
        if(slot == null)
        {
            return false;
        }
        else if( slot.isEmpty() || itemQty > slot.getSlotItemStock() || itemQty <= 0)
            return false;

        else
        {
            pendingOrder.put(slot.getSlotItemName(), itemQty);
            totalCostOfOrder += slot.getItem().getItemPrice() * itemQty;
            return true;
        }

    }

    public HashMap<String, Integer> getPendingOrder() {
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


    private HashMap<String, Integer> pendingOrder;
    private double totalCostOfOrder;
}