import java.util.LinkedHashMap;

/**
 * The class Order represents an order, which contains a list of items to-be-bought.
 *
 * If transaction was successful, then the order contains a list of bought items.
 *
 * @author Paul Josef P. Agbuya
 * @author Vince Kenneth D. Rojo
 * @version 1.0
 */
public class Order
{
	/**
     * Intializes pendingOrder, totalCostOfOrder, and Calories to be 0
     * 
     */
    public Order()
    {
        pendingOrder = new LinkedHashMap<String, Integer>();
        totalCostOfOrder = 0;
        totalCalories = 0;

    }


	/**
     * This method Adds a new item to the order and records the required quantity, or
	 * replaces the original desired quantity with a newer itemQty.
     * 
     * @param slot the slot whose item is being bought
     * @param itemQty the number of items to be bought
     * @return true if order list was modified in any manner, false otherwise
     */
    public boolean addOrder(VM_Slot slot, 
                            int itemQty)
    {
        if(slot == null)
            return false;
        else if( slot.getSlotItemStock() == 0 || itemQty > slot.getSlotItemStock() || itemQty <= 0)
            return false;
        else
        {
            pendingOrder.put(slot.getSlotItemName(), itemQty);
            totalCostOfOrder += slot.getItem().getItemPrice() * itemQty;
            totalCalories += slot.getItem().getItemCalories() * itemQty;

            return true;
        }
    }
	
	
	/**
     * Gets the list of items to be bought
     * 
     * @return the list of what and how many items are to be released
     */
    public LinkedHashMap<String, Integer> getPendingOrder() {
        return pendingOrder;
    }
	
	
	/**
     * Gets the total cost of all items in the list,
     * based on item prices at the time of computation
     * 
     * @return the total cost of the order
     */
    public double getTotalCost()
    {  
        return totalCostOfOrder;

    }

    /**
     * This method gets the total calories that this order contains
     * 
     * @return the number of calories that are contained within this order
     */
    public int getTotalCalories() {
        return totalCalories;
    }
	
	/**
     * Clears the current order list and resets totalCostOfOrder back to zero and Calories back to 0
     * 
     */
    public void clearOrder()
    {
        pendingOrder.clear();
        totalCostOfOrder = 0;
        totalCalories = 0;
    }

  

   


	/* the list of item types in the order, and the desired quantity of each */
    private LinkedHashMap<String, Integer> pendingOrder;
	/* total cost of all items in the order, based on their selling prices */
    private double totalCostOfOrder;
    /* total calories of order, based on the initialized kCal */
    private int totalCalories;
 
}