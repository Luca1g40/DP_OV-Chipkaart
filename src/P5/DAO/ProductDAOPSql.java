package P5.DAO;

import P5.Domain.OVChipkaart;
import P5.Domain.Product;
import P5.Domain.Reiziger;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

public class ProductDAOPSql implements ProductDAO{

    private Connection connection;
    private OVChipkaartDAO odao;

    public ProductDAOPSql(Connection connection){
        this.connection = connection;
    }

    public void setOvdao(OVChipkaartDAO odao) {
        this.odao = odao;
    }


    @Override
    public boolean saveProduct(Product pr) throws SQLException {
        try{
            String q = "INSERT INTO product (product_nummer, naam, beschrijving, prijs) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(q);
            ps.setInt(1, pr.getProductNummer());
            ps.setString(2, pr.getNaam());
            ps.setString(3, pr.getBeschrijving());
            ps.setDouble(4, pr.getPrijs());
            ps.executeQuery();
            ps.close();

            ArrayList<Integer> kaartNummers = pr.getOvCardnummers();
            String q2 = "INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer, status, last_update) VALUES (?, ?, ?, ?)";
            for(Integer kaartNummer : kaartNummers){
                PreparedStatement ps2 = connection.prepareStatement(q2);
                ps2.setInt(1, kaartNummer);
                ps2.setInt(2, pr.getProductNummer());
                ps2.setString(3, "Actief");
                ps2.setDate(4, new Date(System.currentTimeMillis()));
                ps2.executeQuery();
                ps2.close();
            }
        }
        catch (SQLException sqlException){
                sqlException.printStackTrace();
            }
        catch (Exception exception){
            exception.printStackTrace();
        }

        return true;
    }


    @Override
    public boolean updateProduct(Product pr) throws SQLException {
        try {
            String q = "UPDATE product VALUES (?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(q);
            ps.setInt(1, pr.getProductNummer());
            ps.setString(2, pr.getNaam());
            ps.setString(3, pr.getBeschrijving());
            ps.setDouble(4, pr.getPrijs());
            ps.executeQuery();
            ps.close();

            String q2 = "DELETE FROM ov_chipkaart_product WHERE product_nummer = ?";
            PreparedStatement ps2 = connection.prepareStatement(q2);
            ps2.setInt(1, pr.getProductNummer());
            ps2.execute();
            ps2.close();

            ArrayList<Integer> kaartNummers = pr.getOvCardnummers();
            String q3 = "INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer, status, last_update) VALUES (?, ?, ?, ?)";
            for(Integer kaartNummer : kaartNummers){
                PreparedStatement ps3 = connection.prepareStatement(q3);
                ps3.setInt(1, kaartNummer);
                ps3.setInt(2, pr.getProductNummer());
                ps3.setString(3, "Actief");
                ps3.setDate(4, new Date(System.currentTimeMillis()));
                ps3.executeQuery();
                ps3.close();
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteProduct(Product pr) throws SQLException {
        try{
            String q = "DELETE FROM product WHERE product_nummer = ?";
            PreparedStatement pst = connection.prepareStatement(q);
            pst.setInt(1, pr.getProductNummer());
            pst.executeQuery();
            pst.close();

            String q2 = "DELETE FROM ov_chipkaart_product WHERE productnummer = ?";
            PreparedStatement pst2 = connection.prepareStatement(q2);
            pst2.executeQuery();
            pst2.close();

        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        catch (Exception exception){
            exception.printStackTrace();
        }
        return true;
    }

    @Override
    public ArrayList<Product> findAll() throws SQLException {
        ArrayList<Product> products = new ArrayList<>();
        String q = "SELECT * FROM product";
        PreparedStatement pst = connection.prepareStatement(q);
        ResultSet rs = pst.executeQuery();
        while(rs.next()) {
            int productNummer = rs.getInt("product_nummer");
            String productNaam = rs.getString("naam");
            String beschrijving = rs.getString("beschrijving");
            double productPrijs = rs.getDouble("prijs");
            products.add(new Product(productNummer, productNaam, beschrijving, productPrijs));
        }
        ArrayList<OVChipkaart> chipkaarten = odao.findAll();

        for (OVChipkaart ovChipkaart : chipkaarten){
            ArrayList<Product> ovchipkaartproducten = ovChipkaart.getProducts();
            for (Product ovProduct : ovchipkaartproducten){
                for (Product product : products){
                    if (product.getProductNummer() == ovProduct.getProductNummer() && !product.getOvCardnummers().contains(ovChipkaart.getKaartNummer())){
                        product.addOvChipkaar(ovChipkaart.getKaartNummer());
                    }
                }
            }
        }
            return products;
        }

    @Override
    public ArrayList<Product> findByOvchipkaart(OVChipkaart ovChipkaart) throws SQLException {
        String q = "SELECT * FROM ov_chipkaart\n" +
                "JOIN ov_chipkaart_product ON ov_chipkaart.kaart_nummer = ov_chipkaart_product.kaart_nummer\n" +
                "JOIN product ON product.product_nummer = ov_chipkaart_product.product_nummer\n" +
                "WHERE ov_chipkaart.kaart_nummer = ?";
        PreparedStatement pst = connection.prepareStatement(q);
        pst.setInt(1, ovChipkaart.getKaartNummer());
        ResultSet rs = pst.executeQuery();
        ArrayList<Product> products = new ArrayList<>();
        while(rs.next()){
            int productNummer = rs.getInt("product_nummer");
            String naam = rs.getString("naam");
            String beschrijving = rs.getString("beschrijving");
            double prijs = rs.getDouble("prijs");
            Product product = new Product(productNummer, naam, beschrijving, prijs);

            String q3 = "SELECT ov_chipkaart_product.kaart_nummer FROM product " +
                    "JOIN ov_chipkaart_product ON product.product_nummer = ov_chipkaart_product.product_nummer " +
                    "where product.product_nummer = ?";
            PreparedStatement ps3 = connection.prepareStatement(q3);
            ps3.setInt(1, productNummer);
            ResultSet rs3 = ps3.executeQuery();
            while (rs3.next()){
                int kaart_nummer = rs3.getInt("kaart_nummer");
                if (!product.getOvCardnummers().contains(kaart_nummer)){
                    product.addOvChipkaar(kaart_nummer);
                }
            }
            ps3.close();
            rs3.close();

            products.add(product);
        }
        return products;
    }
}
