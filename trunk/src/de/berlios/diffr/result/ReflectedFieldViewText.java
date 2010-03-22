package de.berlios.diffr.result;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import de.berlios.diffr.View;

import javax.swing.*;

public class ReflectedFieldViewText extends View {
	private static final long serialVersionUID = 1L;
	private JTextArea text = new JTextArea();
	private JScrollPane scrollText = new JScrollPane(text);
	private ReflectedField field;
	public ReflectedFieldViewText(ReflectedField field) {
		this.field = field;
	}
	public void paintComponent(Graphics g) {
		String result = "";
		result += "Reflected field consist of " + field.getWaves().length + " waves:\n";
		for (int a=0;a<field.getWaves().length;a++) {
			ReflectedPlaneWave wave = field.getWaves()[a];
			result += "Wave #" + wave.getNumber() + "\n";
			try {
				result += "  angle        " + wave.getAngle() / Math.PI * 180 + "\n";
				result += "  amplitude    " + wave.getAmplitude() + "\n";
				result += "  length       " + wave.getLength() + "\n";
				if (wave.getPolarization() == ReflectedPlaneWave.polarizationH)
					result += "  polarization H\n";
				else
					result += "  polarization E\n";
			} catch (NullPointerException e) {
				result += "  wave not calculated\n";
			}
		}
		if (field.isOutOfDate())
			text.setForeground(Color.lightGray);
		else
			text.setForeground(Color.black);
		text.setText(result);
		text.setEditable(false);
		this.setLayout(new BorderLayout());
		this.add(scrollText);
		this.validate();
	}
}