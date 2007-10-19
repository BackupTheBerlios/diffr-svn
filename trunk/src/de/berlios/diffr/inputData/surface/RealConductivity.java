package de.berlios.diffr.inputData.surface;

import Org.netlib.math.complex.Complex;

public class RealConductivity extends SurfaceConductivity {
	private static final long serialVersionUID = 1L;
	public RealConductivity(Complex epsilon) {
	    this.epsilon = epsilon;
	}
	public RealConductivity() {
		this.epsilon = new Complex(1, 1);
	}
	private Complex epsilon;
	public Complex getEpsilon() {
		return epsilon;
	}
	public SurfaceConductivity nonDimensioning() {
		return new RealConductivity(epsilon) ;
	}
}
