package P3.Domain;

import java.sql.Date;

public class Reiziger {

    private int id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;
    private Adres adres;


    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum) {
        this.id = id;
        this.voorletters = voorletters;
        this.achternaam = achternaam;
        this.tussenvoegsel = tussenvoegsel;
        this.geboortedatum = geboortedatum;
    }

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public String toString(){
        if (adres == null){
            if (tussenvoegsel == null){
            return "P2.Domain.Reiziger " + voorletters + " " + achternaam + " met reizigers id: " +  id
                    + " geboortedatum " + geboortedatum;
        }
            else {
                return "P2.Domain.Reiziger " + voorletters + " " + tussenvoegsel + " " + achternaam + " met reizigers id: " +  id
                        + " geboortedatum " + geboortedatum;
            }
        }
        else {
            if (tussenvoegsel == null){
                return "P2.Domain.Reiziger " + voorletters + " " + achternaam + " met reizigers id: " +  id
                        + " geboortedatum " + geboortedatum + " Adres: " + adres;
            }
            else {
                 return "P2.Domain.Reiziger " + voorletters + " " + tussenvoegsel + " " + achternaam + " met reizigers id: " +  id
                    + " geboortedatum " + geboortedatum + " Adres: " + adres;
            }
        }
    }
}