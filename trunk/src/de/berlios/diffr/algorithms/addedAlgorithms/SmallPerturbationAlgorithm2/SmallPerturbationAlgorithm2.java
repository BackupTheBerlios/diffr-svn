package de.berlios.diffr.algorithms.addedAlgorithms.SmallPerturbationAlgorithm2;

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

public class SmallPerturbationAlgorithm2 extends AbstractAlgorithm {
	public class AlgData implements Serializable {
		public double a, b, gam, gamb;
		public Complex f, f2;
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
		return lam.get(n-minw);
	}
	private double getGam(int n) {
		if (n-minw<0) return 0;
		if (n-minw>=gam.size()) return 0;
		return gam.get(n-minw);
	}
	private Complex getAns(int n) {
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
	private void setAns(int n, Complex nv) {
		ans.set(n-minw, ans.get(n-minw).add(nv));
	}

	public SmallPerturbationAlgorithm2(AlgorithmType type) {
		super(type);
		parameters = new DataString[1];
		parameters[0] = new DataString("Order (1 or 2)", new Integer(1));
	}
	
	public Result calculate(InputData inputData) throws Exception {
		boolean order2 = (Integer)parameters[0].getValue() > 1;
		coefs = inputData.getSurface().getShape().getFourierCoefficients();
		shift = inputData.getSurface().getShape().getShift();
		IncidentWave iwave = inputData.getIncidentWave();
		polarization = iwave.getPolarization();
		if (polarization != IncidentWave.polarizationE) throw new Exception();
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
		
		List<ReflectedPlaneWave> waves = new LinkedList<ReflectedPlaneWave>();
		for (int a=minw; a<=maxw;a++)
			if (getAns(a).abs()>0)
				waves.add(new ReflectedPlaneWave(ReflectedPlaneWave.polarizationE, Math.atan2(-getLam(a), getGam(a)), iwave.getLength(), iwave.getAmplitude().mul(getAns(a)), a));
		ReflectedPlaneWave[] wavesArr = new ReflectedPlaneWave[waves.size()];
		int num = 0;
		for (ReflectedPlaneWave w : waves) wavesArr[num++] = w;
		return new Result(new ReflectedField(wavesArr, iwave), null, null);
	}

	public String getAutor() {
		return "petrmikheev";
	}

	public String getTitle() {
		return "Small perturbation algoritm";
	}

	public String getVersion() {
		return "0.1";
	}

}
