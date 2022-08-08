package kirjalista;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.Iterator;


public class Avainsanat implements Iterable<Avainsana>{
	

    /** Taulukko avainsanoista */
    private final Collection<Avainsana> alkiot        = new ArrayList<Avainsana>();
    
    private String tiedostonPerusNimi = "avsanat";
    private boolean muutettu = false;




    public Avainsanat() {
        // toistaiseksi ei tarvitse tehd‰ mit‰‰n
    }

    /**
     * lis‰‰ avainsanan listaan avainsanoista
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

    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {

            String rivi;
            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Avainsana avs = new Avainsana();
                avs.parse(rivi); // voisi olla virhek‰sittely
                lisaa(avs);
            }
            muutettu = false;

        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
        } catch ( IOException e ) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }

    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }
        

    public void tallenna() throws SailoException {

        File ftied = new File(getTiedostonNimi());
        
        try (PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            for (Avainsana avs : this) {
                fo.println(avs.toString());
            	}
            }  catch ( FileNotFoundException ex ) {
                throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
            }  catch ( IOException ex ) {
                throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
            }
        muutettu = false;
    }

    
    
    /**
     * @return palauttaa avainsanojen lukum‰‰r‰n
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
     * @param tunnusnro
     * @return kirjaan liitetyt avainsanat
     */
    public List<Avainsana> annaAvainsanat(int tunnusnro) {
        List<Avainsana> loydetyt = new ArrayList<Avainsana>();
        for (Avainsana avs : alkiot)
            if (avs.getTunnusNro() == tunnusnro) loydetyt.add(avs);
        return loydetyt;
    }

    /**
     * testiohjelma luokalle
     * @param args
     */
    public static void main(String[] args) {
        Avainsanat avsanat = new Avainsanat();
        
        try {
            avsanat.lueTiedostosta("kirjat");
        } catch (SailoException ex) {
            System.err.println(ex.getMessage());
        }

        
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
        
        try {
            avsanat.tallenna();
        } catch (SailoException e) {
            e.printStackTrace();
        }

        }

	public void setTiedostonPerusNimi(String tied) {
        tiedostonPerusNimi = tied;
		
	}

    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }

    public String getTiedostonNimi() {
        return getTiedostonPerusNimi() + ".dat";
    }

}
