package de.berlios.diffr.result;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Org.netlib.math.complex.Complex;

import de.berlios.diffr.View;

public class SurfaceCurrentViewText extends View {
	private static final long serialVersionUID = 1L;
	private JTextArea text = new JTextArea();
	private JScrollPane scrollText = new JScrollPane(text);
	private String result = null;
	private SurfaceCurrent current = null;
	public SurfaceCurrentViewText(SurfaceCurrent current) {
		this.current = current;
	}
	public void paintComponent(Graphics g) {
		if (result==null) {
			result = "";
			result += "calculate " + current.getPointsNumber() + " points:\n";
			for (int a = 0; a < current.getPointsNumber(); a++) {
				Complex c = current.get(a);
				result += "x: " + a * current.getSurfacePeriod()
						/ current.getPointsNumber();
				result += " phase: " + c.arg();
				result += " abs: " + c.abs() + "\n";
			}
			if (current.isOutOfDate())
				text.setForeground(Color.lightGray);
			else
				text.setForeground(Color.black);
			text.setText(result);
			text.setEditable(false);
			this.setLayout(new BorderLayout());
			this.add(scrollText);
			this.validate();
		}		
		super.paintComponent(g);
	}
}
