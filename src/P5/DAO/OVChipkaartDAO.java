package P5.DAO;

import P5.Domain.OVChipkaart;
import P5.Domain.Reiziger;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OVChipkaartDAO {

    public ArrayList<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException;

    public boolean deleteOVChipkaart(OVChipkaart ov) throws SQLException;

    public boolean saveOVChipkaart(OVChipkaart ov) throws SQLException;

    public boolean updateOVChipkaart(OVChipkaart ov) throws SQLException;

    public ArrayList<OVChipkaart> findAll() throws SQLException;



}
