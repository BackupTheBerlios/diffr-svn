package de.berlios.diffr.result.resultForDiffractionOfPlaneWaveOnPriodicSurface;

import de.berlios.diffr.result.*;

public class ReflectedFieldOfPlaneWaves extends ReflectedField {
	private ReflectedPlaneWave[] waves;
	public ReflectedFieldOfPlaneWaves(ReflectedPlaneWave[] waves) {
		this.waves = waves;
	}
	public ReflectedPlaneWave[] getWaves() {
		return (ReflectedPlaneWave[]) waves.clone();
	}
}
