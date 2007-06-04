package de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface;

import Org.netlib.math.complex.Complex;
import de.berlios.diffr.exceptions.ObjectIsnotEditableException;
import de.berlios.diffr.inputData.ImpingingField;

public class ImpingingPlaneWave extends ImpingingField {
	public static final boolean polarizationE = false;
	public static final boolean polarizationH = true;

	public ImpingingPlaneWave() {}
	public ImpingingPlaneWave(boolean polarization, double angle, double waveLength, Complex amplitude) {
	    if (amplitude == null) throw new NullPointerException();
	    this.amplitude = amplitude;
	    this.angle = angle;
	    this.polarization = polarization;
	    this.waveLength = waveLength;
	}

	private boolean polarization = ImpingingPlaneWave.polarizationE;
	private double angle = 1;
	private Complex amplitude = new Complex(1, 1);
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
}
