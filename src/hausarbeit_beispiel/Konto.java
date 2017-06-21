package hausarbeit_beispiel;

import java.io.Serializable;

public class Konto implements Serializable {
	
	private static final long serialVersionUID = 222;
	
	private String kontoNr;
	private String kontoStand; 

//----------------------------------------------------------------	
	public Konto(String kontoNr, String kontoStand) {
		
		this.kontoNr = kontoNr;
		this.kontoStand = kontoStand;
	}
//----------------------------------------------------------------	
	public String getKontoNr() {
		return kontoNr;
	}
//----------------------------------------------------------------	
	public void setKontoNr(String kontoNr) {
		this.kontoNr = kontoNr;
	}
//----------------------------------------------------------------	
	public String getKontoStand() {
		return kontoStand;
	}
//----------------------------------------------------------------	
	public void setKontoStand(String kontoStand) {
		this.kontoStand = kontoStand;
	}

//----------------------------------------------------------------		
	public String toString() {
		
		return "kontoNr=" + kontoNr + ", kontoStand=" + kontoStand;
				
	}	
//----------------------------------------------------------------	
}
