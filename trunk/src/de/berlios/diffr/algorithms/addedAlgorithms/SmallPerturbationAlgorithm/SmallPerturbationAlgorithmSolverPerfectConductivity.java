package de.berlios.diffr.algorithms.addedAlgorithms.SmallPerturbationAlgorithm;

import Org.netlib.math.complex.Complex;
import de.berlios.diffr.inputData.IncidentWave;
import de.berlios.diffr.math.Formula;
import de.berlios.diffr.result.ReflectedPlaneWave;
import de.berlios.diffr.result.SurfaceCurrent;

public class SmallPerturbationAlgorithmSolverPerfectConductivity extends SmallPerturbationAlgorithmSolver {
public double d;
public Complex f1;
public Complex g1;
	public Complex calculateNonDimensionalAmplitude(int n, int order) {
		if ( polarization == ReflectedPlaneWave.polarizationE ) {
			if (order == 1)	{
				d = delta(n, 0);
				f1=f(n);
				g1=gam(0);
				return term1(n).add(-delta(n,0));
			}
			else
				return term1(n).add(-delta(n,0)).add(term2(n));
		} else {
			if (order == 1)	
				return term1(n).add(delta(n,0));
			else
				return term1(n).add(delta(n,0)).add(term2(n));
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
	
/*	public double calculateEnergyError(int numberOfPoints){
		double s = 0.0;
		int counter = 0;
		for (int j = -gam_min; j<= gam_max; j++){
			double absAmplitude = waves[counter].getAmplitude().abs(); 
			s = s + absAmplitude*absAmplitude * (gam(j).re());
			counter++;
		}
		
		return Math.abs(s/(gam(0).re()) - 1.0);
	}*/

	public SurfaceCurrent calculateSurfaceCurrent(int numberOfPoints) {
		Complex [] current = new Complex [ numberOfPoints];
		double deltaX = Complex.TWO_PI / numberOfPoints;
		double xCurrent = deltaX/2.0;
		for (int j = 0; j< numberOfPoints; j++){
			if (polarization == IncidentWave.polarizationE) {
				current[j] = normalDerivativeOfField(xCurrent);
			} else {
				current[j] = field(xCurrent,f_surface(xCurrent));
			}
			xCurrent += deltaX; 
		}
		SurfaceCurrent surfaceCurrent = new SurfaceCurrent(current, 2*Math.PI);
		return surfaceCurrent;
	}
}
