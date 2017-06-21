import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

public class RezeptListCellRenderer extends DefaultListCellRenderer {
	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		
		Rezept rezept = (Rezept)value;
		setText(rezept.getRezeptName());		
		return this;
	}

}