package de.berlios.diffr.inputData.surface;

import java.io.Serializable;

public class FourierCoefficient implements Serializable {
	private static final long serialVersionUID = 1L;
	private double coefficientOfCosinus;
	private double coefficientOfSinus;
	public FourierCoefficient(double coefficientOfCosinus, double coefficientOfSinus) {
		this.coefficientOfCosinus = coefficientOfCosinus;
		this.coefficientOfSinus = coefficientOfSinus;
	}
	public double getCoefficientOfCosinus() {
		return coefficientOfCosinus;
	}
	public double getCoefficientOfSinus() {
		return coefficientOfSinus;
	}
}