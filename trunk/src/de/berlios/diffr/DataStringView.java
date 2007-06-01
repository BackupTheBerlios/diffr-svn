package de.berlios.diffr;

import de.berlios.diffr.exceptions.ObjectIsnotEditableException;
import de.berlios.diffr.exceptions.WrongTypeException;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Org.netlib.math.complex.*;

public class DataStringView extends View {
	private DataString dataString;
	private JTextField text1 = new JTextField();
	private JTextField text2 = new JTextField();
	public DataStringView(DataString p) {
		this.dataString = p;
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	    this.add(new JLabel(p.getDescription()));
	    if (dataString.getValue().getClass() != Complex.class) {
		    text1.setMaximumSize(new Dimension(300, 20));
		    this.add(text1);
		    text1.setText(p.getValue().toString());
		    text1.addKeyListener(new KeyAdapter() {
		      public void keyReleased(KeyEvent e) {
		    	try {
		    		Object newValue = findNewValue();
					try {
						dataString.setValue(newValue);
					} catch (ObjectIsnotEditableException e1) {
						text1.setText(dataString.getValue().toString());
						cannotSetError();
					}
				} catch (NumberFormatException e1) {
					if (!text1.getText().equals("") && !text1.getText().equals("-"))
						text1.setText(dataString.getValue().toString());
				}
				catch (WrongTypeException e1) {e1.printStackTrace();}
		      }
		    });
	    } else {
	    	text1.setMaximumSize(new Dimension(100, 20));
	    	text2.setMaximumSize(new Dimension(100, 20));
		    this.add(text1);
		    this.add(new JLabel(";"));
		    this.add(text2);
		    text1.setText(Double.toString(((Complex)p.getValue()).re()));
		    text2.setText(Double.toString(((Complex)p.getValue()).im()));
		    KeyAdapter complexKeyAdapter = new KeyAdapter() {
		      public void keyReleased(KeyEvent e) {
		    	try {
		    		Object newValue = new Complex(new Double(Double.parseDouble(text1.getText())), new Double(Double.parseDouble(text2.getText())));
					try {
						dataString.setValue(newValue);
					} catch (ObjectIsnotEditableException e1) {
						text1.setText(Double.toString(((Complex)dataString.getValue()).re()));
					    text2.setText(Double.toString(((Complex)dataString.getValue()).im()));
						cannotSetError();
					}
				} catch (NumberFormatException e1) {
					if (!text1.getText().equals("") && !text2.getText().equals("") && !text1.getText().equals("-") && !text2.getText().equals("-")) {
						text1.setText(Double.toString(((Complex)dataString.getValue()).re()));
						text2.setText(Double.toString(((Complex)dataString.getValue()).im()));
					}
				}
				catch (WrongTypeException e1) {e1.printStackTrace();}
		      }
		    };
		    text1.addKeyListener(complexKeyAdapter);
		    text2.addKeyListener(complexKeyAdapter);
	    }
	}
	private Object findNewValue() throws WrongTypeException, NumberFormatException {
		Object newValue = null;
		if (dataString.getValue().getClass() == Long.class)
			newValue = new Long(Long.parseLong(text1.getText()));
		if (dataString.getValue().getClass() == Integer.class)
			newValue = new Integer(Integer.parseInt(text1.getText()));
		if (dataString.getValue().getClass() == Double.class)
			newValue = new Double(Double.parseDouble(text1.getText()));
		if (newValue == null) throw new WrongTypeException();
		return newValue;
	}
	private void cannotSetError() {
		JOptionPane.showMessageDialog(this, "You can`t change this now");
	}
}
