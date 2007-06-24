package de.berlios.diffr.algorithms.addedAlgorithms;

import Org.netlib.math.complex.Complex;
import de.berlios.diffr.result.resultForDiffractionOfPlaneWaveOnPriodicSurface.ReflectedPlaneWave;

public class SmallPerturbationAlgorithmSolverPerfectConductivity extends SmallPerturbationAlgorithmSolver {

	public Complex calculateNonDimensionalAmplitude(int n) {
		if ( polarization == ReflectedPlaneWave.polarizationE ) {
			return term1(n).add(-delta(n,0));
		} else {
			return term1(n).add(delta(n,0));
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
	
	public double calculateEnergyError(){
		double s = 0.0;
		int counter = 0;
		for (int j = -gam_min; j<= gam_max; j++){
			double absAmplitude = waves[counter].getAmplitude().abs(); 
			s = s + absAmplitude*absAmplitude * (gam(j).re());
			counter++;
		}
		
		return Math.abs(s/(gam(0).re()) - 1.0);
	}
	
}
