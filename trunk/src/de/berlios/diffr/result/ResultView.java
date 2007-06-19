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
			View passedFieldView = null;
			View surfaceCurrentView = null;
			try {
				reflectedFieldView = viewFactory.makeView(newResult.getReflectedField());
				tabbedPane.add("Reflected field", reflectedFieldView);
			} catch (WrongTypeException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Unknown type of reflected field");
			} catch (NullPointerException e) {
				// Algorithm can`t calculate this
			}
			try {
				passedFieldView = viewFactory.makeView(newResult.getPassedField());
				tabbedPane.add("Passed field", passedFieldView);
			} catch (WrongTypeException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Unknown type of passed field");
			} catch (NullPointerException e) {
				// Algorithm can`t calculate this
			}
			try {
				surfaceCurrentView = viewFactory.makeView(newResult.getSurfaceCurrent());
				tabbedPane.add("Surface current", surfaceCurrentView);
			} catch (WrongTypeException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Unknown type of surface current");
			} catch (NullPointerException e) {
				// Algorithm can`t calculate this
			}
			tabbedPane.add("Energetic imperfection", new JLabel("Energetic imperfection " + newResult.getEenergeticImperfection()*100 + "%"));
			this.add(tabbedPane);
		}
		validate();
	}
}
