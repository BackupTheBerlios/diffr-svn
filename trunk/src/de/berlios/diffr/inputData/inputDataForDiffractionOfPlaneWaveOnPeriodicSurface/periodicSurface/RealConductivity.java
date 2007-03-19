package de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface.periodicSurface;

import Org.netlib.math.complex.Complex;

public class RealConductivity extends SurfaceConductivity {
	public RealConductivity(Complex epsilon) {
	    this.epsilon = epsilon;
	}
	private Complex epsilon;
	public Complex getEpsilon() {
		return epsilon;
	}
}
