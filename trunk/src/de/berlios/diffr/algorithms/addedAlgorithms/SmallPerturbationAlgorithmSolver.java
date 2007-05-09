package de.berlios.diffr.algorithms.addedAlgorithms;

import Org.netlib.math.complex.Complex;
import de.berlios.diffr.result.resultForDiffractionOfPlaneWaveOnPriodicSurface.ReflectedFieldOfPlaneWaves;
import de.berlios.diffr.result.resultForDiffractionOfPlaneWaveOnPriodicSurface.ReflectedPlaneWave;
import de.berlios.diffr.result.*;

public class SmallPerturbationAlgorithmSolver extends AlgorithmBase {

	private int gam_min;
	private int gam_max;
	
	public Result solve() {
			System.out.println("SmallPerturbationAlgorithmSolver start");
		gam_min = (int) Math.floor((k * (1 + Math.sin(alpha))));
			System.out.println("gam_min = " + gam_min);
		gam_max =   (int) Math.floor( (k * (1 - Math.sin(alpha))));
			System.out.println("gam_max =  " + gam_max);
		ReflectedPlaneWave[] waves = new ReflectedPlaneWave[gam_min + gam_max + 1];
		int counter = 0;
		for (int j = -gam_min; j<= gam_max; j++){
				System.out.println("j =  " + j);
			waves[counter] = new ReflectedPlaneWave(polarization, calculateAngle(j), waveLength, calculateAmplitude(j));
				Complex r = calculateAmplitude(j);
				System.out.println("Amplitude re =  " + r.re() + "   im = " + r.im());
		}
			System.out.println("waves.length =  " + waves.length);
		ReflectedFieldOfPlaneWaves reflectedField = new ReflectedFieldOfPlaneWaves(waves);
			System.out.println("solver end");
		return new Result(reflectedField, null, null);
	}

	protected Complex calculateNonDimensionalAmplitude(int n) {
		if ( n< -f_size | n > f_size ) return new Complex (0.0, 0.0);
		if ( polarization == ReflectedPlaneWave.polarizationE ) return Complex.i.mul(-2.0).mul(gam(0)).mul(f(n)).add(-delta(n,0));
		return ( Complex.i.mul(2.0).mul( gam0sqweared()-lamNull*n ).div(gam(n)) ).mul(f(n)).add(delta(n,0));
	}

}
