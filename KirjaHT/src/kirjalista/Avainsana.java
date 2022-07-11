package kirjalista;

import java.io.*;
import java.util.Random;

public class Avainsana {
    private int tunnusNro;
    private int kirjaNro;
    private String avainsana = "";

    private static int seuraavaNro = 1;

    public Avainsana() {
        // Viel‰ ei tarvita mit‰‰n
    }

    public Avainsana(int kirjaNro) {
        this.kirjaNro = kirjaNro;
    }
    
    public void tulosta(PrintStream out) {
        out.println(avainsana);
    }

    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }

    public int getTunnusNro() {
        return tunnusNro;
    }

    public int getKirjaNro() {
        return kirjaNro;
    }
    
    public static void main(String[] args) {
        Avainsana avs = new Avainsana();
        avs.vastaaJotain(2);
        avs.tulosta(System.out);
    }

    public void vastaaJotain(int nro) {
        kirjaNro = nro;
        String[] avsanoja = new String[]{"Ven‰j‰","Rikollisuus", "Murha", "Komedia"} ;
        avainsana = avsanoja[nro];

    }
}