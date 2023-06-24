public class VM_Draw {
    
    public VM_Draw(VM_Regular vmMachine)
    {
        drawAndSetVM(vmMachine);
    }

    public void drawAndSetVM(VM_Regular vmMachine)
    {
        int stringInd;
        String[] sampleString = new String[9];
        sampleString[0] = "abcd";
        sampleString[1] = "abcd";
        sampleString[2] = "Ramd";

        sampleString[3] = "Raid";
        sampleString[4] = "abcd";
        sampleString[5] = "Soud";
        sampleString[6] = "abcd";
        sampleString[7] = "abcd";
        sampleString[8] = "abcd";

        double samplePrice = 70.00;
        String stringPrice = samplePrice + "0";
        stringInd = 0;
        for (int i = 0; i < 15; i++) 
        {


            for (int j = 0; j < 27; j++) 
            {
                
                if( i % 5 == 0 || i % 5 == 4)
                {
                    if(j % 9 == 0 || j % 9 == 8)
                    {
                        System.out.print("+");
                    } else {
                        System.out.print("-");
                    }
                    
                }
                else{
                    if(j % 9 == 0 || j % 9 == 8)
                        System.out.print("|");
                    else if(i % 5 == 1 && j % 9 == 3){
                        System.out.print(sampleString[stringInd]);
                        j += 2; // alots the next two characters for the two characters of this word
                        stringInd++;
                    }
                    else if(i % 5 == 1 && (j % 9 < 3 || j % 9 > 5)){
                        System.out.print(" ");
                    } 
                    else if(i % 5 == 3 && j % 9 == 1)
                    {
                        if(stringPrice.length() < 6 ){
                            System.out.print(" ");
                        }

                        System.out.print(stringPrice);
                        j += 5;

                    }
                    else{
                        System.out.print(" ");
                    }



                }


            

                
                
            }

            System.out.println();
        }

      
    }
}
