import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class EingabePanel extends JPanel {

	private JTextField textFieldRezeptName;
	private JTextField tfKontoNr;
	private JTextField textFieldMenge;
	private DefaultListModel<String> model;
	private JButton butSpeichern;
	private JTextArea textAreaZubereitung;
	private JTextField textFieldZutat;
	private JButton butHinzufuegen;
	private Rezept rezept;

	
	EingabePanel() {
		setSize(350, 200);
		
//Rezeptname
        add(new JLabel("Rezeptname"));
		textFieldRezeptName= new JTextField("",20);
        add(textFieldRezeptName);   	
//Zutat Name
        add(new JLabel ("Zutat"));
//......scheinbar ist es egal, ob man einen Leerstring oder nichts übergibt
        textFieldZutat = new JTextField(20);
        add(textFieldZutat);
//Zutat Menge    
        add(new JLabel ("Menge"));
        textFieldMenge = new JTextField("",20);
        add(textFieldMenge);
//Hinzufügen
    	butHinzufuegen = new JButton("Zutat hinzufügen");
    	butHinzufuegen.addActionListener(e->{
// es wird ein Rezept-Objekt erzeugt
    		rezept = new Rezept();
    		rezept.setRezeptName(textFieldRezeptName.getText());
    		hinzufuegen_Zutaten();
    	});
    	add(butHinzufuegen);     	
//Zutaten JList    	
        model = new DefaultListModel<String>();
        JList<String> jlist = new JList<>();        
        jlist.setModel(model);
        JScrollPane scrollZutaten = new JScrollPane(jlist);  
        jlist.setVisibleRowCount(5);
        jlist.setFixedCellWidth(200);
        add(scrollZutaten);

// hier noch die Zubereitung
        add(new JLabel("Zubereitung"));
        textAreaZubereitung = new JTextArea(20, 20);
        textAreaZubereitung.setLineWrap(true);
        add(textAreaZubereitung);
//Button speichern
        butSpeichern = new JButton("Rezept speichern");
        butSpeichern.addActionListener(e->{
//Rezept speichern und Zubereitung hinzufügen
		rezept.setZubereitung(textAreaZubereitung.getText());
		Kochbuch.kochbuch.add(rezept);
//		kochb.add(rezept);
		textFieldRezeptName.setText("");
		textAreaZubereitung.setText("");
		model.clear();
        });
        add(butSpeichern);
		
//      startPanel.validate();
	}

	private void hinzufuegen_Zutaten() {
		String name =textFieldZutat.getText();
    	String menge =textFieldMenge.getText();
    	Zutat zutat = new Zutat(name, menge);
    	rezept.getZutatenListe().add(zutat);	
    	model.addElement(zutat.toString());   	
    	textFieldZutat.setText("");
    	textFieldMenge.setText("");		
	}
	
	
//		panel1.add(new JLabel("Bitte geben Sie den Rezeptnamen ein"));
//		textFieldRezeptName = new JTextField(40);
//		panel1.add(textFieldRezeptName);
//		speichern1Button = new JButton("speichern");
//		speichern1Button.addActionListener(e -> {
//			// es wird ein Rezept-Objekt erzeugt
//			rezept = new Rezept();
//			rezept.setRezeptName(textFieldRezeptName.getText());
//			textFieldRezeptName.setText("");
//			((CardLayout) eingabeMaske.getLayout()).show(eingabeMaske, panel2Name);
//			System.out.println(rezept.getRezeptName());
//		});
//		panel1.add(speichern1Button);
//
//		panel2.add(new JLabel("Bitte beschreiben Sie die Zubereitung des Gerichts"));
//		textAreaZubereitung = new JTextArea(20, 20);
//		textAreaZubereitung.setLineWrap(true);
//		panel2.add(textAreaZubereitung);
//		JButton speichern2Button = new JButton("speichern");
//		speichern2Button.addActionListener(e -> {
//			// Der Zubereitungstext wird zum rezept-Objekt hinzugefügt
//			rezept.setZubereitung(textAreaZubereitung.getText());
//			textAreaZubereitung.setText("");
//			System.out.println(rezept.getZubereitung().toString());
//			((CardLayout) eingabeMaske.getLayout()).show(eingabeMaske, panel3Name);
//		});
//		panel2.add(speichern2Button);
//
//		// Label und Textfeld für Zutat, sowie Menge
//		// Speichern-Button für Zutat und Menge
//		// Rezept speichern-Button
//		panel3.add(new JLabel("Bitte geben Sie die Zutat ein"));
//		textFieldZutat = new JTextField(20);
//		panel3.add(textFieldZutat);
//		panel3.add(new JLabel("Bitte geben Sie die Menge ein"));
//		textFieldMenge = new JTextField(20);
//		panel3.add(textFieldMenge);
//		JButton speichern3aButton = new JButton("Zutat speichern");
//		speichern3aButton.addActionListener(e -> {
//			// Zutat und Menge speichern
//			rezept.getZutatenListe().add(new Zutat((textFieldZutat.getText()), (textFieldMenge.getText())));
//			textFieldZutat.setText("");
//			textFieldMenge.setText("");
//			rezept.getZutatenListe().forEach(z -> System.out.println(z.getName()));
//		});
//		panel3.add(speichern3aButton);
//		panel3.add(new JLabel("Wenn Sie mit der Eingabe der Zutaten fertig sind, speichern Sie das Rezept"));
//		JButton speichern3bButton = new JButton("Rezept speichern");
//		speichern3bButton.addActionListener(e -> {
//			// Rezept speichern
//			kochbuch.add(rezept);
//			kochbuch.forEach(r -> System.out.println(r.getRezeptName()));
//			((CardLayout) eingabeMaske.getLayout()).show(eingabeMaske, panel1Name);
//		});
//		panel3.add(speichern3bButton);	
	
}
