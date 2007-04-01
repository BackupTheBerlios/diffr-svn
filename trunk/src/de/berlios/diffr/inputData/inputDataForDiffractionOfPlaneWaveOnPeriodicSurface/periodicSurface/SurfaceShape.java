package de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface.periodicSurface;

import de.berlios.diffr.Model;
import de.berlios.diffr.exceptions.ObjectIsnotEditableException; 
import java.util.*;

public class SurfaceShape extends Model {
	public SurfaceShape() {
		fourierCoefficients.add(new FourierCoefficient(this, 0.1, 0.05));
	}
	public SurfaceShape(double period, double shift, ArrayList fourierCoefficients) {
		this.period = period;
	    this.shift = shift;
	    this.fourierCoefficients = fourierCoefficients;
	}
	
	private double period = 1;
	private double shift = 0;
	private ArrayList fourierCoefficients = new ArrayList();
	
	public double getPeriod() {
	    return period;
	}
	
	public void setPeriod(double period) throws ObjectIsnotEditableException {
		if (!isEditable()) throw new ObjectIsnotEditableException();
		this.period = period;
		modelWasChangedEvent();
	}
	
	public double getShift() {
	    return shift;
	}
	
	public void setShift(double shift) throws ObjectIsnotEditableException {
		if (!isEditable()) throw new ObjectIsnotEditableException();
		this.shift = shift;
		modelWasChangedEvent();
	}
	
	public ArrayList getFourierCoefficients() {
	    return (ArrayList)fourierCoefficients.clone();
	}
	public void removeFourierCoefficient(FourierCoefficient c) throws ObjectIsnotEditableException {
		if (!isEditable()) throw new ObjectIsnotEditableException();
		fourierCoefficients.remove(c);
		modelWasChangedEvent();
	}
	public void addFourierCoefficient(int index, FourierCoefficient c) throws ObjectIsnotEditableException {
		if (!isEditable()) throw new ObjectIsnotEditableException();
		fourierCoefficients.add(index, c);
		modelWasChangedEvent();
	}
}
