package de.berlios.diffr.inputData.surface;

import Org.netlib.math.complex.Complex;

public class ZeroConductivity extends SurfaceConductivity {
	private static final long serialVersionUID = 1L;
	public ZeroConductivity(Complex epsilon) {
	    this.epsilon = epsilon;
	}
	public ZeroConductivity() {
		this.epsilon = new Complex(1, 1);
	}
	private Complex epsilon;
	public Complex getEpsilon() {
		return epsilon;
	}
	public SurfaceConductivity nonDimensioning() {
		return new ZeroConductivity(epsilon);
	}
}
