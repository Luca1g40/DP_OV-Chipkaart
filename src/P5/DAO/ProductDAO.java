package P5.DAO;

import P5.Domain.OVChipkaart;
import P5.Domain.Product;
import P5.Domain.Reiziger;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductDAO {

    public boolean saveProduct(Product pr) throws SQLException;

    public boolean updateProduct(Product pr) throws SQLException;

    public boolean deleteProduct(Product pr) throws SQLException;

    public ArrayList<Product> findAll() throws SQLException;

    public ArrayList<Product> findByOvchipkaart(OVChipkaart ovChipkaart) throws SQLException;
}
