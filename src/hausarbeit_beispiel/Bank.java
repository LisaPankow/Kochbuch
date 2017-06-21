package hausarbeit_beispiel;

import java.awt.Dimension;
//import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Bank extends JFrame implements ActionListener {
	
	JPanel startPanel;
	JMenuItem ladenItem;
	JMenuItem speichernItem;
	JMenuItem beendenItem;
	JMenuItem neuItem;
	JMenuItem alleItem;
	JMenuItem suchenItem;
	JMenuItem loeschenItem;
	JLabel labName;
    JTextField tfName;
    JLabel labKontoNr;
    JTextField tfKontoNr;
    JLabel labKontoStand;
    JTextField tfKontoStand;
    JButton butHin;
    JButton butSpeichern;
    boolean kundeZeige = false;
	DefaultListModel<String> model; 

	ArrayList<Kunde> alle_kunden = new ArrayList<Kunde>();
	Kunde kunde_neu;
	
//--------------------------------------------
	public static void main(String[] args) {
		Bank bank = new Bank ();
        bank.initGUI();
	}
//-------------------------------------------------------
	public void initGUI(){
    	JMenuBar cMenuBar = erzeugeMenuBar();
    	setJMenuBar (cMenuBar);

        setSize(new Dimension(400, 300));
        setTitle("Kundenbetreuung");

      
        startPanel = new JPanel();
        getContentPane().add(startPanel);
        
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
	}
//-------------------------------------------------------------------
    private JMenuBar erzeugeMenuBar(){

        JMenuBar myMenuBar = new JMenuBar();

        JMenu bankMenu = new JMenu("Bank");

        ladenItem = new JMenuItem("Laden alle Kunden");
        speichernItem = new JMenuItem("Speichern alle Kunden");       
        beendenItem = new JMenuItem("Beenden");

        bankMenu.add (ladenItem );
        bankMenu.add (speichernItem );
        bankMenu.add (beendenItem );
       

        ladenItem.addActionListener(this);
        speichernItem.addActionListener(this);
        beendenItem.addActionListener(this);
        
        JMenu kundeMenu = new JMenu("Kunden");

        neuItem = new JMenuItem("Neu ");
        alleItem = new JMenuItem("Alle Kunden anzeigen");       
        suchenItem = new JMenuItem("Suchen");
        loeschenItem = new JMenuItem("Löschen");

        kundeMenu.add (neuItem );
        kundeMenu.add (alleItem );
        kundeMenu.add (suchenItem );
        kundeMenu.add (loeschenItem );  

        neuItem.addActionListener(this);
        alleItem.addActionListener(this);
        suchenItem.addActionListener(this);
        loeschenItem.addActionListener(this);
        
        myMenuBar.add (bankMenu);
        myMenuBar.add (kundeMenu);
        return myMenuBar; 

   } 
    
	
//--------------------------------------------------------------------
    public void actionPerformed( ActionEvent  item) {

	    if (item.getSource() == ladenItem  ){
	
	       laden_alle_Kunden();
	    }
	    else if (item.getSource() == speichernItem){
	
		   speichern_alle_Kunden();
	    }
	    else if (item.getSource() == beendenItem){
	
		   System.exit(0);
	    }
	    else if (item.getSource() == neuItem){
		   
	    	 neu_Kunde();
	    }
	    else if (item.getSource() == alleItem){
		   
	    	alle_Kunden();
	    }
	    else if (item.getSource() == suchenItem){
		   
	    	suchen_Kunde();
	    }
	    else if (item.getSource() == loeschenItem){
		   
	    	loeschen_Kunde();
	    }
	      
	    else if (item.getSource() == butHin){
			   
	    	hinzufugen_Konto();
	    }
	    else if (item.getSource() == butSpeichern){
		   
	    	speichern_Kunde();
	    }
}

//--------------------------------------------------------------------
  	private void laden_alle_Kunden() 
 {
  		System.out.println("Methode:lade_alle_Kunden()");	
  		
  		//JFileChooser
  	
  		String sDateiName = "Bank";
  		try{
	  		FileInputStream fiStream = new FileInputStream(sDateiName);
	        ObjectInputStream oiStream = new ObjectInputStream(fiStream);
	
	        
	        alle_kunden = (ArrayList<Kunde>)oiStream.readObject();
	       
	        Kunde[ ] kundeArray = (Kunde[ ])alle_kunden.toArray(new Kunde[0]);
			for (int i = 0; i< kundeArray.length; i ++)
				System.out.println(kundeArray [i].toString());
			

			oiStream.close();
  		}
  		catch (Exception e){
  			System.out.println(e.getMessage());
  			
  		}
  		
        		
  		
  	}
//--------------------------------------------------------------------

  	private void speichern_alle_Kunden() {
  		System.out.println("Methode:speichern_alle_Kunden()");
  		
  		Kunde[ ] kundeArray = (Kunde[ ])alle_kunden.toArray(new Kunde[0]);
		for (int i = 0; i< kundeArray.length; i ++)
			System.out.println(kundeArray [i].toString());
		
  		
		//JFileChooser
		
  		String sDateiName = "Bank";
  		
  		try{
	  		FileOutputStream foStream = new FileOutputStream(sDateiName);
	        ObjectOutputStream ooStream = new ObjectOutputStream(foStream);
	        
	        // Erzeugen und Schreiben von Objekten in den Ausgabestrom
	        
	       
	        ooStream.writeObject(alle_kunden);
	        
	        // Ausgabestrom schliessen
	        ooStream.flush();
	        ooStream.close();	
  		}
		catch (Exception e){
			System.out.println(e.getMessage());
			
		}
  			
  	}

 //--------------------------------------------------------------------//
  	private void neu_Kunde() {
  		
  	    zeigeGUIKunde(); 
  	    neuItem.setEnabled(false);
  	    tfName.setEnabled(true);
  	    tfKontoNr.setEnabled(true);
  	    tfKontoStand.setEnabled(true);
  	    butHin.setEnabled(true);
  	    butSpeichern.setEnabled(true);
  	    
  	    kunde_neu = new Kunde();
  	}
 //--------------------------------------------------------------------
    private void alle_Kunden() {
		// TODO Auto-generated method stub
		
	}

//--------------------------------------------------------------------
    private void suchen_Kunde() {
		// TODO Auto-generated method stub
		
	}
    //--------------------------------------------------------------------
    private void loeschen_Kunde() {
		// TODO Auto-generated method stub
		
	}

   
//---------------------------------------------------------
    public void zeigeGUIKunde(){
    	if (kundeZeige)
    		return;
    	
    	labName = new JLabel ("Kundenname");
    	tfName= new JTextField("",20);
    	
    	labKontoNr = new JLabel ("Kontonummer");
    	tfKontoNr = new JTextField("",20);
    	labKontoStand = new JLabel ("KontoStand");
        tfKontoStand = new JTextField("",20);
    	butHin  = new JButton("Hinzufügen");
    	
        model = new DefaultListModel<String>();
        JList<String> jlist = new JList<>();
        
        
        jlist.setModel(model);
        JScrollPane scrollZutaten = new JScrollPane(jlist);
    
        jlist.setVisibleRowCount(5);
        jlist.setFixedCellWidth(200);
       
        butSpeichern = new JButton("Speichern");
        
        
        startPanel.add(labName);
        startPanel.add(tfName);
        startPanel.add(labKontoNr);
        startPanel.add(tfKontoNr);
        startPanel.add(labKontoStand);
        startPanel.add(tfKontoStand);
        startPanel.add(butHin);
        startPanel.add(scrollZutaten);
       
        startPanel.add(butSpeichern);
        butHin.addActionListener(this);
        butSpeichern.addActionListener(this);
       
        
        startPanel.validate();
    	
    	
        kundeZeige = true;
    	
    }
 
  //---------------------------------------------------------
    private void hinzufugen_Konto() {
    	String sKontoNr =tfKontoNr.getText();
    	String sKontoStand =tfKontoStand.getText();
    	Konto konto = new Konto(sKontoNr, sKontoStand);
 
    	kunde_neu.addKonto(konto);
		
    	model.addElement(konto.toString());
    	
    	tfKontoNr.setText("");
    	tfKontoStand.setText("");
	}
  //---------------------------------------------------------  
    
    private void speichern_Kunde() {
    	String sName =tfName.getText();
  
    	kunde_neu.setName(sName);
  
    	alle_kunden.add(kunde_neu);
  
    	tfName.setText("");
    	model.clear();
    	neuItem.setEnabled(true);
    	
    	tfName.setEnabled(false);
  	    tfKontoNr.setEnabled(false);
  	    tfKontoStand.setEnabled(false);
  	    butHin.setEnabled(false);
    	butSpeichern.setEnabled(false);
    	
    	
		
	}
  //---------------------------------------------------------
}
