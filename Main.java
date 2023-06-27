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
		VM_Regular vm = null;
		int noOfSlots;
		int noOfItems; // no. of items PER SLOT
		int qty;
		double amt;
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
		possibleItems.put("LEMON", 0);
		possibleItems.put("MILK", 0);
		possibleItems.put("POWDER", 0);
		possibleItems.put("SALT", 0);
		possibleItems.put("SUGAR", 0);
		
		
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
							System.out.print("No. of slots (min. of 8)\n>> ");
							noOfSlots = sc.nextInt();
							System.out.print("Max. items per slot (min. of 10)\n>> ");
							noOfItems = sc.nextInt();
							if(noOfSlots < 8 || noOfItems < 10)
								System.out.println("-ERROR: PARAMETER(S) TOO SMALL");
							else
								vm = new VM_Regular(noOfSlots, noOfItems);
						}
						catch (InputMismatchException e)
						{
							System.out.println("-ERROR: NON-INTEGER INPUT");
						}
						
						
						
						/* Setting Initial Stocks */
						if(vm != null)
						do
						{
							System.out.print("Specify initial stocks\n>> ");
							input = sc.next();
							if( !input.equalsIgnoreCase("Y") )
							{
								try
								{
									qty = sc.nextInt();
									if(qty >= 0)
									{
										if( possibleItems.get( input.toUpperCase() ) != null )
										{
											initialStock.put(input, qty);
										}
										else
											System.out.println("-ERROR: UNKNOWN ITEM CLASS");		
									}
									else
										System.out.println("-ERROR: NEGATIVE QUANTITIES NOT ALLOWED");		
								}
								catch (InputMismatchException e)
								{
									System.out.println("-ERROR: NON-INTEGER INPUT");
								}
							}
						} while ( !input.equalsIgnoreCase("Y") );
						
						
						/* Setting Initial Cash Reserves */
						if(vm != null)
						do
						{
		
							System.out.print("Specify initial cash reserves\n>> ");
							input = sc.next();
							if( !input.equalsIgnoreCase("Y") )
							{
								try
								{
									amt = Double.parseDouble(input);
									qty = sc.nextInt();
									if(qty >= 0)
										if( Money.getValToStr().get(amt) != null )
											initialCash.put(amt, qty);		
										else
											System.out.println("-ERROR: INVALID DENOMINATION");		
									else
										System.out.println("-ERROR: NEGATIVE QUANTITIES NOT ALLOWED");		
								}
								catch (InputMismatchException e)
								{
									System.out.println("-ERROR: NON-INTEGER INPUT");
								}
								catch(NumberFormatException e)
								{
									System.out.println("-ERROR: NON-DOUBLE INPUT");
								}
							}
						} while ( !input.equalsIgnoreCase("Y") );
						
						
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
							if( s.equalsIgnoreCase("Cheese") )
								vm.addItemStock(new Cheese("Cheese", 20.00, 42), initialStock.get(s), i);
							
							else if( s.equalsIgnoreCase("Cocoa") )
								vm.addItemStock(new Cocoa("Cocoa", 20.00, 42), initialStock.get(s), i);
							
							else if( s.equalsIgnoreCase("Cream") )
								vm.addItemStock(new Cream("Cream", 20.00, 42), initialStock.get(s), i);
							
							else if( s.equalsIgnoreCase("Egg") )
								vm.addItemStock(new Egg("Egg", 20.00, 42), initialStock.get(s), i);
							
							else if( s.equalsIgnoreCase("Kangkong") )
								vm.addItemStock(new Kangkong("Kangkong", 20.00, 42), initialStock.get(s), i);
							
							else if( s.equalsIgnoreCase("Lemon") )
								vm.addItemStock(new Lemon("Lemon", 20.00, 42), initialStock.get(s), i);
							
							else if( s.equalsIgnoreCase("Milk") )
								vm.addItemStock(new Milk("Milk", 27.00, 42), initialStock.get(s), i);
							
							else if( s.equalsIgnoreCase("Powder") )
								vm.addItemStock(new Powder("Powder", 20.00, 42), initialStock.get(s), i);
							
							else if( s.equalsIgnoreCase("Salt") )
								vm.addItemStock(new Salt("Salt", 20.00, 42), initialStock.get(s), i);
							
							else if( s.equalsIgnoreCase("Sugar") )
								vm.addItemStock(new Sugar("Sugar", 20.00, 42), initialStock.get(s), i);
						
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
				System.out.println("VENDING MACHINE CREATION SUCCESSFUL!\n");
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
											"[2] Replenish Money\n" +
											"[3] Set Price\n" +
											"[4] Collect Money\n" +
											"[5] Order History\n" +
											"[6] Stocked Information\n" +
											"[7] Exit to Test A Vending Machine\n" +
											">> ");
						input = sc.next();
						if(input.equalsIgnoreCase("1"))
						{
							vm.emptyOrderHistory();
							vm.restockItems();
						}
						else if(input.equalsIgnoreCase("2"))
							vm.replenishDenominations();
						else if(input.equalsIgnoreCase("3"))
							vm.repriceItems();
						else if(input.equalsIgnoreCase("4"));
							// PLEASE FILL IN
						else if(input.equalsIgnoreCase("5"))
							vm.displayOrderHistory();
						else if(input.equalsIgnoreCase("6"))
							vm.displayAllStockInfo();
						else if(input.equalsIgnoreCase("7"))
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

