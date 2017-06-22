import java.io.Serializable;
import java.util.ArrayList;

public class Rezept implements Serializable {
/**
	 * 
	 */
	//möglicherweise kommen sich die UID von Zutat und Rezept in die Quere?
	private static final long serialVersionUID = 1L;
	//	Die Klasse Rezept beschreibt ein Rezept mit Namen und Zutatenliste. 
	private String rezeptName;
	//vielleicht noch ändern in StringBuilder
	private String zubereitung;
	ArrayList<Zutat> zutatenListe = new ArrayList<Zutat>();
	
	
	
	
	public String getRezeptName() {
		return rezeptName;
	}
	public void setRezeptName(String rezeptName) {
		this.rezeptName = rezeptName;
	}
	public String getZubereitung() {
		return zubereitung;
	}
	public void setZubereitung(String zubereitung) {
		this.zubereitung = zubereitung;
	}
//	public ArrayList<Zutat> getZutatenListe() {
//		return zutatenListe;
//	}
	public void setZutatenListe(ArrayList<Zutat> zutatenListe) {
		this.zutatenListe = zutatenListe;
	}
	
//	Die eingegebenen Zutaten mit ihren Mengen sollen über den Button „Hinzufügen“ 
//	der Zutatenliste des aktuellen Rezept-Objekts hinzugefügt werden. 
	
}
