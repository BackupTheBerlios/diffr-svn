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
//		parameters = new DataString[2]; //Vremeno
		parameters[0] = new DataString("Order", new Integer(1));
//		parameters[1] = new DataString("NumberOfPointsForSurfaceCalculation", new Integer(1)); //Vremeno
	}
	public Algorithm clone() {
		SmallPerturbationAlgorithm r = new SmallPerturbationAlgorithm(super.getAlgorithmType());
		r.parameters = parameters.clone();
		return r;
	}
	public Result calculate(NonDimensionInputData inputData)  {
		int order = ((Integer)parameters[0].getValue()).intValue();
//		int numberOfPoints = ((Integer)parameters[1].getValue()).intValue(); //Vremeno
		int numberOfPoints = 200; //Vremeno
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

