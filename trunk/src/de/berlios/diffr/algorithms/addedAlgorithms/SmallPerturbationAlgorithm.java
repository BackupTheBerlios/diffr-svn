package de.berlios.diffr.algorithms.addedAlgorithms;

import de.berlios.diffr.result.*;
import de.berlios.diffr.inputData.*;
import de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface.NonDimensionInputData;
import de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface.periodicSurface.HeightConductivity;
import de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface.periodicSurface.PerfectConductivity;
import de.berlios.diffr.DataString;
import de.berlios.diffr.algorithms.Algorithm;
import de.berlios.diffr.algorithms.AlgorithmType;
import de.berlios.diffr.exceptions.*;

public class SmallPerturbationAlgorithm extends AbstractAlgorithmForDiffrractionOfPlaneWaveOnPeriodicSurface {
	public SmallPerturbationAlgorithm(AlgorithmType algorithmType) {
		super(algorithmType);
		parameters = new DataString[1];
		parameters[0] = new DataString("Order", new Integer(1));
	}
	public Algorithm clone() {
		SmallPerturbationAlgorithm r = new SmallPerturbationAlgorithm(super.getAlgorithmType());
		r.parameters = parameters.clone();
		return r;
	}
	public Result calculate(NonDimensionInputData inputData)  {
		SmallPerturbationAlgorithmSolver solver = null;
		
		if (inputData.getSurface().getConductivity() instanceof PerfectConductivity)
			solver = new SmallPerturbationAlgorithmSolverPerfectConductivity();
		
		if (inputData.getSurface().getConductivity() instanceof HeightConductivity)
			solver = new SmallPerturbationAlgorithmSolverHeightConductivity();
		
		
		
		solver.initialize(inputData);
			System.out.println("solver initialization ends");
		Result result = solver.solve();
			System.out.println("SmallPerturbationAlgorithm result calculated");
		return result;
	}
}

