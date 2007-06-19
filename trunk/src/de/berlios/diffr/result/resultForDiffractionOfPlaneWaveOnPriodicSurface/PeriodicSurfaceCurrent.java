package de.berlios.diffr.result.resultForDiffractionOfPlaneWaveOnPriodicSurface;

import Org.netlib.math.complex.Complex;
import de.berlios.diffr.inputData.InputData;
import de.berlios.diffr.result.SurfaceCurrent;

public class PeriodicSurfaceCurrent extends SurfaceCurrent {
	private Complex[] points;
	private double surfacePeriod;
	public PeriodicSurfaceCurrent(Complex[] points, double period) {
		this.points = points;
		this.surfacePeriod = period;
	}
	public Complex get(int number) {
		return points[number];
	}
	public int getPointsNumber() {
		return points.length;
	}
	public double getSurfacePeriod() {
		return surfacePeriod;
	}
	public SurfaceCurrent dimensioning(InputData inputData) {
		// TODO Auto-generated method stub
		return null;
	}
}
