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

        String gbdatum = "2000-01-01";
        Reiziger r1 = new Reiziger(10, "Test", "van", "Tester", java.sql.Date.valueOf(gbdatum));
        Adres a1 = new Adres(10, "1234AA", "123", "Teststraat", "Teststad", r1);


        System.out.println("[Test] ReizigerDAO.saveReiziger() Save reiziger");
        System.out.println("Voor het opslaan van de nieuwe reiziger");
        ArrayList<Reiziger> reizigers = rdp.findAll();
        for (Reiziger reiziger : reizigers){
            System.out.println(reiziger);
        }
        rdp.saveReiziger(r1);
        System.out.println();

        System.out.println("Na het opslaan van de nieuwe reiziger");
        ArrayList<Reiziger> reizigers1 = rdp.findAll();
        for (Reiziger reiziger : reizigers1){
            System.out.println(reiziger);
        }

        System.out.println();
        ArrayList<Adres> adressen = adp.findAll();
        for (Adres adres : adressen){
            System.out.println(adres);
        }

        System.out.println();
        System.out.println("[Test] ReizigerDAO.updateReiziger() update naam van de reiziger met adres");
        r1.setVoorletters("Nieuw");
        rdp.updateReiziger(r1);
        System.out.println(rdp.findById(10));

        System.out.println();
        System.out.println("[Test] adresDAO.findByReiziger() ");
        System.out.println(adp.findByReiziger(r1));

        System.out.println();
        System.out.println("[Test] ReizigerDAO.findById() find reiziger by id");
        System.out.println("reiziger met reiziger id 2 ophalen.");
        System.out.println(rdp.findById(2));

        System.out.println();
        System.out.println("[Test] ReizigerDAO.findByGbDatum() find by geboortedatum");
        System.out.println("Reiziger met geboortedatum 1998-08-11 ophalen");
        System.out.println(rdp.findByGbDatum("1998-08-11"));

        System.out.println();
        System.out.println("[Test] ReizigerDAO.deleteReiziger() delete " + r1);
        System.out.println("Lengte van de lijst voor het verwijderen: " + rdp.findAll().size());
        rdp.deleteReiziger(r1);
        System.out.println("Lengte van de lijst na het verwijderen: " + rdp.findAll().size());







    }

}
