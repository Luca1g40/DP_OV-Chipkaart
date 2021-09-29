package P5.DAO;

import P5.Domain.OVChipkaart;
import P5.Domain.Product;
import P5.Domain.Reiziger;

import java.sql.*;
import java.util.ArrayList;

public class OVChipkaartDAOPsql implements OVChipkaartDAO {

    private Connection connection;
    private ReizigerDAO rdao;
    private ProductDAO pdao;

    public OVChipkaartDAOPsql(Connection connection){
        this.connection = connection;
    }
    public void setRdao(ReizigerDAO rdao) {
        this.rdao = rdao;
    }

    public void setPdao(ProductDAO pdao) {
        this.pdao = pdao;
    }

    @Override
    public ArrayList<OVChipkaart> findByReiziger(Reiziger reiziger) {
        try {

            String q2 = "SELECT * FROM product";
            PreparedStatement pst2 = connection.prepareStatement(q2);
            ArrayList<Product> products = new ArrayList<>();
            ResultSet rs2 = pst2.executeQuery();
            while (rs2.next()){
                int productNummer = rs2.getInt("product_nummer");
                String naam = rs2.getString("naam");
                String beschrijving = rs2.getString("beschrijving");
                double prijs = rs2.getDouble("prijs");
                products.add(new Product(productNummer, naam, beschrijving, prijs));
            }
            rs2.close();
            pst2.close();

            String q = "SELECT * FROM ov_chipkaart WHERE reiziger_id = ?";
            PreparedStatement pst = connection.prepareStatement(q);
            pst.setInt(1,reiziger.getId());
            ResultSet rs = pst.executeQuery();
            ArrayList<OVChipkaart> ovchips = new ArrayList<>();

            while (rs.next()) {
                int kaartnummer = rs.getInt("kaart_nummer");
                Date geldigTot = rs.getDate("geldig_tot");
                int klasse = rs.getInt("klasse");
                double saldo = rs.getDouble("saldo");

                OVChipkaart ovChipkaart = new OVChipkaart(kaartnummer, geldigTot, klasse, saldo, reiziger);
                String q3 = "SELECT product.product_nummer FROM product " +
                        "JOIN ov_chipkaart_product ON product.product_nummer = ov_chipkaart_product.product_nummer " +
                        "where ov_chipkaart_product.kaart_nummer = ?";
                PreparedStatement pst3 = connection.prepareStatement(q3);
                pst3.setInt(1, kaartnummer);
                ResultSet rs3 = pst3.executeQuery();

                while(rs3.next()){
                    int productNummer = rs3.getInt("product_nummer");
                    for(Product product : products){
                        if (product.getProductNummer() == productNummer){
                            ovChipkaart.addProduct(product);
                            product.addOvChipkaar(kaartnummer);
                        }
                    }
                }

                pst3.close();
                rs3.close();
                ovchips.add(ovChipkaart);
            }
            rs.close();
            pst.close();
            return ovchips;

        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteOVChipkaart(OVChipkaart ov) {
        try {

            String q = "DELETE FROM ov_chipkaart WHERE kaart_nummer = ?";
            PreparedStatement pst = connection.prepareStatement(q);
            pst.setInt(1, ov.getKaartNummer());
            pst.execute();
            pst.close();

            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean saveOVChipkaart(OVChipkaart ov) {
        try {
            String q = ("INSERT INTO ov_chipkaart" +
                    " (kaart_nummer, geldig_tot, klasse, saldo, reiziger_id)" +
                    "VALUES (?, ?, ? , ? , ?)");

            PreparedStatement pst = connection.prepareStatement(q);
            pst.setInt(1,ov.getKaartNummer());
            pst.setDate(2, (Date) ov.getGeldigTot());
            pst.setInt(3, ov.getKlasse());
            pst.setDouble(4, ov.getSaldo());
            pst.setInt(5, ov.getReiziger().getId());

            pst.execute();
            pst.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateOVChipkaart(OVChipkaart ov) {
        try {
            String q = "UPDATE ov_chipkaart SET " +
                    "kaart_nummer = ?, geldig_tot = ?, klasse = ?, saldo = ?, reiziger_id = ?" +
                    "WHERE kaart_nummer = ?";
            PreparedStatement pst = connection.prepareStatement(q);
            pst.setInt(1,ov.getKaartNummer());
            pst.setDate(2, (Date) ov.getGeldigTot());
            pst.setInt(3, ov.getKlasse());
            pst.setDouble(4, ov.getSaldo());
            pst.setInt(5, ov.getReiziger().getId());
            pst.setInt(6,ov.getKaartNummer());
            pst.execute();
            pst.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ArrayList<OVChipkaart> findAll() throws SQLException {
        try {
            String q2 = "SELECT * FROM product";
            PreparedStatement pst2 = connection.prepareStatement(q2);
            ArrayList<Product> products = new ArrayList<>();
            ResultSet rs2 = pst2.executeQuery();
            while (rs2.next()){
                int productNummer = rs2.getInt("product_nummer");
                String naam = rs2.getString("naam");
                String beschrijving = rs2.getString("beschrijving");
                double prijs = rs2.getDouble("prijs");
                products.add(new Product(productNummer, naam, beschrijving, prijs));
            }
            rs2.close();
            pst2.close();

            String s = "SELECT * FROM ov_chipkaart";
            PreparedStatement ps = connection.prepareStatement(s);
            ResultSet rs = ps.executeQuery();
            ArrayList<OVChipkaart> chipkaarts = new ArrayList<>();
            while (rs.next()){
                int kaartNummer = rs.getInt("kaart_nummer");
                Date geldigTot = rs.getDate("geldig_tot");
                int klasse = rs.getInt("klasse");
                double saldo = rs.getDouble("saldo");
                int reizigerId = rs.getInt("reiziger_id");
                OVChipkaart ovChipkaart = new OVChipkaart(kaartNummer, geldigTot, klasse, saldo, rdao.findById(reizigerId));

                String q3 = "SELECT product.product_nummer FROM product " +
                        "JOIN ov_chipkaart_product ON product.product_nummer = ov_chipkaart_product.product_nummer " +
                        "where ov_chipkaart_product.kaart_nummer = ?";
                PreparedStatement pst3 = connection.prepareStatement(q3);
                pst3.setInt(1, kaartNummer);

                ResultSet rs3 = pst3.executeQuery();

                while(rs3.next()){
                    int productNummer = rs3.getInt("product_nummer");
                    for(Product product : products){
                        if (product.getProductNummer() == productNummer){
                            ovChipkaart.addProduct(product);
                            product.addOvChipkaar(kaartNummer);
                        }
                    }
                }
                pst3.close();
                rs3.close();

                chipkaarts.add(ovChipkaart);
            }
            ps.close();
            rs.close();
            return chipkaarts;
        }catch (SQLException ignored){}
        return null;
    }

}
