package de.berlios.diffr.result;

import Org.netlib.math.complex.Complex;
import de.berlios.diffr.*;
import de.berlios.diffr.algorithms.DimensionData;
import de.berlios.diffr.exceptions.ObjectIsnotEditableException;
import de.berlios.diffr.inputData.IncidentWave;
import de.berlios.diffr.inputData.InputData;

public class ReflectedField extends Model {
	private ReflectedPlaneWave[] waves;
	private IncidentWave incidentWave;
	public ReflectedField(ReflectedPlaneWave[] waves, IncidentWave incidentPlaneWave) {
		this.incidentWave = incidentPlaneWave;
		this.waves = waves;
	}
	public ReflectedPlaneWave[] getWaves() {
		return (ReflectedPlaneWave[]) waves.clone();
	}
	public IncidentWave getIncidentWave() {
		return incidentWave;
	}
	public ReflectedField dimensioning(DimensionData dimensionData) {
		ReflectedPlaneWave[] dimensionWaves = new ReflectedPlaneWave[waves.length];
		double period = dimensionData.getPeriod();
		Complex incidentWaveAmplitude = dimensionData.getAmplitude();
		for (int j=0; j< waves.length; j++) {
			dimensionWaves[j] = waves[j].dimensioning(period, incidentWaveAmplitude);
		}
		
		IncidentWave dimensionIncidentWave = incidentWave.clone();
		try {
			dimensionIncidentWave.setAmplitude(dimensionData.getAmplitude());
		} catch (ObjectIsnotEditableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ReflectedField(dimensionWaves, dimensionIncidentWave);
	}
}
