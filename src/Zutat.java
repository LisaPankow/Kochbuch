import java.io.Serializable;

public class Zutat implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	Die Klasse Zutat beschreibt eine Zutat, bestehend aus einem Namen und einer Menge, 
//	die in einem bestimmten Rezept vorhanden ist. 	
//	Die eingegebenen Zutaten mit ihren Mengen sollen über den Button „Hinzufügen“ 
//	der Zutatenliste des aktuellen Rezept-Objekts hinzugefügt werden. 	
	private String name;
	private String menge;
	
	Zutat(){
		
	}
	
	Zutat(String name, String menge){
		this.name = name;
		this.menge = menge;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMenge() {
		return menge;
	}

	public void setMenge(String menge) {
		this.menge = menge;
	}

	@Override
	public String toString() {
		return "Zutat [name=" + name + ", menge=" + menge + "]";
	}
}
