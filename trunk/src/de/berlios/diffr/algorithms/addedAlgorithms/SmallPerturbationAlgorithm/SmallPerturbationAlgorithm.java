package de.berlios.diffr.algorithms.addedAlgorithms.SmallPerturbationAlgorithm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Org.netlib.math.complex.Complex;
import de.berlios.diffr.DataString;
import de.berlios.diffr.algorithms.AbstractAlgorithm;
import de.berlios.diffr.algorithms.AlgorithmType;
import de.berlios.diffr.inputData.IncidentWave;
import de.berlios.diffr.inputData.InputData;
import de.berlios.diffr.inputData.surface.FourierCoefficient;
import de.berlios.diffr.math.Formula;
import de.berlios.diffr.result.ReflectedField;
import de.berlios.diffr.result.ReflectedPlaneWave;
import de.berlios.diffr.result.Result;
import de.berlios.diffr.result.SurfaceCurrent;

public class SmallPerturbationAlgorithm extends AbstractAlgorithm {
	private static final long serialVersionUID = 1L;

	public class AlgData implements Serializable {
		private static final long serialVersionUID = 1L;
		public double a, b, gam, gama, gamb, lam, lamb, nx, ny;
		public double n, m, x, y;
		public Complex f, f2, coef;
		public Complex sum;
	};
	private ArrayList<FourierCoefficient> coefs;
	private double shift;
	private boolean polarization;
	private double k;
	private AlgData data = new AlgData();
	private ArrayList<Double> lam;
	private ArrayList<Double> gam;
	private ArrayList<Complex> ans;
	private int minw;
	private int maxw;
	private double getLam(int n) {
		if (n-minw<0) return 0;
		if (n-minw>=lam.size()) return 0;
		return lam.get(n-minw);
	}
	private double getGam(int n) {
		if (n-minw<0) return 0;
		if (n-minw>=gam.size()) return 0;
		return gam.get(n-minw);
	}
	private Complex getAns(int n) {
		if (n-minw<0) return new Complex(0, 0);
		if (n-minw>=ans.size()) return new Complex(0, 0);
		return ans.get(n-minw);
	}
	private Complex getF(int m) {
		if (coefs.size()<Math.abs(m)) return new Complex(0, 0);
		if (m>0) {
			FourierCoefficient c = coefs.get(m-1);
			data.b = c.getCoefficientOfSinus();
			data.a = c.getCoefficientOfCosinus();
			return Formula.calculate("(a+b*i)/2", data);
		}
		if (m<0) {
			FourierCoefficient c = coefs.get(-m-1);
			data.b = c.getCoefficientOfSinus();
			data.a = c.getCoefficientOfCosinus();
			return Formula.calculate("(a-b*i)/2", data);
		}
		return new Complex(shift, 0);
	}
	private double Fderivative(double x) {
		double answer = 0;
		for (int m=-coefs.size();m<=coefs.size();m++) {
			Complex coef = getF(m);
			data.m = m;
			data.coef = coef;
			data.x = x;
			answer+= Formula.calculate("coef*exp(i*x*m)*i*m", data).re();
		}
		return answer;
	}
	private double Fvalue(double x) {
		double answer = 0;
		for (int m=-coefs.size();m<=coefs.size();m++) {
			Complex coef = getF(m);
			data.m = m;
			data.coef = coef;
			data.x = x;
			answer+= Formula.calculate("coef*exp(i*x*m)", data).re();
		}
		return answer;
	}
	private void setAns(int n, Complex nv) {
		if (n-minw<0) return;
		if (n-minw>=gam.size()) return;
		ans.set(n-minw, ans.get(n-minw).add(nv));
	}

	public SmallPerturbationAlgorithm(AlgorithmType type) {
		super(type);
		parameters = new DataString[2];
		parameters[0] = new DataString("Order (1 or 2)", new Integer(1));
		parameters[1] = new DataString("Number of points for surface current calculation", new Integer(200));
	}
	
	public Result calculate(InputData inputData) throws Exception {
		boolean order2 = (Integer)parameters[0].getValue() > 1;
		int currentPoints = (Integer)parameters[1].getValue();
		coefs = inputData.getSurface().getShape().getFourierCoefficients();
		shift = inputData.getSurface().getShape().getShift();
		IncidentWave iwave = inputData.getIncidentWave();
		polarization = iwave.getPolarization();
		k = 2*Math.PI / iwave.getLength();
		
		double lam0 = k*Math.sin(-iwave.getAngle());
		double gam0 = k*Math.cos(-iwave.getAngle());
		minw = 0;
		maxw = 0;
		lam = new ArrayList<Double>();
		gam = new ArrayList<Double>();
		ans = new ArrayList<Complex>();
		for (int a=(int)(-k)-1; a<=k+1; a++) {
			if (k*k-(lam0+a)*(lam0+a)>=0) {
				if (a<minw) minw = a;
				if (a>maxw) maxw = a;
				lam.add(lam0+a);
				gam.add(Math.sqrt(k*k-(lam0+a)*(lam0+a)));
				ans.add(new Complex(0, 0));
			}
		}
		
		if (polarization == IncidentWave.polarizationE) {
			setAns(0, new Complex(-1, 0));
			data.gam = gam0;
			for (int a=minw; a<=maxw; a++) {
				data.f = getF(a);
				setAns(a, Formula.calculate("-2*gam*i*f", data));
				if (order2) {
					data.sum = new Complex(0, 0);
					for (int b=-coefs.size();b<=coefs.size();b++) {
						data.f = getF(b);
						data.f2 = getF(a-b);
						data.gamb = getGam(b);
						data.sum = data.sum.add(Formula.calculate("gamb*f*f2", data));
					}
					setAns(a, Formula.calculate("2*gam*sum", data));
				}
			}
		} else {
			setAns(0, new Complex(1, 0));
			data.gam = gam0;
			data.lam = lam0;
			for (int a=minw; a<=maxw; a++) {
				data.f = getF(a);
				data.gama = getGam(a);
				data.n = a;
				setAns(a, Formula.calculate("2*i*f*(gam*gam-lam*n)/gama", data));
				if (order2) {
					for (int b=-coefs.size();b<=coefs.size();b++) {
						data.gamb = getGam(b);
						data.lamb = getLam(b);
						data.m = b;
						data.f = getF(b);
						data.f2 = getF(a-b);
						Complex r = Formula.calculate("-2*(gam*gam-lam*m)*(gam*gam-lam*m)*f*f2/(gama*gamb)", data);
						setAns(a, r);
					}
				}
			}
		}
		
		List<ReflectedPlaneWave> waves = new LinkedList<ReflectedPlaneWave>();
		for (int a=minw; a<=maxw;a++)
			if (getAns(a).abs()>0)
				waves.add(new ReflectedPlaneWave(ReflectedPlaneWave.polarizationE, Math.atan2(-getLam(a), getGam(a)), iwave.getLength(), iwave.getAmplitude().mul(getAns(a)), a));
		ReflectedPlaneWave[] wavesArr = new ReflectedPlaneWave[waves.size()];
		int num = 0;
		for (ReflectedPlaneWave w : waves) wavesArr[num++] = w;
		
		Complex[] points = new Complex[currentPoints];
		for (int a=0;a<currentPoints;a++) {
			double x = (2*Math.PI/(currentPoints-1))*a;
			double der = Fderivative(x);
			data.y = Fvalue(x);
			data.ny = 1 / Math.sqrt(1+der*der);
			data.nx = -der / Math.sqrt(1+der*der);
			data.lam = lam0;
			data.gam = gam0;
			data.x = x;
			
			if (polarization == IncidentWave.polarizationE) {
				points[a] = Formula.calculate("i*exp(i*lam*x+i*gam*y)*(nx*lam+ny*gam)", data);
				for (int b=minw; b<=maxw;b++) {
					data.lamb = getLam(b);
					data.gamb = getGam(b);
					data.coef = getAns(b);
					points[a] = points[a].add(Formula.calculate("coef*i*exp(i*lamb*x-i*gamb*y)*(nx*lamb-ny*gamb)", data));
				}
				points[a] = points[a].mul(iwave.getAmplitude()).mul(Complex.i).div(k);
			} else {
				points[a] = Formula.calculate("exp(i*lam*x+i*gam*y)", data);
				for (int b=minw; b<=maxw;b++) {
					data.lamb = getLam(b);
					data.gamb = getGam(b);
					data.coef = getAns(b);
					points[a] = points[a].add(Formula.calculate("coef*exp(i*lamb*x-i*gamb*y)", data));
				}
			}
		}
		SurfaceCurrent current = new SurfaceCurrent(points, 2*Math.PI);
		return new Result(new ReflectedField(wavesArr, iwave), null, current);
	}

	public String getAutor() {
		return "petrmikheev";
	}

	public String getTitle() {
		return "Small perturbation algorithm";
	}

	public String getVersion() {
		return "v0.9";
	}

}
