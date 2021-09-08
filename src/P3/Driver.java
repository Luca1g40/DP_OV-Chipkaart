package P3;

import P2.Reiziger;
import P2.ReizigerDAOPsql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Driver {

    public static Connection connection;

    public static void main(String[] args) throws SQLException {
        connection = DriverManager.getConnection("jdbc:postgresql:ovchip", "postgres", "1234");
        AdresDAOPsql adp = new AdresDAOPsql(connection);
        ReizigerDAOPsql rdp = new ReizigerDAOPsql(connection);
        String gbdatum = "1993-03-27";

        Reiziger r1 = new Reiziger(7, "L", "", "Fransen", java.sql.Date.valueOf(gbdatum));
        Adres a1 = new Adres(7, "3732BE", "153", "Henrica van erpweg", "De Bilt", r1);
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
//        rdp.saveReiziger(sietske);
//        adp.saveAdres(a1);
//        a1.setHuisnummer("135A");
//        adp.updateAdres(a1);
//        adp.deleteAdres(a1);
        System.out.println(a1);
//        System.out.println(adp.findByReiziger(sietske));

    }

}
