package de.berlios.diffr.result;

import de.berlios.diffr.*;

public class Result extends Model {
	private ReflectedField reflectedField;
	private SurfaceCurrent surfaceCurrent;
	private PassedField passedField;
	public Result(ReflectedField reflectedField, PassedField passedField, SurfaceCurrent surfaceCurrent) {
		this.reflectedField = reflectedField;
		this.passedField = passedField;
		this.surfaceCurrent = surfaceCurrent;
	}
	public ReflectedField getReflectedField() {
		return reflectedField;
	}
	public SurfaceCurrent getSurfaceCurrent() {
		return surfaceCurrent;
	}
	public PassedField getPassedField() {
		return passedField;
	}
}
