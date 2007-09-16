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
		DimensionData dimensionData = calculateDimensionData(inputData);
		InputData data = inputData.nonDimensioning(dimensionData);
		Result result = calculate(data).dimensioning(dimensionData);
		return result;
	}

	private DimensionData calculateDimensionData(InputData inputData) {
		DimensionData d = new DimensionData();
		d.setAmplitude(inputData.getIncidentWave().getAmplitude());
		d.setPeriod(inputData.getSurface().getShape().getPeriod());
		return d;
	}

	public abstract Result calculate(InputData inputData) throws Exception;
}
