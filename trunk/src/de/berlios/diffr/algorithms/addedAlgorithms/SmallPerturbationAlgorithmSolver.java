package de.berlios.diffr.algorithms.addedAlgorithms;

import Org.netlib.math.complex.Complex;
import de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface.ImpingingPlaneWave;
import de.berlios.diffr.result.resultForDiffractionOfPlaneWaveOnPriodicSurface.ReflectedFieldOfPlaneWaves;
import de.berlios.diffr.result.resultForDiffractionOfPlaneWaveOnPriodicSurface.ReflectedPlaneWave;
import de.berlios.diffr.result.*;

public abstract class SmallPerturbationAlgorithmSolver extends AlgorithmBase {

	protected int gam_min;
	protected int gam_max;
	protected ReflectedPlaneWave[] waves = null;
	
	public Result solve(int order, int numberOfPoints) {
//			System.out.println("SmallPerturbationAlgorithmSolver start");
		gam_min = (int) Math.floor((k * (1 + Math.sin(alpha))));
			System.out.println("gam_min = " + gam_min);
		gam_max =   (int) Math.floor( (k * (1 - Math.sin(alpha))));
//			System.out.println("gam_max =  " + gam_max);
		waves = new ReflectedPlaneWave[gam_min + gam_max + 1];
		int counter = 0;
		for (int j = -gam_min; j<= gam_max; j++){
//				System.out.println("j =  " + j);
			waves[counter] = new ReflectedPlaneWave(polarization, calculateAngle(j), waveLength, calculateAmplitude(j,order),j);
			counter++;
/*			Complex r = calculateAmplitude(j,order);
				System.out.println("Amplitude re =  " + r.re() + "   im = " + r.im());*/
		}
//			System.out.println("waves.length =  " + waves.length);
		ReflectedFieldOfPlaneWaves reflectedField = new ReflectedFieldOfPlaneWaves(waves, new ImpingingPlaneWave(polarization, alpha, waveLength, amplitude));
//			System.out.println("solver end");
		
		SurfaceCurrent surfaceCurrent = calculateSurfaceCurrent(numberOfPoints);

		
		double energyError = calculateEnergyError();
			
		return new Result(reflectedField, null, surfaceCurrent, energyError);
	}

	public Complex normalDerivativeOfField(double y) {
		Complex s = (Complex.i.mul(k).mul(f_surface(y)*Math.cos(alpha) + y*Math.sin(alpha))).exp();
		s = s.mul(Complex.i).mul(k).mul(n_x(y)*Math.cos(alpha) + n_y(y)*Math.sin(alpha));
		for (int j = 0; j < waves.length; j++) {
			int n = waves[j].getNumber();
			Complex s1 = (waves[j].getAmplitude()).mul
			(     
					(Complex.i.mul(-1.0).mul(gam(n)).mul(f_surface(y))).exp().mul
					(
							(Complex.i.mul(lam(n)).mul(y)).exp()
					)
			);
			s1 = s1.mul(Complex.i).mul(
					gam(n).mul(n_x(y)).add(
							(lam(n)*n_y(y)) )
			);		
			s = s.add(s1);
		}
		return s;
	}

	public Complex field(double y, double x) {
		Complex s = (Complex.i.mul(k).mul(x*Math.cos(alpha) + y*Math.sin(alpha))).exp();
		for (int j = 0; j < waves.length; j++) {
			int n = waves[j].getNumber();
			s = s.add((waves[j].getAmplitude()).mul(     
				(Complex.i.mul(-1.0).mul(gam(n)).mul(x)).exp().mul(
						(Complex.i.mul(lam(n)).mul(y)).exp()	
				)
			));
		}
		return s;
	}
		public abstract SurfaceCurrent calculateSurfaceCurrent(int numberOfPoints);

	public abstract Complex calculateNonDimensionalAmplitude(int n, int order);
	public abstract double calculateEnergyError();
	public abstract Complex term1(int n);
	public abstract Complex term2(int n);
}
