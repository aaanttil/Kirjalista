package kirjalista;

import java.util.Calendar;

public class VuosiTarkistus {

	public int thisYear = Calendar.getInstance().get(Calendar.YEAR);
	
	public int tarkista(int vuosi) {
		if (vuosi > thisYear) return thisYear;
		if (vuosi < 0) return 0;
		return vuosi;
	}

}