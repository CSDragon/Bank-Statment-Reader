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
    public static final int TYPE_SINGLE_CHECKING = 0;
    public static final int TYPE_COMBINED_ACCOUNTS = 1;
    public static final int TYPE_CREDIT_CARD = 2;
    
    public static final int STATE_START = 0;
    public static final int STATE_DETERMINE_TYPE = 1;
    public static final int STATE_BANK_GET_DATES = 2;
    public static final int STATE_SINGLE_ACC_INFO = 3;
    public static final int STATE_PASS_TO_COMBINED_ACC_INFO = 4;
    public static final int STATE_RECORD_COMBINED_ACCOUNTS = 5;
    public static final int STATE_PASS_TO_ACTIVITY_SUMMARY = 6;
    public static final int STATE_READ_ACTIVITY_SUMMARY = 7;
    public static final int STATE_UNKNOWN_TYPE = 8;
    
    private Date startDate;
    private Date endDate;
    private ArrayList<Account> accounts;
    
    private int type;
    
    
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
                
                //System.out.println(state + " " + line);

                //State machine
                switch (state)
                {
                    //First case of state machine.
                    //Reach the first information we need.
                    case Statement.STATE_START:
                        if(line.length() >= 11 && line.substring(0,11).equals("Wells Fargo"))
                        {
                            state = Statement.STATE_DETERMINE_TYPE;
                            hold = true;
                        }
                        
                        if(line.length() >= 11 && line.substring(0,11).equals("WELLS FARGO"))
                        {
                            type = Statement.TYPE_CREDIT_CARD;
                            state = 100;
                        }

                        break;
                        
                    case STATE_DETERMINE_TYPE:
                        if(line.equals("Wells Fargo Combined Statement of Accounts"))
                        {
                            type = TYPE_COMBINED_ACCOUNTS;
                            state = STATE_BANK_GET_DATES;
                        }

                        else
                        {
                            type = Statement.TYPE_SINGLE_CHECKING;
                            accounts.add(new Account());
                            accounts.get(0).setName(line.substring(12));
                            state = Statement.STATE_SINGLE_ACC_INFO;
                        }
                        
                        //else
                            //state = STATE_UNKNOWN_TYPE;
                        
                        break;
                        
                    case Statement.STATE_SINGLE_ACC_INFO:
                        accounts.get(0).setAccID(Long.parseLong(line.substring(17,27)));
                        state = Statement.STATE_BANK_GET_DATES;
                        hold = true;
                        break;
                        
                    case Statement.STATE_BANK_GET_DATES:
                        ArrayList<Integer> blockIndexes = Reader.indexesOfCharacterInString(line, '■');
                        String dateRange = line.substring(blockIndexes.get(0)+1, blockIndexes.get(1));
                        dateRange = dateRange.trim();
                        ArrayList<Integer> spaceIndexes = Reader.indexesOfCharacterInString(dateRange, ' ');
                        String startDateString = dateRange.substring(0,spaceIndexes.get(2));
                        String endDateString = dateRange.substring(spaceIndexes.get(3)+1);
                        
                        startDate = new SimpleDateFormat("MMMM dd, yyyy").parse(startDateString);
                        endDate   = new SimpleDateFormat("MMMM dd, yyyy").parse(endDateString);

                        state = Statement.STATE_PASS_TO_COMBINED_ACC_INFO;
                        break;
                    
                    case Statement.STATE_PASS_TO_COMBINED_ACC_INFO:
                        //A multiaccount satement will look like this
                        if(line.equals("Account Page Account number last statement this statement"))
                        {
                            state = Statement.STATE_RECORD_COMBINED_ACCOUNTS;
                        }
                        break;
                    
                    //Get the account information
                    case Statement.STATE_RECORD_COMBINED_ACCOUNTS:
                        if(line.length() < 13 || line.substring(0,13).equals("Total deposit"))
                        {
                            state = Statement.STATE_PASS_TO_ACTIVITY_SUMMARY;
                            break;
                        }
                        
                        else
                        {
                            Account a = new Account(line);
                            accounts.add(a);
                            System.out.println(line);
                        }
                        break;
                        
                    //Continue until we find the start of an activity summary.
                    case STATE_PASS_TO_ACTIVITY_SUMMARY:
                        if(line.equals("Activity summary"))
                        {
                            state = STATE_READ_ACTIVITY_SUMMARY;
                        }
                        break;
                        
                    
                        
                        
                        
                    case STATE_UNKNOWN_TYPE:
                        System.out.println("I'm not sure what to do with this kind of statement");
                        done = true;
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
