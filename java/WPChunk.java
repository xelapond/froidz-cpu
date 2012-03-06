import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;
import java.util.HashMap;

/**
 * Represents one assembly operation
 * There is one WPChunk for every operation in the AVR Assembly Definition
 * 
 * @author Jacob Weiss,Alex Teiche 
 * @version 0.0.1
 */
public class WPChunk
{
    private static HashMap<String, String> hexConverter = WPChunk.initHexConverter();    
    private static HashMap<String, String> initHexConverter()
    {
        HashMap<String, String> hexConverter = new HashMap();
        hexConverter.put("0", "0000");
        hexConverter.put("1", "0001");
        hexConverter.put("2", "0010");
        hexConverter.put("3", "0011");
        hexConverter.put("4", "0100");
        hexConverter.put("5", "0101");
        hexConverter.put("6", "0110");
        hexConverter.put("7", "0111");
        hexConverter.put("8", "1000");
        hexConverter.put("9", "1001");
        hexConverter.put("A", "1010");
        hexConverter.put("B", "1011");
        hexConverter.put("C", "1100");
        hexConverter.put("D", "1101");
        hexConverter.put("E", "1110");
        hexConverter.put("F", "1111");
        return hexConverter;
    }
    
    private String opName;
    private String[] operands;
    private String[] ranges;
    private String opCode;
    
    private Pattern opCodePattern;
    
    public WPChunk(String line)
    {
        line = line.replace(" ", "");
        String[] parts = line.split("\\|");
        this.opName = parts[0];
        this.operands = parts[1].split(",");
        this.ranges = parts[2].split(",");
        this.opCode = parts[3];
        
        this.opCodePattern = Pattern.compile(this.formatRegex(this.opCode));
    }
    
    /**
     * Takes an opCode including data directly from an assembled program, and determines if it matches this operation
     * @param opCode The opCode including data
     * @return true -> It matched
     */
    public boolean match(String opCode)
    {
        return this.opCodePattern.matcher(opCode).matches();
    }
    
    /**
     * generateInstruction()
     * 
     * argX is a string representation of a hex value
     * @param List<String> (name, arg0, arg1, ... , argN)
     */
    public String generateInstruction(List<String> asm)
    {
        for (String s : asm)
        {
            System.out.print(s + " ");
        }
        System.out.println();
        
        if (!asm.get(0).equals(this.opName) || asm.size() - 1 != operands.length)
        {
            System.out.println("invalid input");
            return null;
        }
        
        for (int i = 1; i < asm.size(); i++)
        {
            asm.set(i, numberToBinary(asm.get(i)));
        }
        
        String instruction = this.opCode;
        for (int op = 0; op < operands.length; op++)
        {
            String operand = this.operands[op];
            String asmInput = asm.get(op + 1);
            int offset = operand.indexOf("=") + 1;
            for (int i = 0; i < operand.length() - offset; i++)
            {
                instruction = instruction.replaceAll(operand.charAt(i + offset) + "", asmInput.charAt(i) + "");
            }
        }
        
        return instruction;
    }
    
    public String numberToBinary(String num)
    {
        if (num.substring(0, 2).equals("0x"))
        {
            System.out.println("hello");
            String binary = "";
            for (int i = 2; i < num.length(); i++)
            {
                binary += WPChunk.hexConverter.get(num.substring(i, i+1));
            }
            return binary;
        }
        else if (num.substring(0, 2).equals("0b"))
        {
            return num.substring(2);
        }
        else
        {
            if (num.substring(0,1).equalsIgnoreCase("r"))
            {
                num = num.substring(1);
            }
            return Integer.toBinaryString(Integer.parseInt(num));
        }
    }
    
    /**
     * Takes an opcode and replaces all ascii characters with . to match a single character
     * @param opCode An opCode String
     * @return A string representing a regex to match only the static parts of the opcode
     */
    private String formatRegex(String opCode)
    {
        return opCode.replaceAll("[a-z]", ".");
    } 
    
    // Public Getter Methods
    public String getOpName()
    {
        return this.opName;
    }
    
    public String[] getOperands()
    {
        return this.operands;
    }
    
    public String[] getRanges()
    {
        return this.ranges;
    }
    
    public String getOpCode()
    {
        return this.opCode;
    }
}
