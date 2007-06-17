package de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface.periodicSurface;

public class PerfectConductivity extends SurfaceConductivity {

	public SurfaceConductivity nonDimensioning() {
		return new PerfectConductivity();
	}
}
