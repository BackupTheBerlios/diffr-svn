package de.berlios.diffr.inputData;

import de.berlios.diffr.exceptions.ObjectIsnotEditableException;
import de.berlios.diffr.exceptions.WrongTypeException;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Org.netlib.math.complex.*;
import de.berlios.diffr.*;

public class IncidentWaveSeriesView extends InputDataPartView {
	private static final long serialVersionUID = 1L;
	private DataString pointsNumberData;
	private DataStringView pointsNumberView;
	private DataString startAngleData;
	private DataString finalAngleData;
	private DataStringView startAngleView;
	private DataStringView finalAngleView;
	private DataString startLengthData;
	private DataString finalLengthData;
	private DataStringView startLengthView;
	private DataStringView finalLengthView;
	private DataString amplitudeData;
	private DataStringView amplitudeView;
	private IncidentWaveSeries waveSeries;
	private JRadioButton HPolarization = new JRadioButton("H polarization");
	private JRadioButton EPolarization = new JRadioButton("E polarization");
	private ButtonGroup polarization = new ButtonGroup();
	public IncidentWaveSeriesView(IncidentWaveSeries series) {
		this.waveSeries = series;
		waveSeries.addModelChangingListener(new ModelChangingListener() {
			public void modelWasChanged(Model model) {
				calculateSizes();
			}
		});
		ModelChangingListener changeListener = new ModelChangingListener() {
			public void modelWasChanged(Model model) {
				try {
					if (model==pointsNumberData) if (waveSeries.getPointsNumber() != (Integer)pointsNumberData.getValue()) {
						if ((Integer)pointsNumberData.getValue() < 1) try {
							pointsNumberData.setValue(1);
							pointsNumberView.renew();
						} catch (Exception e) {e.printStackTrace();}
						waveSeries.setPointsNumber((Integer)pointsNumberData.getValue());
					}
					if (model==startAngleData) if (waveSeries.getStartAngle() != (Double)startAngleData.getValue() * Math.PI / 180) waveSeries.setStartAngle((Double)startAngleData.getValue() * Math.PI / 180);
					if (model==finalAngleData) if (waveSeries.getFinalAngle() != (Double)finalAngleData.getValue() * Math.PI / 180) waveSeries.setFinalAngle((Double)finalAngleData.getValue() * Math.PI / 180);
					if (model==startLengthData) if (waveSeries.getStartLength() != (Double)startLengthData.getValue()) waveSeries.setStartLength((Double)startLengthData.getValue());
					if (model==finalLengthData) if (waveSeries.getFinalLength() != (Double)finalLengthData.getValue()) waveSeries.setFinalLength((Double)finalLengthData.getValue());
					if (model==amplitudeData) if (!waveSeries.getAmplitude().equals(amplitudeData.getValue())) waveSeries.setAmplitude((Complex)amplitudeData.getValue());
				} catch (ObjectIsnotEditableException e) {
					JOptionPane.showMessageDialog(null, "You can`t change this now");
					try {
						if (model==pointsNumberData) {
							pointsNumberData.setValue(waveSeries.getPointsNumber());
							pointsNumberView.renew();
						}
						if (model==startAngleData) {
							startAngleData.setValue(waveSeries.getStartAngle() / Math.PI * 180);
							startAngleView.renew();
						}
						if (model==finalAngleData) {
							finalAngleData.setValue(waveSeries.getFinalAngle() / Math.PI * 180);
							finalAngleView.renew();
						}
						if (model==startLengthData) {
							startLengthData.setValue(waveSeries.getStartLength());
							startLengthView.renew();
						}
						if (model==finalLengthData) {
							finalLengthData.setValue(waveSeries.getFinalLength());
							finalLengthView.renew();
						}
						if (model==amplitudeData) {
							amplitudeData.setValue(waveSeries.getAmplitude());
							amplitudeView.renew();	
						}
					} catch (WrongTypeException e1) {e1.printStackTrace();
					} catch (ObjectIsnotEditableException e1) {e1.printStackTrace();}
				}
			}
		};
		pointsNumberData = new DataString("Descritization points number", waveSeries.getPointsNumber());
		pointsNumberView = new DataStringView(pointsNumberData);
		startAngleData = new DataString("start angle", waveSeries.getStartAngle() / Math.PI * 180);
		finalAngleData = new DataString("final angle", waveSeries.getFinalAngle() / Math.PI * 180);
		startAngleView = new DataStringView(startAngleData);
		finalAngleView = new DataStringView(finalAngleData);
		startLengthData = new DataString("start length", waveSeries.getStartLength());
		finalLengthData = new DataString("final length", waveSeries.getFinalLength());
		startLengthView = new DataStringView(startLengthData);
		finalLengthView = new DataStringView(finalLengthData);
		amplitudeData = new DataString("amplitude", waveSeries.getAmplitude());
		amplitudeView = new DataStringView(amplitudeData);
		
		pointsNumberData.addModelChangingListener(changeListener);
		startAngleData.addModelChangingListener(changeListener);
		finalAngleData.addModelChangingListener(changeListener);
		startLengthData.addModelChangingListener(changeListener);
		finalLengthData.addModelChangingListener(changeListener);
		amplitudeData.addModelChangingListener(changeListener);
		amplitudeData.addModelChangingListener(changeListener);
		
		polarization.add(HPolarization);
		polarization.add(EPolarization);
		if(IncidentWave.polarizationH == waveSeries.getPolarization())
			HPolarization.doClick();
		else
			EPolarization.doClick();
		HPolarization.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					waveSeries.setPolarization(IncidentWave.polarizationH);
				} catch (ObjectIsnotEditableException e1) {
					if (waveSeries.getPolarization() != IncidentWave.polarizationH) {
						JOptionPane.showMessageDialog(null, "You can`t change this now");
						EPolarization.doClick();
					}
				}
			}
		});
		EPolarization.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					waveSeries.setPolarization(IncidentWave.polarizationE);
				} catch (ObjectIsnotEditableException e1) {
					if (waveSeries.getPolarization() != IncidentWave.polarizationE) {
						JOptionPane.showMessageDialog(null, "You can`t change this now");
						HPolarization.doClick();
					}
				}
			}
		});
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(Box.createVerticalStrut(30));
		this.add(pointsNumberView);
		this.add(Box.createVerticalStrut(30));
		this.add(startAngleView);
		this.add(finalAngleView);
		this.add(Box.createVerticalStrut(10));
		this.add(startLengthView);
		this.add(finalLengthView);
		this.add(Box.createVerticalStrut(20));
		this.add(amplitudeView);
		this.add(Box.createVerticalStrut(20));
		this.add(HPolarization);
		this.add(EPolarization);
	}
	
	private double sizeX;
	private double sizeY;
	public double getModelSizeX() {
		return sizeX;
	}
	public double getModelSizeY() {
		return sizeY;
	}
	
	private void calculateSizes() {
		sizeX = 0;
		sizeY = 0;
		double da = waveSeries.getFinalAngle() - waveSeries.getStartAngle();
		double dl = waveSeries.getFinalLength() - waveSeries.getStartLength();
		int points = waveSeries.getPointsNumber();
		if (points==1) ++points;
		for (int i=0; i<points; i++) {
			double length = waveSeries.getStartLength() + (double)i/(points-1) * dl;
			double angle = waveSeries.getStartAngle() + (double)i/(points-1) * da;
			sizeX = Math.max(sizeX, Math.abs(length * Math.sin(angle) * 2.5));
			sizeY = Math.max(sizeY, Math.abs(length * Math.cos(angle) * 2.5));
		}	
	}
	
	public void drawImage(Graphics g, double scale) {
		int width = g.getClipBounds().width;
		int height = g.getClipBounds().height;
		int x = width / 2;
		int y = height / 2;
		
		double da = waveSeries.getFinalAngle() - waveSeries.getStartAngle();
		double dl = waveSeries.getFinalLength() - waveSeries.getStartLength();
		int points = waveSeries.getPointsNumber();
		
		for (int i=1; i<points; i++) {
			drawWave(g, scale, x, y, da, dl, points, i);
		}
		drawWave(g, scale, x, y, da, dl, points, 0);
		if (currentWave>=0 && currentWave<points)
			drawWave(g, scale, x, y, da, dl, points, currentWave);
	}
	private int currentWave = -1;
	public void setCurrentWave(int point) {
		currentWave = point;
	}
	private void drawWave(Graphics g, double scale, int x, int y, double da,
			double dl, int points, int i) {
		double length, angle;
		if (points!=1) {
			length = waveSeries.getStartLength() + (double)i/(points-1) * dl;
			angle = waveSeries.getStartAngle() + (double)i/(points-1) * da;
		} else {
			length = waveSeries.getStartLength();
			angle = waveSeries.getStartAngle();
		}
		if (i==0 || i==points-1)
			g.setColor(new Color(100, 0, 0));
		else
			g.setColor(new Color(50, 0, 0));
		if (i==currentWave) g.setColor(new Color(255, 0, 0));
		int x1 = (int) (x + length * scale * Math.sin(angle));
		int y1 = (int) (y - length * scale * Math.cos(angle));
		double arrowLength = length/10*scale;
		int x2 = (int) (x + arrowLength * Math.sin(angle + 0.2));
		int y2 = (int) (y - arrowLength * Math.cos(angle + 0.2));
		int x3 = (int) (x + arrowLength * Math.sin(angle - 0.2));
		int y3 = (int) (y - arrowLength * Math.cos(angle - 0.2));
		g.drawLine(x, y, x1, y1);
		g.drawLine(x, y, x2, y2);
		g.drawLine(x, y, x3, y3);
	}
}
