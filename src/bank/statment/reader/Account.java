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
        //We have to do this backwards because spaces can occur in the account name.
        String endBalString = initialString.substring(initialString.lastIndexOf(" ")+1);
        endingBal = Double.parseDouble(endBalString.replaceAll(",", ""));
        initialString = initialString.substring(0,initialString.lastIndexOf(" "));
        
        String startBalString = initialString.substring(initialString.lastIndexOf(" ")+1);
        startingBal = Double.parseDouble(startBalString.replaceAll(",", ""));
        initialString = initialString.substring(0,initialString.lastIndexOf(" "));
        
        String accIDString = initialString.substring(initialString.lastIndexOf(" ")+1);
        accID = Long.parseLong(accIDString);
        initialString = initialString.substring(0,initialString.lastIndexOf(" "));
        
        name = initialString;
    }
}
