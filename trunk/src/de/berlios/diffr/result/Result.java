package de.berlios.diffr.result;

import de.berlios.diffr.*;
import de.berlios.diffr.algorithms.DimensionData;
import de.berlios.diffr.inputData.InputData;

public class Result extends Model {
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
		/*ReflectedField dimensionalReflectedField = reflectedField.dimensioning(inputData);
		SurfaceCurrent dimensionalSurfaceCurrent = null;
		PassedField dimensionalPassedField = null;
		if ( surfaceCurrent != null)
			dimensionalSurfaceCurrent = surfaceCurrent.dimensioning(inputData);
		if ( passedField != null)
			dimensionalPassedField = passedField.dimensioning(inputData);
		return 	new Result(dimensionalReflectedField, dimensionalPassedField, dimensionalSurfaceCurrent, energeticImperfection);*/
		return null;
	}
}
