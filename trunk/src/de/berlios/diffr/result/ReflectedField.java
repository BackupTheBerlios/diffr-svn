package de.berlios.diffr.result;

import Org.netlib.math.complex.Complex;
import de.berlios.diffr.*;
import de.berlios.diffr.inputData.ImpingingField;
import de.berlios.diffr.inputData.InputData;

public class ReflectedField extends Model {
	private ReflectedPlaneWave[] waves;
	private ImpingingField impingingWave;
	public ReflectedField(ReflectedPlaneWave[] waves, ImpingingField impingingPlaneWave) {
		this.impingingWave = impingingPlaneWave;
		this.waves = waves;
	}
	public ReflectedPlaneWave[] getWaves() {
		return (ReflectedPlaneWave[]) waves.clone();
	}
	public ImpingingField getImpingingWave() {
		return impingingWave;
	}
	public ReflectedField dimensioning(InputData inputData) {
		ReflectedPlaneWave[] dimensionWaves = new ReflectedPlaneWave[waves.length];
		double period = inputData.getSurface().getShape().getPeriod();
		Complex impingingWaveAmplitude = inputData.getImpingingField().getAmplitude();
		for (int j=0; j< waves.length; j++) {
			dimensionWaves[j] = waves[j].dimensioning(period, impingingWaveAmplitude);
		}
		
		ImpingingField dimensionImpingingWave = inputData.getImpingingField();
		return new ReflectedField(dimensionWaves, dimensionImpingingWave);
	}
}
