package P5.DAO;

import P5.Domain.Adres;
import P5.Domain.Reiziger;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AdresDAO {


    public boolean saveAdres(Adres adres) throws SQLException;

    public boolean updateAdres(Adres adres) throws SQLException;

    public boolean deleteAdres(Adres adres) throws SQLException;

    public Adres findByReiziger(Reiziger reiziger) throws SQLException;

    public ArrayList<Adres> findAll() throws SQLException;

}
