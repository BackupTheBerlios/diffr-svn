package de.berlios.diffr.result;

import de.berlios.diffr.*;
import de.berlios.diffr.algorithms.DimensionData;

public class Result extends Model {
	private static final long serialVersionUID = 1L;
	private ReflectedField reflectedField;
	private SurfaceCurrent surfaceCurrent;
	private PassedField passedField;
	private double energeticImperfection;
	public Result(ReflectedField reflectedField, PassedField passedField, SurfaceCurrent surfaceCurrent, double energeticImperfection) {
		this.reflectedField = reflectedField;
		this.passedField = passedField;
		this.surfaceCurrent = surfaceCurrent;
		this.energeticImperfection = energeticImperfection;
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
	public double getEnergeticImperfection() {
		return energeticImperfection;
	}
	public Result dimensioning(DimensionData d) {
		ReflectedField dimensionalReflectedField = reflectedField.dimensioning(d);
		SurfaceCurrent dimensionalSurfaceCurrent = null;
		PassedField dimensionalPassedField = null;
		if ( surfaceCurrent != null)
			dimensionalSurfaceCurrent = surfaceCurrent.dimensioning(d);
		if ( passedField != null)
			dimensionalPassedField = passedField.dimensioning(d);
		return 	new Result(dimensionalReflectedField, dimensionalPassedField, dimensionalSurfaceCurrent, energeticImperfection);
	}
}
