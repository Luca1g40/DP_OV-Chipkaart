package P4.DAO;

import P4.Domain.OVChipkaart;
import P4.Domain.Reiziger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface OVChipkaartDAO {

    public ArrayList<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException;

    public boolean deleteOVChipkaart(OVChipkaart ov) throws SQLException;

    public boolean saveOVChipkaart(OVChipkaart ov) throws SQLException;

    public boolean updateOVChopkaart(OVChipkaart ov) throws SQLException;



}
