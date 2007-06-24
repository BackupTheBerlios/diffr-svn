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
	
	public Result solve() {
			System.out.println("SmallPerturbationAlgorithmSolver start");
		gam_min = (int) Math.floor((k * (1 + Math.sin(alpha))));
			System.out.println("gam_min = " + gam_min);
		gam_max =   (int) Math.floor( (k * (1 - Math.sin(alpha))));
			System.out.println("gam_max =  " + gam_max);
		waves = new ReflectedPlaneWave[gam_min + gam_max + 1];
		int counter = 0;
		for (int j = -gam_min; j<= gam_max; j++){
				System.out.println("j =  " + j);
			waves[counter] = new ReflectedPlaneWave(polarization, calculateAngle(j), waveLength, calculateAmplitude(j));
			counter++;
			Complex r = calculateAmplitude(j);
				System.out.println("Amplitude re =  " + r.re() + "   im = " + r.im());
		}
			System.out.println("waves.length =  " + waves.length);
		ReflectedFieldOfPlaneWaves reflectedField = new ReflectedFieldOfPlaneWaves(waves, new ImpingingPlaneWave(polarization, alpha, waveLength, amplitude));
			System.out.println("solver end");
			
		double energyError = calculateEnergyError();
			
		return new Result(reflectedField, null, null, energyError);
	}

	public abstract Complex calculateNonDimensionalAmplitude(int n);
	public abstract double calculateEnergyError();
	public abstract Complex term1(int n);
	public abstract Complex term2(int n);
}
