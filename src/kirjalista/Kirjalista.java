package kirjalista;

import java.io.File;
import java.util.List;

public class Kirjalista {
	
    public Kirjat kirjat = new Kirjat();
    public Avainsanat avainsanat = new Avainsanat(); 
    private String hakemisto = "kirjat";

    /**
     * palauttaa kirjojen lukum‰‰r‰n
     * @return kirjojen lukum‰‰r‰
     */
    public int getKirjoja() {
        return kirjat.getLkm();
    }
   
    /**
     * lukee kirjat ja avainsanat tiedostoista
     * @throws SailoException
     */
    public void lueTiedostosta() throws SailoException {
        kirjat = new Kirjat(); // jos luetaan olemassa olevaan niin helpoin tyhjent‰‰ n‰in
        avainsanat = new Avainsanat();

        kirjat.lueTiedostosta();
        avainsanat.lueTiedostosta();
    }
    
    /**
     * korvaa kirjan
     * @param kirja korvatta kirja
     * @throws SailoException
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     *  Kirjalista klista = new Kirjalista(); 
     *  Kirja b1 = new Kirja();
     *  b1.rekisteroi();
     *  klista.lisaa(b1);
     *  klista.getKirjoja() === 1;
     *  Kirja b2 = new Kirja();
     *  b2.rekisteroi();
     *  klista.korvaaTaiLisaa(b2);
     *  klista.getKirjoja() === 2;
     *  Kirja b3 = new Kirja();
     *  b3.rekisteroi();
     *  b3.setTunnusNro(b1.getTunnusNro());
     *  klista.korvaaTaiLisaa(b3);
     *  klista.getKirjoja() === 2;
     * </pre>
     */
    public void korvaaTaiLisaa(Kirja kirja) throws SailoException { 
        kirjat.korvaaTaiLisaa(kirja); 
    } 

    
    /**
     * poistaa kirjan taulukosta
     * @param nro
     * @return montako kirjaa poistettiin
     * @example
     * <pre name="test">
     * #THROWS Exception
     * 	Kirjalista klista = new Kirjalista();
     * 	Kirja b1 = new Kirja();
     *  Kirja b2 = new Kirja();
     * 	klista.lisaa(b1); klista.lisaa(b2);
     * 	klista.getKirjoja() === 2;
     *  klista.poista(b1); klista.getKirjoja() === 1;
     * </pre>
     */
    public void poista(Kirja kirja) {
        if (kirja == null ) return;
        kirjat.poista(kirja.getTunnusNro());
        avainsanat.poistaKaikki(kirja.getTunnusNro());
    }

    /**
     * poistaa avainsanan listasta
     * @param avainsana poistettava avainsana
     * @example
     * <pre name="test">
     * #THROWS Exception
     *   Kirjalista klista = new Kirjalista();
     *   Avainsana a1 = new Avainsana();
     *   a1.uusiAvainsana(1, "jee");
     *   klista.lisaa(a1);  
     *   List<Avainsana> avs = klista.annaAvainsanat(1);
     *   klista.avainsanat.getLkm() === 1;
     *   klista.poista(a1);
     *   avs = klista.annaAvainsanat(1);
     *   klista.avainsanat.getLkm() === 0;
     * </pre>
     */ 

    public void poista(Avainsana avs) {
        avainsanat.poista(avs);
    }
    
    /**
     * lis‰‰ kirjan kirjat taulukkoon
     * @param kirja
     * @throws SailoException
     * @example
     * <pre name="test">
     * #THROWS SailoException
     *  Kirjalista kl = new Kirjalista();  
     *  kl.getKirjoja() === 0; 
     *  Kirja b1 = new Kirja();
     *  kl.lisaa(b1);
     *  kl.getKirjoja() === 1; 
     */
    public void lisaa(Kirja kirja) throws SailoException {
        kirjat.lisaa(kirja);
    }
    
    /**
     * lis‰‰ avainsanan avainsanat listaan
     * @param avs
     */
    public void lisaa(Avainsana avs) {
        avainsanat.lisaa(avs);
    }
    
    /**
     * 
     * @param i indeksi
     * @return palauttaa kirjan indeksist‰ i
     * @throws IndexOutOfBoundsException
     */
    public Kirja annaKirja(int i) throws IndexOutOfBoundsException {
        return kirjat.anna(i);
    }
    
    /**
     * tallentaa kirjat ja avainsanat tiedostoihin
     * @throws SailoException
     */
    public void tallenna() throws SailoException {
        String virhe = "";
        try {
            kirjat.tallenna();
        } catch ( SailoException ex ) {
            virhe = ex.getMessage();
        }

        try {
            avainsanat.tallenna();
        } catch ( SailoException ex ) {
            virhe += ex.getMessage();
        }
        if ( !"".equals(virhe) ) throw new SailoException(virhe);
    }

    
    
    /**
     * 	
     * @param tunnusNro viite kirjaan
     * @return palauttaa listan kirjaan liitetyist‰ avainsanoista
     */
    public List<Avainsana> annaAvainsanat(int kirjaNro) {
    	return avainsanat.annaAvainsanat(kirjaNro);
	}
    
    /**
     * testiohjelma kirjalistalle
     * @param args
     * @throws SailoException
     */
    public static void main(String[] args) throws SailoException {
    	Kirjalista lista = new Kirjalista();
    	
        try {
            lista.lueTiedostosta();
        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
        try {
            Kirja b1 = new Kirja(), b2 = new Kirja();
            b1.rekisteroi();
            b1.vastaaValtio();
            b2.rekisteroi();
            b2.vastaaValtio();

            lista.lisaa(b1);
            lista.lisaa(b2);

        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
        try {
            System.out.println("============= Kerhon testi =================");

            for (int i = 0; i < lista.getKirjoja(); i++) {
                Kirja kirja = lista.annaKirja(i);
                System.out.println("Kirja paikassa: " + i);
                kirja.tulosta(System.out);
            }
            
            lista.tallenna();

        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }

    	
    	Kirja kirja1 = new Kirja();
    	Kirja kirja2 = new Kirja();
    	
    	kirja1.rekisteroi();
    	kirja1.vastaaValtio();
    	kirja2.rekisteroi();
    	kirja2.vastaaValtio();
    	
    	lista.lisaa(kirja1);
    	lista.lisaa(kirja2);
    	
    	for (int i=0; i<lista.getKirjoja(); i++) {
    		Kirja kirja = lista.annaKirja(i);
    		kirja.tulosta(System.out);	
    	}
    }


}
