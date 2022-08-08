package kirjalista;

import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import fi.jyu.mit.ohj2.Mjonot;

/**
 * Kirjalistan kirja joka osaa mm. itse huolehtia tunnusNro:staan.
 *
 * @author Aapo Anttila
 * @version 1.0, 17.07.2022
 */


public class Kirja {
	private int tunnusNro;    
	/// private int kirjaNro;
	private String kirjanimi = "";
	private String kirjailija = "";
	private int vuosi;
	private String kieli = "";
	private int sivumaara;
    private static int seuraavaNro = 1;
    private String tila; ;
    private int arvosana;
    final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
    private LocalDate aloitusPvm = LocalDate.now();
    private LocalDate lopetusPvm = LocalDate.now();

    
    /**
     * Oletus muodostaja
     */
    public Kirja() {	
    }
    
    private void setTunnusNro(int nr) {
        tunnusNro = nr;
        if (tunnusNro >= seuraavaNro) seuraavaNro = tunnusNro + 1;
    }

    @Override
    public String toString() {
        return "" +
                getTunnusNro() + "|" +
                kirjanimi + "|" +
                kirjailija + "|" +
                vuosi + "|" +
                tila + "|" +
                kieli + "|" +
                sivumaara + "|" +
                arvosana + "|" +
                aloitusPvm + "|" +
                lopetusPvm + "|"; 
    }


    public void parse(String rivi) {
        var sb = new StringBuilder(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        kirjanimi = Mjonot.erota(sb, '|', kirjanimi);
        kirjailija = Mjonot.erota(sb, '|', kirjailija);
        vuosi = Mjonot.erota(sb, '|', vuosi);
        tila = Mjonot.erota(sb, '|', tila);
        kieli = Mjonot.erota(sb, '|', kieli);
        sivumaara = Mjonot.erota(sb, '|', sivumaara);
        arvosana = Mjonot.erota(sb, '|', arvosana);
        ///aloitusPvm = LocalDate.parse(Mjonot.erota(sb, '|', "2000-01-01"), dtf);
        ///lopetusPvm = LocalDate.parse(Mjonot.erota(sb, '|', "2000-01-01"), dtf);
    }

    
    /**
     * Tulostetaan kirjan tiedot
     * @param out tietovirta johon tulostetaan
     */
	public void tulosta(PrintStream out) {
		out.println(String.format("%03d", tunnusNro, 3) + "  " + kirjanimi);
	    out.println(" Kirjoittanut " + kirjailija);
        out.println(" Kirjoitettu vuonna " + vuosi);
	    out.println(" Kieli: " + kieli);
        out.println(" Sivuja " + sivumaara);
    }
		
    /**
     * Antaa kirjalle seuraavan rekisterinumeron.
     * @return kirjan uusi tunnusNro
     * @example
     * <pre name="test">
     *   Kirja b1 = new Kirja();
     *   b1.getTunnusNro() === 0;
     *   b1.rekisteroi();
     *   Kirja b2 = new Kirja();
     *   b2.rekisteroi();
     *   int n1 = b1.getTunnusNro();
     *   int n2 = b2.getTunnusNro();
     *   n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
    	tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }
    
    /**
     * Palauttaa kirjan nimen
     * @return kirjan nimi
     * @example
     * <pre name="test">
     * 	Kirja b1 = new Kirja();
     * 	b1.vastaaValtio();
     *  b1.getNimi() =R= "Valtio.*";
     * </pre>
     */
    public String getNimi() {
    	return kirjanimi;
    }
    
    /**
     * Palauttaa kirjailijan nimen
     * @return kirjailijan nimi
     * @example
     * <pre name="test">
     * 	Kirja b1 = new Kirja();
     * 	b1.vastaaValtio();
     *  b1.getKirjailija() =R= "Pratchett.*";
     *  </pre>
     */
    public String getKirjailija() {
    	return kirjailija;
    }
    
    /**
     * Palauttaa kirjan tunnusnumeron
     * @return kirjan tunnusnumero
     */
    public int getTunnusNro() {
    	return tunnusNro;
    }
    
    /**
     * @return kirjan kieli
     * @example
     * <pre name="test">
     * 	Kirja b1 = new Kirja();
     * 	b1.vastaaValtio();
     *  b1.getKieli() =R= "Suomi.*";
     *  </pre>
     */
    public String getKieli() {
    	return kieli;
    }
    
    /**
     * @return sivumäärä
     */
    public int getSivut() {
    	return sivumaara;
    }
    
    /**
     * @return julkaisu vuosi
     */
    public int getVuosi() {
    	return vuosi;
    }
    
    /**
     * @return tila
     */
    public String getTila() {
    	return tila;
    }
    
    /**
     * @return arvosana
     */
    public int getArvosana() {
    	return arvosana;
    }
    
    /**
     * @return aloituspäivämäärä
     */
    public LocalDate getAloitusPvm() {
    	return aloitusPvm;	
    }
    
    /**
     * @return lopetuspäivämäärä
     */
    public LocalDate getLopetusPvm() {
    	return lopetusPvm;
    }
    
    /**
     * apumetodi, joka muuttaa kokonaislukua vastaavan merkkijonon
     * @param tila kirjan tila
     * @return tila merkkijonona
     */
	public String tilaSanaksi(int tila) {
    	if(tila == 0) return "Luettu";
    	if(tila == 1) return "Kesken";
    	if(tila == 2) return "Aion lukea";
    	if(tila == 3) return "Lukeminen lopetettu";
    	else return "Ei mitään";
    }   
    
    /**
     * asettaa kirjan attribuuteille arvoja
     */
    public void vastaaValtio() {
        kirjanimi = "Valtio";
        kirjailija = "Pratchett, Terry";
        Random r = new Random();
        vuosi = 2000 + r.nextInt(20);
        kieli = "Suomi";
        tila = tilaSanaksi(r.nextInt(5));
        sivumaara = r.nextInt(500);
        aloitusPvm = LocalDate.of(2000 + r.nextInt(23), 1 + r.nextInt(11), 1 + r.nextInt(27));
        lopetusPvm = aloitusPvm.plusMonths(6);
        arvosana = r.nextInt(11);
        
    }
    
    public String getKysymys(int k ) {
    	switch (k) {
    		case 0:
    			return "id";
    		case 1:
    			return "Kirja";
    		case 2:
    			return "Kirjailija";
    		case 3:
    			return "Vuosi";
    		case 4:
    			return "Tila";
    		case 5:
    			return "Sivumäärä";
    		case 6:
    			return "Arvosana";
    		case 7:
    			return "Aloitus pvm";
    		case 8:
    			return "Lopetus pvm";
    		default:
    			return "???";
    	}
    }
    
    
    /**
     * Testiohjelma Kirjalle
     * @param args ei käytetä
     */
    public static void main(String args[]) {
        Kirja  book1 = new Kirja(), book2 = new Kirja();
        
        book1.rekisteroi();
        book2.rekisteroi();
        book1.tulosta(System.out);
        book1.vastaaValtio();
        book1.tulosta(System.out);

        book2.vastaaValtio();
        book2.tulosta(System.out);

        book2.vastaaValtio();
        book2.tulosta(System.out);
    }



    

}
