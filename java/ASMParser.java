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
    private WPParser parser;

    public ASMParser()
    {
        this("../test.asm");
    }
    
    public ASMParser(String path)
    {
        try
        {
            Scanner scanner = new Scanner(new File(path));
            LinkedList<String> lines = new LinkedList();
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
    
    private List<String> assemble(List<String> lines)
    {
        for (String line : lines)
        {
            List parsed = this.parseLine(line);
            
            WPChunk op = null;
        }
        
        return null;
            
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
    

}
