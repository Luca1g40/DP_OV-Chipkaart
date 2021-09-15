package P4.Domain;

import java.util.Date;

public class OVChipkaart {
    private int kaart_nummer;
    private Date geldigTot;
    private int klasse;
    private double saldo;
    private Reiziger reiziger;

    public OVChipkaart(int kaart_nummer, Date geldigTot, int klasse, double saldo, Reiziger reiziger) {
        this.kaart_nummer = kaart_nummer;
        this.geldigTot = geldigTot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger = reiziger;
        reiziger.addOvCard(this);
    }

    public int getKaart_nummer() {
        return kaart_nummer;
    }

    public void setKaart_nummer(int kaart_nummer) {
        this.kaart_nummer = kaart_nummer;
    }

    public Date getGeldigTot() {
        return geldigTot;
    }

    public void setGeldigTot(Date geldigTot) {
        this.geldigTot = geldigTot;
    }

    public int getKlasse() {
        return klasse;
    }

    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    @Override
    public String toString() {
        return "OVChipkaart{" +
                "Kaart nummer: " + kaart_nummer +
                ", geldigTot :" + geldigTot +
                ", klasse: " + klasse +
                ", saldo: " + saldo +
                '}';
    }
}

