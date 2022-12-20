import java.util.*;

class HvitRute extends Rute {

  public HvitRute(int radnummer, int kolonnenummer, Labyrint lab) {
    super(radnummer, kolonnenummer, lab);
  }

  @Override
  public String toString() {

    return ".";
  }

  @Override
  public void finn(Rute fra) {

    for (Rute nabo : lab.finnNaboer(this).values()) {
      if (!nabo.equals(fra)) {
        nabo.finn(this);
      }
    }

  }
}
