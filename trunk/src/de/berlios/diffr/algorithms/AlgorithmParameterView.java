package de.berlios.diffr.algorithms;

import de.berlios.diffr.View;
import de.berlios.diffr.exceptions.ObjectIsnotEditableException;
import de.berlios.diffr.exceptions.WrongTypeException;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AlgorithmParameterView extends View {
	private AlgorithmParameter algorithmParameter;
	private JTextField text = new JTextField();
	public AlgorithmParameterView(AlgorithmParameter p) {
		this.algorithmParameter = p;
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	    this.add(new JLabel(p.getDescription()));
	    text.setMaximumSize(new Dimension(300, 20));
	    this.add(text);
	    text.setText(p.getValue().toString());
	    text.addKeyListener(new KeyAdapter() {
	      public void keyReleased(KeyEvent e) {
	    	  try {
				Object newValue = "";
				  if (algorithmParameter.getValue().getClass() == Long.class)
					  newValue = new Long(Long.parseLong(text.getText()));
				  if (algorithmParameter.getValue().getClass() == Integer.class)
					  newValue = new Integer(Integer.parseInt(text.getText()));
				  if (algorithmParameter.getValue().getClass() == Double.class)
					  newValue = new Double(Double.parseDouble(text.getText()));
				  try {
					algorithmParameter.setValue(newValue);
				} catch (ObjectIsnotEditableException e1) {
					text.setText(algorithmParameter.getValue().toString());
					cannotSetError();
				}
			} catch (NumberFormatException e1) {}
			catch (WrongTypeException e1) {}
	      }
	    });
	}
	private void cannotSetError() {
		JOptionPane.showMessageDialog(this, "You can`t change this while task running");
	}
}
