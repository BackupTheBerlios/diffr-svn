package de.berlios.diffr.algorithms.addedAlgorithms;

import Org.netlib.math.complex.Complex;
import de.berlios.diffr.inputData.*;
import de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface.*;
import de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface.periodicSurface.*;
import de.berlios.diffr.result.resultForDiffractionOfPlaneWaveOnPriodicSurface.ReflectedPlaneWave;

public abstract class AlgorithmBase {

	protected double k;
	protected double alpha;
	protected double waveLength;
	protected double lamNull;
	protected boolean polarization;
	private Complex amplitude;
	private Complex [] f_negative_index;
	private Complex [] f_positive_and_0_index;
	protected int f_size;
	
	public void initialize (InputData inputData){
		System.out.println("initialization started");
		PeriodicSurface surface = (PeriodicSurface) inputData.getSurface();
		ImpingingPlaneWave wave = (ImpingingPlaneWave) inputData.getImpingingField();
		polarization = wave.getPolarization();
		System.out.println("polarization = " + polarization);
		amplitude = wave.getAmplitude();
		System.out.println("amplitude re = " + amplitude.re() + "amplitude re = " + amplitude.im());
		waveLength = wave.getLength();
		System.out.println("waveLength = " + waveLength);
		k = surface.getShape().getPeriod() / waveLength; 
		System.out.println("k = " + k);
		alpha = wave.getAngle();
		System.out.println("alpha = " + alpha);
		lamNull = k * Math.sin(alpha);
		System.out.println("lamNull = " + lamNull);
 		f_size = ( surface.getShape().getFourierCoefficients()).size()-1;
		System.out.println("f_size = " + f_size);
 		f_negative_index = new Complex[f_size + 1];
		System.out.println("f_negative_index = " + f_negative_index);
 		f_positive_and_0_index = new Complex[f_size + 1];
		System.out.println("f_positive_and_0_index = " + f_positive_and_0_index);
 		double shiftDimension = surface.getShape().getShift();
 		double shiftDimensionless = shiftDimension * 2.0 * Math.PI / surface.getShape().getPeriod(); 
		System.out.println("shiftDimensionless = " + shiftDimensionless);
 		f_positive_and_0_index[0] = new Complex(shiftDimensionless,0.0);
 		
 		if ( f_size > 0){
 			for (int i = 1; i <= f_size; i++) {
 				System.out.println("f_size cycle " );
 				double reDimension = ( (FourierCoefficient) surface.getShape().getFourierCoefficients().get(i)).getCoefficientOfCosinis() / 2.0;
 				double reDimensionless = reDimension * 2.0 * Math.PI / surface.getShape().getPeriod();
 			
 				double imDimension = - ( (FourierCoefficient) surface.getShape().getFourierCoefficients().get(i)).getCoefficientOfSinis() / 2.0;
 				double imDimensionless = imDimension * 2.0 * Math.PI / surface.getShape().getPeriod();
 			
 				f_negative_index[i] = new Complex(reDimensionless,-imDimensionless);
 				f_positive_and_0_index[i] = new Complex(reDimensionless,imDimensionless);
 			}
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

	protected double gam0sqweared(){
		return k*k - lamNull*lamNull;
	}

	protected double calculateAngle(int n) {
		return Math.atan(lam(n)/(gam(n).re()));
	}

	protected Complex f(int n){
		if ( n >= 0 ){
			return f_positive_and_0_index[n];
		} else {
			return f_negative_index[-n];
		}
	}

	protected double delta(int n, int m) {
		if ( n == m) return 1.0;
		else return 0.0;
	}

	
	protected abstract Complex calculateNonDimensionalAmplitude(int n);

	protected Complex calculateAmplitude(int n){
		return calculateNonDimensionalAmplitude(n).mul(amplitude);
	}
}