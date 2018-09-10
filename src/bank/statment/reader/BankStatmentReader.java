/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.statment.reader;

import java.io.BufferedReader;
import java.io.File;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;



/**
 *
 * @author chrsc
 */
public class BankStatmentReader 
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        
        test();
        
        
    }
    
    public static void test()
    {
        String s = Reader.readPDF("resources/Aug2017Old.pdf");
        
        String[] lines = Reader.separateMultiLineString(s);
        
        BufferedReader br = Reader.stringToBufferedReader(s);
        
        boolean done = false;
        
        Reader.readStatementLines(br);
        
        /*try
        {
            while(!done)
            {
                String line = br.readLine();
                if(line == null)
                    done = true;
                else
                {
                    System.out.println(line);
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("oops");
        }*/
        
       // System.out.println(s);
    }
    
}
