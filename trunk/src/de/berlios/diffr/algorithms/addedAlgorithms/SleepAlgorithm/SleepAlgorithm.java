package de.berlios.diffr.algorithms.addedAlgorithms.SleepAlgorithm;

import de.berlios.diffr.*;
import de.berlios.diffr.algorithms.*;
import de.berlios.diffr.inputData.InputData;
import de.berlios.diffr.result.Result;

public class SleepAlgorithm extends Algorithm {
	private static final long serialVersionUID = 1L;
	public SleepAlgorithm(AlgorithmType algorithmType) {
		super(algorithmType);
		parameters = new DataString[1];
		parameters[0] = new DataString("delay", new Long(5000));
	}
	public Result run(InputData inputData, boolean surfaceCurrentNeed) throws Exception {
		try {
			Thread.sleep(( (Long) parameters[0].getValue()).longValue());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return new Result(null, null, null);
	}

	public String getAutor() {
		return "petrmikheev";
	}

	public String getTitle() {
		return "Sleep algorithm";
	}

	public String getVersion() {
		return "v1.0";
	}
}
