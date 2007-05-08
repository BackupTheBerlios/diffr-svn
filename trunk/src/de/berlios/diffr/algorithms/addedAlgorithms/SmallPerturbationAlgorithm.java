package de.berlios.diffr.algorithms.addedAlgorithms;

import de.berlios.diffr.DataString;
import de.berlios.diffr.result.*;
import de.berlios.diffr.result.resultForDiffractionOfPlaneWaveOnPriodicSurface.ReflectedWavesAndCurrent;
import de.berlios.diffr.result.resultForVerySimpleTask.*;
import de.berlios.diffr.inputData.*;
import de.berlios.diffr.inputData.inputDataForVerySimpleTask.*;
import de.berlios.diffr.algorithms.Algorithm;
import de.berlios.diffr.algorithms.AlgorithmType;
import de.berlios.diffr.exceptions.*;

public class SmallPerturbationAlgorithm extends Algorithm {
	public SmallPerturbationAlgorithm(AlgorithmType algorithmType) {
		super(algorithmType);
//		parameters = new DataString[1];
//		parameters[0] = new DataString("delay", new Long(10000));
	}
	
	public Result run(InputData inputData) throws ErrorInAlgorithmException {
		SmallPerturbationAlgorithmSolver solver = new SmallPerturbationAlgorithmSolver();
		solver.initialize(inputData);
		ReflectedWavesAndCurrent reflectedField = solver.solve();
		return new Result(reflectedField);
	}
}

