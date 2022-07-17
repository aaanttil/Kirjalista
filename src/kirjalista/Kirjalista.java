package kirjalista;

import java.util.List;


public class Kirjalista {
	
    private final Kirjat kirjat = new Kirjat();
    private final Avainsanat avainsanat = new Avainsanat(); 

    /**
     * 
     * @return kirjojen lukum‰‰r‰
     */
    public int getKirjoja() {
        return kirjat.getLkm();
    }
    
    /**
     * 
     * @param nro
     * @return
     */
    public int poista(@SuppressWarnings("unused") int nro) {
        return 0;
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
     * 
     * @param nimi
     * @throws SailoException
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        kirjat.lueTiedostosta(nimi);
    }

    /**
     * 
     * @throws SailoException
     */
    public void talleta() throws SailoException {
        kirjat.talleta();
        // TODO: yrit‰ tallettaa toinen vaikka toinen ep‰onnistuisi
    }
    
    /**
     * 	
     * @param tunnusNro viite kirjaan
     * @return palauttaa listan kirjaan liitetyist‰ avainsanoista
     */
    public List<Avainsana> annaAvainsanat(int tunnusNro) {
    	return avainsanat.annaAvainsanat(tunnusNro);
	}
    
    /**
     * testiohjelma kirjalistalle
     * @param args
     * @throws SailoException
     */
    public static void main(String[] args) throws SailoException {
    	Kirjalista lista = new Kirjalista();
    	
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
