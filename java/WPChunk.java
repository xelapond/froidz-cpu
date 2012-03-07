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
    private String opName; // The syntax eg. "add"
    private String[] operands; // The operands eg. {"Rd=mnopq", "Rr=abcde"}
    private String[] ranges; // The ranges eg. {"0-Rd-31", "0-Rr-31"}
    private String opCode; // The template eg. "0010 00am nopq bcde"
    
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
     * PRECONDITION: 
     * 
     * argX is a string representation of a hex value
     * @param List<String> (name, arg0, arg1, ... , argN)
     */
    public Binary generateInstruction(List<String> asm) throws InvalidInputException
    {
        // Check to make sure that the number of arguments to this method equals
        // the number of operands that this operation takes.
        if (!asm.get(0).equals(this.opName) || asm.size() - 1 != operands.length)
        {
            System.out.println("invalid input");
            return null;
        }
        for (int i = 1; i < asm.size(); i++)
        {
            System.out.print(asm.get(i) + " --- ");
            // THIS DOESN'T WORK BECAUSE asm.get(i) RETURNS A STRING, NOT A BINARY OBJECT, SO THE LENGTH WILL NEVER BE EQUAL
            // ADDITIONALLY, IT SHOULD PROBABLY BE A >= CHECK INSTEAD OF !=, BECAUSE IT COULD BE SHORTER
            //if (asm.get(i).length() != this.operands[i - 1].split("=")[1].length())
            //{
            //    throw new InvalidInputException();
            //}
        }
        System.out.println();
        
        String instruction = this.opCode;
        for (int op = 0; op < operands.length; op++)
        {
            String operand = this.operands[op].split("=")[1]; // Template of input
            System.out.println(operand);
            Binary asmInput = new Binary(asm.get(op + 1));  // Instuction operand
            System.out.println(asm.get(op + 1));
            for (int i = 0; i < operand.length(); i++)
            {
                instruction = instruction.replaceAll(operand.charAt(i) + "", asmInput.toBinaryString().charAt(i) + "");
            }
        }
        return new Binary("0b" + instruction);
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
