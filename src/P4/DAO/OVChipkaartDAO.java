package P4.DAO;

import P4.Domain.OVChipkaart;
import P4.Domain.Reiziger;

import java.util.ArrayList;
import java.util.List;

public interface OVChipkaartDAO {

    public ArrayList<OVChipkaart> findByReiziger(Reiziger reiziger);

    public boolean deleteOVChipkaart(OVChipkaart ov);

    public boolean saveOVChipkaart(OVChipkaart ov);

    public boolean updateOVChopkaart(OVChipkaart ov);



}
