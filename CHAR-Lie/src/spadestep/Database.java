 
package spadestep;

import java.lang.String;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import spadestep.Segment;

public class Database 
{
    public Database(String name)
    {
        filename = name;
    }
    
    private static String filename = "";
    
    private static List<String> database = new ArrayList<String>();
    
    private static final Random rnd = new Random(System.nanoTime() % 100000);
    
    public static void createDatabase() throws FileNotFoundException, IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String str;
        
        while ((str = reader.readLine()) != null)
        {
            database.add(str);
        }
    }
    
    public static void editDatabase(String input, String output)
    {
        for (int i = 0; i < database.size(); i++)
        {
            String line = database.get(i);
            if (line.contains(output))
            {
                database.add(i + 1, input); 
                break;
            }
        }
    }
    
    public static void write() throws IOException 
    {
        System.out.println(database.size());
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        for (int i = 0; i < database.size(); i++)
        {
            String str = "";
            
            str = database.get(i);
            
            writer.write(str);
            writer.newLine();
            writer.flush();
        }
        writer.close();
    }
    
    public String read(boolean query, String prevInput)
    {
        if (query)
            {
                int wordCounter = 0;
                ArrayList<String> words = new ArrayList<>();
                int wordStart = 0;
                String word;
                String lowerCase = prevInput;
                lowerCase.toLowerCase();

                for (int j = 0; j < prevInput.length(); j++)
                {
                    if ( j == 0 && Segment.isSegment(lowerCase.charAt(j)))
                    {
                        wordStart = j;
                    }
                    
                    else if (!Segment.isSegment(lowerCase.charAt(j - 1)) && Segment.isSegment(lowerCase.charAt(j)))
                    {
                        wordStart = j;
                    }

                    else if (Segment.isSegment(lowerCase.charAt(j - 1)) && !Segment.isSegment(lowerCase.charAt(j)))
                    {
                        wordCounter++;
                        word = lowerCase.substring(wordStart, j);
                        words.add(word);
                        word = "";
                    }
                    
                    else if(Segment.isSegment(lowerCase.charAt(j)) && j == lowerCase.length() - 1)
                    {
                        wordCounter++;
                        word = lowerCase.substring(wordStart);
                        words.add(word);
                        word = "";
                    }
                }
                
                int numerator = 0;
                
                for (int j = 0; j < database.size(); j++)
                {
                    double y = 0;
                    String db = database.get(j).toLowerCase();
                    for (int i = 0; i < database.get(j).length(); i++)
                    {
                        if (i != 0)
                        {
                            if (Segment.isSegment(db.charAt(i - 1)) && !Segment.isSegment(db.charAt(i)))
                            {
                                y++;
                            }
                            else if(Segment.isSegment(db.charAt(i)) && i == db.length() - 1)
                            {
                                y++;
                            }  
                        }
                    }
                    for (int i = 0; i < wordCounter; i++)
                    {
                        if (db.contains(words.get(i)) && numerator < wordCounter)
                        {
                            numerator++;
                            System.out.println("!" + words.get(i) + " " + database.get(j));
                        }
                    }
                    double x = (double) numerator;
                    
                    if (x / y > .5)
                    {
                        System.out.println(database.get(j) + (x / y));
                        return database.get(j + 1);
                    }
                    else
                    {
                        y = 0;
                        numerator = 0;
                    }
                }
                
                return database.get(1);
                    
            }
        else
        {
            for (int i = 0; i < database.size(); i++)
            {
                if (database.get(i).contains("?") && rnd.nextInt() % 2 == 0 && !query)
                    return database.get(i);
            }
        }
        return "Hello";
    }
    
    public static void cleanup()
    {
        for (int i = 0; i < database.size(); i++)
        {
            if (!(database.get(i).trim().length() > 0))
                database.remove(i);
        } 
    }
}
