import java.util.Scanner;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.InputMismatchException;

/*
	javac Main.java && javac Money.java && javac VM_Slot.java && javac VM_Regular.java && javac VM_Item.java && javac VM_StockedInfo.java
	javac MainDisplay.java && javac Order.java
	javac Cream.java && javac Egg.java && javac Kangkong.java && javac Lemon.java
	javac Milk.java && javac Powder.java && javac Salt.java && javac Sugar.java
	javac Chicken.java && javac BBQ.java && javac Flour.java
	
 */

/**
 * Citations:
 * https://ascii-generator.site/t/
 */

public class Main{
    public static void main(String[] args) {
		MainDisplay mDisplay = new MainDisplay();
		mDisplay.displayWelcome();
		//md.displayChoices();
		Scanner sc = new Scanner(System.in);
		String input;
		String inputQty;
		VM_Regular vm = null;
		int noOfSlots;
		int noOfItems; // no. of items PER SLOT
		int qty;
		double amt;
		String vmName;
		int i;
		
		
		// all the hashmaps :>
		LinkedHashMap<String, Integer> duplicate = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> payment = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> change = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> initialStock = null;
		LinkedHashMap<Double, Integer> initialCash = null;
		LinkedHashMap<String, Integer> possibleItems = new LinkedHashMap<String, Integer>();
		Order order = new Order();
		
		// setting the item types
		possibleItems.put("CHEESE", 0);
		possibleItems.put("COCOA", 0);
		possibleItems.put("CREAM", 0);
		possibleItems.put("EGG", 0);
		possibleItems.put("KANGKONG", 0);
		possibleItems.put("MILK", 0);
		possibleItems.put("SALT", 0);
		possibleItems.put("SUGAR", 0);
		
		possibleItems.put("POWDER", 0); // delete
		possibleItems.put("LEMON", 0); // delete
		
		possibleItems.put("CHICKEN", 0); // add
		possibleItems.put("BBQ", 0); // add
		possibleItems.put("FLOUR", 0); // add
		
		
		while(true) 
		{
			System.out.print(	"[C] Create a New Vending Machine\n" +
								"[E] Exit\n" +
								">> ");
			input = sc.next();
			
			if(input.equalsIgnoreCase("C"))
			/* Create a Vending Machine */
			while(true) 
			{
				System.out.print("[R] Regular Vending Machine\n[S] Special Vending Machine\n>> ");
				input = sc.next();
				if(input.equalsIgnoreCase("R"))
				{
					while(true)
					{
						// clearing away old vending machine
						vm = null;
						
						// starting from a blank hashmap of initial items
						initialStock = new LinkedHashMap<String, Integer>();
						
						// starting from a blank hashmap of initial cash reserves
						initialCash = new LinkedHashMap<Double, Integer>();
						
						/* Setting Regular VM's No. of Slots and Max No. of Items per Slot */
						try
						{
							System.out.println("Please indicate: ");
							System.out.print("Name\n>> ");
							vmName = sc.next();
							System.out.print("No. of slots (min. of 8)\n>> ");
							noOfSlots = sc.nextInt();
							System.out.print("Max. items per slot (min. of 10)\n>> ");
							noOfItems = sc.nextInt();
							if(noOfSlots < 8 || noOfItems < 10)
								System.out.println("-ERROR: PARAMETER(S) TOO SMALL");
							else
							{
								vm = new VM_Regular(noOfSlots, noOfItems);
								vm.setName(vmName);
							}
						}
						catch (InputMismatchException e)
						{
							System.out.println("-ERROR: NON-INTEGER INPUT");
						}
						
						
						
						/* Setting Initial Stocks */
						if(vm != null)
						while(true)
						{
							try
							{
								System.out.print("Specify initial stocks\n>> ");
								input = sc.next();
								if( input.equalsIgnoreCase("Y") )
									break;
								inputQty = sc.next();
									
								
								qty = Integer.parseInt(inputQty);
								
								if(qty >= 0)
									if( possibleItems.get( input.toUpperCase() ) != null )
										initialStock.put(input, qty);
									else
										System.out.println("-ERROR: UNKNOWN ITEM CLASS");		
								else
									System.out.println("-ERROR: NEGATIVE QUANTITIES NOT ALLOWED");		
							}
							catch(NumberFormatException e)
							{
								System.out.println("-ERROR: NON-DOUBLE INPUT");
							}
						}
						
						
						/* Setting Initial Cash Reserves */
						if(vm != null)
						while(true)
						{
							try
							{
								System.out.print("Specify initial cash reserves\n>> ");
								input = sc.next();
								if( input.equalsIgnoreCase("Y") )
									break;
								inputQty = sc.next();
								
								
								amt = Double.parseDouble(input);
								qty = Integer.parseInt(inputQty);
								
								if(qty >= 0)
									if( Money.getValToStr().get(amt) != null )
										initialCash.put(amt, qty);		
									else
										System.out.println("-ERROR: INVALID DENOMINATION");		
								else
									System.out.println("-ERROR: NEGATIVE QUANTITIES NOT ALLOWED");		
							}
							catch(NumberFormatException e)
							{
								System.out.println("-ERROR: NON-DOUBLE INPUT");
							}
						}
						
						
						// final check for exiting while-loop
						if ( input.equalsIgnoreCase("Y") )
							break;
					}
					
					/* Initializing Vending Machine Items */
					i = 0;
					if(vm != null)
					if( initialStock.size() > 0 )
					{
						for( String s : initialStock.keySet() )
						{	
							vm.addItemStock(s, i, initialStock.get(s));
							i++;
						}
					}
					
					/* Initializing Vending Machine Cash Reserves */
					if(vm != null)
					for( Double d : initialCash.keySet() )
						vm.addBillsOrCoins(d, initialCash.get(d)); 
					
					
					break;
				}
				else if(input.equalsIgnoreCase("S"))
					System.out.println("SPECIAL VENDING MACHINE UNAVAILABLE :<");
				else
					System.out.println("-ERROR: CHOOSE A VALID VENDING MACHINE TYPE");
			}
			else if(input.equalsIgnoreCase("E"))
				break;
			else
				vm = null;
				
			
			
			
			/* Test a Vending Machine */   // assumes only Regular Vending Machine is available
			if( vm != null )
				System.out.println("\nVENDING MACHINE CREATION SUCCESSFUL!\n");
			if( vm != null )
			while(true) 
			{	
				
				System.out.print("[V] Vending Features\n[M] Maintenance Features\n[C] Create a New Vending Machine\n>> ");
				input = sc.next();
				
				if(input.equalsIgnoreCase("V"))
				{
					vm.sellingOperation(duplicate, payment, change, order);
				}
				else if(input.equalsIgnoreCase("M"))
				{
					while(true)
					{
						System.out.print(	"[1] Restock Items\n" +
											"[2] Replace/Fill with Items\n" +
											"[3] Replenish Money\n" +
											"[4] Set Price\n" +
											"[5] Collect Money\n" +
											"[6] Order History\n" +
											"[7] Stocked Information\n" +
											"[8] Exit to Test A Vending Machine\n" +
											">> ");
						input = sc.next();
						if(input.equalsIgnoreCase("1"))
						{
							vm.emptyOrderHistory();
							vm.restockItems();
						}
						else if(input.equalsIgnoreCase("2"))
							vm.replaceItemStock(possibleItems);
						else if(input.equalsIgnoreCase("3"))
							vm.replenishDenominations();
						else if(input.equalsIgnoreCase("4"))
							vm.repriceItems();
						else if(input.equalsIgnoreCase("5"));
							// PLEASE FILL IN
						else if(input.equalsIgnoreCase("6"))
							vm.displayOrderHistory();
						else if(input.equalsIgnoreCase("7"))
							vm.displayAllStockInfo();
						else if(input.equalsIgnoreCase("8"))
							break;
						else;
					}
				}
				else if(input.equalsIgnoreCase("C"))
					break;
				else;
			}
		}
		
		
		if(vm != null)
		{
			System.out.println("\nFINAL STOCKS: ");
			vm.displayAllItems();
		}
		
		sc.close();
    }

	/*
    public void displayChoices(){
        Paint.turnOnYellow();
        System.out.println("Choices:");
        System.out.println("\t[1] Make Regular Vending Machine");
        System.out.println("\t[2] Make Special Vending Machine");
        System.out.println("\t[3] Test Vending Features");
        System.out.println("\t[4] Test Maintenance Features");
        System.out.println("\t[5] Exit");
        System.out.println();

        Paint.turnOffColor();
    }
	*/
}

