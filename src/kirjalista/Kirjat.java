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
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Iterator;



public class Kirjat implements Iterable<Kirja>{
	
    private static final int MAX_KIRJOJA   = 20;
    private int              lkm           = 0;
    private boolean			 muutettu = false;
    private String 			 tiedostonPerusNimi = "nimet";
    private Kirja            alkiot[]      = new Kirja[MAX_KIRJOJA];

    
    /**
     * Oletusmuodostaja
     */
    public Kirjat() {
        alkiot = new Kirja[MAX_KIRJOJA];
    }
    
    /**
     * Palautetaan iteraattori kirjoistaan.
     * @return kirja iteraattori
     */
    @Override
    public Iterator<Kirja> iterator() {
        return new KirjatIterator();
    }
    
    /**
     * Lisää uuden kirjan tietorakenteeseen. Ottaa kirjan omistukseensa.
     * @param kirja lisättävän kirjan viite
     * @throws SailoException jos tietorakenne on jo täynnä
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * Kirjat kirjat = new Kirjat();
     * Kirja b1 = new Kirja(); Kirja b2 = new Kirja();
     * kirjat.getLkm() === 0;
     * kirjat.lisaa(b1); kirjat.getLkm() === 1;
     * kirjat.lisaa(b2); kirjat.getLkm() === 2;
     * kirjat.lisaa(b1); kirjat.getLkm() === 3;
     * Iterator<Kirja> it = kirjat.iterator();     
     * it.next() === b1;
     * it.next() === b2; 
     * it.next() === b1; 
     * kirjat.lisaa(b1); kirjat.getLkm() === 4;
     * kirjat.lisaa(b2); kirjat.getLkm() === 5;
     * </pre>
     */
    public void lisaa(Kirja kirja) throws SailoException {
        if (lkm >= alkiot.length) alkiot = Arrays.copyOf(alkiot, lkm+20);
        alkiot[lkm] = kirja;
        lkm++;
        muutettu = true;
    }
    
    /**
     * poistaa kirjan, jolla parametrina annettu tunnusnumero
     * @param id poistettavan kirjan tunnusnumero
     * @return 1 jos poistettiiinn, 0 jos ei löydy
     * @example 
     * <pre name="test"> 
     * #THROWS SailoException  
     * Kirjat kirjat = new Kirjat();
     * Kirja b1 = new Kirja();
     * kirjat.lisaa(b1); kirjat.getLkm() === 1;
     * kirjat.poista(b1.getTunnusNro()); kirjat.getLkm() === 0;
     * Kirja b2 = new Kirja();
     * kirjat.lisaa(b2); kirjat.getLkm() === 1;
     */
    public int poista(int id) {
    	int ind = etsiId(id);
    	if (ind < 0) return 0;
    	lkm--;
    	for (int i = ind; i < lkm; i++) {
    		alkiot[i] = alkiot[i + 1];
    	}
    	alkiot[lkm] = null;
    	muutettu = true;
    	return 1;
    }
    
    /**
     * etsii kirjan id:n
     * @param id tunnusnumero, jonka mukaan etsitään
     * @return
     */
    public int etsiId(int id) { 
    	for (int i = 0; i < lkm; i++) {
    		if (id == alkiot[i].getTunnusNro()) return i; 
    	}
    	return -1;    
    } 
    
    /**
     * lukee kirjat tiedostosta
     * @param tied
     * @throws SailoException
     */
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {
            String rivi = fi.readLine();

            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Kirja kirja = new Kirja();
                kirja.parse(rivi); // voisi olla virhekäsittely
                lisaa(kirja);
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

    /**
     * korvaa kirjan tietorakenteessa
     * etsii kirjaa tunnusnumerolla, jos ei löydy. niin lisätään uutena
     * @param kirja korvattava kirja
     * @throws SailoException
     * <pre name="test">
     * #THROWS SailoException,CloneNotSupportedException
     * #PACKAGEIMPORT
     * Kirjat kirjat = new Kirjat();
     * Kirja b1 = new Kirja(), b2 = new Kirja();
     * b1.rekisteroi(); b2.rekisteroi();
     * kirjat.getLkm() === 0;
     * kirjat.korvaaTaiLisaa(b1); kirjat.getLkm() === 1;
     * kirjat.korvaaTaiLisaa(b2); kirjat.getLkm() === 2;
     * Kirja b3 = b1.clone();
     * Iterator<Kirja> it = kirjat.iterator();
     * it.next() == b1 === true;
     * kirjat.korvaaTaiLisaa(b3); kirjat.getLkm() === 2;
     * it = kirjat.iterator();
     * Kirja j0 = it.next();
     * j0 === b3;
     * j0 == b3 === true;
     * j0 == b1 === false;
     * </pre>
     */
    public void korvaaTaiLisaa(Kirja kirja) throws SailoException {
        int id = kirja.getTunnusNro();
        for (int i = 0; i < lkm; i++) {
            if ( alkiot[i].getTunnusNro() == id ) {
                alkiot[i] = kirja;
                muutettu = true;
                return;
            }
        }
        lisaa(kirja);
    }

    /**
     * palauttaa viitteen i:teen kirjaan
     * @param i monennenko kirjan viite halutaan
     * @return viite kirjaan, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella  
     */
    public Kirja anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }    
    
    /**
     * antaa backup tiedoston nimen
     * @return backuptiedoston nimi
     */
    public String getBakNimi() {
        return tiedostonPerusNimi + ".bak";
    }

    /**
     * tallentaa kirjojen tiedot tiedostoon
     * @throws SailoException
     */
    public void tallenna() throws SailoException {
    	if (!muutettu) return;      
    	File bkup = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        bkup.delete();
        ftied.renameTo(bkup);
        
        
        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            fo.println(alkiot.length);
            for (Kirja kirja : this) {
                fo.println(kirja.toString());
            }
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }

        muutettu = false;
    }

    
    /**
     * @return kirjojen lukumäärä
     */
    public int getLkm() {
        return lkm;
    }
	
    public void setTiedostonPerusNimi(String nimi) {
        tiedostonPerusNimi = nimi;	
	}
    
    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }

    public String getTiedostonNimi() {
        return getTiedostonPerusNimi() + ".dat";
    }

    
    /**
     * luokka kirjojen iteroimiseksi
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #PACKAGEIMPORT
     * #import java.util.*;
     * Kirjat kirjat = new Kirjat();
     * Kirja b1 = new Kirja(), b2 = new Kirja();
     * b1.rekisteroi(); b2.rekisteroi();
     * kirjat.lisaa(b1); 
     * kirjat.lisaa(b2); 
     * kirjat.lisaa(b1); 
     * Iterator<Kirja>  i=kirjat.iterator();
     * i.next() == b1  === true;
     * i.next() == b2  === true;
     * i.next() == b1  === true;
     * i.next();  #THROWS NoSuchElementException
     * </pre>
     */
    public class KirjatIterator implements Iterator<Kirja> {
        private int kohdalla = 0;

        @Override
        public boolean hasNext() {
            return kohdalla < getLkm();
        }

        @Override
        public Kirja next() throws NoSuchElementException {
            if ( !hasNext() ) throw new NoSuchElementException("Ei oo");
            return anna(kohdalla++);
        }

        @Override
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("Me ei poisteta");
        }
    }
    
    /**
     * testi ohjelma luokalle
     * @param args
     */
    public static void main(String[] args) {
    	Kirjat kirjat = new Kirjat();
    	Kirja kirja1 = new Kirja();
    	Kirja kirja2 = new Kirja();
    	kirja1.rekisteroi();
    	kirja1.vastaaValtio();
    	kirja2.rekisteroi();
    	kirja2.vastaaValtio();
    	
        try {
            kirjat.lueTiedostosta("kirjat");
        } catch (SailoException ex) {
            System.err.println(ex.getMessage());
        }
        	
    	
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
    	
        
        try {
            kirjat.tallenna();
        } catch (SailoException e) {
            e.printStackTrace();
        }

    	
    }

}
