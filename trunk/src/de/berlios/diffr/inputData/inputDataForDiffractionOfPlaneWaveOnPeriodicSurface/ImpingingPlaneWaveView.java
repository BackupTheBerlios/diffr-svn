package de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface;

import de.berlios.diffr.exceptions.ObjectIsnotEditableException;
import de.berlios.diffr.exceptions.WrongTypeException;
import de.berlios.diffr.inputData.InputDataPartView;
import java.awt.*;
import java.awt.event.*;
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
	private JRadioButton HPolarization = new JRadioButton("H polarization");
	private JRadioButton EPolarization = new JRadioButton("E polarization");
	private ButtonGroup polarization = new ButtonGroup();
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
		lengthData = new DataString("length", new Double(wave.getLength()));
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
		polarization.add(HPolarization);
		polarization.add(EPolarization);
		if(ImpingingPlaneWave.polarizationH == wave.getPolarization())
			HPolarization.doClick();
		else
			EPolarization.doClick();
		HPolarization.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					planeWave.setPolarization(ImpingingPlaneWave.polarizationH);
				} catch (ObjectIsnotEditableException e1) {
					if (planeWave.getPolarization() != ImpingingPlaneWave.polarizationH) {
						JOptionPane.showMessageDialog(null, "You can`t change this now");
						EPolarization.doClick();
					}
				}
			}
		});
		EPolarization.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					planeWave.setPolarization(ImpingingPlaneWave.polarizationE);
				} catch (ObjectIsnotEditableException e1) {
					if (planeWave.getPolarization() != ImpingingPlaneWave.polarizationE) {
						JOptionPane.showMessageDialog(null, "You can`t change this now");
						HPolarization.doClick();
					}
				}
			}
		});
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(angleView);
		this.add(Box.createVerticalStrut(10));
		this.add(lengthView);
		this.add(Box.createVerticalStrut(10));
		this.add(amplitudeView);
		this.add(Box.createVerticalStrut(10));
		this.add(HPolarization);
		this.add(EPolarization);
	}
	public double getModelSizeX() {
		return Math.abs(planeWave.getAmplitude().abs() * Math.sin(planeWave.getAngle()) * 2.5);
	}
	public double getModelSizeY() {
		return Math.abs(planeWave.getAmplitude().abs() * Math.cos(planeWave.getAngle()) * 2.5);
	}
	public void drawImage(Graphics g, double scale) {
		int width = g.getClipBounds().width;
		int height = g.getClipBounds().height;
		g.setColor(new Color(255, 0, 0));
		int x = width / 2;
		int y = height / 2;
		int x1 = (int) (x - planeWave.getAmplitude().abs() * scale * Math.sin(planeWave.getAngle()));
		int y1 = (int) (y - planeWave.getAmplitude().abs() * scale * Math.cos(planeWave.getAngle()));
		int x2 = (int) (x - 10 * Math.sin(planeWave.getAngle() + 0.3));
		int y2 = (int) (y - 10 * Math.cos(planeWave.getAngle() + 0.3));
		int x3 = (int) (x - 10 * Math.sin(planeWave.getAngle() - 0.3));
		int y3 = (int) (y - 10 * Math.cos(planeWave.getAngle() - 0.3));
		g.drawLine(x, y, x1, y1);
		g.drawLine(x, y, x2, y2);
		g.drawLine(x, y, x3, y3);
	}
}
