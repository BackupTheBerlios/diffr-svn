package de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface;

import de.berlios.diffr.exceptions.ObjectIsnotEditableException;
import de.berlios.diffr.exceptions.WrongTypeException;
import de.berlios.diffr.inputData.InputDataPartView;
import java.awt.*;
import javax.swing.*;
import Org.netlib.math.complex.*;
import de.berlios.diffr.*;

public class ImpingingPlaneWaveView extends InputDataPartView {
	private DataString angleData;
	private DataStringView angleView;
	private DataString lengthData;
	private DataStringView lengthView;
	private DataString amplitudeData;
	private DataStringView amplitudeView;
	private ImpingingPlaneWave planeWave;
	public ImpingingPlaneWaveView(ImpingingPlaneWave wave) {
		this.planeWave = wave;
		angleData = new DataString("angle", new Double(wave.getAngle()));
		angleView = new DataStringView(angleData);
		angleData.addModelChangingListener(new ModelChangingListener() {
			public void modelWasChanged(Model model) {
				try {
					planeWave.setAngle(((Double)angleData.getValue()).doubleValue());
				} catch (ObjectIsnotEditableException e) {
					try {
						angleData.setValue(new Double(planeWave.getAngle()));
					} catch (WrongTypeException e1) {e1.printStackTrace();
					} catch (ObjectIsnotEditableException e1) {e1.printStackTrace();}
				}
			}
		});
		lengthData = new DataString("length", new Double(wave.getAngle()));
		lengthView = new DataStringView(lengthData);
		lengthData.addModelChangingListener(new ModelChangingListener() {
			public void modelWasChanged(Model model) {
				try {
					planeWave.setLength(((Double)lengthData.getValue()).doubleValue());
				} catch (ObjectIsnotEditableException e) {
					try {
						lengthData.setValue(new Double(planeWave.getLength()));
					} catch (WrongTypeException e1) {e1.printStackTrace();
					} catch (ObjectIsnotEditableException e1) {e1.printStackTrace();}
				}
			}
		});
		amplitudeData = new DataString("amplitude", wave.getAmplitude());
		amplitudeView = new DataStringView(amplitudeData);
		amplitudeData.addModelChangingListener(new ModelChangingListener() {
			public void modelWasChanged(Model model) {
				try {
					planeWave.setAmplitude((Complex)amplitudeData.getValue());
				} catch (ObjectIsnotEditableException e) {
					try {
						amplitudeData.setValue(planeWave.getAmplitude());
					} catch (WrongTypeException e1) {e1.printStackTrace();
					} catch (ObjectIsnotEditableException e1) {e1.printStackTrace();}
				}
			}
		});
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(angleView);
		this.add(Box.createVerticalStrut(10));
		this.add(lengthView);
		this.add(Box.createVerticalStrut(10));
		this.add(amplitudeView);
	}
	public void drawImage(Graphics g) {
		
	}
}
