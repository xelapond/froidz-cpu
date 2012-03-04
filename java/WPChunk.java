/**
 * Represents one assembly operation
 * 
 * @author Jacob Weiss,Alex Teiche 
 * @version 0.0.1
 */
public class WPChunk
{
    String opName;
    String[] operands;
    String[] ranges;
    String opCode;
    
    public WPChunk(String line)
    {
        line = line.replace(" ", "");
        String[] parts = line.split("\\|");
        this.opName = parts[0];
        this.operands = parts[1].split(",");
        this.ranges = parts[2].split(",");
        this.opCode = parts[3];
    }
}
