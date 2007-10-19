package de.berlios.diffr.algorithms.addedAlgorithms;

import de.berlios.diffr.inputData.IncidentWave;
import de.berlios.diffr.result.ReflectedPlaneWave;
import de.berlios.diffr.result.SurfaceCurrent;
import Org.netlib.math.complex.Complex;

public class SmallPerturbationAlgorithmSolverHeightConductivity extends SmallPerturbationAlgorithmSolver {

	public Complex calculateNonDimensionalAmplitude(int n, int order) {
		if (order == 1)	
			return term0().mul(delta(n,0)).add(term1(n));
		else
			return term0().mul(delta(n,0)).add(term1(n)).add(term2(n));
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
			
			Complex t1 = Complex.i.mul(-2.0).mul(gam(0));
			Complex t2 = gam(0).mul(gam(0)).sub( n*lam(0) );
			Complex t3 = h.mul(h).mul( t2 ).add(1.0);
 
			Complex t4 = Complex.i.mul(gam(0)).mul(h).add(1.0);
			Complex t5 = Complex.i.mul(gam(n)).mul(h).add(1.0);

			return t1.mul(t3).div(t4).div(t5).mul(f(n));
		} 
		else {
			
			Complex t1 = Complex.i.mul(-2.0).mul(gam(0));
			Complex t2 = gam(0).mul(gam(0)).sub( n*lam(0) );
			Complex t3 = h.mul(h).add( t2 );
 
			Complex t4 = Complex.i.mul(gam(0)).add(h);
			Complex t5 = Complex.i.mul(gam(n)).add(h);

			return t1.mul(t3).div(t4).div(t5).mul(f(n));
		}
	}

	public Complex term2(int n) {
		Complex s = new Complex(0.0, 0.0);
		for (int m = -f_size; m <= f_size; m++){
			Complex c_mn = null;
			if ( polarization == ReflectedPlaneWave.polarizationE ) {
				Complex t1 = gam(m).mul(gam(m)).sub( gam(0).mul(gam(0))  );
				Complex t2 = t1.add(m*n);
				Complex t3 = Complex.i.mul(gam(m)).mul(h).add(1.0);
				Complex t4 = h.mul(t2).mul(t3);
				Complex t5 = gam(0).mul(gam(0)).sub( m*lam(0) );
				Complex t6 = h.mul(h).mul( t5 ).add(1.0).mul(2.0);
				Complex t7 = h.mul(lam(m)).mul(n-m);
				Complex t8 = t3;
				Complex t9 = Complex.i.mul(gam(m)).mul(t8);
				Complex t13 = t7.add(t9);
				Complex t14 = t6.mul(t13);
				Complex t10 = Complex.i.mul(gam(0)).mul(h).add(1.0);
				Complex t11 = t3;
				Complex t12 = Complex.i.mul(gam(n)).mul(h).add(1.0);
				c_mn = ( t4.add(t14) ).div(t10).div(t11).div(t12);
				
				s = s.add( c_mn.mul(f(m)).mul(f(n-m)) );
			} else {
				Complex t1 = gam(m).mul(gam(m)).sub( gam(0).mul(gam(0))  );
				Complex t2 = t1.add(m*n);
				Complex t3 = Complex.i.mul(gam(m)).add(h);
				Complex t4 = h.mul(t2).mul(t3);
				Complex t5 = gam(0).mul(gam(0)).sub( m*lam(0) );
				Complex t6 = h.mul(h).add( t5 ).mul(2.0);
				Complex t7 = new Complex (lam(m)*(n-m));
				Complex t8 = t3;
				Complex t9 = Complex.i.mul(gam(m)).mul(t8);
				Complex t13 = t7.add(t9);
				Complex t14 = t6.mul(t13);
				Complex t10 = Complex.i.mul(gam(0)).add(h);
				Complex t11 = t3;
				Complex t12 = Complex.i.mul(gam(n)).add(h);
				c_mn = ( t4.add(t14) ).div(t10).div(t11).div(t12);
				
				s = s.add( c_mn.mul(f(m)).mul(f(n-m)) );
			}
		}
			s = s.mul(-1.0).mul(Complex.i).mul(gam(0));
		return s;
	}

	public double calculateEnergyError(int numberOfPoints){
		double s = 0.0;
		int counter = 0;
		for (int j = -gam_min; j<= gam_max; j++){
			double absAmplitude = waves[counter].getAmplitude().abs(); 
			s = s + absAmplitude*absAmplitude * (gam(j).re());
			counter++;
		}
		
		double surfaceAbsorbtion = calculateSurfaceAbsorbtion(numberOfPoints);
		
		return Math.abs((surfaceAbsorbtion +  s)/(gam(0).re()) - 1.0);
	}

	private double calculateSurfaceAbsorbtion(int numberOfPoints) {

		SurfaceCurrent surfaceCurrent = calculateSurfaceCurrent(numberOfPoints);
		double s = 0.0;
		double deltaX = Complex.TWO_PI / numberOfPoints;
		double xCurrent = deltaX/2.0;
		for (int j = 0; j< numberOfPoints; j++){
			s = s + surfaceCurrent.get(j).norm()*di(xCurrent);
			xCurrent += deltaX; 
		}
		
		double term = 0.0;
		if (polarization == IncidentWave.polarizationE) {
			term = h.im()*(-1.0)/Math.PI/2.0*s;
		} else {
			term = h.im()/Math.PI/2.0*s;
		}
		
		
		return term;
	}

	private double di(double current) {
		double deriv = derivative_f_surface(current);
		return Math.sqrt(1.0 + deriv*deriv);
	}

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
