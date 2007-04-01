/**
 * 
 */
package de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface.periodicSurface;

public class FourierCoefficient {
	/**
	 * 
	 */
	private final SurfaceShape FourierCoefficient;
	private double coefficientOfCosinus;
	private double coefficientOfSinus;
	public FourierCoefficient(SurfaceShape shape, double coefficientOfCosinus, double coefficientOfSinus) {
		FourierCoefficient = shape;
		this.coefficientOfCosinus = coefficientOfCosinus;
		this.coefficientOfSinus = coefficientOfSinus;
	}
	public double getCoefficientOfCosinis() {
		return coefficientOfCosinus;
	}
	public double getCoefficientOfSinis() {
		return coefficientOfSinus;
	}
}