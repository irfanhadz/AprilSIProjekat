package Izuzeci;

public class Izuzeci {
  public static class NeispravanOdabirProizvodaException extends Exception {
      public NeispravanOdabirProizvodaException(String message) {
          super("Neispravan odabir: " + message);
      }
  }

  public static class NevazeciUnosException extends Exception {
      public NevazeciUnosException(String message) {
          super("Nevazeci Unos: " + message);
      }
  }
}
