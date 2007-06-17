package de.berlios.diffr.result.resultForDiffractionOfPlaneWaveOnPriodicSurface;

import Org.netlib.math.complex.Complex;
import de.berlios.diffr.inputData.InputData;
import de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface.ImpingingPlaneWave;
import de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface.periodicSurface.PeriodicSurface;
import de.berlios.diffr.result.*;

public class ReflectedFieldOfPlaneWaves extends ReflectedField {
	private ReflectedPlaneWave[] waves;
	private ImpingingPlaneWave impingingWave;
	public ReflectedFieldOfPlaneWaves(ReflectedPlaneWave[] waves, ImpingingPlaneWave impingingPlaneWave) {
		this.impingingWave = impingingPlaneWave;
		this.waves = waves;
	}
	public ReflectedPlaneWave[] getWaves() {
		return (ReflectedPlaneWave[]) waves.clone();
	}
	public ImpingingPlaneWave getImpingingWave() {
		return (ImpingingPlaneWave) impingingWave;
	}
	public ReflectedFieldOfPlaneWaves dimensioning(InputData inputData) {
		ReflectedPlaneWave[] dimensionWaves = new ReflectedPlaneWave[waves.length];
		double period = ( (PeriodicSurface) inputData.getSurface()).getShape().getPeriod();
		Complex impingingWaveAmplitude = ( (ImpingingPlaneWave) inputData.getImpingingField()).getAmplitude();
		for (int j=0; j< waves.length; j++) {
			dimensionWaves[j] = waves[j].dimensioning(period, impingingWaveAmplitude);
		}
		
		ImpingingPlaneWave dimensionImpingingWave = (ImpingingPlaneWave ) inputData.getImpingingField();
		return new ReflectedFieldOfPlaneWaves(dimensionWaves, dimensionImpingingWave);
	}
}
