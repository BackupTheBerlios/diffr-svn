package de.berlios.diffr.algorithms.addedAlgorithms;

import Org.netlib.math.complex.Complex;
import de.berlios.diffr.inputData.*;
import de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface.*;
import de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface.periodicSurface.*;

public class AlgorithmBase {

	protected double k;
	protected double alpha;
	protected double lamNull;
	private Complex f_m;
//	private double lam_m;
//	private Complex gam_m;
	private Complex [] f_negative_index;
	private Complex [] f_positive_and_0_index;
	protected int f_size;
	
	public void initialize (InputData inputData){
		PeriodicSurface surface = (PeriodicSurface) inputData.getSurface();
		ImpingingPlaneWave wave = (ImpingingPlaneWave) inputData.getImpingingField();
		k = surface.getShape().getPeriod() / wave.getLength();
		alpha = wave.getAngle();
//		gam_min = (int) Math.floor((k * (1 + Math.sin(alpha))));
//		gam_max =   (int) Math.floor( (k * (1 - Math.sin(alpha))));

 		
		lamNull = k * Math.sin(alpha);
 		
 		
 // 			lam_positive_and_0_index[i] = lamCurrent; 
// 			gam_positive_and_0_index[i] = new Complex(Math.sqrt(k*k - lamCurrent * lamCurrent), 0.0  );
 		
 		f_size = ( surface.getShape().getFourierCoefficients()).size();
 		f_negative_index = new Complex[f_size + 1];
 		f_positive_and_0_index = new Complex[f_size + 1];
 		double shiftDimension = surface.getShape().getShift();
 		double shiftDimensionless = shiftDimension * 2.0 * Math.PI / surface.getShape().getPeriod(); 
 		f_positive_and_0_index[0] = new Complex(shiftDimensionless,0.0);
 		
 		for (int i = 1; i <= f_size; i++) {
 			double reDimension = ( (FourierCoefficient) surface.getShape().getFourierCoefficients().get(i)).getCoefficientOfCosinis() / 2.0;
 			double reDimensionless = reDimension * 2.0 * Math.PI / surface.getShape().getPeriod();
 			
 			double imDimension = - ( (FourierCoefficient) surface.getShape().getFourierCoefficients().get(i)).getCoefficientOfSinis() / 2.0;
 			double imDimensionless = imDimension * 2.0 * Math.PI / surface.getShape().getPeriod();
 			
 			f_negative_index[i] = new Complex(reDimensionless,-imDimensionless);
 			f_positive_and_0_index[i] = new Complex(reDimensionless,imDimensionless);
 		}
	}
	
	protected double lam(int n){
			return lamNull + n;
	}

	protected Complex gam(int n){
		double s = k*k - lam(n)*lam(n);
		if ( s >= 0 ){
			return new Complex(Math.sqrt(s),0.0);
		} else {
			return new Complex(0.0,Math.sqrt(-s));
		}
	}
}
