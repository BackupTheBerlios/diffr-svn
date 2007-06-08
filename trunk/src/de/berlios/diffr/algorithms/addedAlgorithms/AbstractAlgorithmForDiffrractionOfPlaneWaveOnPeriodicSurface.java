package de.berlios.diffr.algorithms.addedAlgorithms;

import de.berlios.diffr.DataString;
import de.berlios.diffr.algorithms.Algorithm;
import de.berlios.diffr.algorithms.AlgorithmType;
import de.berlios.diffr.exceptions.ErrorInAlgorithmException;
import de.berlios.diffr.inputData.InputData;
import de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface.*;
import de.berlios.diffr.result.Result;

public abstract class AbstractAlgorithmForDiffrractionOfPlaneWaveOnPeriodicSurface
		extends Algorithm {
	public AbstractAlgorithmForDiffrractionOfPlaneWaveOnPeriodicSurface(AlgorithmType algorithmType) {
		super(algorithmType);
	}

	public Result run(InputData inputData) throws ErrorInAlgorithmException {
		NonDimensionInputData data = nonDimensioning(inputData);
		Result result = calculate(data);
		return dimensioning(result);
	}
	private Result dimensioning(Result result) {
		// TODO Auto-generated method stub
		return null;
	}

	private NonDimensionInputData nonDimensioning(InputData inputData) {
		// TODO Auto-generated method stub
		return null;
	}

	public abstract Result calculate(NonDimensionInputData inputData) throws ErrorInAlgorithmException;
}
