package de.berlios.diffr.algorithms.addedAlgorithms;

import de.berlios.diffr.algorithms.*;
import de.berlios.diffr.exceptions.InputDataNotSupportedException;
import de.berlios.diffr.inputData.*;
import de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface.ImpingingPlaneWave;
import de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface.periodicSurface.*;
import de.berlios.diffr.result.Result;
import de.berlios.diffr.*;

public class AlgorithmPattern extends Algorithm {
	public AlgorithmPattern(AlgorithmType algorithmType) {
		super(algorithmType);
		parameters = new DataString[2];
		parameters[0] = new DataString("parameter1", new Long(2));
		parameters[1] = new DataString("parameter2", new Double(0.2));
	}
	public Algorithm clone() {
		AlgorithmPattern r = new AlgorithmPattern(super.getAlgorithmType());
		r.parameters = parameters.clone();
		return r;
	}
	
	public Result run(InputData inputData) throws InputDataNotSupportedException {
		PeriodicSurface surface = (PeriodicSurface)inputData.getSurface();
		ImpingingPlaneWave impingingField = (ImpingingPlaneWave)inputData.getImpingingField();
		double k = ((FourierCoefficient)surface.getShape().getFourierCoefficients().get(0)).getCoefficientOfCosinus();
		return null;//temporary
	}
}
