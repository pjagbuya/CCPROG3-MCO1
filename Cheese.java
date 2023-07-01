/** The class Cheese represents a sub type of VM_Item
 * that will be an option in the available items that a regular
 * vending machine can have and combinations for the special
 * vending machine
  *
  * @author Paul Josef P. Agbuya
  * @author Vince Kenneth D. Rojo
  * @version 1.0
  */

public class Cheese extends VM_Item
{


    /**
     * This constructor initializes a Cheese's name, price and
     * calories based on the given parameters
     * 
     * @param name name of this Cheese item
     * @param price price of this item
     * @param calories  number of calories this contains
     */
    public Cheese(String name, 
                  double price, 
                  int calories)
    {
        super(name, price, calories);

    }
}
