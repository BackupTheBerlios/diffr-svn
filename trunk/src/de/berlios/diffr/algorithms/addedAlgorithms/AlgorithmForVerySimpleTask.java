package de.berlios.diffr.algorithms.addedAlgorithms;

import de.berlios.diffr.result.*;
import de.berlios.diffr.result.resultForVerySimpleTask.*;
import de.berlios.diffr.inputData.*;
import de.berlios.diffr.inputData.inputDataForVerySimpleTask.*;
import de.berlios.diffr.algorithms.Algorithm;
import de.berlios.diffr.algorithms.AlgorithmParameter;
import de.berlios.diffr.algorithms.AlgorithmType;
import de.berlios.diffr.exceptions.*;

public class AlgorithmForVerySimpleTask extends Algorithm {
	public AlgorithmForVerySimpleTask(AlgorithmType algorithmType) {
		super(algorithmType);
		parameters = new AlgorithmParameter[1];
		parameters[0] = new AlgorithmParameter("delay", new Long(10000));
	}
	
	public Result run(InputData inputData) throws ErrorInAlgorithmException {
		try {
			Thread.sleep(( (Long) parameters[0].getValue()).longValue());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double angle = ((VerySimpleWave) inputData.getImpingingField()).getAngle();
		return new Result(new ReflectedWave(0 - angle));
	}
}
