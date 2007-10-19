package de.berlios.diffr.result;

import de.berlios.diffr.*;
import java.awt.*;
import javax.swing.*;

public class ResultView extends View {
	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane = null;
	public ResultView() {
		this.setLayout(new BorderLayout());
	}
	public void setResult(Result newResult) {
		if (tabbedPane != null) this.remove(tabbedPane);
		if (newResult != null) {
			tabbedPane = new JTabbedPane();
			View reflectedFieldView = null;
			View passedFieldView = null;
			View surfaceCurrentView = null;
			if (newResult.getReflectedField()!=null)
				reflectedFieldView = new ReflectedFieldView(newResult.getReflectedField());
			if (newResult.getSurfaceCurrent()!=null)
				surfaceCurrentView = new SurfaceCurrentView(newResult.getSurfaceCurrent());
			if (reflectedFieldView!=null) tabbedPane.add("Reflected field", reflectedFieldView);
			if (passedFieldView!=null) tabbedPane.add("Passed field", passedFieldView);
			if (surfaceCurrentView!=null) tabbedPane.add("Surface current", surfaceCurrentView);
			tabbedPane.add("Energetic imperfection", new JLabel("Energetic imperfection " + newResult.getEnergeticImperfection()*100 + "%"));
			this.add(tabbedPane);
		}
		validate();
	}
}
