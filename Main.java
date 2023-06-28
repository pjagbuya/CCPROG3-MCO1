import java.util.Scanner;
import java.util.LinkedHashMap;

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
		Main mainHelp = new Main();
		mDisplay.displayWelcome();
		//md.displayChoices();
		Scanner sc = new Scanner(System.in);
		String input;
		String userHelp;
		String inputQty;
		String vmName;
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
		VM_Draw vmDraw;
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
		

		userHelp = "(\033[1;33m" + "Enter 'Y' to confirm prompt" + "\033[0m)";
		vmDraw = null;
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
					mainHelp.displayPossibleItems(possibleItems);
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
							System.out.println("\033[1;33mPlease indicate the details below: \033[0m" );
							System.out.print("Name of this regular vending machine: \n>> ");
							vmName = sc.next();
							System.out.print("No. of slots (\033[1;36mmin. of 8\033[0m)\n>> ");
							noOfSlots = sc.nextInt();
							System.out.print("Max. items per slot (\033[1;36mmin. of 10\033[0m)\n>> ");
							noOfItems = sc.nextInt();
							if(noOfSlots < VM_Regular.getMinSLOTS() || noOfItems < VM_Regular.getMaxITEMS())
								System.out.println("-ERROR: PARAMETER(S) TOO SMALL");
							else
								vm = new VM_Regular(vmName, noOfSlots, noOfItems);
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
								System.out.print("Specify initial stocks, \033[1;32m<name> <number>\033[0m "+ userHelp + "\n>> ");
								input = sc.next();
								if( input.equalsIgnoreCase("Y") )
									break;
								inputQty = sc.next();
									
								
								qty = Integer.parseInt(inputQty);
								
								if(qty >= 0)
									if( possibleItems.get( input.toUpperCase() ) != null )
										initialStock.put(input, qty);
									else
										System.out.println("\033[1;38;5;202m-ERROR: UNKNOWN ITEM CLASS\033[0m");		
								else
									System.out.println("\033[1;38;5;202m-ERROR: NEGATIVE QUANTITIES NOT ALLOWED\033[0m");		
							}
							catch(NumberFormatException e)
							{
								System.out.println("\033[1;38;5;202m-ERROR: NON-DOUBLE INPUT\033[0m");
							}
						}
						
						
						/* Setting Initial Cash Reserves */
						if(vm != null)
						while(true)
						{
		

							try
							{
								System.out.print("Specify initial cash reserves \033[1;32m<cash> <number>\033[0m"+ userHelp + "\n>> ");
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
										System.out.println("\033[1;38;5;202m-ERROR: INVALID DENOMINATION\033[0m");		
								else
									System.out.println("\033[1;38;5;202m-ERROR: NEGATIVE QUANTITIES NOT ALLOWED\033[0m");		
							}
							catch(NumberFormatException e)
							{
								System.out.println("\033[1;38;5;202m-ERROR: NON-DOUBLE INPUT\033[0m");
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
					System.out.println("\033[1;38;5;202m-ERROR: CHOOSE A VALID VENDING MACHINE TYPE\033[0m");
			}
			else if(input.equalsIgnoreCase("E"))
				break;
			else
				vm = null;
				
			
			
			
			/* Test a Vending Machine */   // assumes only Regular Vending Machine is available
			if( vm != null )
			{
				vmDraw = new VM_Draw(vm);
				vmDraw.drawAndSetVM();
				vm.updateStockedInfos();
				System.out.println("\033[1;32mVENDING MACHINE CREATION SUCCESSFUL!\033[0m\n");
			}
				
			if( vm != null )
			while(true) 
			{	
				
				System.out.print("[V] Vending Features\n[M] Maintenance Features\n[C] Create a New Vending Machine\n>> ");
				input = sc.next();
				
				if(input.equalsIgnoreCase("V"))
				{
					if(vmDraw != null)
					{
						vmDraw.updateVM(vm);
						vmDraw.drawAndSetVM();
					}	
				
					vm.sellingOperation(duplicate, payment, change, order);
				}
				else if(input.equalsIgnoreCase("M"))
				{
					while(true)
					{
						System.out.print(	"\t\033[1;36m[1]\033[0m Restock Items\n" +
											"\t\033[1;36m[2]\033[0m Replace/Fill with Items\n" +
											"\t\033[1;36m[3]\033[0m Replenish Money\n" +
											"\t\033[1;36m[4]\033[0m Set Price\n" +
											"\t\033[1;36m[5]\033[0m Collect Money\n" +
											"\t\033[1;36m[6]\033[0m Order History\n" +
											"\t\033[1;36m[7]\033[0m Stocked Information\n" +
											"\t\033[1;36m[8]\033[0m Exit to Test A Vending Machine\n" +
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

	private void displayPossibleItems(LinkedHashMap<String, Integer> possibleItems)
	{
		System.out.println("Here are your available options to set an item to your vending Machine");
		for(String stringTemp : possibleItems.keySet())
		{
			System.out.println("\033[1;33m" + stringTemp + "\033[0m");
		}

	}
}

