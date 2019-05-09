
package ChatBash;


public class Key {
    //In order to Clear Console
    public static final String CLEAR_KEY = "\033[H\033[2J";
        //In order to Clear Console
    public static final String ERASE_LINE_UP = "\033[1A\033[1K";
    //Colours
    
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    
    public static final String[] COLOURS = {RED, GREEN, YELLOW, BLUE, PURPLE, CYAN};
}
