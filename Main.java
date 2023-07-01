import java.util.Scanner;
import java.util.LinkedHashMap;

import java.util.InputMismatchException;

/** This class is a driver that simulates running the MCO1 project
 *  which involves creating a vending machine, ordering from said vending machine,
 *  restocking the vending machine, and purchasing items in the vending machine. There is
 *  also a seperate maintenance feature as such to restock/reprice/replace
  *
  * @author Paul Josef P. Agbuya
  * @author Vince Kenneth D. Rojo
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
		Maintenance maintenance = new Maintenance();
		SellingOperator sellingOperator;


		
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
		

		
		/* hashmaps, might remove or keep in MCO2 */
		Money duplicate = new Money();
		Money payment = new Money();
		Money change = new Money();

		LinkedHashMap<String, Integer> initialStock = null;
		LinkedHashMap<Double, Integer> initialCash = null;
		
		VM_Draw vmDraw;
		Order order = new Order();


		userHelp = "(\033[1;33m" + "Enter 'Y' to confirm prompt" + "\033[0m)";
		mainHelp.displayWelcome();
		vmDraw = null;
		while(true) 
		{
			System.out.print(	"\t\033[1;36m[C]\033[0m Create a New Vending Machine\n" +
								"\t\033[1;36m[E]\033[0m Exit\n" +
								">> ");
			input = sc.next();
			
			if(input.equalsIgnoreCase("C"))
			/* Create a Vending Machine */
			while(true) 
			{
				System.out.print("\t\033[1;36m[R]\033[0m Regular Vending Machine\n\t\033[1;36m[S]\033[0m Special Vending Machine\n>> ");
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
							System.out.println();
							System.out.println("\033[1;33mPlease indicate the details below: \033[0m" );
							System.out.print("Name of this regular vending machine: \n>> ");
							vmName = sc.next();
							System.out.print("No. of slots (\033[1;36mmin. of 8\033[0m)\n>> ");
							noOfSlots = sc.nextInt();
							System.out.print("Max. items per slot (\033[1;36mmin. of 10\033[0m)\n>> ");
							noOfItems = sc.nextInt();
							if(noOfSlots < VM_Regular.getMinSLOTS() || noOfItems < VM_Regular.getMaxITEMS())
								System.out.println("\033[1;38;5;202m-ERROR: PARAMETER(S) TOO SMALL\033[0m");
							else
							{
								vm = new VM_Regular(vmName, noOfSlots, noOfItems);
								break;
							}
						}
						catch (InputMismatchException e)
						{
							System.out.println("\033[1;38;5;202m-ERROR: NON-INTEGER INPUT\033[0m");
						}
					}
						
						
						
					/* Setting Initial Stocks */
					while(true)
					{
						try
						{
							//stops when there is too much inputs
							if(vm.getSlots().length == initialStock.size())
								break;

							System.out.print("Specify initial stocks, \033[1;32m<name> <number>\033[0m "+ userHelp + "\n>> ");
							input = sc.next();
							if( input.equalsIgnoreCase("Y" )) 
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
							if( input.equalsIgnoreCase("Y"))
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
						vm.getCurrentMoney().addBillsOrCoins(d, initialCash.get(d)); 
					
					
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
				maintenance.updateStockedInfos(vm);
				System.out.println("\033[1;32mVENDING MACHINE CREATION SUCCESSFUL!\033[0m\n");
			}
			
			/* Features of the Vending Machine */
			if(vm != null)
			while(true) 
			{	
				System.out.print("\t\033[1;36m[V]\033[0m Vending Features\n\t\033[1;36m[M]\033[0m Maintenance Features\n\t\033[1;36m[C]\033[0m Create a New Vending Machine\n>> ");
				input = sc.next();
				
				/* Vending Features */
				if(input.equalsIgnoreCase("V"))
				{
					if(vmDraw != null)
					{
						vmDraw.updateVM(vm);
						vmDraw.drawAndSetVM();
					}
					sellingOperator = new SellingOperator(vm);
					sellingOperator.sellingOperation(duplicate, payment, change, order);
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
							vmDraw.updateVM(vm);
							vmDraw.drawAndSetVM();
							vm.emptyOrderHistory();
							maintenance.restockItems(vm);
							vmDraw.updateVM(vm);
							vmDraw.drawAndSetVM();
						}
						else if(input.equalsIgnoreCase("2"))
						{
							vmDraw.updateVM(vm);
							vmDraw.drawAndSetVM();
							maintenance.replaceItemStock(vm);
							vmDraw.updateVM(vm);
							vmDraw.drawAndSetVM();
						}
							
						else if(input.equalsIgnoreCase("3"))
							maintenance.replenishDenominations(vm);
						else if(input.equalsIgnoreCase("4"))
						{
							vmDraw.updateVM(vm);
							vmDraw.drawAndSetVM();
							maintenance.repriceItems(vm);
							vmDraw.updateVM(vm);
							vmDraw.drawAndSetVM();
						}
							
						else if(input.equalsIgnoreCase("5"))
							maintenance.collectCashReserves(vm);
						else if(input.equalsIgnoreCase("6"))
							vm.displayOrderHistory();
						else if(input.equalsIgnoreCase("7"))
							vm.displayAllStockInfo();
						else if(input.equalsIgnoreCase("8"))
							break;
						else
							System.out.println("\033[1;38;5;202mNOT IN OPTIONS!\033[0m");
					}
				}
				
				/* triggers creation of a new Vending Machine */
				else if(input.equalsIgnoreCase("C"))
					break;
				/* catcher for non-options */
				else
					System.out.println("\033[1;38;5;202mNOT IN OPTIONS!\033[0m");
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

	/**
	 * Displays an ASCII art of the Welcome message for initial start of the program
	 */
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
	
	
	

	/* a list of all possible items in the program */
	private static LinkedHashMap<String, Integer> possibleItems = new LinkedHashMap<String, Integer>();
}

