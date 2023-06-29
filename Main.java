import java.util.Scanner;
import java.util.LinkedHashMap;

import java.util.InputMismatchException;

/*
	javac Main.java && javac Money.java && javac VM_Slot.java && javac VM_Regular.java && javac VM_Item.java && javac VM_StockedInfo.java
	javac MainDisplay.java && javac Order.java && javac VM_Draw.java
	javac Cream.java && javac Egg.java && javac Kangkong.java && javac Cornstarch.java
	javac Milk.java && javac Tofu.java && javac Salt.java && javac Sugar.java
	javac Chicken.java && javac BBQ.java && javac Flour.java
	
 */

/**
 * Citations:
 * https://ascii-generator.site/t/
 */

/**
 * This class is the driver class of the Vending Machine Project
 *
 *
 */
public class Main{
	
	
	/**
	 * Determines all possible items in the program
	 *
	 */
	public Main()
	{
		possibleItems.put("CHEESE", 0);
		possibleItems.put("COCOA", 0);
		possibleItems.put("CREAM", 0);
		possibleItems.put("EGG", 0);
		possibleItems.put("KANGKONG", 0);
		possibleItems.put("MILK", 0);
		possibleItems.put("SALT", 0);
		possibleItems.put("SUGAR", 0);
		possibleItems.put("CORNSTARCH", 0);
		possibleItems.put("TOFU", 0);
		possibleItems.put("CHICKEN", 0);
		possibleItems.put("BBQ", 0);
		possibleItems.put("FLOUR", 0);
	}
	
	
	/**
	 * Main method
	 *
	 * @param args ...
	 */
    public static void main(String[] args) {
		
		Main mainHelp = new Main();
		mDisplay.displayWelcome();
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
		userHelp = "(\033[1;33m" + "Enter 'Y' to confirm prompt" + "\033[0m)";

		
		/* hashmaps, might remove or keep in MCO2 */
		LinkedHashMap<String, Integer> duplicate = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> payment = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> change = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> initialStock = null;
		LinkedHashMap<Double, Integer> initialCash = null;
		
		VM_Draw vmDraw;
		Order order = new Order();

		//userHelp = "(\033[1;33m" + "Enter 'Y' to confirm prompt" + "\033[0m)";
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
					mainHelp.displayPossibleItems();
					while(true)
					{
						/* clearing away old vending machine */
						vm = null;
						
						/* initializes blank hashmap of initial items */
						initialStock = new LinkedHashMap<String, Integer>();
						
						/* initializes blank hashmap of initial cash reserves */
						initialCash = new LinkedHashMap<Double, Integer>();
						
						/* Setting VM's No. of Slots and Max No. of Items per Slot */
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
							{
								vm = new VM_Regular(vmName, noOfSlots, noOfItems);
								break;
							}
						}
						catch (InputMismatchException e)
						{
							System.out.println("-ERROR: NON-INTEGER INPUT");
						}
					}
						
						
						
					/* Setting Initial Stocks */
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
							System.out.println("\033[1;38;5;202m-ERROR: ITEM CLASS DOES NOT EXIST\033[0m");
						}
					}
						
						
					/* Setting Initial Cash Reserves */
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
							System.out.println("\033[1;38;5;202m-ERROR: INPUT MUST BE <DOUBLE> <INTEGER>\033[0m");
						}
					}
						
					
					/* Initializing Vending Machine Stocks */
					i = 0;
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
				
			
			/* Test a Vending Machine */     // assumes only Regular Vending Machine is available
			if( vm != null )
			{
				vmDraw = new VM_Draw(vm);
				vmDraw.drawAndSetVM();
				vm.updateStockedInfos();
				System.out.println("\033[1;32mVENDING MACHINE CREATION SUCCESSFUL!\033[0m\n");
			}
			
			/* Features of the Vending Machine */
			if(vm != null)
			while(true) 
			{	
				System.out.print("[V] Vending Features\n[M] Maintenance Features\n[C] Create a New Vending Machine\n>> ");
				input = sc.next();
				
				/* Vending Features */
				if(input.equalsIgnoreCase("V"))
				{
					if(vmDraw != null)
					{
						vmDraw.updateVM(vm);
						vmDraw.drawAndSetVM();
					}	
					vm.sellingOperation(duplicate, payment, change, order);
				}
				/* Maintenance Features */
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
							vm.replaceItemStock();
						else if(input.equalsIgnoreCase("3"))
							vm.replenishDenominations();
						else if(input.equalsIgnoreCase("4"))
							vm.repriceItems();
						else if(input.equalsIgnoreCase("5"))
							vm.collectCashReserves();
						else if(input.equalsIgnoreCase("6"))
							vm.displayOrderHistory();
						else if(input.equalsIgnoreCase("7"))
							vm.displayAllStockInfo();
						else if(input.equalsIgnoreCase("8"))
							break;
						else
							System.out.println("NOT IN OPTIONS!");
					}
				}
				
				/* triggers creation of a new Vending Machine */
				else if(input.equalsIgnoreCase("C"))
					break;
				/* catcher for non-options */
				else
					System.out.println("NOT IN OPTIONS!");
			}
		}
		
		
		
		
		
		if(vm != null)
		{
			System.out.println("\nFINAL STOCKS: ");
			vm.displayAllItems();
		}
		
		sc.close();
    }
	
	
	/**
	 * Lists all items available in the program
	 *
	 */
	private void displayPossibleItems()
	{
		System.out.println("Here are your available options to set an item to your vending Machine");
		for(String stringTemp : possibleItems.keySet())
		{
			System.out.println("\033[1;33m" + stringTemp + "\033[0m");
		}

	}

	private void displayWelcome()
	{
        System.out.print("\033[1;33m");
        System.out.println("\t\t\t\t    :::       ::: :::::::::: :::         ::::::::   ::::::::  ::::    ::::  :::::::::: ");
        System.out.println("\t\t\t\t    :+:       :+: :+:        :+:        :+:    :+: :+:    :+: +:+:+: :+:+:+ :+:        ");
        System.out.println("\t\t\t\t    +:+       +:+ +:+        +:+        +:+        +:+    +:+ +:+ +:+:+ +:+ +:+        ");
        System.out.println("\t\t\t\t    +#+  +:+  +#+ +#++:++#   +#+        +#+        +#+    +:+ +#+  +:+  +#+ +#++:++#   ");
        System.out.println("\t\t\t\t    +#+ +#+#+ +#+ +#+        +#+        +#+        +#+    +#+ +#+       +#+ +#+        ");
        System.out.println("\t\t\t\t     #+#+# #+#+#  #+#        #+#        #+#    #+# #+#    #+# #+#       #+# #+#        ");
        System.out.println("\t\t\t\t      ###   ###   ########## ##########  ########   ########  ###       ### ########## ");

        System.out.print("\033[0m");

        System.out.println();
        System.out.println("\t\t\t\t\t\t\t\t\t ____   __ ");
        System.out.println("\t\t\t\t\t\t\t\t\t(_  _) /  \\ ");
        System.out.println("\t\t\t\t\t\t\t\t\t  )(  ( () )");
        System.out.println("\t\t\t\t\t\t\t\t\t (__)  \\__/ ");

        System.out.println();


     
        System.out.println("\t\t\t\t\033[1;44m\\ \\     /                   | _)                   \\  |               |     _)              \033[0m");
        System.out.println("\t\t\t\t\033[1;44m \\ \\   /    _ \\  __ \\    _` |  |  __ \\    _` |    |\\/ |   _` |   __|  __ \\   |  __ \\    _ \\ \033[0m");
        System.out.println("\t\t\t\t\033[1;44m  \\ \\ /     __/  |   |  (   |  |  |   |  (   |    |   |  (   |  (     | | |  |  |   |   __/ \033[0m");
        System.out.println("\t\t\t\t\033[1;44m   \\_/    \\___| _|  _| \\__,_| _| _|  _| \\__, |   _|  _| \\__,_| \\___| _| |_| _| _|  _| \\___| \033[0m");
        System.out.println("\t\t\t\t\033[1;44m                                        |___/                                               \033[0m");
        System.out.print("\033[0m");
        System.out.println();
        System.out.println();

    }
	
	
	/**
	 * Gets the list of all item subclasses available in the program
	 *
	 * @return the list of all possible items
	 */
	public static LinkedHashMap<String, Integer> getPossibleItems()
	{
		return possibleItems;
	}
	
	
	
	/* the prompt for user to use "Y" when they want to proceed to next section */
	private String userHelp;
	/* a list of all possible items in the program */
	private static LinkedHashMap<String, Integer> possibleItems = new LinkedHashMap<String, Integer>();
}

