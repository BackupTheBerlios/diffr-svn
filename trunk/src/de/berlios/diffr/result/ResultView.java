package de.berlios.diffr.result;

import de.berlios.diffr.*;
import java.awt.*;
import javax.swing.*;

public class ResultView extends View {
	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane = null;
	public ResultView(Component buttons) {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(Box.createVerticalStrut(10));
		this.add(buttons);
		this.add(Box.createVerticalStrut(10));
	}
	public void setResult(Result newResult) {
		if (tabbedPane != null) this.remove(tabbedPane);
		if (newResult != null) {
			tabbedPane = new JTabbedPane();
			View reflectedFieldView = null;
			View passedFieldView = null;
			View surfaceCurrentView = null;
			if (newResult.getReflectedField()!=null)
				reflectedFieldView = new ReflectedFieldView(newResult);
			if (newResult.getSurfaceCurrent()!=null)
				surfaceCurrentView = new SurfaceCurrentView(newResult.getSurfaceCurrent());
			if (reflectedFieldView!=null) tabbedPane.add("Reflected field", reflectedFieldView);
			if (passedFieldView!=null) tabbedPane.add("Passed field", passedFieldView);
			if (surfaceCurrentView!=null) tabbedPane.add("Surface current", surfaceCurrentView);
			Box energyErrorBox = Box.createVerticalBox();
			String error = String.format("%f%%", newResult.getEnergyError()*100);
			JTextField energyErrorText = new JTextField(error);
			energyErrorText.setMaximumSize(new Dimension(200, 30));
			energyErrorText.setMinimumSize(new Dimension(200, 30));
			energyErrorText.setEditable(false);
			energyErrorBox.add(Box.createVerticalStrut(50));
			energyErrorBox.add(new JLabel("Energy error"));
			energyErrorBox.add(energyErrorText);
			tabbedPane.add("Energy error", energyErrorBox);
			this.add(tabbedPane);
		}
		validate();
	}
}
