package de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface.periodicSurface;

import Org.netlib.math.complex.Complex;

public class HeightConductivity extends SurfaceConductivity {	
	public HeightConductivity(Complex epsilon) {
	    this.epsilon = epsilon;
	}
	private Complex epsilon;
	public Complex getEpsilon() {
		return epsilon;
	}
}
