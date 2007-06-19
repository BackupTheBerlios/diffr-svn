package de.berlios.diffr.algorithms.addedAlgorithms;

import de.berlios.diffr.result.resultForDiffractionOfPlaneWaveOnPriodicSurface.ReflectedPlaneWave;
import Org.netlib.math.complex.Complex;

public class SmallPerturbationAlgorithmSolverHeightConductivity extends SmallPerturbationAlgorithmSolver {

	public Complex calculateNonDimensionalAmplitude(int n) {
		if ( polarization == ReflectedPlaneWave.polarizationE ) {
			return term1(n).add(-delta(n,0));
		} else {
			return term1(n).add(delta(n,0));
		}
	}

	public Complex term0() {
		if ( polarization == ReflectedPlaneWave.polarizationE ) {
			return (Complex.i.mul(gam(0)).mul(h).sub(1.0)).div(Complex.i.mul(gam(0)).mul(h).add(1.0));
		} else {
			return (Complex.i.mul(gam(0)).sub(h)).div(Complex.i.mul(gam(0)).add(h));
		}
	}

	
	public Complex term1(int n) {
		if ( polarization == ReflectedPlaneWave.polarizationE ) {
			return Complex.i.mul(-2.0).mul(gam(0)).mul(f(n));
		} else {
			return ( Complex.i.mul(2.0).mul( gam0sqweared()-lamNull*n ).div(gam(n)) ).mul(f(n));
		}
	}

	public Complex term2(int n) {
		Complex s = new Complex(0.0, 0.0);
		for (int m = -f_size; m <= f_size; m++){
			if ( polarization == ReflectedPlaneWave.polarizationE ) {
				s = s.add( gam(m).mul(f(m)).mul(f(n-m)) );
			} else {
				s = s.add((f(m).mul(f(n-m)).mul(-2*(gam0sqweared()-lamNull*n)).div(gam(m).mul(gam(n)))).mul(gam0sqweared()-lamNull*m-lam(m)*n)  );  //  ( Complex.i.mul(2.0).mul( gam0sqweared()-lamNull*n ).div(gam(n)) ).mul(f(n));
			}
		}
		if ( polarization == ReflectedPlaneWave.polarizationE ) {
			s = s.mul(2.0).mul(gam(0));
		}
		return s;
	}
	}
