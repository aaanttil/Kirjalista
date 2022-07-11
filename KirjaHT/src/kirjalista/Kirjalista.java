package kirjalista;

public class Kirjalista {
	
    private final Kirjat kirjat = new Kirjat();
    private final Avainsanat avainsanat = new Avainsanat(); 
    private final Tilat tilat = new Tilat();

    public int getKirjoja() {
        return kirjat.getLkm();
    }
    
    public int poista(@SuppressWarnings("unused") int nro) {
        return 0;
    }

    public void lisaa(Kirja kirja) throws SailoException {
        kirjat.lisaa(kirja);
    }
    
    public void lisaa(Avainsana avs) {
        avainsanat.lisaa(avs);
    }

    public void lisaa(Tila til) {
        tilat.lisaa(til);
    }
    
    public Kirja annaKirja(int i) throws IndexOutOfBoundsException {
        return kirjat.anna(i);
    }
    
    public void lueTiedostosta(String nimi) throws SailoException {
        kirjat.lueTiedostosta(nimi);
    }

    public void talleta() throws SailoException {
        kirjat.talleta();
        // TODO: yritä tallettaa toinen vaikka toinen epäonnistuisi
    }
    
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
