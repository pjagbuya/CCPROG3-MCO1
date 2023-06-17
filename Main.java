import java.util.Scanner;

public class Main{
    public static void main(String[] args) {
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


        sc.close();
    }

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
}

