import java.util.*;
import java.lang.NullPointerException;

class Rute {
  int radnummer;
  int kolonnenummer;
  Labyrint lab;
  Rute nord;
  Rute syd;
  Rute vest;
  Rute oest;

  public Rute(int radnummer, int kolonnenummer, Labyrint lab) {
    this.radnummer = radnummer;
    this.kolonnenummer = kolonnenummer;
    this.lab = lab;
  }

  public void finn() {
    System.out.println("Start-Rute er :" + this.radnummer + " " + this.kolonnenummer);
    for (Rute nabo : lab.finnNaboer(this).values()) {
      try {
        nabo.finn(this);
      } catch (NullPointerException e) {

      }

    }
  }

  public void finn(Rute fra) {

    this.finn(fra);

  }

}
