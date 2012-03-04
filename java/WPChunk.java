import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents one assembly operation
 * There is one WPChunk for every operation in the AVR Assembly Definition
 * 
 * @author Jacob Weiss,Alex Teiche 
 * @version 0.0.1
 */
public class WPChunk
{
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
