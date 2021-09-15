package P2;

import P2.Domain.Reiziger;
import P2.DAO.ReizigerDAO;
import P2.DAO.ReizigerDAOPsql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Driver {

    public static Connection connection;

    public static void main(String[] args) throws SQLException {
        connection = DriverManager.getConnection("jdbc:postgresql:ovchip", "postgres", "1234");
        ReizigerDAOPsql rdp = new ReizigerDAOPsql(connection);
        testReizigerDAO(rdp);

    }
    private static void testReizigerDAO(ReizigerDAO rdp) throws SQLException{
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdp.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

//         Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdp.saveReiziger(sietske);
        reizigers = rdp.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Pas naam aan van een reiziger
        System.out.println("[Test] ReizigerDAO.updateReiziger() verander de achternaam");
        System.out.println(sietske);
        sietske.setTussenvoegsel("van");
        sietske.setAchternaam("Rijn");
        rdp.updateReiziger(sietske);
        System.out.println(sietske);
        System.out.println();

        // Verwijder reiziger
        System.out.println("[Test] ReizigerDAO.deleteReiziger() delete reiziger");
        System.out.print("Aantal reizigers voor aanroepen van de methode: " + reizigers.size() + ", ");
        rdp.deleteReiziger(sietske);
        reizigers = rdp.findAll();
        System.out.println("aantal na het aanroepen " + reizigers.size());
        System.out.println();

        System.out.println("[Test] ReizigerDAO.findById(2)");
        System.out.println(rdp.findById(2));
        System.out.println();

        System.out.println("[Test] ReizigerDAO.findByGbDatum()");
        ArrayList<Reiziger> geboorteReiziger = rdp.findByGbDatum("1993-03-27");
        for (Reiziger reiziger : geboorteReiziger){
            System.out.println(reiziger);
        }


    }
}
