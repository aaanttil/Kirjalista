package kirjalista;
import java.util.*;


public class Avainsanat implements Iterable<Avainsana>{
	
    private String                      tiedostonNimi = "";

    /** Taulukko avainsanoista */
    private final Collection<Avainsana> alkiot        = new ArrayList<Avainsana>();

    public Avainsanat() {
        // toistaiseksi ei tarvitse tehd‰ mit‰‰n
    }

    /**
     * 
     * @param avs
     */
    public void lisaa(Avainsana avs) {
        alkiot.add(avs);
    }

    /**
     * 
     * @param hakemisto
     * @throws SailoException
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + ".avs";
        throw new SailoException("Ei osata viel‰ lukea tiedostoa " + tiedostonNimi);
    }

    /**
     * 
     * @throws SailoException
     */
    public void talleta() throws SailoException {
        throw new SailoException("Ei osata viel‰ tallettaa tiedostoa " + tiedostonNimi);
    }

    /**
     * 
     * @return
     */
    public int getLkm() {
        return alkiot.size();
    }

    /**
     * 
     */
    @Override
    public Iterator<Avainsana> iterator() {
        return alkiot.iterator();
    }

    /**
     * 
     * @param tunnusnro
     * @return
     */
    public List<Avainsana> annaAvainsanat(int tunnusnro) {
        List<Avainsana> loydetyt = new ArrayList<Avainsana>();
        for (Avainsana avs : alkiot)
            if (avs.getTunnusNro() == tunnusnro) loydetyt.add(avs);
        return loydetyt;
    }

    /**
     * 
     * @param args
     */
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
            System.out.print(avs.getTunnusNro() + " ");
            avs.tulosta(System.out);
        }
    }
}
