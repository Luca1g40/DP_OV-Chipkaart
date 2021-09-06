package P1;

import java.security.spec.ECField;
import java.sql.*;

public class Driver {

    private Connection connection;

    public static void main(String[] args){
        try{
            Connection myConn = DriverManager.getConnection("jdbc:postgresql:ovchip", "postgres", "1234");
            Statement myStmt = myConn.createStatement();
            ResultSet rs = myStmt.executeQuery("SELECT * FROM reiziger");

            while ((rs.next())){
                System.out.println("#"+ rs.getString("reiziger_id") + ", " + rs.getString("voorletters")
                        + ", " + rs.getString("achternaam") + ", " + rs.getString("geboortedatum"));
            }

            rs.close();
            myStmt.close();
            myConn.close();
        }
        catch (Exception exc){
            System.out.println(exc);

        }

    }
}
