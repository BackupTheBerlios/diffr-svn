package de.berlios.diffr.result.resultForVerySimpleTask;

import de.berlios.diffr.result.*;

public class ReflectedWave extends ReflectedField {
	private double angle;
	public ReflectedWave(double angle) {
		this.angle = angle;
	}
	public double getAngle() {
		return angle;
	}
}
