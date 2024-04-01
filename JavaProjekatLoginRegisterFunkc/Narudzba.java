import java.util.*;
import Narudzba.*;






public class Narudzba {
    public static void main(String[] args) {
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

            int odabir = scanner.nextInt();

            if (odabir == artikli.size() + 1) {
                if (korpa.isEmpty()) {
                    System.out.println("Došlo je do greške, molimo odaberite neki proizvod");
                    continue;
                } else {
                    break;
                }
            } else if (odabir < 1 || odabir > artikli.size()) {
                System.out.println("Nevazeci unos, molimo pokušajte ponovno.");
                continue;
            }

            System.out.println("Unesite kolicinu:");
            int kolicinaArtikla = scanner.nextInt();
            if (kolicinaArtikla <= 0) {
                System.out.println("Neispravan unos kolicine, molimo pokušajte ponovno.");
                continue;
            }
            ukupnaCijena += artikli.get(odabir - 1).cijena * kolicinaArtikla;
            korpa.add(artikli.get(odabir - 1));
            kolicina.add(kolicinaArtikla);
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
            System.out.println("Unesite adresu dostave:");
            scanner.nextLine(); 
            adresa = scanner.nextLine();
            System.out.println("Unesite sprat (ako nije dostupno, unesite 0):");
            sprat = scanner.nextInt();

            if (adresa.isEmpty()) {
                System.out.println("Došlo je do greške, molimo upišite adresu");
            }
        } while (adresa.isEmpty());

        Korisnik korisnik = new Korisnik(adresa, sprat);

        System.out.println("Odaberite nacin placanja:");
        System.out.println("1. Plati odmah (credit card)");
        System.out.println("2. Plati pri preuzimanju");

        int odabirPlacanja = scanner.nextInt();

        if (odabirPlacanja == 1) {
            System.out.println("Unesite podatke kartice:");
            System.out.println("Ime:");
            String ime = scanner.next();
            System.out.println("Broj kartice (mora biti 16 znamenki):");
            long brojKartice = scanner.nextLong();
            if (String.valueOf(brojKartice).length() != 16) {
                System.out.println("Nevazeci unos, broj kartice mora imati 16 cifara");
                return;
            }
            System.out.println("Datum isteka (mjesec/godina):");
            String datumIsteka = scanner.next();
            if (!datumIsteka.matches("\\d{2}/\\d{2}")) {
                System.out.println("Nevazeci unos, unesite ispravan datum");
                return;
            }
            String[] parts = datumIsteka.split("/");
            int month = Integer.parseInt(parts[0]);
            int year = Integer.parseInt(parts[1]);
            Calendar cal = Calendar.getInstance();
            int currentYear = cal.get(Calendar.YEAR) - 2000; 
            int currentMonth = cal.get(Calendar.MONTH) + 1; 
            if (year < currentYear || (year == currentYear && month < currentMonth)) {
                System.out.println("Nevazeci unos, vaša kartica je istekla");
                return;
            }
            System.out.println("CVV:");
            int cvv = scanner.nextInt();

            // Simulacija placanja...
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
        } else if (odabirPlacanja == 2) {
            System.out.println("Hvala na narudzbi");
        } else {
            System.out.println("Nevazeci unos, molimo pokušajte ponovno.");
        }
    }
}
