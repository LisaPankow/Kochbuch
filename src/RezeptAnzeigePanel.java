import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class RezeptAnzeigePanel extends JPanel {
	JLabel rezeptName;
	DefaultListModel<String> listenModell;
	JList<String> zutaten;
	JTextArea zubereitung;
	
	RezeptAnzeigePanel(){
		rezeptName = new JLabel();
		this.add(rezeptName);
		this.add(new JLabel("Zutaten:"));
		listenModell = new DefaultListModel<String>();
		zutaten = new JList<String>(listenModell);
		this.add(zutaten);
		this.add(new JLabel("Zubereitung"));
		zubereitung = new JTextArea(100, 100);
		zubereitung.setLineWrap(true);
		zubereitung.setWrapStyleWord(true); 
//      add(new JScrollPane(zubereitung));		
		this.add(zubereitung);
	}	

	public void updatePanel(Rezept r){
		rezeptName.setText(r.getRezeptName());
		listenModell.clear();
		r.getZutatenListe().forEach(z -> {
			listenModell.addElement(""+z.getName().toString()+", "+z.getMenge().toString());		
		});
		zubereitung.setText(r.getZubereitung());
	}	
}