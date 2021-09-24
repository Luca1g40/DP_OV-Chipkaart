package P5.DAO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductDAO {

    public boolean saveProduct(ProductDAO pr) throws SQLException;

    public boolean updateProduct(ProductDAO pr) throws SQLException;

    public boolean deleteProduct(ProductDAO pr) throws SQLException;

    public ArrayList<ProductDAO> findAll() throws SQLException;

    public ArrayList<ProductDAO> findByReiziger(ReizigerDAO reizigerDAO);
}
