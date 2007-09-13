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
			View reflectedFieldView = new ReflectedFieldView(newResult.getReflectedField());
			View passedFieldView = null;
			View surfaceCurrentView = new SurfaceCurrentView(newResult.getSurfaceCurrent());
			tabbedPane.add("Reflected field", reflectedFieldView);
			tabbedPane.add("Passed field", passedFieldView);
			tabbedPane.add("Surface current", surfaceCurrentView);
			tabbedPane.add("Energetic imperfection", new JLabel("Energetic imperfection " + newResult.getEnergeticImperfection()*100 + "%"));
			this.add(tabbedPane);
		}
		validate();
	}
}
