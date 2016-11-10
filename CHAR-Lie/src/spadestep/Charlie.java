
package spadestep;

import chatterbotapi.ChatterBot;
import chatterbotapi.ChatterBotFactory;
import chatterbotapi.ChatterBotSession;
import chatterbotapi.ChatterBotType;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Charlie 
{ 
    public enum State 
    {
       START, RUN, END, PAUSE
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException, Exception 
    {
        ChatterBotFactory factory = new ChatterBotFactory();

        ChatterBot bot1 = factory.create(ChatterBotType.CLEVERBOT);
        ChatterBotSession bot1session = bot1.createSession();
        Bot bot = new Bot();

        bot.setState(State.END);

        bot.start();
        String s = "Hi";
        String prevInput = "";
        Scanner reader = new Scanner(System.in);
        while (bot.getState() == State.RUN)
        {
            bot.run(prevInput);
            prevInput = reader.nextLine();
            bot.pause(prevInput);
            /*bot.pause(s);
            s = bot.run(prevInput);
            
            s = bot1session.think(s);
            prevInput = s;
            System.out.println("Cleverbot: " + s);*/
        }
        if (bot.getState() == State.END)
            bot.end();
    }
    
}
