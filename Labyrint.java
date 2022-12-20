import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

class Labyrint {
  int antallRader;
  int antallKolonner;
  Rute[][] labyrint;

  public Labyrint(String filnavn) {

    labyrint = LesFil(filnavn);
    // System.out.println(this);

  }

  public Rute[][] LesFil(String filnavn) {
    Scanner sc = null;

    try {
      sc = new Scanner(new File(filnavn));
    } catch (FileNotFoundException e) {
      System.out.println("fant ikke filen");

    }

    String[] str = sc.nextLine().split(" ");
    int antR = Integer.parseInt(str[0]);
    antallRader = antR;
    int antK = Integer.parseInt(str[1]);
    antallKolonner = antK;

    Rute[][] labyrint = new Rute[antR][antK];
    int rad;
    int kol;
    for (rad = 0; rad < antR; rad++) {
      String linje = sc.nextLine();

      for (kol = 0; kol < antK; kol++) {

        if (linje.charAt(kol) == '#') {

          labyrint[rad][kol] = new SortRute(rad, kol, this);

        }
        if (linje.charAt(kol) == '.') {

          labyrint[rad][kol] = new HvitRute(rad, kol, this);

        }
        if (linje.charAt(kol) == '.') {
          if (rad == antallRader - 1 || rad == 0 || kol == antallKolonner - 1 || kol == 0) {
            labyrint[rad][kol] = new Apning(rad, kol, this);
          }
        }

      }

    }

    return labyrint;

  }

  public HashMap<String, Rute> finnNaboer(Rute r) {
    int rad = r.radnummer;
    int kol = r.kolonnenummer;

    HashMap<String, Rute> h = new HashMap<>();
    // fikse øvre grense
    if (rad == 0) {
      if (kol == 0) {
        h.put("nord", null);
        h.put("vest", null);
        h.put("oest", labyrint[rad][kol + 1]);
        h.put("syd", labyrint[rad + 1][kol]);
      }

      if (kol == antallKolonner - 1) {
        h.put("oest", null);
        h.put("vest", labyrint[rad][kol - 1]);
        h.put("nord", null);
        h.put("syd", labyrint[rad + 1][kol]);
      }
    }
    if (rad == 0 && kol != 0 && kol != antallKolonner - 1) {
      h.put("nord", null);
      h.put("vest", labyrint[rad][kol - 1]);
      h.put("oest", labyrint[rad][kol + 1]);
      h.put("syd", labyrint[rad + 1][kol]);

    }
    // fikse nedre grense
    if (rad == antallRader - 1) {
      if (kol == 0) {
        h.put("syd", null);
        h.put("vest", null);
        h.put("nord", labyrint[rad - 1][kol]);
        h.put("oest", labyrint[rad][kol + 1]);
      }
      if (kol == antallKolonner - 1) {
        h.put("oest", null);
        h.put("syd", null);
        h.put("nord", labyrint[rad - 1][kol]);
        h.put("vest", labyrint[rad][kol - 1]);
      }
    }

    if (rad == antallRader - 1 && kol != 0 && kol != antallKolonner - 1) {
      h.put("syd", null);
      h.put("vest", labyrint[rad][kol - 1]);
      h.put("oest", labyrint[rad][kol + 1]);
      h.put("nord", labyrint[rad - 1][kol]);
    }
    // fikse vestre grense
    if (kol == 0) {
      if (rad == 0) {
        h.put("nord", null);
        h.put("vest", null);
        h.put("oest", labyrint[rad][kol + 1]);
        h.put("syd", labyrint[rad + 1][kol]);
      }
      if (rad == antallRader - 1) {
        h.put("syd", null);
        h.put("vest", null);
        h.put("oest", labyrint[rad][kol + 1]);
        h.put("nord", labyrint[rad - 1][kol]);
      }
    }
    if (kol == 0 && rad != 0 && rad != antallRader - 1) {
      h.put("vest", null);
      h.put("nord", labyrint[rad - 1][kol]);
      h.put("oest", labyrint[rad][kol + 1]);
      h.put("syd", labyrint[rad + 1][kol]);
    }
    // fikse østre grense
    if (kol == antallKolonner - 1) {
      if (rad == 0) {
        h.put("oest", null);
        h.put("nord", null);
        h.put("vest", labyrint[rad][kol - 1]);
        h.put("syd", labyrint[rad + 1][kol]);
      }
      if (rad == antallRader - 1) {
        h.put("oest", null);
        h.put("syd", null);
        h.put("nord", labyrint[rad - 1][kol]);
        h.put("vest", labyrint[rad][kol - 1]);
      }
    }
    if (kol == antallKolonner - 1 && rad != 0 && rad != antallRader - 1) {
      h.put("oest", null);
      h.put("nord", labyrint[rad - 1][kol]);
      h.put("vest", labyrint[rad][kol - 1]);
      h.put("syd", labyrint[rad + 1][kol]);
    }
    if (rad > 0 && rad < antallRader - 1 && kol > 0 && kol < antallKolonner - 1) {
      h.put("nord", labyrint[rad - 1][kol]);
      h.put("syd", labyrint[rad + 1][kol]);
      h.put("vest", labyrint[rad][kol - 1]);
      h.put("oest", labyrint[rad][kol + 1]);
    }

    return h;
  }

  public String toString() {
    String str = "";
    String s;

    for (int i = 0; i < labyrint.length; i++) {
      for (int j = 0; j < labyrint[i].length; j++) {
        if (labyrint[i][j] instanceof SortRute) {
          s = "#";
        } else if (labyrint[i][j] instanceof Apning) {
          s = ".";
        } else {
          s = ".";
        }

        str += " " + s;
        // om delelig på antall kolonner, så skift linje, concate to Strengen, men
        // ved
        // linjeskift må "\n" of " " fjernes for å ikke telles.
        if (str.replace("\n", "").replace(" ", "").chars().count() % labyrint[i].length == 0) {
          str = str + "\n";
        }

      }

    }
    return ("--- LABYRINTH ----" + "\n" + "\n" + str);

  }

  //
  public void finnUtveiFra(int rad, int kol) {
    if (rad < 0 || rad > antallRader - 1 || kol < 0 || kol > antallKolonner - 1) {
      System.out.println("Ugyldige Koordinater");
      System.out.println("Vennligst oppgi gyldig input ");
      return;
    }
    if (labyrint[rad][kol] instanceof SortRute) {
      System.out.println("Du oppga en sort rute");
      System.out.println("Vennligst oppgi koordinater");
      System.out.println("til en Hvit rute");
      System.out.println("for å finne en åpning");
      System.out.println();
      return;
    }
    labyrint[rad][kol].finn();

  }

}
