package P3.DAO;

import P3.Domain.Reiziger;
import P3.Domain.Adres;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AdresDAO {


    public boolean saveAdres(Adres adres) throws SQLException;

    public boolean updateAdres(Adres adres) throws SQLException;

    public boolean deleteAdres(Adres adres) throws SQLException;

    public Adres findByReiziger(Reiziger reiziger) throws SQLException;

    public ArrayList<Adres> findAll() throws SQLException;

}
