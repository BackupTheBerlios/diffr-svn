package de.berlios.diffr.result.resultForDiffractionOfPlaneWaveOnPriodicSurface;

import Org.netlib.math.complex.Complex;
import de.berlios.diffr.exceptions.ObjectIsnotEditableException;
import de.berlios.diffr.*;

public class ReflectedPlaneWave extends Model {
	public static final boolean polarizationE = false;
	public static final boolean polarizationH = true;

	public ReflectedPlaneWave(boolean polarization, double angle, double waveLength, Complex amplitude) {
	    if (amplitude == null) throw new NullPointerException();
	    this.amplitude = amplitude;
	    this.angle = angle;
	    this.polarization = polarization;
	    this.waveLength = waveLength;
	}

	private boolean polarization;
	private double angle;
	private Complex amplitude;
	private double waveLength;

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

	public ReflectedPlaneWave dimensioning(double period, Complex impingingWaveAmplitude) {
	    Complex dimensionalAmplitude = amplitude.mul(impingingWaveAmplitude);
	    double dimensionalAngle = angle;
	    boolean dimensionalPolarization = polarization;
	    double dimensionalWaveLength = waveLength/Complex.TWO_PI*period;
		return new ReflectedPlaneWave(dimensionalPolarization, dimensionalAngle, dimensionalWaveLength, dimensionalAmplitude);
	}
}
