package P4.DAO;

import P4.Domain.Adres;
import P4.Domain.Reiziger;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AdresDAO {


    public boolean saveAdres(Adres adres) throws SQLException;

    public boolean updateAdres(Adres adres) throws SQLException;

    public boolean deleteAdres(Adres adres) throws SQLException;

    public Adres findByReiziger(Reiziger reiziger) throws SQLException;

    public ArrayList<Adres> findAll() throws SQLException;

}
