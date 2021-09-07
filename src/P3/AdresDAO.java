package P3;

import P2.Reiziger;
import P2.ReizigerDAO;

public interface AdresDAO {


    public boolean saveAdres(Adres adres);

    public boolean updateAdres(Adres adres);

    public boolean deleteAdres(Adres adres);

    public Adres findById(Adres adres);

    public Adres findByReiziger(Reiziger reiziger);

}
