/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.statment.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

/**
 *
 * @author chrsc
 */
public class Reader 
{
    /**
     * Extracts all text from a PDF and returns it as a string.
     * @param fileLoc The location of the file
     * @return The PDF as a String, or null if there's an issue reading the PDF.
     */
    public static String readPDF(String fileLoc)
    {
        //gotta create this ahead of time becaues of the try/catch.
        String ret = null;
        try
        {
            //Create document
            PDDocument document = PDDocument.load(new File(fileLoc));
            //If not encrypted
            if (!document.isEncrypted()) 
            {
                //Strip the text and put it in a string.
                PDFTextStripper stripper = new PDFTextStripper();
                ret = stripper.getText(document);
            }
            document.close();
        }
        
        catch(Exception e)
        {
            System.out.println("Error opening file.");
        }
        
        //If there's issues of any kind, this will return null.
        return ret;
    }
    
    /**
     * Turns a String into a BufferedReader
     * @param input The input string
     * @return the string in BufferedReader form.
     */
    public static BufferedReader stringToBufferedReader(String input)
    {
        return new BufferedReader(new StringReader(input));
        
    }
    
    
    /**
     * Separates a multi-line string into an array of single line strings.
     * @param multiLineString
     * @return An array of Strings corresponding to each line of the input string.
     */
    public static String[] separateMultiLineString(String multiLineString)
    {
        //Split on newline characters
        String[] lines = multiLineString.split("\n\r|\r");
        for(int i = 0; i<lines.length; i++)
        {   
            //remove newline characters
            lines[i] = lines[i].replace("\n", "").replace("\r", "");
        }
        return lines;
    }
    
    /**
     * Returns an array list of all indexs of a specific character in a string.
     * @param input The input string.
     * @param c The character we are looking for
     * @return The ArrayList of locations.
     */
    public static ArrayList<Integer> indexesOfCharacterInString(String input, char c)
    {
        ArrayList<Integer> ret = new ArrayList<>();
        
        for(int i = 0; i<input.length(); i++)
            if(input.charAt(i) == c)
                ret.add(i);
        
        return ret;
    }
    
}
