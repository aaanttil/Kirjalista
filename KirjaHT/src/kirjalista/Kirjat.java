package kirjalista;

public class Kirjat {
	
    private static final int MAX_KIRJOJA   = 1000;
    private int              lkm           = 0;
    private String           tiedostonNimi = "";
    private Kirja            alkiot[]      = new Kirja[MAX_KIRJOJA];
    
    public Kirjat() {
        alkiot = new Kirja[MAX_KIRJOJA];
    }

    public void lisaa(Kirja kirja) throws SailoException {
        if (lkm >= alkiot.length) throw new SailoException("Liikaa alkioita");
        alkiot[lkm] = kirja;
        lkm++;
    }
    
    public Kirja anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }    
    
    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + "/nimet.dat";
        throw new SailoException("Ei osata vielä lukea tiedostoa " + tiedostonNimi);
    }

    public void talleta() throws SailoException {
        throw new SailoException("Ei osata vielä tallettaa tiedostoa " + tiedostonNimi);
    }  
    
    public int getLkm() {
        return lkm;
    }

    public static void main(String[] args) {
    	Kirjat kirjat = new Kirjat();
    	Kirja kirja1 = new Kirja();
    	Kirja kirja2 = new Kirja();
    	kirja1.rekisteroi();
    	kirja1.vastaaValtio();
    	kirja2.rekisteroi();
    	kirja2.vastaaValtio();
    	
    	try {
    	   kirjat.lisaa(kirja1);
    	   kirjat.lisaa(kirja2);
    	}  catch (SailoException e) {
    		System.err.println(e.getMessage());
    	}
    	
    	System.out.println("============ Kirjat testi =============");
    	
    	for (int i = 0; i < kirjat.getLkm(); i++) {
    		Kirja kirja = kirjat.anna(i);
    		System.out.println("Kirja indeksi: " + i);
    		kirja.tulosta(System.out);
    	}
    }
    
}
