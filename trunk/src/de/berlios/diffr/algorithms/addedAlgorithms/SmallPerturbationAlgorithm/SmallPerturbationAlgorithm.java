package de.berlios.diffr.algorithms.addedAlgorithms.SmallPerturbationAlgorithm;

import de.berlios.diffr.result.*;
import de.berlios.diffr.inputData.*;
import de.berlios.diffr.inputData.surface.*;
import de.berlios.diffr.DataString;
import de.berlios.diffr.algorithms.AbstractAlgorithm;
import de.berlios.diffr.algorithms.AlgorithmType;

public class SmallPerturbationAlgorithm extends AbstractAlgorithm {
	private static final long serialVersionUID = 1L;
	public SmallPerturbationAlgorithm(AlgorithmType algorithmType) {
		super(algorithmType);
		parameters = new DataString[2];
		parameters[0] = new DataString("Order", new Integer(1));
		parameters[1] = new DataString("NumberOfPointsForSurfaceCalculation", new Integer(200)); //Vremeno
	}
	public Result calculate(InputData inputData)  {
		int order = ((Integer)parameters[0].getValue()).intValue();
		int numberOfPoints = ((Integer)parameters[1].getValue()).intValue();
		SmallPerturbationAlgorithmSolver solver = null;
		
		if (inputData.getSurface().getConductivity() instanceof PerfectConductivity)
			solver = new SmallPerturbationAlgorithmSolverPerfectConductivity();
		
		if (inputData.getSurface().getConductivity() instanceof ZeroConductivity) {
			solver = new SmallPerturbationAlgorithmSolverHeightConductivity();
		}
		
		solver.initialize(inputData);
			System.out.println("solver initialization ends");
		Result result = solver.solve(order, numberOfPoints);
			System.out.println("SmallPerturbationAlgorithm result calculated");
		return result;
	}

	public String getAutor() {
		return "andrmikheev";
	}

	public String getTitle() {
		return "Small perturbation algorithm";
	}

	public String getVersion() {
		return "0.01";
	}
}

