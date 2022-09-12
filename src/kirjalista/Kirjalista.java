package kirjalista;

import java.io.File;
import java.util.List;


public class Kirjalista {
	
    private Kirjat kirjat = new Kirjat();
    private Avainsanat avainsanat = new Avainsanat(); 
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
     */
    public void korvaaTaiLisaa(Kirja kirja) throws SailoException { 
        kirjat.korvaaTaiLisaa(kirja); 
    } 

    
    /**
     * poistaa kirjan taulukosta
     * @param nro
     * @return
     */
    public int poista(Kirja kirja) {
        if (kirja == null ) return 0;
        int ret = kirjat.poista(kirja.getTunnusNro());
        avainsanat.poistaKaikki(kirja.getTunnusNro());
        return ret; 
    }

    /**
     * poistaa avainsanan listasta
     * @param nro
     * @return
     */
    public void poista(Avainsana avs) {
        avainsanat.poista(avs);
    }
    
    /**
     * lis‰‰ kirjan kirjat taulukkoon
     * @param kirja
     * @throws SailoException
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
     * @param i
     * @return
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
