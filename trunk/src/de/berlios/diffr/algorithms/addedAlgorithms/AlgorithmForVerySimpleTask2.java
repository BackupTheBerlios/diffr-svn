package de.berlios.diffr.algorithms.addedAlgorithms;

import de.berlios.diffr.DataString;
import de.berlios.diffr.result.*;
import de.berlios.diffr.result.resultForVerySimpleTask.*;
import de.berlios.diffr.inputData.*;
import de.berlios.diffr.inputData.inputDataForVerySimpleTask.*;
import de.berlios.diffr.algorithms.Algorithm;
import de.berlios.diffr.algorithms.AlgorithmType;
import de.berlios.diffr.exceptions.*;

public class AlgorithmForVerySimpleTask2 extends Algorithm {
	public AlgorithmForVerySimpleTask2(AlgorithmType algorithmType) {
		super(algorithmType);
		parameters = new DataString[1];
		parameters[0] = new DataString("delay", new Long(500));
	}
	
	public Result run(InputData inputData) throws ErrorInAlgorithmException {
		try {
			Thread.sleep(( (Long) parameters[0].getValue()).longValue());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double angle = ((VerySimpleWave) inputData.getImpingingField()).getAngle();
		return new Result(new ReflectedWave(0 - angle), null, null);
	}
}
