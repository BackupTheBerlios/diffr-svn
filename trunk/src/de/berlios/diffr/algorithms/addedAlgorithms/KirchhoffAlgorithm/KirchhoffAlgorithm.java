package de.berlios.diffr.algorithms.addedAlgorithms.KirchhoffAlgorithm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Org.netlib.math.complex.Complex;
import de.berlios.diffr.*;
import de.berlios.diffr.algorithms.*;
import de.berlios.diffr.inputData.IncidentWave;
import de.berlios.diffr.inputData.InputData;
import de.berlios.diffr.inputData.surface.FourierCoefficient;
import de.berlios.diffr.math.Formula;
import de.berlios.diffr.result.ReflectedField;
import de.berlios.diffr.result.ReflectedPlaneWave;
import de.berlios.diffr.result.Result;
import de.berlios.diffr.result.SurfaceCurrent;

public class KirchhoffAlgorithm extends AbstractAlgorithm {
	private static final long serialVersionUID = 1L;
	public class AlgData implements Serializable {
		private static final long serialVersionUID = 1L;
		public double a, b, gam, gama, gamb, lam, lamb, nx, ny;
		public double n, m, x, y;
		public Complex f, f2, coef;
		public Complex sum;
	};
	public KirchhoffAlgorithm(AlgorithmType algorithmType) {
		super(algorithmType);
		parameters = new DataString[2];
		parameters[0] = new DataString("Number of points for surface current calculation", new Integer(200));
		parameters[1] = new DataString("Precision of integral", new Integer(200));
	}
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
	public Result calculate(InputData inputData) throws Exception {
		int currentPoints = (Integer)parameters[0].getValue();
		int precision = (Integer)parameters[1].getValue();
		if (precision<1) precision = 1;
		
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
		data.gam = gam0;
		for (int a=minw; a<=maxw; a++) {
			data.gama = getGam(a);
			data.lam = getLam(a);
			double delta = 2*Math.PI/precision;
			Complex intg = new Complex(0, 0);
			for (double al=0;al<2*Math.PI;al+=delta) {
				data.f = new Complex(Fvalue(al), 0);
				data.f2 = new Complex(Fderivative(al), 0);
				data.b = al;
				data.m = a;
				Complex v = Formula.calculate("exp(-i*m*b+i*f*(gam+gama))*(gama+lam*f2)", data);
				intg = intg.add(v.mul(delta));
			}
			data.f = intg;
			data.a = 2*Math.PI;
			if (polarization == IncidentWave.polarizationE)
				setAns(a, Formula.calculate("-f/gama/a", data));
			else
				setAns(a, Formula.calculate("f/gama/a", data));
		}
		List<ReflectedPlaneWave> waves = new LinkedList<ReflectedPlaneWave>();
		for (int a=minw; a<=maxw;a++)
			if (getAns(a).abs()>0)
				waves.add(new ReflectedPlaneWave(polarization, Math.atan2(-getLam(a), getGam(a)), iwave.getLength(), iwave.getAmplitude().mul(getAns(a)), a));
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
		return "Kirchhoff algorithm";
	}

	public String getVersion() {
		return "v0.1";
	}
}
