package de.berlios.diffr.algorithms.addedAlgorithms;

import de.berlios.diffr.DataString;
import de.berlios.diffr.algorithms.Algorithm;
import de.berlios.diffr.algorithms.AlgorithmType;
import de.berlios.diffr.exceptions.ErrorInAlgorithmException;
import de.berlios.diffr.inputData.ImpingingField;
import de.berlios.diffr.inputData.InputData;
import de.berlios.diffr.inputData.Surface;
import de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface.*;
import de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface.periodicSurface.PeriodicSurface;
import de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface.periodicSurface.SurfaceConductivity;
import de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface.periodicSurface.SurfaceShape;
import de.berlios.diffr.result.Result;

public abstract class AbstractAlgorithmForDiffrractionOfPlaneWaveOnPeriodicSurface
		extends Algorithm {
	public AbstractAlgorithmForDiffrractionOfPlaneWaveOnPeriodicSurface(AlgorithmType algorithmType) {
		super(algorithmType);
	}

	public Result run(InputData inputData) throws ErrorInAlgorithmException {
		NonDimensionInputData data = nonDimensioning(inputData);
		Result result = calculate(data);
		return dimensioning(result, inputData);
	}
	private Result dimensioning(Result result, InputData inputData) {
		return result.dimensioning(inputData);
	}

	private NonDimensionInputData nonDimensioning(InputData inputData) {
		return inputData.nonDimensioning();
	}

	public abstract Result calculate(NonDimensionInputData inputData) throws ErrorInAlgorithmException;
}
