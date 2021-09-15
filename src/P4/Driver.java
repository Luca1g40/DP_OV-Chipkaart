package P4;

import P4.DAO.AdresDAOPsql;
import P4.DAO.OVChipkaartDAOPsql;
import P4.DAO.ReizigerDAOPsql;
import P4.Domain.Adres;
import P4.Domain.OVChipkaart;
import P4.Domain.Reiziger;

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
        OVChipkaartDAOPsql odp = new OVChipkaartDAOPsql(connection);

        adp.setRdao(rdp);
        rdp.setAdao(adp);
        rdp.setOvdao(odp);
        odp.setRdao(rdp);



        String gbdatum = "1993-03-27";

        Reiziger r1 = new Reiziger(7, "L", "", "Fransen", java.sql.Date.valueOf(gbdatum));
        Adres a1 = new Adres(12, "3732BE", "153", "Henrica van erpweg", "De Bilt", r1);
        OVChipkaart ov1 = new OVChipkaart(8, java.sql.Date.valueOf("2000-05-25"), 1, 24.0, r1);

        rdp.saveReiziger(r1);
//        adp.saveAdres(a1);
//        a1.setHuisnummer("135A");
//        adp.updateAdres(a1);
//        adp.deleteAdres(a1);



        ArrayList<Reiziger> reizigers = rdp.findAll();
        for (Reiziger reiziger : reizigers){
            System.out.println(reiziger);
        }

        System.out.println();
        System.out.println("[Test] ReizigerDAO.findById() find reiziger by id");
        System.out.println(rdp.findById(1));

        System.out.println();
        System.out.println("[Test] ReizigerDAO.findByGbDatum() find by geboortedatum");
        System.out.println(rdp.findByGbDatum("2002-09-17"));

        System.out.println();
        System.out.println("[Test] ReizigerDAO.deleteReiziger() delete reiziger L Fransen");
        System.out.println(rdp.findAll().size());
        rdp.deleteReiziger(r1);
        System.out.println(rdp.findAll().size());




    }

}
