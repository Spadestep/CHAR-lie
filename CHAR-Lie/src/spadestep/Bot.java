
package spadestep;

import java.io.FileNotFoundException;
import java.io.IOException;
import spadestep.Charlie.State;

public class Bot 
{
    
    public Bot()
    {
        
    }
    
    
    private static String filename = "H:\\2016-17_year\\CHAR-Lie\\text\\CharlieDatabase.txt";
    private static State runState = State.END;
    private static String prevPrompt = "";
    
    public static Database db = new Database(filename);
   
    
    public static State getState()
    {
        return runState;
    }
    
    public static void setState(State state)
    {
        runState = state;
    }
    
    public static void start() throws IOException
    {
        if (!(runState == State.START))
            runState = State.START;
        db.createDatabase();
        System.out.println("Hello, my name is CHAR-lie. I will learn as you speak to me.");
        runState = State.RUN;
    }
    
    public static String run(String prevInput) throws FileNotFoundException 
    {
        if (!(runState == State.RUN))
            runState = State.RUN;
        
        String output = db.read((prevInput.contains("?")), prevInput);
        
        System.out.println("CHAR-lie:" + output);
        prevPrompt = output;
        return output;
    }
    
    public static void pause(String input) throws IOException
    {
        if (!(runState == State.PAUSE))
            runState = State.PAUSE;
        
        if (input.length() == 0)
        {
            runState = State.END;           
        }

        else 
            runState = State.RUN;
    }
    
    public static void end() throws IOException
    {
        db.cleanup();
        db.write();
        System.exit(0);
    }
}
