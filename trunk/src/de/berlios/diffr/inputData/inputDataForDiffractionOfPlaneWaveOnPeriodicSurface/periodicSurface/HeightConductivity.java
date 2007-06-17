package de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface.periodicSurface;

import Org.netlib.math.complex.Complex;

public class HeightConductivity extends SurfaceConductivity {	
	public HeightConductivity(Complex epsilon) {
	    this.epsilon = epsilon;
	}
	public HeightConductivity() {
		this.epsilon = new Complex(1, 1);
	}
	private Complex epsilon;
	public Complex getEpsilon() {
		return epsilon;
	}
	public SurfaceConductivity nonDimensioning() {
		return new HeightConductivity(epsilon);
	}
}
