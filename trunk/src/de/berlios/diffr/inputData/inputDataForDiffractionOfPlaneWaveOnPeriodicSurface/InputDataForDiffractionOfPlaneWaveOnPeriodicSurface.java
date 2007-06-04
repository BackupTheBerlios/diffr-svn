package de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface;

import Org.netlib.math.complex.*;
import de.berlios.diffr.*;
import de.berlios.diffr.inputData.*;
import de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface.periodicSurface.*;

public class InputDataForDiffractionOfPlaneWaveOnPeriodicSurface extends
		InputData {
	public InputDataForDiffractionOfPlaneWaveOnPeriodicSurface(PeriodicSurface surface, ImpingingPlaneWave field) {
		super(surface, field);
		this.addModelChangingListener(new ModelChangingListener() {
			public void modelWasChanged(Model m) {
				impedance = calculateImpedance(getPeriodicSurface(), getImpingingPlaneWave());
				h = calculateH(getPeriodicSurface(), getImpingingPlaneWave());
			}
		});
		impedance = calculateImpedance(getPeriodicSurface(), getImpingingPlaneWave());
		h = calculateH(getPeriodicSurface(), getImpingingPlaneWave());
	}
	public PeriodicSurface getPeriodicSurface() {
		return (PeriodicSurface)getSurface();
	}
	public ImpingingPlaneWave getImpingingPlaneWave() {
		return (ImpingingPlaneWave)getImpingingField();
	}
	public Complex getImpedance() {
		return impedance;
	}
	public Complex getH() {
		return h;
	}
	private Complex impedance;
	private Complex h;
	private Complex calculateImpedance(PeriodicSurface surface, ImpingingPlaneWave field) {
		return new Complex(0, 0);
	}
	private Complex calculateH(PeriodicSurface surface, ImpingingPlaneWave field) {
		return new Complex(0, 0);
	}
}
