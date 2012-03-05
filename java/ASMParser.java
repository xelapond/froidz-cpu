import java.util.Scanner;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.io.File;
/**
 * Write a description of class ASMParser here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ASMParser
{
    public ASMParser()
    {
        this("../test.asm");
    }
    
    public ASMParser(String path)
    {
        Scanner scanner = new Scanner(new File(path));
        Iterator iterator = scanner.iterator();
        LinkedList<String> lines = new LinkedList();
        while (iterator.hasNext())
        {
            lines.add(iterator.next());
        }
        this.format(lines);
    }
    
    private List<String> format(List<String>)
    {
    }
}
