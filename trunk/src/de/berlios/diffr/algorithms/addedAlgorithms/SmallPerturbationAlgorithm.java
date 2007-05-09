package de.berlios.diffr.algorithms.addedAlgorithms;

import de.berlios.diffr.result.*;
import de.berlios.diffr.inputData.*;
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
			System.out.println("solver initialization ends");
		Result result = solver.solve();
			System.out.println("SmallPerturbationAlgorithm result calculated");
		return result;
	}
}

