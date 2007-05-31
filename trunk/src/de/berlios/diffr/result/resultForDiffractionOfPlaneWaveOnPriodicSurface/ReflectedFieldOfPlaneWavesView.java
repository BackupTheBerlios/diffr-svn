package de.berlios.diffr.result.resultForDiffractionOfPlaneWaveOnPriodicSurface;

import java.awt.BorderLayout;

import de.berlios.diffr.View;
import javax.swing.*;

public class ReflectedFieldOfPlaneWavesView extends View {
	private JTextArea text = new JTextArea();
	public ReflectedFieldOfPlaneWavesView(ReflectedFieldOfPlaneWaves field) {
		String result = "";
		result += "Reflected field consist of " + field.getWaves().length + " waves:\n";
		for (int a=0;a<field.getWaves().length;a++) {
			ReflectedPlaneWave wave = field.getWaves()[a];
			result += "Wave ¹" + (a + 1) + "\n";
			result += "  angle        " + wave.getAngle() + "\n";
			result += "  amplitude    " + wave.getAmplitude() + "\n";
			result += "  length       " + wave.getLength() + "\n";
			if (wave.getPolarization() == wave.polarizationH)
				result += "  polarization H\n";
			else
				result += "  polarization E\n";
		}
		text.setText(result);
		text.setEditable(false);
		this.setLayout(new BorderLayout());
		this.add(text);
		this.validate();
	}
}
