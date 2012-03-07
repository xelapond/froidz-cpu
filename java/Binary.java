import java.util.HashMap;
/**
 * Represents a binary number using a String and performs operations on it.
 * 
 * @author Jacob Weiss
 * @version 0.0.4
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
    
    /**
     * Binary(String input)
     * 
     * Create a binary object from a given input string. For the rest of its life,
     * the new binary object will maintain the same number of bits that it is 
     * given in this constructor. With hex, each character is represented by
     * 4 bits. In binary, every bit is remembered.
     * 
     * @param  value is a String that represents a number
     *         valid formats: hex -> "0x{0-F}"
     *                        bin -> "0b{0-1}"
     */
    public Binary(String input)
    {
        double value = 0;

        if (!Double.isNaN(value = hexToValue(input)))
        {
            this.numBits = (input.length() - 2) * 4;
        }
        else if (!Double.isNaN(value = binaryToValue(input)))
        {
            this.numBits = (input.length() - 2);
        }
        else
        {
            assert false : "Invalid input: " + input;
        }
        
        this.value = (int)value;
    } 
    /**
     * Binary(int input, int minNumBits)
     * 
     * The number of bits remembered is at least minNumBits.
     * 
     * @param  value is a base 10 int
     *         valid formats: dec -> {0-9}
     * @param  minNumBits  the number of bits to remember, at least.
     */
    public Binary(Integer input, int minNumBits)
    {
        this("0b" + pad(Integer.toBinaryString(input), minNumBits));
    } ;
    /**
     * Binary(int input)
     * 
     * The number of bits remembered is the least possible to represent the decimal
     * number in binary.
     * 
     * @param  value is a base 10 int
     *         valid formats: dec -> {0-9}
     */
    public Binary(Integer input)
    {
        this("0b" + Integer.toBinaryString(input));
    }
    
    public Binary()
    {
        this.value = 0;
        this.numBits = 0;
    }
    
    // ***********************************Static methods**********************************
    
    /**
     * hexToValue(String s)
     * 
     * valid format for s: "0x{0-F}"
     * 
     * @param The string representation of a hex value
     * @return the double value contained in the string
     *         Double.NaN if the hex string is not correctly formatted.
     */
    static public double hexToValue(String s)
    {
        if (!s.substring(0, 2).equalsIgnoreCase("0x"))
        {
            return Double.NaN;
        }
        int value = 0;
        try
        {
            for (int i = 2; i < s.length(); i++)
            {
                value += hexConverter.get(s.substring(i,i+1).toUpperCase()) * Math.pow(16,s.length() - i - 1);
            }
        }
        catch (Throwable e)
        {
            return Double.NaN;
        }
        return value;
    }
    /**
     * binaryToValue(String s)
     * 
     * valid format for s: "0b{0-1}"
     * 
     * @param The string representation of a binary value
     * @return the double value contained in the string
     *         Double.NaN if the binary string is not correctly formatted.
     */
    public static double binaryToValue(String s)
    {
        if (!s.substring(0, 2).equalsIgnoreCase("0b"))
        {
            return Double.NaN;
        }
        int value = 0;
        for (int i = 2; i < s.length(); i++)
        {
            int bit = Integer.parseInt(s.substring(i, i + 1));
            if (bit > 1 || bit < 0)
            {
                return Double.NaN;
            }
            value += bit * Math.pow(2, s.length() - i - 1);
        }
        return value;
    }
        
    /**
     * pad(String s, int numChars)
     * 
     * Pad the beginning of s with "0"s until it is numChars length
     * 
     * @param  String    the input string (normally hex or binary)
     * @param  numChars  the desired length of the input string
     * @return String    the new string with preceding "0"s or...
     *                      the input string if numChars <= s.length()
     */
    private static String pad(String s, int numChars)
    {
        while (s.length() < numChars)
        {
            s = "0" + s;
        }
        return s;
    }   
    
    // ***********************************Instance methods**********************************
    
    /**
     * concatBack(Binary... args)
     * 
     * Concatenates the given binary objects to the end of this.
     * ***Modifies this***
     * 
     * Eg. this.toBinaryString() = "1111"
     *     bin2.toBinaryString() = "0000"
     *     this.concatBack(bin2, bin2);
     *     this.toBinaryString() = "111100000000"
     * 
     * @param  Binary  The binary objects to append to this one.
     */
    public void concatBack(Binary... args)
    {
        String newValue = this.toBinaryString();
        int bitsToAdd = 0;
        for (Binary b : args)
        {
            newValue = newValue + b.toBinaryString();
            bitsToAdd += b.numBits;
        }
        this.value = (int)binaryToValue("0b" + newValue);
        this.numBits += bitsToAdd;
    }
    
    /**
     * concatFront(Binary... args)
     * 
     * Concatenates the given binary objects to the front of this.
     * ***Modifies this***
     * 
     * Eg. this.toBinaryString() = "1111"
     *     bin2.toBinaryString() = "1100"
     *     this.concatFront(bin2, this);
     *     this.toBinaryString() = "110011111111"
     * 
     * @param  Binary  The binary objects to add in front of this one.
     */
    public void concatFront(Binary... args)
    {
        String newValue = this.toBinaryString();
        int bitsToAdd = 0;
        for (int i = args.length - 1; i >= 0; i--)
        {
            Binary b = args[i];
            newValue = b.toBinaryString() + newValue;
            bitsToAdd += b.numBits;
        }
        this.value = (int)binaryToValue("0b" + newValue);
        this.numBits += bitsToAdd;
    }
    
    /**
     * add(Binary... args)
     * 
     * Adds the given Binary objects to this.
     * 
     * @param   Binary(s)  The binary objects to add.
     */
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
    
    /**
     * leastSignificantByte()
     * 
     * Returns the 8 least significant bits of this.
     * 
     * @return  Binary  the least significant 8 bits
     */
    public Binary leastSignificantByte()
    {
        String hex = this.toString();
        return new Binary("0x" + hex.substring(hex.length() - 1));
    }
    
    /**
     * twoComp()
     * 
     * Returns the two's complement of this. 
     * The length of the value returned is equal to the shortest number of bits
     * that this can be represented in. Leading 0s are ignored.
     * 
     * @return  Binary  the two's complement of this
     */
    public Binary twoComp()
    {
        return new Binary((int)Math.pow(2, this.minNumBits()) - this.value);
    }
    
    /**
     * toString()
     * 
     * Returns this as a string formatted in hex.
     * 
     * @return  String  the hex representation of this.
     */
    public String toString()
    {
        return pad(Integer.toHexString(this.value).toUpperCase(), (int)Math.ceil((this.numBits) / 4.0));
    }
    
    /**
     * toBinaryString()
     * 
     * Returns this as a string formatted in binary.
     * 
     * @return  String  the binary representation of this.
     */
    public String toBinaryString()
    {
        return pad(Integer.toBinaryString(this.value), this.numBits);
    }
    
    /**
     * split(int start, int end)
     * 
     * Acts like substring. Returns a new binary from [start, end)
     * 
     * @return  Binary  the selected part of this
     */
    public Binary split(int start, int end)
    {
        return new Binary("0b" + this.toBinaryString().substring(start, end));
    }
    /**
     * split(int start)
     * 
     * Acts like substring. Returns a new binary from [start, end)
     * 
     * @return  Binary  the selected part of this
     */
    public Binary split(int start)
    {
        return new Binary("0b" + this.toBinaryString().substring(start));
    }
    
    /**
     * minNumBits()
     * 
     * Returns the minimum number of bits needed to represent this in base 2.
     * 
     * @return  int   the number of bits
     */
    public int minNumBits()
    {
        return Integer.toBinaryString(this.value).length();
    }
    
    // Public getter methods
    public int getValue()
    {
        return this.value;
    }
    public int getNumBits()
    {
        return this.numBits;
    }
}
