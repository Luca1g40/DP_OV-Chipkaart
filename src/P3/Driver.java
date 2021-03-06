package P3;

import P3.Domain.Reiziger;
import P3.DAO.AdresDAOPsql;
import P3.DAO.ReizigerDAOPsql;
import P3.Domain.Adres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class Driver {

    public static Connection connection;

    public static void main(String[] args) throws SQLException {
        connection = DriverManager.getConnection("jdbc:postgresql:ovchip", "postgres", "1234");
        AdresDAOPsql adp = new AdresDAOPsql(connection);
        ReizigerDAOPsql rdp = new ReizigerDAOPsql(connection);

        adp.setRdao(rdp);
        rdp.setAdao(adp);


        String gbdatum = "1993-03-27";

        Reiziger r1 = new Reiziger(7, "L", "", "Fransen", java.sql.Date.valueOf(gbdatum));
        Adres a1 = new Adres(7, "3732BE", "153", "Henrica van erpweg", "De Bilt", r1);
        rdp.saveReiziger(r1);
        a1.setHuisnummer("135A");
        adp.updateAdres(a1);

        ArrayList<Reiziger> reizigers = rdp.findAll();
        for (Reiziger reiziger : reizigers){
            System.out.println(reiziger);
        }

        System.out.println();
        System.out.println("[Test] ReizigerDAO.findById() find reiziger by id");
        System.out.println(rdp.findById(7));

        System.out.println();
        System.out.println("[Test] ReizigerDAO.findByGbDatum() find by geboortedatum");
        System.out.println(rdp.findByGbDatum("1993-03-27"));

        System.out.println();
        System.out.println("[Test] ReizigerDAO.deleteReiziger() delete reiziger L Fransen");
        System.out.println(rdp.findAll().size());
        rdp.deleteReiziger(r1);
        System.out.println(rdp.findAll().size());
        System.out.println();

        System.out.println("[Test] AdresDAO.findById(7) Adres van r1 - bestaat niet");
        System.out.println(r1.getAdres());
        System.out.println(adp.findAll().contains(r1.getAdres()));






    }

}
