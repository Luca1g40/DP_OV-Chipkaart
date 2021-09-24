package P5.DAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDAOPSql implements ProductDAO{
    @Override
    public boolean saveProduct(ProductDAO pr) throws SQLException {
        return false;
    }

    @Override
    public boolean updateProduct(ProductDAO pr) throws SQLException {
        return false;
    }

    @Override
    public boolean deleteProduct(ProductDAO pr) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<ProductDAO> findAll() throws SQLException {
        return null;
    }

    @Override
    public ArrayList<ProductDAO> findByReiziger(ReizigerDAO reizigerDAO) {
        return null;
    }
}
