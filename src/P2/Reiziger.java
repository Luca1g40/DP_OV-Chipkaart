package P2;

import java.time.LocalDate;

public class Reiziger {

    private int id;
    private String voorletters;
    private String achternaam;
    private String tussenvoegsel;
    private LocalDate geboortedatum;


    public Reiziger(int id, String voorletters, String achternaam, String tussenvoegsel, LocalDate geboortedatum) {
        this.id = id;
        this.voorletters = voorletters;
        this.achternaam = achternaam;
        this.tussenvoegsel = tussenvoegsel;
        this.geboortedatum = geboortedatum;
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

    public LocalDate getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(LocalDate geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public String toString(){
        return "P2.Reiziger " + voorletters + " " + tussenvoegsel + " " + achternaam + " met reizigers id: " +  id
                + " geboortedatum " + geboortedatum;
    }
}