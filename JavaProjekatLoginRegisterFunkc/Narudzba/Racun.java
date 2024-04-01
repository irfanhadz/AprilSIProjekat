package Narudzba;
import java.util.*;

public class Racun {
  public int brojTransakcije;
  public String nacinPlacanja;
  public ArrayList<Artikal> narudzba;
  public ArrayList<Integer> kolicina;
  public double ukupnaCijena;
  public Date datumIVrijeme;

  public Racun(int brojTransakcije, String nacinPlacanja, ArrayList<Artikal> narudzba, ArrayList<Integer> kolicina, double ukupnaCijena, Date datumIVrijeme) {
      this.brojTransakcije = brojTransakcije;
      this.nacinPlacanja = nacinPlacanja;
      this.narudzba = narudzba;
      this.kolicina = kolicina;
      this.ukupnaCijena = ukupnaCijena;
      this.datumIVrijeme = datumIVrijeme;
  }
}