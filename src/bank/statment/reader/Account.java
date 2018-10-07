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
     * Constructor for multiple account statement
     * @param initialString The string containing all the important info.
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
    
    /**
     * Constructor for single accounts
     */
    public Account()
    {
    }
    
    
    /**
     * @return the name
     */
    public String getName() 
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) 
    {
        this.name = name;
    }

    /**
     * @return the accID
     */
    public long getAccID() 
    {
        return accID;
    }

    /**
     * @param accID the accID to set
     */
    public void setAccID(long accID) 
    {
        this.accID = accID;
    }

    /**
     * @return the startingBal
     */
    public double getStartingBal() 
    {
        return startingBal;
    }

    /**
     * @param startingBal the startingBal to set
     */
    public void setStartingBal(double startingBal) 
    {
        this.startingBal = startingBal;
    }

    /**
     * @return the endingBal
     */
    public double getEndingBal() 
    {
        return endingBal;
    }

    /**
     * @param endingBal the endingBal to set
     */
    public void setEndingBal(double endingBal) 
    {
        this.endingBal = endingBal;
    }
    
}
