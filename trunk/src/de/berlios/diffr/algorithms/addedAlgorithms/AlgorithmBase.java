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
	private int f_min;
	private int f_max;
	private int gam_min;
	private int gam_max;
	
	public void initialize (InputData inputData){
		PeriodicSurface surface = (PeriodicSurface) inputData.getSurface();
		ImpingingPlaneWave wave = (ImpingingPlaneWave) inputData.getImpingingField();
		( (FourierCoefficient) surface.getShape().getFourierCoefficients().get(1)).getCoefficientOfSinis();// pattern
	}
	
	protected Complex gam(int n){
		if (n >= 0 ){
			return gam_positive_and_0_index[n];
		} else {
			return gam_negative_index[n];
		}
	}
	protected double lam(int n){
		if (n >= 0 ){
			return lam_positive_and_0_index[n];
		} else {
			return lam_negative_index[n];
		}
	}
}
