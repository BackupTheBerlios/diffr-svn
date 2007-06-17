package de.berlios.diffr.result;

import de.berlios.diffr.*;
import de.berlios.diffr.inputData.InputData;
import de.berlios.diffr.result.resultForDiffractionOfPlaneWaveOnPriodicSurface.ReflectedFieldOfPlaneWaves;

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
	public Result dimensioning(InputData inputData) {
		ReflectedFieldOfPlaneWaves dimensionalReflectedField = ((ReflectedFieldOfPlaneWaves)reflectedField).dimensioning(inputData);
		SurfaceCurrent dimensionalSurfaceCurrent = null;
		PassedField dimensionalPassedField = null;
		if ( surfaceCurrent != null)
			dimensionalSurfaceCurrent = surfaceCurrent.dimensioning(inputData);
		if ( passedField != null)
			dimensionalPassedField = passedField.dimensioning(inputData);
		return 	new Result(dimensionalReflectedField, dimensionalPassedField, dimensionalSurfaceCurrent);
	}
}
