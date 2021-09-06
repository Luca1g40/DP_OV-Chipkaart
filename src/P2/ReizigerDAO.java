package P2;

import java.time.LocalDate;
import java.util.ArrayList;

public interface ReizigerDAO {

    public boolean saveReiziger(Reiziger reiziger);

    public boolean updateReiziger(Reiziger reiziger);

    public boolean deleteReiziger(Reiziger reiziger);

    public ArrayList<Reiziger> findAll();
}
