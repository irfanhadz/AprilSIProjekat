import java.util.*;
import Narudzba.*;
import Izuzeci.*;
import Izuzeci.Izuzeci.NevazeciUnosException; 






public class Narudzba {
    public static void main(String[] args) throws NevazeciUnosException {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Artikal> artikli = new ArrayList<>();
        artikli.add(new Artikal("King Burger", 9));
        artikli.add(new Artikal("Coca Cola", 3));
        artikli.add(new Artikal("Kinder Pancake", 8));

        ArrayList<Artikal> korpa = new ArrayList<>();
        ArrayList<Integer> kolicina = new ArrayList<>();
        double ukupnaCijena = 0;

        while (true) {
            System.out.println("Odaberite jedan od artikala ili Checkout:");
            for (int i = 0; i < artikli.size(); i++) {
                System.out.println((i + 1) + ". " + artikli.get(i).naziv + " - " + artikli.get(i).cijena + "KM");
            }
            System.out.println((artikli.size() + 1) + ". Checkout");

            try {
                int odabir = scanner.nextInt();

                if (odabir == artikli.size() + 1) {
                    if (korpa.isEmpty()) {
                        throw new Izuzeci.NeispravanOdabirProizvodaException("Doslo je do greske, molimo odaberite neki proizvod");
                    } else {
                        break;
                    }
                } else if (odabir < 1 || odabir > artikli.size()) {
                    throw new Izuzeci.NevazeciUnosException("Nevazeci unos, molimo pokusajte ponovno.");
                }

                System.out.println("Unesite kolicinu:");
                int kolicinaArtikla = scanner.nextInt();
                if (kolicinaArtikla <= 0) {
                    throw new Izuzeci.NevazeciUnosException("Neispravan unos kolicine, molimo pokusajte ponovno.");
                }
                ukupnaCijena += artikli.get(odabir - 1).cijena * kolicinaArtikla;
                korpa.add(artikli.get(odabir - 1));
                kolicina.add(kolicinaArtikla);
            } catch (Izuzeci.NeispravanOdabirProizvodaException e1) {
                System.out.println(e1.getMessage());
            } catch (Izuzeci.NevazeciUnosException e2) {
                System.out.println(e2.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("Nevazeci unos, molimo unesite broj.");
                scanner.next(); 
            }
        }
        System.out.println("================");
        System.out.println("Trenutna narudzba:");
        for (int i = 0; i < korpa.size(); i++) {
            System.out.println(korpa.get(i).naziv + " - " + korpa.get(i).cijena + "KM x " + kolicina.get(i));
        }
        System.out.println("Ukupna cijena: " + ukupnaCijena + "KM");

        String adresa;
        int sprat;
        do {
            try {
                System.out.println("Unesite adresu dostave:");
                scanner.nextLine(); 
                adresa = scanner.nextLine();
                if (adresa.isEmpty()) {
                    throw new Izuzeci.NevazeciUnosException("Doslo je do greske, molimo upisite adresu");
                }
        
                System.out.println("Unesite sprat (ako nije dostupno, unesite 0):");
                sprat = scanner.nextInt();
        
                if (sprat < 0) {
                    throw new Izuzeci.NevazeciUnosException("Nevazeci unos za sprat, molimo unesite pozitivan broj ili 0 ako nije dostupno.");
                }
            } catch (Izuzeci.NevazeciUnosException e) {
                System.out.println(e.getMessage());
                adresa = ""; 
                sprat = -1; 
            }
        } while (adresa.isEmpty() || sprat < 0);

        Korisnik korisnik = new Korisnik(adresa, sprat);

        System.out.println("Odaberite nacin placanja:");
        System.out.println("1. Plati odmah (credit card)");
        System.out.println("2. Plati pri preuzimanju");

        int odabirPlacanja = scanner.nextInt();

        if (odabirPlacanja == 1) {
            while (true) {
                try {
                    System.out.println("Unesite podatke kartice:");
                    System.out.println("Ime:");
                    String ime = scanner.next();
                    
                    System.out.println("Broj kartice (mora biti 16 znamenki):");
                    long brojKartice = scanner.nextLong();
                    if (String.valueOf(brojKartice).length() != 16) {
                        throw new Izuzeci.NevazeciUnosException("Nevazeci unos, broj kartice mora imati 16 cifara");
                    }
                    else if (String.valueOf(brojKartice).matches(".*[^0-9].*")) {
                        throw new InputMismatchException("Broj kartice ne smije sadržavati druge karaktere osim brojeva od 0 do 9");
                    }
                    
                    
                    System.out.println("Datum isteka (mjesec/godina):");
                    String datumIsteka = scanner.next();
                    if (!datumIsteka.matches("(0[1-9]|1[0-2])/\\d{2}")) {
                        throw new Izuzeci.NevazeciUnosException("Nevazeci unos, unesite ispravan datum u formatu (mjesec/godina)");
                    }
                    
                    String[] parts = datumIsteka.split("/");
                    int month = Integer.parseInt(parts[0]);
                    int year = Integer.parseInt(parts[1]);
                    Calendar cal = Calendar.getInstance();
                    int currentYear = cal.get(Calendar.YEAR) - 2000; 
                    int currentMonth = cal.get(Calendar.MONTH) + 1; 
                    if (year < currentYear || (year == currentYear && month < currentMonth)) {
                        throw new Izuzeci.NevazeciUnosException("Nevazeci unos, vasa kartica je istekla");
                    }
                    
                    System.out.println("CVV:");
                    int cvv = scanner.nextInt();
            
                    System.out.println("Hvala na narudzbi");
            
                    Date datumIVrijeme = new Date();
                    Racun racun = new Racun(123456789, "Plati odmah (credit card)", korpa, kolicina, ukupnaCijena, datumIVrijeme);
                    System.out.println("================");
                    System.out.println("Racun:");
                    System.out.println("Broj transakcije: " + racun.brojTransakcije);
                    System.out.println("Nacin placanja: " + racun.nacinPlacanja);
                    System.out.println("Lista proizvoda sa cijenom i kolicinom:");
                    for (int i = 0; i < racun.narudzba.size(); i++) {
                        System.out.println(racun.narudzba.get(i).naziv + " - " + racun.narudzba.get(i).cijena + "KM x " + racun.kolicina.get(i));
                    }
                    System.out.println("Ukupna cijena: " + racun.ukupnaCijena + "KM");
                    System.out.println("Datum i vrijeme: " + racun.datumIVrijeme);
                    
                    break;
                } catch (Izuzeci.NevazeciUnosException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
         else if (odabirPlacanja == 2) {
            System.out.println("Hvala na narudzbi");
        } else {
            if (odabirPlacanja > 2) {
                throw new Izuzeci.NevazeciUnosException("Nevazeci unos, molimo pokusajte ponovno.");
            }
        }
        
        
    }
}
