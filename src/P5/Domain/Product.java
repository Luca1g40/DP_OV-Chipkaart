package P5.Domain;

import java.sql.Date;
import java.util.ArrayList;

public class Product {
    private int productNummer;
    private String naam;
    private String beschrijving;
    private double prijs;
    private ArrayList<Integer> ovIdnummer;

    public Product(int productNummer, String naam, String beschrijving, double prijs) {
        this.productNummer = productNummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
        this.ovIdnummer = new ArrayList<>();
    }



    public int getProductNummer() {
        return productNummer;
    }

    public void setProductNummer(int productNummer) {
        this.productNummer = productNummer;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(int prijs) {
        this.prijs = prijs;
    }

    public ArrayList<Integer> getOvCardnummers() {
        return ovIdnummer;
    }

    public void addOvChipkaar(int kaartnummer){
        ovIdnummer.add(kaartnummer);
    }

    public String toString(){
        return "productnummer " + productNummer;
    }
}
