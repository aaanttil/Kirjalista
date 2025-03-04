package kirjalista;

import java.io.*;
import java.util.Random;
import fi.jyu.mit.ohj2.Mjonot;


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
     * Kiinnitt�� kirjaan avainsanan
     * @param nro
     * @param avsana
     */
    public void uusiAvainsana(int nro, String avsana) {
        kirjaNro = nro;
        avainsana = avsana;
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
     * Asettaa tunnusnumeron ja samalla varmistaa ett�
     * seuraava numero on aina suurempi kuin t�h�n menness� suurin.
     * @param nr asetettava tunnusnumero
     */
    private void setTunnusNro(int nr) {
        tunnusNro = nr;
        if ( tunnusNro >= seuraavaNro ) seuraavaNro = tunnusNro + 1;
    }

    /**
     * Muuttaa avainsanan tiedot merkkijonoksi tiedostoon tallentamista varten
     * @example
     * <pre name="test">
     * 	Avainsana avs = new Avainsana();
     * 	avs.uusiAvainsana(2,"Rikollisuus");
     * 	avs.toString().equals("1|2|Rikollisuus");
     * </pre>
     */
    @Override
    public String toString() {
        return "" + getTunnusNro() + "|" + getKirjaNro() + "|" + avainsana;
    }

    /**
     * Erottaa merkkijonon avainsanan attribuuteiksi
     * @param rivi
     * @example
     * <pre name="test">
     * 	Avainsana avs = new Avainsana();
     * 	String jono = "2|3|Murha"; 
     * 	avs.parse(jono);
     *  avs.getKirjaNro() === 3;
     *  avs.getAvainsana() === "Murha";
     *  </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        kirjaNro = Mjonot.erota(sb, '|', kirjaNro);
        avainsana = Mjonot.erota(sb, '|', avainsana);
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
     * @param args ei k�ytet�
     */
    public static void main(String[] args) {
        Avainsana avs = new Avainsana();
        avs.vastaaJotain(2);
        avs.tulosta(System.out);
    }

    /**
     * Apumetodi, joka arpoo kirjalle avainsanan.
     * @param nro viite kirjaan, johon avainsana liitet��n
     */
    public void vastaaJotain(int nro) {
        kirjaNro = nro;
        Random r = new Random();
        String[] avsanoja = new String[]{"Ven�j�","Rikollisuus", "Murha", "Komedia","Fantasia","Dekkari","Tragedia"};
        avainsana = avsanoja[r.nextInt(6)];
    }
    
    

}