package de.berlios.diffr.algorithms;

import de.berlios.diffr.DataString;
import de.berlios.diffr.inputData.*;
import de.berlios.diffr.result.*;

public abstract class AbstractAlgorithm
		extends Algorithm {
	public AbstractAlgorithm(AlgorithmType algorithmType) {
		super(algorithmType);
	}

	public Result run(InputData inputData) throws Exception {
		DimensionData dimensionData = calculateDimensionData();
		InputData data = inputData.nonDimensioning(dimensionData);
		Result result = calculate(data).dimensioning(dimensionData);
		return result;
	}

	private DimensionData calculateDimensionData() {
		// TODO Auto-generated method stub
		return null;
	}

	public abstract Result calculate(InputData inputData) throws Exception;
}
