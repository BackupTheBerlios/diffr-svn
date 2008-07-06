package de.berlios.diffr.algorithms.addedAlgorithms;

import java.util.ArrayList;

import Org.netlib.math.complex.Complex;
import de.berlios.diffr.inputData.*;
import de.berlios.diffr.inputData.surface.*;

public abstract class AlgorithmBase  {

	protected double k;
	protected double alpha;
	protected double waveLength;
	protected double lamNull;
	protected boolean polarization;
	protected Complex h = null;
	protected Complex amplitude;
	private Complex [] f_negative_index;
	private Complex [] f_positive_and_0_index;
	private ArrayList f_coef;
	private double shift;
	protected int f_size;
	protected IncidentWave wave; 
	
	public void initialize (InputData inputData){
//				System.out.println("initialization started");
		Surface surface = inputData.getSurface();
		wave = inputData.getIncidentWave();
		polarization = wave.getPolarization();
//				System.out.println("polarization = " + polarization);
		amplitude = wave.getAmplitude();
//				System.out.println("amplitude re = " + amplitude.re() + "   amplitude im = " + amplitude.im());
		waveLength = wave.getLength();
//				System.out.println("waveLength = " + waveLength);
		k = surface.getShape().getPeriod() / waveLength; 
//				System.out.println("k = " + k);
		alpha = wave.getAngle();
//				System.out.println("alpha = " + alpha);
		lamNull = k * Math.sin(alpha);
//				System.out.println("lamNull = " + lamNull);
 		f_size = ( surface.getShape().getFourierCoefficients()).size()-1;
// 				System.out.println("f_size = " + f_size);
 		f_negative_index = new Complex[f_size + 1];
 		f_positive_and_0_index = new Complex[f_size + 1];
 		double shiftDimensionless = surface.getShape().getShift();
// 				System.out.println("shiftDimensionless = " + shiftDimensionless);
 		f_positive_and_0_index[0] = new Complex(shiftDimensionless,0.0);
 		f_coef = surface.getShape().getFourierCoefficients();
 		shift =  surface.getShape().getShift();

		if ( surface.getConductivity() instanceof ZeroConductivity ) {
			Complex epsilon = ((ZeroConductivity) surface.getConductivity()).getEpsilon();
			Complex impedance = ((new Complex(1.0,0.0)).div(epsilon)).sqrt();
			if (wave.getPolarization() == IncidentWave.polarizationE) {
				h = Complex.i.mul(-1.0).mul(impedance).div(k);
			}else {
				h = Complex.i.mul(impedance).mul(k);
			}
		}

 		
 		if ( f_size > 0){
 			for (int i = 1; i <= f_size; i++) {
// 				System.out.println("f_size cycle " );
 				double reDimensionless = ( (FourierCoefficient) surface.getShape().getFourierCoefficients().get(i)).getCoefficientOfCosinus() / 2.0;
 			
 				double imDimensionless = - ( (FourierCoefficient) surface.getShape().getFourierCoefficients().get(i)).getCoefficientOfSinus() / 2.0;
 			
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
			if ( n <= f_size ){
				return f_positive_and_0_index[n];
			} else {
				return new Complex (0.0, 0.0);
			}
		} else {
			if ( n >= -f_size ){
				return f_negative_index[-n];
			} else {
				return new Complex (0.0, 0.0);
			}
		}
	}

	protected double delta(int n, int m) {
		if ( n == m) return 1.0;
		else return 0.0;
	}

	
	protected abstract Complex calculateNonDimensionalAmplitude(int n, int order);

	protected Complex calculateAmplitude(int n, int order){
		return calculateNonDimensionalAmplitude(n, order);
	}

	public double f_surface(double x){
		double s = shift;
		for (int j =0; j< f_coef.size(); j++){
			s = s + ((FourierCoefficient)f_coef.get(j)).getCoefficientOfCosinus() * Math.cos((j+1)*x);
			s = s + ((FourierCoefficient)f_coef.get(j)).getCoefficientOfSinus() * Math.sin((j+1)*x);
		}
		return s;
	}

	public double derivative_f_surface(double x){
		double s = 0;
		for (int j =0; j< f_coef.size(); j++){
			s = s + (-((FourierCoefficient)f_coef.get(j)).getCoefficientOfCosinus()) * Math.sin((j+1)*x)*(j+1);
			s = s + ((FourierCoefficient)f_coef.get(j)).getCoefficientOfSinus() * Math.cos((j+1)*x)*(j+1);
		}
		return s;
	}

	public double n_y(double y){
		double fd = derivative_f_surface(y);
		double s = Math.sqrt(1.0+fd*fd);
		return -fd/s;
	}
	
	public double n_x(double y){
		double fd = derivative_f_surface(y);
		double s = Math.sqrt(1.0+fd*fd);
		return 1.0/s;
	}
}