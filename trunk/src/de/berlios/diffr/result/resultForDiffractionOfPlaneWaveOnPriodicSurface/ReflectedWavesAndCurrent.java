package de.berlios.diffr.result.resultForDiffractionOfPlaneWaveOnPriodicSurface;

import de.berlios.diffr.result.*;

public class ReflectedWavesAndCurrent extends ReflectedField {
	private ReflectedPlaneWave[] waves;
	public ReflectedWavesAndCurrent(ReflectedPlaneWave[] waves) {
		this.waves = waves;
	}
	public ReflectedPlaneWave[] getWaves() {
		return (ReflectedPlaneWave[]) waves.clone();
	}
}