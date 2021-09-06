package P2;

import java.security.spec.ECField;
import java.sql.*;
import java.time.LocalDate;
import java.util.Properties;

public class Driver {

    public static Connection connection;

    public static void main(String[] args) throws SQLException {
        connection = DriverManager.getConnection("jdbc:postgresql:ovchip", "postgres", "1234");
        ReizigerDAOPsql rdp = new ReizigerDAOPsql(connection);
        Reiziger r1 = new Reiziger(1, "L", "franken", "", LocalDate.of(1993, 3, 27));
        System.out.println(rdp.findAll());
        r1.setAchternaam("hagenaars");
        rdp.updateReiziger(r1);
        System.out.println(rdp.findAll());



    }
}
