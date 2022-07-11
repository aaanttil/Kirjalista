package kirjalista;

import java.io.*;

public class Tila {
    private int tunnusNro;
    private int kirjaNro;
    private int tila; 
    private String aloitusPvm = "";
    private String lopetusPvm = "";
    
    private static int seuraavaNro = 1;

    public Tila(int kirjaNro) {
        this.kirjaNro = kirjaNro;
    }
    
    public String tilaSanaksi(int tila) {
    	if(tila == 0) return "Luettu";
    	if(tila == 1) return "Kesken";
    	if(tila == 2) return "Aion lukea";
    	if(tila == 3) return "Lukeminen lopetettu";
    	else return "Ei mit‰‰n";
    }
    
    public void tulosta(PrintStream out) {
        out.println(tilaSanaksi(tila));   
        out.println(aloitusPvm + " " + lopetusPvm);
    }
    
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }

    public int getTunnusNro() {
        return tunnusNro;
    }

    public int getKirjaNro() {
        return kirjaNro;
    }


}
