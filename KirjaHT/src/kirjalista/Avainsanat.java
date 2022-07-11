package kirjalista;
import java.util.*;


public class Avainsanat implements Iterable<Avainsana>{
	
    private String                      tiedostonNimi = "";

    /** Taulukko avainsanoista */
    private final Collection<Avainsana> alkiot        = new ArrayList<Avainsana>();

    public Avainsanat() {
        // toistaiseksi ei tarvitse tehd‰ mit‰‰n
    }

    public void lisaa(Avainsana avs) {
        alkiot.add(avs);
    }

    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + ".avs";
        throw new SailoException("Ei osata viel‰ lukea tiedostoa " + tiedostonNimi);
    }

    public void talleta() throws SailoException {
        throw new SailoException("Ei osata viel‰ tallettaa tiedostoa " + tiedostonNimi);
    }

    public int getLkm() {
        return alkiot.size();
    }

    @Override
    public Iterator<Avainsana> iterator() {
        return alkiot.iterator();
    }

    public List<Avainsana> annaAvainsanat(int tunnusnro) {
        List<Avainsana> loydetyt = new ArrayList<Avainsana>();
        for (Avainsana avs : alkiot)
            if (avs.getKirjaNro() == tunnusnro) loydetyt.add(avs);
        return loydetyt;
    }

    public static void main(String[] args) {
        Avainsanat avsanat = new Avainsanat();
        Avainsana sana1 = new Avainsana();
        Avainsana sana2 = new Avainsana();

        sana1.vastaaJotain(1);
        sana2.vastaaJotain(2);
        
        avsanat.lisaa(sana1);
        avsanat.lisaa(sana2);


        System.out.println("============= Avainsanat testi =================");

        List<Avainsana> avsanat2 = avsanat.annaAvainsanat(2);

        for (Avainsana avs : avsanat2) {
            System.out.print(avs.getKirjaNro() + " ");
            avs.tulosta(System.out);
        }
    }
}
