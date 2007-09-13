package de.berlios.diffr.algorithms.addedAlgorithms;

import de.berlios.diffr.*;
import de.berlios.diffr.algorithms.*;
import de.berlios.diffr.inputData.InputData;
import de.berlios.diffr.result.Result;

public class SleepAlgorithm extends Algorithm {
	public SleepAlgorithm(AlgorithmType algorithmType) {
		super(algorithmType);
		parameters = new DataString[1];
		parameters[0] = new DataString("delay", new Long(5000));
	}
	public Algorithm clone() {
		SleepAlgorithm r = new SleepAlgorithm(super.getAlgorithmType());
		r.parameters = parameters.clone();
		return r;
	}
	public Result run(InputData inputData) throws Exception {
		try {
			Thread.sleep(( (Long) parameters[0].getValue()).longValue());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Result(null, null, null, 0);
	}
}
