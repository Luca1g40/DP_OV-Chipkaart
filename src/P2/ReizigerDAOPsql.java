package P2;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ReizigerDAOPsql implements ReizigerDAO{

    private Connection connection;

    public ReizigerDAOPsql(Connection connection){
        this.connection = connection;
    }


    @Override
    public boolean saveReiziger(Reiziger reiziger) {
        try {
            int id = reiziger.getId();
            String naam = reiziger.getVoorletters();
            String tussenvoegsel = reiziger.getTussenvoegsel();
            String achternaam = reiziger.getAchternaam();
            Date geboortedatum = reiziger.getGeboortedatum();
            String q = ("INSERT INTO reiziger" +
                    " (voorletters, tussenvoegsel, achternaam, geboortedatum, reiziger_id)" +
                    "VALUES (?, ?, ? , ? , ?)");

            PreparedStatement pst = connection.prepareStatement(q);
            pst.setString(1,naam);
            pst.setString(2, tussenvoegsel);
            pst.setString(3, achternaam);
            pst.setDate(4, geboortedatum);
            pst.setInt(5, id);
            pst.execute();
            pst.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Maak een SQL query aan de hand van Object
        // exectute query
        return false;
    }

    @Override
    public boolean updateReiziger(Reiziger reiziger) {
        try {
            int id = reiziger.getId();
            String naam = reiziger.getVoorletters();
            String tussenvoegsel = reiziger.getTussenvoegsel();
            String achternaam = reiziger.getAchternaam();
            Date geboortedatum = reiziger.getGeboortedatum();


            String q = "UPDATE reiziger SET " +
                    "voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ?, reiziger_id = ? " +
                    "WHERE reiziger_id = ?";
            PreparedStatement pst = connection.prepareStatement(q);
            pst.setString(1,naam);
            pst.setString(2, tussenvoegsel);
            pst.setString(3, achternaam);
            pst.setDate(4, geboortedatum);
            pst.setInt(5, id);
            pst.setInt(6,id);
            pst.execute();
            pst.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteReiziger(Reiziger reiziger) {
        try {
            int id = reiziger.getId();

            String q = "DELETE FROM reiziger adres WHERE reiziger_id = ?";
            PreparedStatement pst = connection.prepareStatement(q);
            pst.setInt(1,id);
            pst.execute();
            pst.close();


            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    @Override
    public ArrayList<Reiziger> findAll() {
        try{
            Statement myStmt = connection.createStatement();
            ResultSet rs = myStmt.executeQuery("SELECT * FROM reiziger");
            ArrayList<Reiziger> reizigers = new ArrayList<>();

            while ((rs.next())){
                String naam = rs.getString("voorletters");
                String achternaam = rs.getString("achternaam");
                String tussenvoegsel = rs.getString("tussenvoegsel");
                Date geboortedatum = rs.getDate("geboortedatum");
                int reizigerid = rs.getInt("reiziger_id");
                reizigers.add(new Reiziger(reizigerid, naam, achternaam, tussenvoegsel, geboortedatum));
            }


            rs.close();
            myStmt.close();
            return reizigers;
        }
        catch (Exception exc){
            System.out.println(exc);
        }
        return null;
    }

}
