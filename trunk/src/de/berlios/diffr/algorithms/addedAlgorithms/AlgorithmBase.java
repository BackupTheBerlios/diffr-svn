package de.berlios.diffr.algorithms.addedAlgorithms;

import Org.netlib.math.complex.Complex;
import de.berlios.diffr.inputData.*;
import de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface.*;
import de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface.periodicSurface.*;

public class AlgorithmBase {

	protected double k;
	protected double alpha;
	private Complex f_m;
	private double lam_m;
	private Complex gam_m;
	private Complex [] f_negative_index;
	private Complex [] f_positive_and_0_index;
	private double [] lam_negative_index;
	private double [] lam_positive_and_0_index;
	private Complex [] gam_negative_index;
	private Complex [] gam_positive_and_0_index;
	private int f_size;
	private int gam_min;
	private int gam_max;
	
	public void initialize (InputData inputData){
		PeriodicSurface surface = (PeriodicSurface) inputData.getSurface();
		ImpingingPlaneWave wave = (ImpingingPlaneWave) inputData.getImpingingField();
		k = surface.getShape().getPeriod() / wave.getLength();
		alpha = wave.getAngle();
		gam_min = (int) Math.floor((k * (1 + Math.sin(alpha))));
 		lam_negative_index = new double[gam_min + 1];
 		gam_negative_index = new Complex[gam_min + 1];
		gam_max =   (int) Math.floor( (k * (1 - Math.sin(alpha))));
 		lam_positive_and_0_index = new double[gam_max + 1];
 		gam_positive_and_0_index = new Complex[gam_max + 1];

 		for (int i = 1; i <= gam_min; i++) {
 			double lamCurrent = k * Math.sin(alpha) - i;
 			lam_negative_index[i] = lamCurrent; 
 			gam_negative_index[i] = new Complex(Math.sqrt(k*k - lamCurrent * lamCurrent), 0.0  );
 		}
 		
			double lamNull = k * Math.sin(alpha);
 			lam_positive_and_0_index[0] = lamNull; 
 			gam_positive_and_0_index[0] = new Complex(Math.sqrt(k*k - lamNull * lamNull), 0.0  );
 		
 		
 		for (int i = 1; i <= gam_max; i++) {
 			double lamCurrent = k * Math.sin(alpha) + i;
 			lam_positive_and_0_index[i] = lamCurrent; 
 			gam_positive_and_0_index[i] = new Complex(Math.sqrt(k*k - lamCurrent * lamCurrent), 0.0  );
 		}
 		
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
	
	protected Complex gam(int n){
		if (n >= 0 ){
			return gam_positive_and_0_index[n];
		} else {
			return gam_negative_index[-n];
		}
	}
	protected double lam(int n){
		if (n >= 0 ){
			return lam_positive_and_0_index[n];
		} else {
			return lam_negative_index[-n];
		}
	}
}
