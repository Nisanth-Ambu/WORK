/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplereminder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Frankenstein
 */
public class SimpleReminder {
    static Statement stmnt;
static Connection con;
 public void connection()
    {
       
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch(ClassNotFoundException b)
        {
            System.out.println("not walk");
        }
        try
        {
             con = DriverManager.getConnection("jdbc:mysql://localhost:3306/reminder","root","");
             stmnt = con.createStatement();
            System.out.println("done");
            new ReminderHome().setVisible(true);
         
        }
        catch (SQLException ex)
        {
            System.out.println("connection terminated"+ex);
            System.exit(0);
        }
    } 
    
    public static void main(String[] args) {
        new SimpleReminder().connection();
        
    }
    
}
