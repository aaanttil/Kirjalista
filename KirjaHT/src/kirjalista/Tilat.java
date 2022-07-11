package kirjalista;
import java.util.*;


public class Tilat implements Iterable<Tila>{
	
    private String                      tiedostonNimi = "";


    private final Collection<Tila> alkiot        = new ArrayList<Tila>();

    public Tilat() {
        // toistaiseksi ei tarvitse tehd‰ mit‰‰n
    }

    public void lisaa(Tila til) {
        alkiot.add(til);
    }

    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + ".til";
        throw new SailoException("Ei osata viel‰ lukea tiedostoa " + tiedostonNimi);
    }

    public void talleta() throws SailoException {
        throw new SailoException("Ei osata viel‰ tallettaa tiedostoa " + tiedostonNimi);
    }

    public int getLkm() {
        return alkiot.size();
    }

    @Override
    public Iterator<Tila> iterator() {
        return alkiot.iterator();
    }

    public List<Tila> annaTilat(int tunnusnro) {
        List<Tila> loydetyt = new ArrayList<Tila>();
        for (Tila til : alkiot)
            if (til.getKirjaNro() == tunnusnro) loydetyt.add(til);
        return loydetyt;
    }

}
