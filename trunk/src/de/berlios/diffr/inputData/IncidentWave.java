package de.berlios.diffr.inputData;

import Org.netlib.math.complex.Complex;
import de.berlios.diffr.Model;
import de.berlios.diffr.algorithms.DimensionData;
import de.berlios.diffr.exceptions.ObjectIsnotEditableException;

public class IncidentWave extends Model {
	private static final long serialVersionUID = 1L;
	public static final boolean polarizationE = false;
	public static final boolean polarizationH = true;

	public IncidentWave() {}
	public IncidentWave(boolean polarization, double angle, double waveLength, Complex amplitude) {
	    if (amplitude == null) throw new NullPointerException();
	    this.amplitude = amplitude;
	    this.angle = angle;
	    this.polarization = polarization;
	    this.waveLength = waveLength;
	}

	public IncidentWave clone() {
		return new IncidentWave(polarization, angle, waveLength, (Complex)amplitude.clone());
	}
	
	private boolean polarization = IncidentWave.polarizationE;
	private double angle = Math.PI/6;
	private Complex amplitude = new Complex(1.0, 0.0);
	private double waveLength = 1;

	public double getLength() {
	    return waveLength;
	}

	public double getAngle() {
	    return angle;
	}

	public Complex getAmplitude() {
	    return amplitude;
	}

	public boolean getPolarization() {
	    return polarization;
	}

	public void setLength(double length) throws ObjectIsnotEditableException {
		if (!isEditable()) throw new ObjectIsnotEditableException();
		this.waveLength = length;
		modelWasChangedEvent();
	}
	public void setAngle(double angle) throws ObjectIsnotEditableException {
		if (!isEditable()) throw new ObjectIsnotEditableException();
		this.angle = angle;
		modelWasChangedEvent();
	}
	
	public void setAmplitude(Complex amplitude) throws ObjectIsnotEditableException {
		if (!isEditable()) throw new ObjectIsnotEditableException();
		this.amplitude = amplitude;
		modelWasChangedEvent();
	}
	
	public void setPolarization(boolean polarization) throws ObjectIsnotEditableException {
		if (!isEditable()) throw new ObjectIsnotEditableException();
		this.polarization = polarization;
		modelWasChangedEvent();
	}
	public IncidentWave nonDimensioning(DimensionData d) {
	    Complex nonDimensionalAmplitude = new Complex(1.0,0.0);
	    double nonDimensionalAngle = angle;
	    boolean nonDimensionalPolarization = polarization;
	    double nonDimensionalWaveLength = waveLength*Complex.TWO_PI/d.getPeriod();
		return new IncidentWave(nonDimensionalPolarization, nonDimensionalAngle, nonDimensionalWaveLength, nonDimensionalAmplitude) ;
	}
}
