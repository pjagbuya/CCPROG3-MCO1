
import java.util.LinkedHashMap;



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