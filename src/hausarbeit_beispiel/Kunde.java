package hausarbeit_beispiel;

import java.io.Serializable;
import java.util.ArrayList;



public class Kunde implements Serializable {
	

	private static final long serialVersionUID = 111;
	private String name;
	
	private ArrayList<Konto> alleKonten;
//----------------------------------------------------------------	
	public Kunde(){
		this.alleKonten =new ArrayList<Konto>(); 
		
	}
	
//----------------------------------------------------------------	
	public boolean addKonto(Konto k){
		return alleKonten.add(k);
	}
//----------------------------------------------------------------		
	public String toString(){
			
		return name + " hat die Konten: " + alleKonten.toString();
	}
//----------------------------------------------------------------		
	public Konto[] getAllKontos(){
		return alleKonten.toArray(new Konto[0]);
	}
//----------------------------------------------------------------	
	public String getName() {
		return name;
	}
//----------------------------------------------------------------	
	public void setName(String name) {
		this.name = name;
	}
	//----------------------------------------------------------------	
public ArrayList<Konto> getAlleKonten() {
		return alleKonten;
	}
//----------------------------------------------------------------	
	public void setAlleKonten(ArrayList<Konto> alleKonten) {
		this.alleKonten = alleKonten;
	}

}
