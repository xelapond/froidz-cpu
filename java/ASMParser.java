import java.util.Scanner;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;  

/**
 * Write a description of class ASMParser here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ASMParser
{
    public static final int AVR_WORDS_PER_LINE = 8;
    private static final Binary EOF = new Binary("0x00000001FF");
    private static final Binary DATA_RECORD = new Binary("0x00");

    private WPParser parser;

    public ASMParser()
    {
        this("../test.asm");
    }
    
    /**
     * Create an assembly parser given a path to an assembly file
     * @param path The path to the file
     */
    public ASMParser(String path)
    {
        LinkedList<String> lines = new LinkedList();
        
        try
        {
            Scanner scanner = new Scanner(new File(path));

            while (scanner.hasNextLine())
            {
                lines.add(scanner.nextLine());
            }
        }
        catch (Throwable e)
        {
            System.out.println("404 File Not Found");
        }
        
        this.parser = new WPParser();
        //this.generateInstructions(this.preprocess(this.format(lines)));
    }
    
    /**
     * Remove all newlines
     */
    private void format(List<String> lines)
    {
        Iterator<String> i = lines.iterator();
        String line;
        
        while (i.hasNext())
        {
            line = i.next().trim();
            if (line.length() == 0)
            {
                i.remove();
            }
        }
        
    }
    
    private void preprocess(List<String> lines)
    {

    }
    
    private List<Binary> generateInstructions(List<String> lines)
    {
        ArrayList<Binary> instructions = new ArrayList();
        
        for (String line : lines)
        {
            List<String> parsed = this.parseLine(line);
            
            WPChunk op = this.parser.getChunk(parsed.get(0));
            instructions.add(op.generateInstruction(parsed));
        }
        
        return instructions;
            
    }
    
    /**
     * Separates the instructions to wordsPerLine words per line
     * @param lines The lines of raw instructions, one instruction per line
     * @param wordsPerLine The number of words to put on one line in the hex file
     * @return List of lines, wordsPerLine words to a line
     */
    private List<Binary> separateInstructions(List<Binary> instructions, int wordsPerLine)
    {
        Iterator<Binary> it = instructions.iterator();
        
        List<String> out = new ArrayList();
        
        Binary curLine;
        
        while (it.hasNext())
        {
            curLine = new Binary("0x00");
            for(int i = 0; it.hasNext() && i < wordsPerLine; i++)
            {
                curLine.concatBack(it.next());
            }
            out.add(curLine);
        }
        
        return out;
            
    }
    
    private List<Binary> separateInstructions(List<Binary> instructions)
    {
        return separateInstructions(instructions, ASMParser.AVR_WORDS_PER_LINE);
    }
    
    private void generateByteCountsAndAddresses(List<Binary> lines)
    {
        Binary addr = new Binary("0x0000");
        
        for (Binary line : lines)
        {
            Binary byteCount = line.minNumBits();
            line.concatFront(addr);
            line.concatFront(byteCount);
            addr.add(byteCount);
        }
        

    }
    
    private void generateCheckSums(List<Binary> lines)
    {
        for(Binary b : lines)
        {
            b.concatFront(ASMParser.generateLineChecksum(b.toString()));
        }
    }
    
    public void addEOF(List<String> lines)
    {
        lines.add(ASMParser.EOF);
    }
    
    /**
     * Add the preceding colons to every line of Intel Hex and convert the binary objects to Strings
     * @param 
     */
    private List<String> finalize(List<String> lines)
    {
        List<String> l = new ArrayList();
        
        for (String line : lines)
        {
            l.add(":" + line);
        }
        
        return l;
    }

        
    public static List<String> parseLine(String line)
    {
        List<String> parsed = new ArrayList();
        
        for (String s : line.replace(",", " ").split(" "))
        {
            if (s.length() != 0)
            {
                parsed.add(s);
            }
        }
       
        return parsed;

    }
    
    public static String generateLineChecksum(String line)
    {
        return null;
    }
    

    
    public void test()
    {
        List<String> lines = new ArrayList();
        
        lines.add("0E");
        lines.add("DE");
        lines.add("FE");
        lines.add("4E");
        lines.add("5A");
        lines.add("9D");
        lines.add("QF");
        lines.add("82");
        lines.add("90");
        lines.add("50");
        lines.add("29");
        lines.add("40");
        lines.add("F9");
        lines.add("F1");
        lines.add("F2");
        lines.add("F3");
        lines.add("FF");
        lines.add("F4");
        lines.add("F5");
        lines.add("F6");
        lines.add("F7");
        lines.add("F8");
        lines.add("DD");
        lines.add("AC");
        lines.add("DC");
    
        lines = separateInstructions(lines);
        
        for (String l : lines)
        {
            System.out.println(l);
        }
    }

}
