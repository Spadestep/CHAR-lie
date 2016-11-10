
package spadestep;

public class Segment 
{
    public Segment()
    {
    };
    
    public static boolean isSegment(char Char)
    {
        return (Character.isLetter(Char) || Char == '\'');
    }
}
