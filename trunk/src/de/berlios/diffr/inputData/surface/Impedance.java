package de.berlios.diffr.inputData.surface;

import Org.netlib.math.complex.Complex;

public class Impedance extends SurfaceConductivity {
	private static final long serialVersionUID = 1L;
	public Impedance(Complex epsilon) {
	    this.epsilon = epsilon;
	}
	public Impedance() {
		this.epsilon = new Complex(1, 1);
	}
	private Complex epsilon;
	public Complex getEpsilon() {
		return epsilon;
	}
	public SurfaceConductivity nonDimensioning() {
		return new Impedance(epsilon) ;
	}
}
