package de.berlios.diffr.inputData.surface;

public class PerfectConductivity extends SurfaceConductivity {
	private static final long serialVersionUID = 1L;
	public SurfaceConductivity nonDimensioning() {
		return new PerfectConductivity();
	}
}
