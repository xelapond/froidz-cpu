import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;
import java.util.HashMap;

/**
 * Represents one assembly operation
 * There is one WPChunk for every operation in the AVR Assembly Definition
 * 
 * @author Jacob Weiss,Alex Teiche 
 * @version 0.0.2
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
    
    public Binary generateInstruction(Binary... args)
    {
        return this.generateInstruction(args);
    }
    /**
     * generateInstruction()
     * 
     * argX is a string representation of a hex value
     * @param List<String> (name, arg0, arg1, ... , argN)
     */
    public Binary generateInstruction(List<Binary> asm) throws InvalidInputException
    {
        // Check to make sure that the number of arguments to this method equals
        // the number of operands that this operation takes.
        if (asm.size() != operands.length)
        {
            throw new InvalidInputException();
        }
        for (int i = 0; i < asm.size(); i++)
        {
            if (asm.get(i).getNumBits() >= this.operands[i].split("=")[1].length())
            {
                throw new InvalidInputException();
            }
        }
        
        String instruction = this.opCode;
        for (int op = 0; op < operands.length; op++)
        {
            String operandTemplate = this.operands[op].split("=")[1]; // Template of input
            Binary asmInput = asm.get(op);  // Instuction operand
            for (int i = operandTemplate.length() - 1; i >= 0; i--)
            {
                instruction = instruction.replaceAll(operandTemplate.substring(i, i + 1), asmInput.toBinaryString().substring(i, i + 1));
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
