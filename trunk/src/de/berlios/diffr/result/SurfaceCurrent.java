package de.berlios.diffr.result;

import Org.netlib.math.complex.Complex;
import de.berlios.diffr.*;
import de.berlios.diffr.algorithms.DimensionData;
import de.berlios.diffr.inputData.InputData;

public class SurfaceCurrent extends Model {
	private Complex[] points;
	private double surfacePeriod;
	public SurfaceCurrent(Complex[] points, double period) {
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
	public SurfaceCurrent dimensioning(DimensionData d) {
		for (int j = 0; j < points.length; j++){
			points[j] = points[j].mul(d.getAmplitude());
		}
		surfacePeriod = d.getPeriod();
		return this;
	}
}
