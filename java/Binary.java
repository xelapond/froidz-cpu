import java.util.HashMap;
/**
 * Deals with binary numbers.
 * 
 * @author Jacob Weiss
 * @version 0.0.1
 */
public class Binary
{
    private static HashMap<String, Integer> hexConverter = initHexConverter();    
    private static HashMap<String, Integer> initHexConverter()
    {
        HashMap<String, Integer> hexConverter = new HashMap();
        hexConverter.put("0", 0);
        hexConverter.put("1", 1);
        hexConverter.put("2", 2);
        hexConverter.put("3", 3);
        hexConverter.put("4", 4);
        hexConverter.put("5", 5);
        hexConverter.put("6", 6);
        hexConverter.put("7", 7);
        hexConverter.put("8", 8);
        hexConverter.put("9", 9);
        hexConverter.put("A", 10);
        hexConverter.put("B", 11);
        hexConverter.put("C", 12);
        hexConverter.put("D", 13);
        hexConverter.put("E", 14);
        hexConverter.put("F", 15);
        return hexConverter;
    }
    
    private int value = 0;
    
    public Binary(Integer value)
    {
        this.value = value;
    }
    
    public Binary(Double value)
    {
        this.value = (int)(double)value;
    }
    
    public Binary(String value)
    {
        this.value = this.stringToValue(value);
    }
    
    static public int stringToValue(String input)
    {
        String base = input.substring(0,2);
        input = input.substring(2);
        int value = 0;

        if (base.equalsIgnoreCase("0x"))
        {
            try
            {
                for (int i = 0; i < input.length(); i++)
                {
                    value += hexConverter.get(input.substring(i,i+1).toUpperCase()) * Math.pow(16,input.length() - i - 1);
                }
            }
            catch (Throwable e)
            {
                assert false : "Not a valid hex value";
            }
        }
        else if (base.equalsIgnoreCase("0b"))
        {
            for (int i = 0; i < input.length(); i++)
            {
                int bit = Integer.parseInt(input.substring(i, i + 1));
                if (bit > 1 || bit < 0)
                {
                    assert false : "Not a valid binary value";
                }
                value += bit * Math.pow(2, input.length() - i - 1);
            }
        }
        else
        {
            value = Integer.parseInt(base + input);
        }
        
        return value;
    }
    
    public Binary lsbyte()
    {
        String hex = this.toString();
        System.out.println("0x" + hex.substring(hex.length() - 1));
        return new Binary("0x" + hex.substring(hex.length() - 1));
    }
    
    public Binary twoComp()
    {
        return new Binary(Math.pow(2, this.numBits()) - this.value);
    }
    
    public String toString()
    {
        return Integer.toHexString(this.value).toUpperCase();
    }
    
    public int numBits()
    {
        return Integer.toBinaryString(this.value).length();
    }
    
    public int getValue()
    {
        return this.value;
    }
}
