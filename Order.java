import java.util.HashMap;

/** The class VM_Item represents an item
  * that is built within a slot that is
  * inside the vending machine 
  *
  * @author Paul Josef P. Agbuya
  * @author Vince Kenneth D. Rojo
  * @version 1.0
  */
public class Order
{

    /**
     * This constructor creates an Order with initialization
     * of an empty HashMap and totalCost of the Order to be 0
     */
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