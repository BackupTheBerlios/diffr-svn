package de.berlios.diffr.result;

import de.berlios.diffr.*;
import de.berlios.diffr.algorithms.DimensionData;

public class Result extends Model {
	private static final long serialVersionUID = 1L;
	private ReflectedField reflectedField;
	private SurfaceCurrent surfaceCurrent;
	private RefractedField refractedField;
	private double energyError;
	public Result(ReflectedField reflectedField, RefractedField refractedField, SurfaceCurrent surfaceCurrent) {
		this.reflectedField = reflectedField;
		this.refractedField = refractedField;
		this.surfaceCurrent = surfaceCurrent;
		this.energyError = calculateEnergyError();
	}
	
	private double calculateEnergyError() {
		if (reflectedField != null) {
			double sum = 0;
			for (ReflectedPlaneWave w : reflectedField.getWaves())
				sum += w.getAmplitude().abs()*w.getAmplitude().abs()*Math.cos(w.getAngle());
			return sum / Math.cos(reflectedField.getIncidentWave().getAngle()) - 1;
		} else return -1;
	}
	
	public ReflectedField getReflectedField() {
		return reflectedField;
	}
	public SurfaceCurrent getSurfaceCurrent() {
		return surfaceCurrent;
	}
	public RefractedField getPassedField() {
		return refractedField;
	}
	public double getEnergyError() {
		return energyError;
	}
	public Result dimensioning(DimensionData d) {
		ReflectedField dimensionalReflectedField = reflectedField.dimensioning(d);
		SurfaceCurrent dimensionalSurfaceCurrent = null;
		RefractedField dimensionalPassedField = null;
		if ( surfaceCurrent != null)
			dimensionalSurfaceCurrent = surfaceCurrent.dimensioning(d);
		if ( refractedField != null)
			dimensionalPassedField = refractedField.dimensioning(d);
		return new Result(dimensionalReflectedField, dimensionalPassedField, dimensionalSurfaceCurrent);
	}
}
