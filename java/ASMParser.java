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

    private WPParser parser;

    public ASMParser()
    {
        this("../test.asm");
    }
    
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
            for (String s : this.format(lines))
            {
                System.out.println(s);
            }
        }
        catch (Throwable e)
        {
            System.out.println("404 File Not Found");
        }
        
        this.parser = new WPParser();
        //this.generateInstructions(this.preprocess(this.format(lines)));
    }
    
    //TODO: Remove comments
    private List<String> format(List<String> lines)
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
        
        return lines;
    }
    
    private List<String> preprocess(List<String> lines)
    {
        return lines;
    }
    
    private List<String> generateInstructions(List<String> lines)
    {
        ArrayList<String> instructions = new ArrayList();
        
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
    private List<String> separateInstructions(List<String> lines, int wordsPerLine)
    {
        Iterator<String> it = lines.iterator();
        
        List<String> out = new ArrayList();
        
        String curLine = "";
        
        while (it.hasNext())
        {
            curLine = "";
            for(int i = 0; it.hasNext() && i < wordsPerLine; i++)
            {
                curLine += it.next();
            }
            System.out.println();
            out.add(curLine);
        }
        
        return out;
            
    }
    
    private List<String> separateInstructions(List<String> lines)
    {
        return separateInstructions(lines, ASMParser.AVR_WORDS_PER_LINE);
    }
    
    private List<String> generateByteCountsAndAddresses(List<String> lines)
    {
        List<String> l = new ArrayList();
        
        for (String line : lines)
        {
        }
        
        return null;
    }
    
    private List<String> generateCheckSums(List<String> lines)
    {
        return lines;
    }
    
    
    /**
     * Add the preceding colons to every line of Intel Hex
     * @param 
     */
    private List<String> finalize(List<String> lines)
    {
        List l = new ArrayList();
        
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
