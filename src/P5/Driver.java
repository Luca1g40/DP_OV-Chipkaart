package P5;

import P5.DAO.AdresDAOPsql;
import P5.DAO.OVChipkaartDAOPsql;
import P5.DAO.ReizigerDAOPsql;
import P5.Domain.Adres;
import P5.Domain.OVChipkaart;
import P5.Domain.Reiziger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
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



        LocalDate date = LocalDate.of(1993,3,27);

        Reiziger r1 = new Reiziger(7, "L", "", "Fransen", Date.valueOf(date));
        Adres a1 = new Adres(12, "3732BE", "153", "Henrica van erpweg", "De Bilt", r1);
        OVChipkaart ov1 = new OVChipkaart(8, Date.valueOf("2000-05-25"), 1, 24.0, r1);


        System.out.println(odp.findAll());
        ArrayList<Reiziger> reizigers = rdp.findAll();
        for (Reiziger reiziger : reizigers){
            System.out.println(reiziger);
        }

        System.out.println();
        System.out.println("[Test] ReizigerDAO.findById() find reiziger by id");
        System.out.println(rdp.findById(1));

        System.out.println();
        System.out.println("[Test] ReizigerDAO.findByGbDatum() find by geboortedatum");
        System.out.println(rdp.findByGbDatum(LocalDate.of(1993,3,27)));

        System.out.println();
        System.out.println("[Test] ReizigerDAO.deleteReiziger() delete reiziger L Fransen");
        System.out.println(rdp.findAll().size());
        rdp.deleteReiziger(r1);
        System.out.println(rdp.findAll().size());




    }

}
