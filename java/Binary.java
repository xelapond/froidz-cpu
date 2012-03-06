import java.util.HashMap;
/**
 * Deals with binary numbers.
 * 
 * @author Jacob Weiss
 * @version 0.0.2
 */
public class Binary
{
    // Allows for quick conversions from hex to decimal.
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
    
    private int value = 0; // Holds the value of the binary number.
    private int numBits = 0; // Holds the number of bits to display of the number.
    
    private Binary(Integer value)
    {
        this.value = value;
    }
    
    private Binary(Double value)
    {
        this.value = (int)(double)value;
    }
    
    public Binary()
    {
    }
    
    public Binary(String value)
    {
        if (isHex(value))
        {
            this.numBits = (value.length() - 2) * 4;
        }
        else if (isBinary(value))
        {
            this.numBits = (value.length() - 2);
        }
        else
        {            
            assert false : "Invalid input";
        }
        
        this.value = this.stringToValue(value);
    }
    
    static public boolean isHex(String s)
    {
        return s.substring(0, 2).equalsIgnoreCase("0x");
    }
    static public boolean isBinary(String s)
    {
        return s.substring(0, 2).equalsIgnoreCase("0b");
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
            assert false : "Invalid input";
        }
        
        return value;
    }
    
    public String pad(String s, int numChars)
    {
        while (s.length() < numChars)
        {
            s = "0" + s;
        }
        return s;
    }
    
    public void concatBack(Binary... args)
    {
        String newValue = this.toBinaryString();
        for (Binary b : args)
        {
            newValue = newValue + b.toBinaryString();
            this.numBits += b.numBits;
        }
        this.value = stringToValue("0b" + newValue);
    }
    
    public void concatFront(Binary... args)
    {
        String newValue = this.toBinaryString();
        for (int i = args.length - 1; i >= 0; i--)
        {
            Binary b = args[i];
            newValue = b.toBinaryString() + newValue;
            this.numBits += b.numBits;
        }
        this.value = stringToValue("0b" + newValue);
    }
    
    public void add(Binary... args)
    {
        for (Binary b : args)
        {
            this.value += b.getValue();
            if (b.numBits > this.numBits)
            {
                this.numBits = b.numBits;
            }   
        }
        if (this.minNumBits() > this.numBits)
        {
            this.numBits = this.minNumBits();
        }
    }
    
    public Binary lsbyte()
    {
        String hex = this.toString();
        System.out.println("0x" + hex.substring(hex.length() - 1));
        return new Binary("0x" + hex.substring(hex.length() - 1));
    }
    
    public Binary twoComp()
    {
        return new Binary(Math.pow(2, this.minNumBits()) - this.value);
    }
    
    public String toString()
    {
        return pad(Integer.toHexString(this.value).toUpperCase(), (int)Math.ceil((this.numBits) / 4.0));
    }
    
    public String toBinaryString()
    {
        return pad(Integer.toBinaryString(this.value), this.numBits);
    }
    
    public int minNumBits()
    {
        return Integer.toBinaryString(this.value).length();
    }
    
    public int getValue()
    {
        return this.value;
    }
}
