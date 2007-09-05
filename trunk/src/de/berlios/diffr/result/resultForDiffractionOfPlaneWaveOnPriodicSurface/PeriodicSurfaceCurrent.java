package de.berlios.diffr.result.resultForDiffractionOfPlaneWaveOnPriodicSurface;

import Org.netlib.math.complex.Complex;
import de.berlios.diffr.inputData.InputData;
import de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface.ImpingingPlaneWave;
import de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface.periodicSurface.PeriodicSurface;
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
		for (int j = 0; j < points.length; j++){
			points[j] = points[j].mul(((ImpingingPlaneWave)inputData.getImpingingField()).getAmplitude());
		}
		surfacePeriod = ((PeriodicSurface)inputData.getSurface()).getShape().getPeriod();
		return this;
	}
}
