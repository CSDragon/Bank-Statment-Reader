/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.statment.reader;

import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author chrsc
 */
public class Statement 
{
    private Date startDate;
    private Date endDate;
    private ArrayList<Account> accounts;
    
    
    /**
     * Reads a statement and creates statement objects. 
     * @param statement The statement as a buffered reader.
     */
    public Statement(BufferedReader statement)
    {
        accounts = new ArrayList<>();
        
        try
        {
            //initialize state machine variables
            int state = 0;
            String line = "";
            boolean done = false;
            boolean hold = false;

            while(!done)
            {
                //If we want to hold the same line for another instruction we set hold to true.
                //If not hold, get a new line.
                if(!hold)
                    line = statement.readLine();
                else
                    hold = false;

                //State machine
                switch (state)
                {
                    //First case of state machine.
                    //Reach the first information we need.
                    case 0:
                        if(line.equals("Wells Fargo Combined Statement of Accounts"))
                            state = 1;
                        break;
                        
                    case 1:
                        ArrayList<Integer> blockIndexes = Reader.indexesOfCharacterInString(line, '■');
                        String dateRange = line.substring(blockIndexes.get(0)+1, blockIndexes.get(1));
                        dateRange = dateRange.trim();
                        ArrayList<Integer> spaceIndexes = Reader.indexesOfCharacterInString(dateRange, ' ');
                        String startDateString = dateRange.substring(0,spaceIndexes.get(2));
                        String endDateString = dateRange.substring(spaceIndexes.get(3)+1);
                        
                        startDate = new SimpleDateFormat("MMMM dd, yyyy").parse(startDateString);
                        endDate   = new SimpleDateFormat("MMMM dd, yyyy").parse(endDateString);

                        state = 2;
                        break;
                    
                    case 2:
                        if(line.equals("Account Page Account number last statement this statement"))
                            state = 3;
                        break;
                        
                    case 3:
                        System.out.println(line);
                        System.out.println(line.substring(0,13));
                        if(line.length() < 13 || line.substring(0,13).equals("Total deposit"))
                        {
                            state = 4;
                            break;
                        }
                        
                        else
                        {
                            Account a = new Account(line);
                            accounts.add(a);
                            System.out.println(accounts.size());
                        }
                        break;
                        
                    default:
                        done = true;
                        break;
                        
                }
            }
        }
        catch(Exception e)
        {
            System.err.println(e);
        }
    }
}
