package de.berlios.diffr.algorithms.addedAlgorithms.KirchgoffAlgorithm;

import Org.netlib.math.complex.Complex;
import de.berlios.diffr.result.SurfaceCurrent;
import de.berlios.diffr.inputData.IncidentWave;
import de.berlios.diffr.result.ReflectedPlaneWave;

public class KirchgoffAlgorithmSolverPerfectConductivity extends
		KirchgoffAlgorithmSolver {

	public SurfaceCurrent calculateSurfaceCurrent(int numberOfPoints) {
		Complex [] current = new Complex [ numberOfPoints];
		double deltaX = Complex.TWO_PI / numberOfPoints;
		double xCurrent = deltaX/2.0;
		for (int j = 0; j< numberOfPoints; j++){
			if (polarization == IncidentWave.polarizationE) {
				current[j] = normalDerivativeOfIncidentField(xCurrent).mul(2.0);
			} else {
				current[j] = incidentField(xCurrent,f_surface(xCurrent));
			}
			xCurrent += deltaX; 
		}
		SurfaceCurrent surfaceCurrent = new SurfaceCurrent(current, 2*Math.PI);
		return surfaceCurrent;
	}
	

	public Complex calculateNonDimensionalAmplitude(int numberOfPoints, int m, int order) {
		if ( polarization == ReflectedPlaneWave.polarizationE ) {
				return calculateIntegral(numberOfPoints, m).div(  gam(m).mul(-Complex.TWO_PI)    );
		} else {
			return calculateIntegral(numberOfPoints, m).div(  gam(m).mul(Complex.TWO_PI)    );
		}
	}

	public Complex calculateIntegral(int numberOfPoints, int m) {
		Complex s = new Complex(0.0, 0.0) ;
		double deltaX = Complex.TWO_PI / numberOfPoints;
		double xCurrent = deltaX/2.0;
		for (int j = 0; j< numberOfPoints; j++){
			s = s.add(insideIntegral(xCurrent, m));
			xCurrent += deltaX; 
		}
		s = s.mul(deltaX);
		
		return s;
	}
	

	
	public Complex insideIntegral(double y, int m) {
		Complex s1 = (Complex.i.mul(-m).mul(y)).exp();
		
		Complex s2 = (
				Complex.i.mul( gam(0).add(gam(m)) ).mul(f_surface(y))
		).exp();
		
		Complex s3 = gam(0).sub( lamNull * derivative_f_surface(y)   ) ;
		return s1.mul(s2).mul(s3);
	}
	
	public double calculateEnergyError(int numberOfPoints){
		double s = 0.0;
		int counter = 0;
		for (int j = -gam_min; j<= gam_max; j++){
			double absAmplitude = waves[counter].getAmplitude().abs(); 
			s = s + absAmplitude*absAmplitude * (gam(j).re());
			counter++;
		}
		
		return Math.abs(s/(gam(0).re()) - 1.0);
	}


	@Override
	protected Complex calculateNonDimensionalAmplitude(int n, int order) {
		// TODO Auto-generated method stub
		return null;
	}

}
