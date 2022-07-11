package kirjalista;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Random;

/**
 * Kirjalistan kirja joka osaa mm. itse huolehtia tunnusNro:staan.
 *
 * @author Aapo Anttila
 * @version 1.0, 22.02.2003
 */


public class Kirja {
	private int tunnusNro;
	private String kirjanimi = "";
	private String kirjailija = "";
	private int vuosi;
	private String kieli = "";
	private int sivumaara;
    private static int seuraavaNro = 1;
	
    
    public Kirja() {
    	/////////
    }
    
    public Kirja(int tunnusNro, String kirjanimi, String kirjailija, int vuosi, String kieli, int sivumaara) {
    	this.tunnusNro = tunnusNro;
    	this.kirjanimi = kirjanimi;
    	this.kirjailija = kirjailija;
    	this.vuosi = vuosi;
    	this.kieli = kieli;
    	this.sivumaara = sivumaara;
    }
    
	public void tulosta(PrintStream out) {
		out.println(String.format("%03d", tunnusNro, 3) + "  " + kirjanimi);
	    out.println(" Kirjoittanut " + kirjailija);
        out.println(" Kirjoitettu vuonna " + vuosi);
	    out.println(" Kieli: " + kieli);
        out.println(" Sivuja " + sivumaara);
    }
	
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    public int rekisteroi() {
    	tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }
    
    public String getNimi() {
    	return kirjanimi;
    }
    
    public String getKirjailija() {
    	return kirjailija;
    }
    
    public int getTunnusNro() {
    	return tunnusNro;
    }
    
    public String getKieli() {
    	return kieli;
    }
    
    public int getSivut() {
    	return sivumaara;
    }
    
    public int getVuosi() {
    	return vuosi;
    }
    
    public void vastaaValtio() {
        kirjanimi = "Valtio ";
        kirjailija = "Pratchett, Terry";
        Random r = new Random();
        vuosi = 2000 + r.nextInt(20);
        kieli = "Suomi";
        sivumaara = r.nextInt(500);
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
    			return "Sivum‰‰r‰";
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
