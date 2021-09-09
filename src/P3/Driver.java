package P3;

import P2.Reiziger;
import P2.ReizigerDAO;
import P2.ReizigerDAOPsql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Driver {

    public static Connection connection;

    public static void main(String[] args) throws SQLException {
        connection = DriverManager.getConnection("jdbc:postgresql:ovchip", "postgres", "1234");
        AdresDAOPsql adp = new AdresDAOPsql(connection);
        ReizigerDAOPsql rdp = new ReizigerDAOPsql(connection);
        String gbdatum = "1993-03-27";
        Reiziger r1 = new Reiziger(12, "L J", "Van", "Staal", java.sql.Date.valueOf(gbdatum));
        Adres a1 = new Adres(15, "1123 XJ", "15A", "Dorpstraat", "Utrecht", r1);
        Reiziger r2 = new Reiziger(22, "J L", "Van", "Ijzer", java.sql.Date.valueOf(gbdatum));

//        rdp.saveReiziger(r2);
//        adp.saveAdres(a1);
        testAdresDAO(adp);
    }


    private static void testAdresDAO(AdresDAO adp) throws SQLException{
        String gbdatum = "1993-03-27";
        Reiziger r1 = new Reiziger(12, "L J", "Van", "Staal", java.sql.Date.valueOf(gbdatum));
        Adres a1 = new Adres(15, "1123 XJ", "15A", "Dorpstraat", "Utrecht", r1);

        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle adressen op
        System.out.println("[Test] adresDAOPsql.findAllAdressen() ");
        System.out.println("alle adressen:");
        for(Adres adres : adp.findAllAdressen()){
            System.out.println(adres);
        }
        System.out.println();


        // Haal adres op per reiziger
        System.out.println("[Test] adresDAOPsql.findByReiziger(r1) ");
        System.out.println("print uit reiziger object: " + r1.getAdres());
        System.out.println("print uit ADP: " + adp.findByReiziger(r1));
        System.out.println();

//        Opslaan adres
        System.out.println("[Test] adresDAOPsql.save() ");
        System.out.println("De lengte van de lijst voor het opslaan van een nieuwe adres :" + adp.findAllAdressen().size());
        Reiziger r2 = new Reiziger(22, "J L", "Van", "Ijzer", java.sql.Date.valueOf(gbdatum));
        Adres a2 = new Adres(25, "2256 XJ", "15B", "Stadstraat", "Utrecht", r2);
        adp.saveAdres(a2);
        System.out.println("De lengte van de lijst na het opslaan van een nieuw adres : " + adp.findAllAdressen().size());
        System.out.println();

        //Updaten adres
        System.out.println("[Test] adresDAOPsql.updateAdres() ");
        System.out.println("Adres voor het updaten" + a1);
        a1.setPostcode("3333MK");
        a1.setHuisnummer("44");
        adp.updateAdres(a1);
        System.out.println("Adres na het updaten" + a1);
        System.out.println();

        //Delete adres
        System.out.println("[Test] adresDAOPsql.deleteAdres()");
        System.out.println("Het totaal aantal adressen in de lijst :" + adp.findAllAdressen().size());
        adp.deleteAdres(a2);
        System.out.println("Het totaal aantal adressen in de lijst na het verwijderen:"+ adp.findAllAdressen().size());
    }

}
