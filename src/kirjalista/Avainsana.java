package kirjalista;

import java.io.*;
import java.util.Random;

public class Avainsana {
    private int tunnusNro;
    private int kirjaNro;
    private String avainsana = "";

    private static int seuraavaNro = 1;

    /**
     * Alustetaan avainsana
     */
    public Avainsana() {
    }

    /**
     * Alustetaan tietyn kirjan avainsana
     * @param kirjaNro kirjan viitenro
     */
    public Avainsana(int kirjaNro) {
        this.kirjaNro = kirjaNro;
    }
    
    /**
     * tulostetaan avainsana
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(avainsana);
    }

    /**
     * Antaa avainsanalle seuraavan rekisterinumeron.
     * @return avainsanan uusi tunnus_nro
     * @example
     * <pre name="test">
     *   Avainsana as1 = new Avainsana();
     *   as1.getTunnusNro() === 0;
     *   as1.rekisteroi();
     *   Avainsana as2 = new Avainsana();
     *   as2.rekisteroi();
     *   int n1 = as1.getTunnusNro();
     *   int n2 = as2.getTunnusNro();
     *   n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }

    /**
     * Palautetaan avainsanan oma id
     * @return avainsana id
     */
    public int getTunnusNro() {
        return tunnusNro;
    }

    
    /**
     * Palautetaan mille kirjalle avainsana kuuluu
     * @return kirjan id
     */
	public int getKirjaNro() {
		return kirjaNro;
	}
    
    
    
    /**
     * Palautetaan avainsana merkkijonona
     * @return avainsana	
     */
    public String getAvainsana() {
        return avainsana;
    }
    
    
    /**
     * Testiohjelma avainsanalle
     * @param args ei käytetä
     */
    public static void main(String[] args) {
        Avainsana avs = new Avainsana();
        avs.vastaaJotain(2);
        avs.tulosta(System.out);
    }

    /**
     * Apumetodi, joka arpoo kirjalle avainsanan.
     * @param nro viite kirjaan, johon avainsana liitetään
     */
    public void vastaaJotain(int nro) {
        tunnusNro = nro;
        Random r = new Random();
        String[] avsanoja = new String[]{"Venäjä","Rikollisuus", "Murha", "Komedia","Fantasia","Dekkari","Tragedia"};
        avainsana = avsanoja[r.nextInt(6)];
    }
}