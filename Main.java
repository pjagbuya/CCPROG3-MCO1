import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

/*
	javac Main.java && javac Money.java && javac VM_Slot.java && javac VM_Regular.java && javac VM_Item.java
 */

public class Main{
    public static void main(String[] args) {
		/*
        Main md = new Main();
        Scanner sc = new Scanner(System.in);
        int userInt;
        
		
		
        // VM_Draw draw = new VM_Draw(null, 0);
        userInt = 0;

        // draw.drawAndSetVM();

        VM_Slot slotMilk = new VM_Slot(new VM_Item("Milk", 27.00, 42), 6);
        slotMilk.addStock(8);
        slotMilk.dislpayAllItems();
	

        while(userInt != 5){
            md.displayChoices();
            System.out.print("Enter a choice(1-5): ");
            userInt = sc.nextInt();
    
            switch(userInt){
                case 1:
                    System.out.println("\tLorem Ipsum make Regular Vending Machine");
                    break;
                case 2:
                    System.out.println("\tLorem Ipsum Make Special Vending Machine");
                    break;
                case 3:
                    System.out.println("\tLorem Ipsum Test Vending Features");
                    break;
                case 4:
                    System.out.println("\tLorem Ipsum Test Maintenance Features");
                    break;
                case 5:
                    System.out.println("\tNow exiting");
                    break;
            }
        }
		*/
		
		
		
		int[] quantities = new int[1];
		quantities[0] = 1;
		HashMap<String, Integer> denominations = new HashMap<String, Integer>();
		HashMap<String, Integer> currentMoney = new HashMap<String, Integer>();
		
		VM_Regular vm = new VM_Regular(1);
		VM_Slot milkSlot = new VM_Slot(new VM_Item("Milk", 27.00, 42), 6);
		
		vm.setSlot(milkSlot, 0);
		
		vm.displayAllItems();
		
		// duplicating denomination hashmap of VM
		currentMoney = vm.getCurrentMoney().getDenominations();
		for(String i : currentMoney.keySet()) {
			denominations.put(i, currentMoney.get(i));
		}
		
		//display duplicate of denomination hashmap
		for( Map.Entry m : denominations.entrySet() ) {
			System.out.println(m.getKey() + " " + m.getValue());
		}
		
		if(	vm.hasEnoughStock(quantities) &&
			(vm.getTotalOfMoneyReserves() - vm.computeTotalCost(quantities) >= 0) &&
			vm.canGiveChange(50 - vm.computeTotalCost(quantities), denominations) )
		{
			vm.releaseStock(quantities);
			//vm.getCurrentMoney().getDenominations() = denominations;
		}
		
		vm.displayAllItems();
		
		//display duplicate of denomination hashmap
		for( Map.Entry m : denominations.entrySet() ) {
			System.out.println(m.getKey() + " " + m.getValue());
		}
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

