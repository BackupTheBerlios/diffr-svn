package de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface;

import de.berlios.diffr.inputData.InputDataPartView;
import java.awt.*;
import javax.swing.*;
import de.berlios.diffr.*;

public class ImpingingPlaneWaveView extends InputDataPartView {
	private DataString angleData;
	private DataStringView angleView;
	private DataString lengthData;
	private DataStringView lengthView;
	public ImpingingPlaneWaveView(ImpingingPlaneWave wave) {
		angleData = new DataString("angle", new Double(wave.getAngle()));
		angleView = new DataStringView(angleData);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(angleView);
		this.add(Box.createVerticalStrut(10));
		this.add(lengthView);
	}
	public void drawImage(Graphics g) {
		
	}
}
