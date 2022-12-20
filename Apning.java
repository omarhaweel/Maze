class Apning extends HvitRute {
  public Apning(int radnummer, int kolonnenummer, Labyrint lab) {
    super(radnummer, kolonnenummer, lab);
  }

  @Override
  public String toString() {

    return ".";
  }

  @Override
  public void finn(Rute fra) {

    System.out.println("Apningen er : " + this.radnummer + " " + this.kolonnenummer);
    return;
  }
}
