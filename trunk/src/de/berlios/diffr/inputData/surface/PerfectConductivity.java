package de.berlios.diffr.inputData.surface;

public class PerfectConductivity extends SurfaceConductivity {

	public SurfaceConductivity nonDimensioning() {
		return new PerfectConductivity();
	}
}
