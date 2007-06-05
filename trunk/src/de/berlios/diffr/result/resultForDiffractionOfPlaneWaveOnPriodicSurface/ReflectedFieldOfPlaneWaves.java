package de.berlios.diffr.result.resultForDiffractionOfPlaneWaveOnPriodicSurface;

import de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface.ImpingingPlaneWave;
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
}
