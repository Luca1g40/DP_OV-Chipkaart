package P4.DAO;

import P4.Domain.Reiziger;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public interface ReizigerDAO {

    public boolean saveReiziger(Reiziger reiziger) throws SQLException;

    public boolean updateReiziger(Reiziger reiziger) throws SQLException;

    public boolean deleteReiziger(Reiziger reiziger) throws SQLException;

    public ArrayList<Reiziger> findAll() throws SQLException;

    public Reiziger findById(int id) throws SQLException;

    public ArrayList<Reiziger> findByGbDatum(LocalDate datum) throws SQLException;
}
