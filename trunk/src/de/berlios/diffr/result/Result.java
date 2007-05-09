package de.berlios.diffr.result;

import de.berlios.diffr.*;

public class Result extends Model {
	private ReflectedField reflectedField;
	private SurfaceCurrent surfaceCurrent;
	public Result(ReflectedField field, SurfaceCurrent surfaceCurrent) {
		this.reflectedField = field;
		this.surfaceCurrent = surfaceCurrent;
	}
	public ReflectedField getReflectedField() {
		return reflectedField;
	}
	public SurfaceCurrent getSurfaceCurrent() {
		return surfaceCurrent;
	}
}
