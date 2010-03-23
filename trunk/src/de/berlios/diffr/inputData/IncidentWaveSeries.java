package de.berlios.diffr.inputData;

import Org.netlib.math.complex.Complex;
import de.berlios.diffr.Model;
import de.berlios.diffr.exceptions.ObjectIsnotEditableException;

public class IncidentWaveSeries extends Model {
	private static final long serialVersionUID = 1L;
	public static final boolean polarizationE = false;
	public static final boolean polarizationH = true;

	public IncidentWaveSeries() {}
	public IncidentWaveSeries(boolean polarization, double angle, double waveLength, Complex amplitude) {
	    if (amplitude == null) throw new NullPointerException();
	    this.amplitude = amplitude;
	    this.startAngle = angle;
	    this.finalAngle = angle;
	    this.startWaveLength = waveLength;
	    this.finalWaveLength = waveLength;
	    this.polarization = polarization;
	}

	private boolean polarization = IncidentWave.polarizationE;
	public boolean getPolarization() {
	    return polarization;
	}
	
	public double getFinalLength() {
		return finalWaveLength;
	}
	public int getPointsNumber() {
		return pointsNumber;
	}
	public double getStartAngle() {
		return startAngle;
	}
	public double getFinalAngle() {
		return finalAngle;
	}
	public Complex getAmplitude() {
		return amplitude;
	}
	public double getStartLength() {
		return startWaveLength;
	}

	private double startAngle = -Math.PI/6;
	private double finalAngle = Math.PI/3;
	private Complex amplitude = new Complex(1.0, 0.0);
	private double startWaveLength = 1;
	private double finalWaveLength = 1;
	private int pointsNumber = 25;

	public IncidentWave getIncidentWave(int i) {
		double da = finalAngle - startAngle;
		double dl = finalWaveLength - startWaveLength;
		double length, angle;
		if (pointsNumber!=1) {
			length = startWaveLength + (double)i/(pointsNumber-1) * dl;
			angle = startAngle + (double)i/(pointsNumber-1) * da;
		} else {
			length = startWaveLength;
			angle = startAngle;
		}
		
		return new IncidentWave(polarization, angle, length, amplitude);
	}
	
	public void setPointsNumber(int pointsNumber) throws ObjectIsnotEditableException {
		if (!isEditable()) throw new ObjectIsnotEditableException();
		this.pointsNumber = pointsNumber;
		modelWasChangedEvent();
	}
	public void setStartLength(double length) throws ObjectIsnotEditableException {
		if (!isEditable()) throw new ObjectIsnotEditableException();
		this.startWaveLength = length;
		modelWasChangedEvent();
	}
	public void setStartAngle(double angle) throws ObjectIsnotEditableException {
		if (!isEditable()) throw new ObjectIsnotEditableException();
		this.startAngle = angle;
		modelWasChangedEvent();
	}
	
	public void setAmplitude(Complex amplitude) throws ObjectIsnotEditableException {
		if (!isEditable()) throw new ObjectIsnotEditableException();
		this.amplitude = amplitude;
		modelWasChangedEvent();
	}
	
	public void setFinalLength(double length) throws ObjectIsnotEditableException {
		if (!isEditable()) throw new ObjectIsnotEditableException();
		this.finalWaveLength = length;
		modelWasChangedEvent();
	}
	public void setFinalAngle(double angle) throws ObjectIsnotEditableException {
		if (!isEditable()) throw new ObjectIsnotEditableException();
		this.finalAngle = angle;
		modelWasChangedEvent();
	}
	public void setPolarization(boolean polarization) throws ObjectIsnotEditableException {
		if (!isEditable()) throw new ObjectIsnotEditableException();
		this.polarization = polarization;
		modelWasChangedEvent();
	}
}
