import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Kochbuch extends JFrame implements WindowListener {

	private JMenuItem menueItemLaden;
	private JMenuItem menueItemSpeichern;
	private JMenuItem menueItemBeenden;
	private JMenuItem menueItemNeu;
	private JMenuItem menueItemSuchen;
	private JMenuItem menueItemAlleAnzeigen;
	private JMenuItem menueItemLoeschen;
	private Container container;
	private String nameStartPanel;
	private String nameEingabePanel;
//	private JTextField textFieldRezeptName;
//	private JButton speichern1Button;
//	private String panel1Name;
//	private String panel2Name;
//	private String panel3Name;
//	private Rezept rezept;
//	private JTextArea textAreaZubereitung;
//	private JTextField textFieldZutat;
//	private JTextField textFieldMenge;
	private String nameRezepteIndexPanel;
	private String nameRezeptAnzeigePanel;
	DefaultListModel<Rezept> rezeptListModel;
	private RezeptAnzeigePanel rezeptAnzeigePanel;

// ...........Warum muss lisasKochbuch static sein???????	
//	static Kochbuch lisasKochbuch;
	static ArrayList<Rezept> kochbuch = new ArrayList<Rezept>();

//-----------------------------------------------------------------------------------------	
	public static void main(String[] args) {
		Kochbuch lisasKochbuch = new Kochbuch();
		lisasKochbuch.initGUI();
	}

// ------------------------------------Anfang GUI erzeugen-------------------------------
	private void initGUI() {
		// JFrame lisasKochbuch wird formatiert
		setSize(new Dimension(1200, 700));
		setTitle("Lisas Lieblingsrezepte");
		addWindowListener(this);
		// Men�leiste
		JMenuBar menueLeiste = erzeugeMenuBar();
		setJMenuBar(menueLeiste);

// ------------------------------------------------einzelne Panel erzeugen------------------
// Startpanel
		JPanel startPanel = new JPanel();
		nameStartPanel = "PanelStart";
		// Eingabepanel
		// JPanel eingabePanel = erzeugeEingabePanel();
		EingabePanel eingabePanel = new EingabePanel();
		nameEingabePanel = "PanelEingabe";
		// Anzeige Rezept�bersicht
		JPanel rezepteIndexPanel = erzeugeRezepteIndexPanel();
		nameRezepteIndexPanel = "PanelRezepteIndex";
		// Anzeige Rezept
		rezeptAnzeigePanel = new RezeptAnzeigePanel();
		nameRezeptAnzeigePanel = "PanelRezeptAnzeige";

		// Layout des JFrames lisasKochbuch festlegen
		container = getContentPane();
		container.setLayout(new CardLayout());

		// ben�tigte Panels zum CardLayout hinzuf�gen
		container.add(startPanel, nameStartPanel);
		container.add(eingabePanel, nameEingabePanel);
		container.add(rezepteIndexPanel, nameRezepteIndexPanel);
		container.add(rezeptAnzeigePanel, nameRezeptAnzeigePanel);

// JFrame sichtbar machen
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
// ------------------------------------Ende GUI erzeugen----------------------------------------------

// ------------------------------Men�leiste erzeugen-------------------------------------------------
	private JMenuBar erzeugeMenuBar() {
		JMenuBar menueLeiste = new JMenuBar();
// erzeuge Men� "Kochbuch"
		JMenu menueKochbuch = erzeugeMenueKochbuch();
		menueLeiste.add(menueKochbuch);
// erzeuge Men� "Rezept"
		JMenu menueRezept = erzeugeMenueRezept();
		menueLeiste.add(menueRezept);
		return menueLeiste;
	}

// -------------------------------Men� Kochbuch erzeugen-----------------------------------------------
	private JMenu erzeugeMenueKochbuch() {
		JMenu menueKochbuch = new JMenu("Kochbuch");
// f�r Men� "Kochbuch" werden drei Men�Items initialisiert
		menueItemLaden = new JMenuItem("Laden");
		menueItemSpeichern = new JMenuItem("Speichern");
		menueItemBeenden = new JMenuItem("Beenden");

// 1. Laden � Gespeichertes Kochbuch (alle Rezepte) unter Verwendung von JFileChooser einlesen.
		menueItemLaden.addActionListener(e -> {
			ladeKochbuch();
// ..................... Laden-Item ausblenden
		});
// 2. Speichern � das Kochbuch (alle Rezepte) unter Verwendung von JFileChooser speichern.
		menueItemSpeichern.addActionListener(e -> {
			speichereKochbuch();
// ...................... Speichern-Item bis zur n�chsten �nderung ausblenden
		});
// 3. Beenden � das Programm wird beendet.
		menueItemBeenden.addActionListener(e -> {
			beendenAbfrage();
			System.exit(0);
		});
// die drei Men�Items werden dem Men� "Kochbuch" hinzugef�gt
		menueKochbuch.add(menueItemLaden);
		menueKochbuch.add(menueItemSpeichern);
		menueKochbuch.add(menueItemBeenden);
		return menueKochbuch;
	}
// -------------------------------------Ende Men� Kochbuch erzeugen------------------------

// -------------------------------------Anfang Men� Rezept erzeugen-------------------------
	private JMenu erzeugeMenueRezept() {
		JMenu menueRezept = new JMenu("Rezepte");
// Men�Items
		menueItemNeu = new JMenuItem("Neu");
		menueItemAlleAnzeigen = new JMenuItem("Alle anzeigen");
		menueItemSuchen = new JMenuItem("Suchen");
		menueItemLoeschen = new JMenuItem("L�schen");

// Funktionalit�t Neu -> Anzeige Eingabepanel
		menueItemNeu.addActionListener(e -> ((CardLayout) container.getLayout()).show(container, nameEingabePanel));
// Funktionalit�t Alle anzeigen -> Anzeige Indexpanel �ber Methode alleRezepteAnzeigen(),
// d.h. RezeptListModel wird geleert und neu gef�llt
		menueItemAlleAnzeigen.addActionListener(e -> {
			alleRezepteAnzeigen();
		});
// Funktionalit�t Suchen -> Anzeige Indexpanel
		menueItemSuchen.addActionListener(e -> {
			((CardLayout) container.getLayout()).show(container, nameRezepteIndexPanel);
		});
// Funktionalit�t L�schen -> Anzeige Indexpanel
		menueItemLoeschen.addActionListener(e -> {
			((CardLayout) container.getLayout()).show(container, nameRezepteIndexPanel);
		});
// Men�Items zum Men� hinzuf�gen
		menueRezept.add(menueItemNeu);
		menueRezept.add(menueItemAlleAnzeigen);
		menueRezept.add(menueItemSuchen);
		menueRezept.add(menueItemLoeschen);
		return menueRezept;
	}
// -------------------------------------Ende Men� Rezept erzeugen-------------------------

	// Hier geht es ums Eingabepanel
	// private JPanel erzeugeEingabePanel() {
	// JPanel eingabePanel = new JPanel();
	// eingabePanel.setSize(350, 200);
	// eingabePanel.setLayout(new BorderLayout());
	// JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	// JPanel eingabeMaske = erzeugeEingabeMaske();
	// splitPane.setLeftComponent(eingabeMaske);
	// JPanel anzeigeFeld = erzeugeAnzeigeFeld();
	// splitPane.setRightComponent(anzeigeFeld);
	// eingabePanel.add(splitPane);
	// return eingabePanel;
	// }

	// private JPanel erzeugeEingabeMaske() {
	// JPanel eingabeMaske = new JPanel(new CardLayout());
	// JPanel panel1 = new JPanel();
	// panel1Name = "panel1Name";
	// panel1.add(new JLabel("Bitte geben Sie den Rezeptnamen ein"));
	// textFieldRezeptName = new JTextField(40);
	// panel1.add(textFieldRezeptName);
	// speichern1Button = new JButton("speichern");
	// speichern1Button.addActionListener(e -> {
	// // es wird ein Rezept-Objekt erzeugt
	// rezept = new Rezept();
	// rezept.setRezeptName(textFieldRezeptName.getText());
	// textFieldRezeptName.setText("");
	// ((CardLayout) eingabeMaske.getLayout()).show(eingabeMaske, panel2Name);
	// System.out.println(rezept.getRezeptName());
	// });
	// panel1.add(speichern1Button);
	// eingabeMaske.add(panel1, panel1Name);
	//
	// JPanel panel2 = new JPanel();
	// panel2Name = "panel2Name";
	// panel2.add(new JLabel("Bitte beschreiben Sie die Zubereitung des Gerichts"));
	// textAreaZubereitung = new JTextArea(20, 20);
	// textAreaZubereitung.setLineWrap(true);
	// panel2.add(textAreaZubereitung);
	// JButton speichern2Button = new JButton("speichern");
	// speichern2Button.addActionListener(e -> {
	// // Der Zubereitungstext wird zum rezept-Objekt hinzugef�gt
	// rezept.setZubereitung(textAreaZubereitung.getText());
	// textAreaZubereitung.setText("");
	// System.out.println(rezept.getZubereitung().toString());
	// ((CardLayout) eingabeMaske.getLayout()).show(eingabeMaske, panel3Name);
	// });
	// panel2.add(speichern2Button);
	// eingabeMaske.add(panel2, panel2Name);
	//
	// JPanel panel3 = new JPanel();
	// panel3Name = "panel3Name";
	// // Label und Textfeld f�r Zutat, sowie Menge
	// // Speichern-Button f�r Zutat und Menge
	// // Rezept speichern-Button
	// panel3.add(new JLabel("Bitte geben Sie die Zutat ein"));
	// textFieldZutat = new JTextField(20);
	// panel3.add(textFieldZutat);
	// panel3.add(new JLabel("Bitte geben Sie die Menge ein"));
	// textFieldMenge = new JTextField(20);
	// panel3.add(textFieldMenge);
	// JButton speichern3aButton = new JButton("Zutat speichern");
	// speichern3aButton.addActionListener(e -> {
	// // Zutat und Menge speichern
	// rezept.getZutatenListe().add(new Zutat((textFieldZutat.getText()),
	// (textFieldMenge.getText())));
	// textFieldZutat.setText("");
	// textFieldMenge.setText("");
	// rezept.getZutatenListe().forEach(z -> System.out.println(z.getName()));
	// });
	// panel3.add(speichern3aButton);
	// panel3.add(new JLabel("Wenn Sie mit der Eingabe der Zutaten fertig sind,
	// speichern Sie das Rezept"));
	// JButton speichern3bButton = new JButton("Rezept speichern");
	// speichern3bButton.addActionListener(e -> {
	// // Rezept speichern
	// kochbuch.add(rezept);
	// kochbuch.forEach(r -> System.out.println(r.getRezeptName()));
	// ((CardLayout) eingabeMaske.getLayout()).show(eingabeMaske, panel1Name);
	// });
	// panel3.add(speichern3bButton);
	//
	// eingabeMaske.add(panel3, panel3Name);
	// return eingabeMaske;
	// }

// Hier wird der Rezeptindex erzeugt
	private JPanel erzeugeRezepteIndexPanel() {
// .....................Wie soll das Layout sein??
		JPanel rezepteIndexPanel = new JPanel(new BorderLayout());
// Label im Norden
		JLabel l = new JLabel("W�hle das gew�nschte Rezept aus");
		rezepteIndexPanel.add(l, BorderLayout.NORTH);
// DefaultListModell wird erzeugt
		rezeptListModel = new DefaultListModel<>();
// JList mit Eintr�gen wird erstellt
		JList<Rezept> rezeptJList = new JList<>();
		rezeptJList.setModel(rezeptListModel);
		rezeptJList.setCellRenderer(new RezeptListCellRenderer());
// JList wird Panel hinzugef�gt
		rezepteIndexPanel.add(rezeptJList, BorderLayout.CENTER);

// Such- und Rezeptanzeigepanel wird erzeugt und hinzugef�gt
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(new JLabel("Suche Rezept nach Namen"));
		JTextField namenSuche = new JTextField(20);
		namenSuche.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				suche();
			}

			public void removeUpdate(DocumentEvent e) {
				suche();
			}

			public void insertUpdate(DocumentEvent e) {
				suche();
			}

			public void suche() {
// Rezepte im Kochbuch (oder Eintr�ge in JList?) sollen mit Suchbegriff abgeglichen und bei �bereinstimmung markiert
// werden
				kochbuch.forEach(r -> {
					if (r.getRezeptName().contains(namenSuche.getText())) {
						rezeptJList.setSelectedValue(r, true);
					}
// dann muss der entsprechende Listeneintrag markiert werden
				});
				System.out.println(namenSuche.getText());
			}
		});
		
		buttonPanel.add(namenSuche);

		JButton rezeptAnzeige = new JButton("Anzeigen");
// .................Achtung! Es kommt zu einer Null-Pointer-Exception, wenn Anzeigen gedr�ckt wird, ohne dass ein Rezept
// ausgew�hlt ist
		rezeptAnzeige.addActionListener(e -> {
// Rezeptanzeigeseite wird mit Daten des gew�hlten Rezeptes ge�ffnet
//............die Rezeptanzeige funktioniert nicht mehr: hier kommt es zu einer Null-Pointer-Exception			
			rezeptAnzeigePanel.updatePanel(rezeptJList.getSelectedValue());
			((CardLayout) container.getLayout()).show(container, nameRezeptAnzeigePanel);
		});
		buttonPanel.add(rezeptAnzeige);
		JButton rezeptLoeschen = new JButton("L�schen");
		rezeptLoeschen.addActionListener(e -> {
			int rueckgabeWert = JOptionPane.showConfirmDialog(null, "M�chten Sie das Rezept "
					+ rezeptJList.getSelectedValue().getRezeptName().toString() + " wirklich l�schen?");
			if (rueckgabeWert == JOptionPane.YES_OPTION) {
				kochbuch.removeIf(
						r -> r.getRezeptName().equals(rezeptJList.getSelectedValue().getRezeptName().toString()));
// das L�schen wird nicht an das rezeptListModel �bermittelt das m�sste in einem echten Programm anders sein. Wie geht
// das?
				alleRezepteAnzeigen();
			}
		});
		buttonPanel.add(rezeptLoeschen);
		rezepteIndexPanel.add(buttonPanel, BorderLayout.SOUTH);

		return rezepteIndexPanel;
	}

	private JPanel erzeugeAnzeigeFeld() {
		JPanel anzeigeFeld = new JPanel();
		anzeigeFeld.add(new JButton());
		return anzeigeFeld;
	}

	private void einlesen(File file) {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
			kochbuch = (ArrayList<Rezept>) ois.readObject();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void speichern(File file) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
			oos.writeObject(kochbuch);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void alleRezepteAnzeigen() {
		((CardLayout) container.getLayout()).show(container, nameRezepteIndexPanel);
// es werden alle Rezepte hinzugef�gt, die nicht schon enthalten sind (die Einschr�nkung ist nach "clear()" unn�tig)
// Problem: Rezepte, die zwischenzeitlich gel�scht wurden, werden nicht entfernt
// Ausweg: rezeptListModel vorher leeren, in einem echten Programm w�re das aber ein Performanceproblem
		rezeptListModel.clear();
		kochbuch.forEach(r -> {
			if (!(rezeptListModel.contains(r)))
				rezeptListModel.addElement(r);
		});
// ....... Men�item "Alle Anzeigen" ausblenden.
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		beendenAbfrage();

	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	private void beendenAbfrage() {
		int rueckgabeWert = JOptionPane.showConfirmDialog(null, "M�chten Sie das Kochbuch vor dem Beenden speichern?");
		if (rueckgabeWert == JOptionPane.YES_OPTION) {
			speichereKochbuch();
		}
	}

	private void speichereKochbuch() {
		System.out.println("Kochbuch speichern");
// JFileChooser-Objekt erstellen
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
// Dialog zum Speichern von Dateien anzeigen
// ............... Frage: ist null ok oder muss man eine Komponente w�hlen?
		int rueckgabeWert = chooser.showSaveDialog(null);
		if (rueckgabeWert == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();

			// .......... mit ausgew�hltem File abspeichern
			speichern(file);
		}
	}

	private void ladeKochbuch() {
		// JFileChooser-Objekt erstellen
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		int rueckgabeWert = chooser.showOpenDialog(null);
		if (rueckgabeWert == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			// Kochbuch aus ausgew�hltem File laden
			einlesen(file);
		}
	}
}

// Problem im Moment: Nach L�schen eines Rezepts verschwindet das Rezept nicht aus der �bersicht. Erledigt
// bei erneutem Laden des Kochbuchs werden die Rezepte angeh�ngt, sind also doppelt vorhanden. Erledigt
// .................Achtung! Es kommt zu einer Null-Pointer-Exception, wenn Anzeigen gedr�ckt wird, ohne dass ein Rezept
// ausgew�hlt ist