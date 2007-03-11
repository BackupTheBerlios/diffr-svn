package de.berlios.diffr.diffraction.algorithms;

import de.berlios.diffr.diffraction.tasks.Task;
import de.berlios.diffr.diffraction.surfaces.periodicSurface.*;
import de.berlios.diffr.diffraction.fields.*;

public class TaskHelper {

	private Task task;

	public void setTask(Task task){
		this.task = task;
	}
	
	public double getK(){
		double period = ((PeriodicSurface)task.getSurface()).getShape().getPeriod();
		double waveLength = ((PlaneWave)task.getImpingingField()).getLength();
		return period / waveLength;
	}

	public double getLambda(){
		double lambda = ((PlaneWave)task.getImpingingField()).getAngle();
		return lambda;
	}
	
}
