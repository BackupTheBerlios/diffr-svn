	package de.berlios.diffr.algorithms.addedAlgorithms;

	import de.berlios.diffr.result.*;
	import de.berlios.diffr.inputData.*;
	import de.berlios.diffr.inputData.surface.*;
	import de.berlios.diffr.DataString;
	import de.berlios.diffr.algorithms.AbstractAlgorithm;
	import de.berlios.diffr.algorithms.Algorithm;
	import de.berlios.diffr.algorithms.AlgorithmType;

	public class KirchgoffAlgorithm extends AbstractAlgorithm {
		private static final long serialVersionUID = 1L;
		public KirchgoffAlgorithm(AlgorithmType algorithmType) {
			super(algorithmType);
			parameters = new DataString[2];
			parameters[0] = new DataString("Order", new Integer(1));
			parameters[1] = new DataString("NumberOfPointsForSurfaceCalculation", new Integer(200)); //Vremeno
		}
		public Algorithm clone() {
			KirchgoffAlgorithm r = new KirchgoffAlgorithm(super.getAlgorithmType());
			r.parameters = parameters.clone();
			return r;
		}
		public Result calculate(InputData inputData)  {
			int order = ((Integer)parameters[0].getValue()).intValue();
			int numberOfPoints = ((Integer)parameters[1].getValue()).intValue();
			KirchgoffAlgorithmSolver solver = null;
			
			if (inputData.getSurface().getConductivity() instanceof PerfectConductivity)
				solver = new KirchgoffAlgorithmSolverPerfectConductivity();
			
			if (inputData.getSurface().getConductivity() instanceof HeightConductivity) {
				solver = new KirchgoffAlgorithmSolverHeightConductivity();
			}
			
			solver.initialize(inputData);
				System.out.println("Kirchgoff solver initialization ends");
			Result result = solver.solve(order, numberOfPoints);
				System.out.println("KirchgoffAlgorithm result calculated");
			return result;
		}
	}
