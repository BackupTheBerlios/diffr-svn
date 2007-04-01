package de.berlios.diffr.diffraction.algorithms;

import Org.netlib.math.complex.Complex;
import de.berlios.diffr.diffraction.tasks.Task;
import de.berlios.diffr.diffraction.surfaces.periodicSurface.*;
import de.berlios.diffr.diffraction.fields.*;

public class TaskHelper {
	
	private Task task;

	public void setTask(Task task){
		this.task = task;
	}
	
	private double getPeriod(){
		double period = ((PeriodicSurface)task.getSurface()).getShape().getPeriod();
		return period;
	}

	private double getWaveNumber(){
		double period = getPeriod();
		double waveLength = ((PlaneWave)task.getImpingingField()).getLength();
		return period / waveLength;
	}

	public double getNormalizedWaveNumber(){
		return getWaveNumber();
	}

	private double getWaveLength(){
		double waveLength = ((PlaneWave)task.getImpingingField()).getLength();
		return waveLength;
	}
	
	public double getNormalizedWaveLength(){
		double normalizedWaveLength = getWaveLength()/getPeriod() *(2.0*Math.PI);
		return normalizedWaveLength;
	}
	
	private double getLambda0(){
		return getNormalizedWaveNumber() * Math.sin(((PlaneWave)task.getImpingingField()).getAngle());
	}
	
	public double getLambda(int n){
		return getLambda0() + n;
	}
	
	public Complex getGamma(int n){
		if (getNormalizedWaveNumber() > Math.abs(getLambda(n))) return new Complex(Math.sqrt(
				getNormalizedWaveNumber()*getNormalizedWaveNumber() - getLambda(n)*getLambda(n)	),0.0);
		else
		return new Complex(0.0, Math.sqrt(
			- getNormalizedWaveNumber()*getNormalizedWaveNumber() + getLambda(n)*getLambda(n)));
	}
	
	
	private Complex getImpedance(){
//		double impedance = (()task.getSurface().;
		//todo - inset real impedance here
		return null;
//		return new Complex(0.0, 0.0);
	}

/*	private double getRc(int i) {
		
	}
	
	private double getRs(int i) {
		
	}*/
	
}
