package de.berlios.diffr.algorithms.addedAlgorithms;

import de.berlios.diffr.result.*;
import de.berlios.diffr.inputData.*;
import de.berlios.diffr.inputData.surface.*;
import de.berlios.diffr.DataString;
import de.berlios.diffr.algorithms.AbstractAlgorithm;
import de.berlios.diffr.algorithms.Algorithm;
import de.berlios.diffr.algorithms.AlgorithmType;
import de.berlios.diffr.exceptions.*;

public class SmallPerturbationAlgorithm extends AbstractAlgorithm {
	public SmallPerturbationAlgorithm(AlgorithmType algorithmType) {
		super(algorithmType);
		parameters = new DataString[2];
		parameters[0] = new DataString("Order", new Integer(1));
		parameters[1] = new DataString("NumberOfPointsForSurfaceCalculation", new Integer(200)); //Vremeno
	}
	public Algorithm clone() {
		SmallPerturbationAlgorithm r = new SmallPerturbationAlgorithm(super.getAlgorithmType());
		r.parameters = parameters.clone();
		return r;
	}
	public Result calculate(InputData inputData)  {
		int order = ((Integer)parameters[0].getValue()).intValue();
		int numberOfPoints = ((Integer)parameters[1].getValue()).intValue();
		SmallPerturbationAlgorithmSolver solver = null;
		
		if (inputData.getSurface().getConductivity() instanceof PerfectConductivity)
			solver = new SmallPerturbationAlgorithmSolverPerfectConductivity();
		
		if (inputData.getSurface().getConductivity() instanceof HeightConductivity) {
			solver = new SmallPerturbationAlgorithmSolverHeightConductivity();
		}
		
		solver.initialize(inputData);
			System.out.println("solver initialization ends");
		Result result = solver.solve(order, numberOfPoints);
			System.out.println("SmallPerturbationAlgorithm result calculated");
		return result;
	}
}

