import java.util.Scanner;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.InputMismatchException;
/**
 * The class Maintenance represents a Maintenance feature that allows the user to restock,
 * replenish, reprice, add/replace items, and update stock information of a Vending Machine
 *
 * 
 *
 * @author Paul Josef P. Agbuya
 * @author Vince Kenneth D. Rojo
 * @version 1.0
 */
public class Maintenance
{
	/**
	 * Takes note of the list of possible items in the program
	 *
	 * @param possibleItems list of names of all item classes in the program, excluding VM_Item
	
	 */
    public Maintenance(LinkedHashMap<String, Integer> possibleItems)
    {
        ITEM_OPTIONS = possibleItems;
    }


    /**
	 * Provides for manual restocking of the VM's sellable items. Keeps looping
     * and prompting the user of which item slot and how much of such would be restocked
	 * 
	 * @param vm the VM to restock
	 */
    public void restockItems(VM_Regular vm)
    {
        int qty;
		Scanner sc = new Scanner(System.in);
		String input;
		String inputQty;
		int slotNum;
		
        boolean anItemIsRestocked = false; // initially false
		boolean slotNumOutOfBounds = false; // intially false

        VM_Slot[] slots = vm.getSlots();


		while(true)
		try
		{
			System.out.println("Restock item: \033[1;32m<slot num> <qty>\033[0m");
			input = sc.next();
			if(input.equalsIgnoreCase("Y"))
				break;
			inputQty = sc.next();
				
			/* converts String input into numerical data types */
			slotNum = Integer.parseInt(input);
			qty = Integer.parseInt(inputQty);
				
			if(slotNum < 1 || slotNum > slots.length)
			{
				System.out.println("\033[1;38;5;202m-ERROR: SLOT NUM IS OUT OF BOUNDS\033[0m");
				slotNumOutOfBounds = true;
			}
        
            // Only proceed updating and allow adding if the slot
			if( !slotNumOutOfBounds && slots[slotNum-1].getSlotItemName() != null )
			{
				if ( !anItemIsRestocked )
				{
					updateStockedInfos(vm); /* creates a new inventory record */
					anItemIsRestocked = true;
				}
				slots[slotNum-1].addItemStock(qty);
			}
			else
				System.out.println("\033[1;38;5;202m-ERROR: SLOT HAS NO ASSIGNED ITEM. ENTER A DIFF. SLOT NUM\033[0m");		
		}
		catch (NumberFormatException e)
		{
			System.out.println("\033[1;38;5;202m-ERROR: SLOT HAS NO ASSIGNED ITEM. ENTER A DIFF. SLOT NUM\033[0m");		
		}
		
		sc = null;

    }

    /**
	 * Provides for repricing of the VM's items, via console prompting
	 *
	 * @param vm the VM whose items are to be replaced
	 */
	public void repriceItems(VM_Regular vm) 
	{
		double amt;
		Scanner sc = new Scanner(System.in);
		String input;
		String inputAmt;

		int slotNum;

        VM_Slot[] slots = vm.getSlots();

		System.out.println("Our minimum price for an item is \033[1;33m50 cents (0.5)\033[0m");
		while(true)
		try
		{
			System.out.println("Reprice item (\033[1;33mEnter Y to confirm/finish\033[0m): \033[1;32m<slot num> <new price>\033[0m");
			input = sc.next();
			if(input.equalsIgnoreCase("Y"))
				break;
			inputAmt = sc.next();
		
			/* conversion of String input into numerical data types */
			slotNum = Integer.parseInt(input);
			amt = Double.parseDouble(inputAmt);
			
			// Ensure slot num given is positive
			if(slotNum >= 1 && slotNum <= slots.length)
				if( slots[slotNum-1].getSlotItemName() != null ) {

					slots[slotNum-1].repriceItem(amt);
				}
				else
					System.out.println("\033[1;38;5;202m-ERROR: SLOT DOES NOT HOLD THIS ITEM. ENTER A DIFF. SLOT NUM\033[0m");		
			else
				System.out.println("\033[1;38;5;202m-ERROR: SLOT NUM OUT OF BOUNDS\033[0m");
		}	
		catch (NumberFormatException e)
		{
			System.out.println("\033[1;38;5;202m-ERROR: SLOT DOES NOT HOLD THIS ITEM. ENTER A DIFF INPUT\033[0m");	
		}
		catch (InputMismatchException e)
		{
			System.out.println("\033[1;38;5;202m-ERROR: SLOT DOES NOT HOLD THIS ITEM. ENTER A DIFF INPUT\033[0m");	
		}
		
		sc = null;
	}


    /**
	 * Provides for manual replenishing of the VM's cash reserves
	 *
	 * @param vm is the VM that will receive more cash reserves
	 */
	public void replenishDenominations(VM_Regular vm) {
		Scanner sc = new Scanner(System.in);
		String input = null;
		String inputQty = null;
		
        Money vmMoney;
        double origMoney;

		double denom;
		int qty;
		vmMoney = vm.getCurrentMoney();


		origMoney = vmMoney.getTotalMoney();

        
		System.out.println("Current Denominations");
		for(Map.Entry<String,Integer> tempEntry2 : vmMoney.getDenominations().entrySet())
		{
			String denomination = tempEntry2.getKey();
			int count = tempEntry2.getValue();
			System.out.println(denomination + ": \033[1;33m" + count + "\033[0m");
		}
		/* inserting  bills/coins */
		while(true)
		try
		{
			System.out.print("Replenish Denominations: \033[1;32m<bill/coin value> <quantity>\033[0m\n >>");
			input = sc.next();
			if(input.equalsIgnoreCase("Y"))
				break;
			inputQty = sc.next();
				
			/* conversion of String input into numerical data types */
			qty = Integer.parseInt(inputQty);
			denom = Double.parseDouble(input);
						
			if( Money.getValToStr().get(denom) != null )
				vm.getCurrentMoney().addBillsOrCoins(denom, qty);
			else
				System.out.println("\033[1;38;5;202m-ERROR: DENOMINATION DOES NOT EXIST\033[0m");	
		}
		catch (NumberFormatException e)
		{
			e.printStackTrace();
		}


		//Show denominations only if there are changes
		if(vmMoney.getTotalMoney() != origMoney)
		{
			System.out.println("Resulting Denominations");
			for(Map.Entry<String,Integer> tempEntry2 : vmMoney.getDenominations().entrySet())
			{
				String denomination = tempEntry2.getKey();
				int count = tempEntry2.getValue();
				System.out.println(denomination + ": \033[1;33m" + count + "\033[0m");
			}
		}

		
		sc = null;
	}

    /**
	 * Provides console based prompting replacing the items in a slot, or for filling up a slot with a null name
	 *
     * @param vm the VM whose slots are to be replaced/filled in
	 */
	public void replaceItemStock(VM_Regular vm)
	{
		int i;
		int qty;
		int slotNum;
		Scanner sc = new Scanner(System.in);
		String input;
		String inputQty;
		String inputSlotNum;
		boolean stockIsReplaced = false; // initially false
		boolean sameNameExists = false; // initially false
        VM_Slot[] slots = vm.getSlots();

		while(true)
		try
		{
			/* asking for item(s) to add */

			System.out.println("Replace/Fill in with these" + USER_HELP + ": \033[1;32m<name> <qty>\033[0m");
			input = sc.next();
			if(input.equalsIgnoreCase("Y"))
				break;
			inputQty = sc.next();
			
			/* asking for which slot to replace/fill in */
			System.out.println("in Stock of Slot No: \033[1;32m<num>\033[0m");
			inputSlotNum = sc.next();
			
			/* converting String input into numerical values */
			qty = Integer.parseInt(inputQty);
			slotNum = Integer.parseInt(inputSlotNum);
			
			for(i = 0; i < slots.length; i++)
				if(	slots[i].getSlotItemName() != null &&
					slots[i].getSlotItemName().equalsIgnoreCase(input) &&
					slots[i].getSlotItemStock() != 0)
				{
					System.out.println("-ERROR: SLOT WITH SAME NAME EXISTS");
					sameNameExists = true;
				}
			
			
			if(	Main.getPossibleItems().get(input.toUpperCase()) != null 	// If indicated item name corresponds to one of the item classes
				&& qty > 0							
				&& !sameNameExists)
			{
				stockIsReplaced = true;
				vm.addItemStock(input, slotNum-1, qty);
			}
			else
				System.out.println("\033[1;38;5;202m-ERROR: NON-EXISTENT CLASS/NON-POSITIVE QUANTITY\033[0m");
				
		}
		catch(NumberFormatException e)
		{
			System.out.println("\033[1;38;5;202m-ERROR: INPUT MUST BE <DOUBLE> <INTEGER>\033[0m");
		}
		
		if(stockIsReplaced)
			updateStockedInfos(vm);
		
		sc = null;
	}
	/**
	 * This method updates the stocked infos by instantiating a new Stocked info
     * It will also reset all slot stored Profit and Items sold
	 *
	 * @param vm the VM that will save a copy of its current inventory (as a VM_StockedInfo object)
	 */
	public void updateStockedInfos(VM_Regular vm) {
		int i;
		VM_Slot[] slots;
		vm.addStockInd();
		slots = vm.getSlots();

		for(i = 0; i < slots.length; i++)
			if(slots[i] != null)
			{
				// Clear all storedProfit 
				slots[i].clearStoredProfit();

				// resets no. of sold items per slot back to
				slots[i].setSlotItemSold(0); 
			}
				
	
	}


	/**
	 * This method provides a simple collection of cash reserves from VM
     * via console prompts
	 *
	 * @param vm the VM from which to collect (subtract) reserves														  
	 */
	public void collectCashReserves(VM_Regular vm)
	{
		String input;
		String inputQty;
		double denom;
		int qty;
		Scanner sc = new Scanner(System.in);
   
		LinkedHashMap<String, Integer> reserves = vm.getCurrentMoney().getDenominations();
		boolean canSubtract;
		
		
		while(true)
		{
			/* Displays denominations currently in cash reserves */
			System.out.println("\nDENOMINATIONS IN STOCK:");
			for(Map.Entry<String, Integer> m : reserves.entrySet() )
				System.out.println( m.getValue() + " pc(s). " + m.getKey() );
		
			/* asks user to collect denominations from VM */
			while(true)
			try
			{
				canSubtract = true; // initially true
				System.out.print("\nSubtract from cash reserves \033[1;32m<cash> <number>\033[0m"+ USER_HELP + "\n>> ");
				input = sc.next();
				if(input.equalsIgnoreCase("Y"))
					break;
				inputQty = sc.next();
				
				denom = Double.parseDouble(input);
				qty = Integer.parseInt(inputQty);
				
				/* input validation */ 	
				if( Money.getValToStr().get(denom) == null ) {																		/* if denomination does not exist in the standard set */
					System.out.println("\033[1;38;5;202m-ERROR: INVALID DENOMINATION\033[0m");
					canSubtract = false;
				} if( qty < 0 ) {																									/* if no. of pieces to subtract is negative */
					System.out.println("\033[1;38;5;202m-ERROR: NEGATIVE SUBTRACTION NOT ALLOWED\033[0m");
					canSubtract = false;
				} if( Money.getValToStr().get(denom) != null && reserves.get(Money.getValToStr().get(denom)) - qty < 0 ) {			/* if VM cannot release that number of pieces of the denomination */
					System.out.println("\033[1;38;5;202m-ERROR: SUBTRACTION RESULTS IN NEGATIVE DENOMINATIONS\033[0m");
					canSubtract = false;
				}
				
				if( canSubtract )
				{
					reserves.put( Money.getValToStr().get(denom), reserves.get(Money.getValToStr().get(denom)) - qty );
					System.out.println("\033[1;32mSUCCESSFULLY SUBTRACTED\033[0m");
				}
			}
			catch(NumberFormatException e)
			{
				System.out.println("\033[1;38;5;202m-ERROR: INPUT MUST BE <DOUBLE> <INTEGER>\033[0m");
			}
			
			if(input.equalsIgnoreCase("Y"))
				break;
		}
		
		sc = null;
	}
	
	/** the list of items that exist in the universe of the program */
	private final LinkedHashMap<String, Integer> ITEM_OPTIONS;	
	/** the prompt for user to use "Y" when they want to proceed to next section */
	private static final String USER_HELP = "(\033[1;33m" + "Enter 'Y' to confirm prompt" + "\033[0m)";
}