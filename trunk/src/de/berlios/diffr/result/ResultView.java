package de.berlios.diffr.result;

import de.berlios.diffr.*;
import de.berlios.diffr.exceptions.WrongTypeException;
import java.awt.*;
import javax.swing.*;

public class ResultView extends View {
	private JTabbedPane tabbedPane = null;
	public ResultView() {
		this.setLayout(new BorderLayout());
	}
	public void setResult(Result newResult) {
		if (tabbedPane != null) this.remove(tabbedPane);
		if (newResult != null) {
			tabbedPane = new JTabbedPane();
			ViewFactory viewFactory = new ViewFactory();
			View reflectedFieldView = null;
			try {
				reflectedFieldView = viewFactory.makeView(newResult.getReflectedField());
				tabbedPane.add("Reflected field", reflectedFieldView);
				this.add(tabbedPane);
			} catch (WrongTypeException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Unknown type of reflected field");
			}
		}
		validate();
	}
}
