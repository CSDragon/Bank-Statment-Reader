/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.statment.reader;

/**
 *
 * @author chrsc
 */
public class Account 
{
    
    private String name;
    private long accID;
    private double startingBal;
    private double endingBal;
    
    /**
     * 
     * @param initialString 
     */
    public Account(String initialString)
    {
        String endBalString = initialString.substring(initialString.lastIndexOf(" ")+1);
        endingBal = Double.parseDouble(endBalString);
        System.out.println(endingBal);

    }
}
