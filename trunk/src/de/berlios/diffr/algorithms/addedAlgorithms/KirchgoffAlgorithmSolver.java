	package de.berlios.diffr.algorithms.addedAlgorithms;

	import Org.netlib.math.complex.Complex;
	import de.berlios.diffr.inputData.IncidentWave;
	import de.berlios.diffr.result.*;

	public abstract class KirchgoffAlgorithmSolver extends AlgorithmBase {

		protected int gam_min;
		protected int gam_max;
		protected ReflectedPlaneWave[] waves = null;
		
		public Result solve(int order, int numberOfPoints) {

			SurfaceCurrent surfaceCurrent = calculateSurfaceCurrent(numberOfPoints);

//				System.out.println("KirchgoffAlgorithmSolver start");
			gam_min = (int) Math.floor((k * (1 + Math.sin(alpha))));
				System.out.println("gam_min = " + gam_min);
			gam_max =   (int) Math.floor( (k * (1 - Math.sin(alpha))));
//				System.out.println("gam_max =  " + gam_max);
			waves = new ReflectedPlaneWave[gam_min + gam_max + 1];
			int counter = 0;
			for (int j = -gam_min; j<= gam_max; j++){
//					System.out.println("j =  " + j);
				waves[counter] = new ReflectedPlaneWave(polarization, calculateAngle(j), waveLength, calculateAmplitude(j,order),j);
				counter++;
	/*			Complex r = calculateAmplitude(j,order);
					System.out.println("Amplitude re =  " + r.re() + "   im = " + r.im());*/
			}
//				System.out.println("waves.length =  " + waves.length);
			ReflectedField reflectedField = new ReflectedField(waves, new IncidentWave(polarization, alpha, waveLength, amplitude));
//				System.out.println("solver end");
			
			double energyError = calculateEnergyError(numberOfPoints);
				
			return new Result(reflectedField, null, surfaceCurrent, energyError);
		}

		public Complex normalDerivativeOfIncidentField(double y) {
			Complex s = (Complex.i.mul(k).mul(f_surface(y)*Math.cos(alpha) + y*Math.sin(alpha))).exp();
			s = s.mul(Complex.i).mul(k).mul(n_x(y)*Math.cos(alpha) + n_y(y)*Math.sin(alpha));
			return s;
		}

		public Complex incidentField(double y, double x) {
			Complex s = (Complex.i.mul(k).mul(x*Math.cos(alpha) + y*Math.sin(alpha))).exp();
			return s;
		}
			public abstract SurfaceCurrent calculateSurfaceCurrent(int numberOfPoints);

		public abstract Complex calculateNonDimensionalAmplitude(int numberOfPoints, int m, int order);
		
		public abstract double calculateEnergyError(int numberOfPoints);
	}

