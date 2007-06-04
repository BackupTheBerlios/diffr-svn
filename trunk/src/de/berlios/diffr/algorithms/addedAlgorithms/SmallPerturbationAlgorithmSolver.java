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
			counter++;
			Complex r = calculateAmplitude(j);
				System.out.println("Amplitude re =  " + r.re() + "   im = " + r.im());
		}
			System.out.println("waves.length =  " + waves.length);
		ReflectedFieldOfPlaneWaves reflectedField = new ReflectedFieldOfPlaneWaves(waves);
			System.out.println("solver end");
		return new Result(reflectedField, null, null);
	}

	protected Complex calculateNonDimensionalAmplitude(int n) {
		if ( polarization == ReflectedPlaneWave.polarizationE ) {
			return term1(n).add(-delta(n,0));
		} else {
			return term1(n).add(delta(n,0));
		}
	}

	private Complex term1(int n) {
		if ( polarization == ReflectedPlaneWave.polarizationE ) {
			return Complex.i.mul(-2.0).mul(gam(0)).mul(f(n));
		} else {
			return ( Complex.i.mul(2.0).mul( gam0sqweared()-lamNull*n ).div(gam(n)) ).mul(f(n));
		}
	}

	private Complex term2(int n) {
		Complex s = new Complex(0.0, 0.0);
		for (int m = -f_size; m <= f_size; m++){
			if ( polarization == ReflectedPlaneWave.polarizationE ) {
				s = s.add( gam(m).mul(f(m)).mul(f(n-m)) );
			} else {
				s = s.add((f(m).mul(f(n-m)).mul(-2*(gam0sqweared()-lamNull*n)).div(gam(m).mul(gam(n)))).mul(gam0sqweared()-lamNull*m-lam(m)*n)  );  //  ( Complex.i.mul(2.0).mul( gam0sqweared()-lamNull*n ).div(gam(n)) ).mul(f(n));
			}
		}
		if ( polarization == ReflectedPlaneWave.polarizationE ) {
			s = s.mul(2.0).mul(gam(0));
		}
		return s;
	}

}
