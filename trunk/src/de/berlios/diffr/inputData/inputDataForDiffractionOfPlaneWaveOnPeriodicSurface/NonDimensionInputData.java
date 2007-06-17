package de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface;

import de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface.periodicSurface.*;

public class NonDimensionInputData {
	public PeriodicSurface surface;
	public ImpingingPlaneWave wave;
	public NonDimensionInputData(PeriodicSurface surface, ImpingingPlaneWave wave) {
		this.surface = surface;
		this.wave = wave;
	}
	public PeriodicSurface getSurface() {
		return surface;
	}
	public ImpingingPlaneWave getImpingingField() {
		return wave;
	}
}
