


// PAUL - Some painting of some strings to be printed
public class Paint {


    public static void turnOnCyan(){
        System.out.print("\033[1;36m");
    }
    public static void turnOnOrange(){
        System.out.print("\033[1;38;5;202m");
    }
    public static void turnOnRed(){
        System.out.print("\033[1;31m");
    }
    public static void turnOnYellow(){
        System.out.print("\033[1;33m");
    }
    public static void turnOnGreen(){
        System.out.print("\033[1;32m");
    }
    public static void turnOffColor(){
        System.out.print("\033[0m");
    }


    public static String paintTextOrange(String sText)
    {
        return "\033[1;38;5;202m" + sText + "\033[0m";
    }

    public static String paintTextCyan(String sText)
    {
        return "\033[1;36m" + sText + "\033[0m";
    }

    public static String paintTextRed(String sText)
    {
        return "\033[1;31m" + sText + "\033[0m";
    }
    
    public static String paintTextYellow(String sText)
    {
        return "\033[1;33m" + sText + "\033[0m";
    }
    public static String paintTextMagenta(String sText)
    {
        return "\033[1;35m" + sText + "\033[0m";
    }
    public static String paintTextGreen(String sText)
    {
        return "\033[1;32m" + sText + "\033[0m";
    }


}
