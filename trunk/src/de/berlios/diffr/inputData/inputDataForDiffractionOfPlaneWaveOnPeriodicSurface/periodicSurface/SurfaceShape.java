package de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface.periodicSurface;

import de.berlios.diffr.Model;
import de.berlios.diffr.exceptions.ObjectIsnotEditableException; 
import java.util.*;

import Org.netlib.math.complex.Complex;

public class SurfaceShape extends Model {
	public SurfaceShape() {
		fourierCoefficients.add(new FourierCoefficient(0.1, 0.05));
	}
	public SurfaceShape(double period, double shift, ArrayList fourierCoefficients) {
		this.period = period;
	    this.shift = shift;
	    this.fourierCoefficients = fourierCoefficients;
	}
	
	private double period = Complex.TWO_PI;
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
	
	public void setFourierCoefficients(ArrayList c) throws ObjectIsnotEditableException {
		if (!isEditable()) throw new ObjectIsnotEditableException();
	    fourierCoefficients = (ArrayList)c.clone();
	    modelWasChangedEvent();
	}
	
	public SurfaceShape nonDimensioning(){
		double nonDimensionalPeriod = Complex.TWO_PI;
		double nonDimensionalShift = shift*Complex.TWO_PI/period;
		ArrayList nonDimensionalFourierCoefficients = (ArrayList) fourierCoefficients.clone();
		for (int j=0;j<fourierCoefficients.size();j++){
			FourierCoefficient coef = (FourierCoefficient) fourierCoefficients.get(j);
			double nonDimenisonalCosCoef = coef.getCoefficientOfCosinus()*Complex.TWO_PI/period;
			double nonDimenisonalSinCoef = coef.getCoefficientOfSinus()*Complex.TWO_PI/period;
			FourierCoefficient nonDimensionCoef = new FourierCoefficient( nonDimenisonalCosCoef,  nonDimenisonalSinCoef); 
			nonDimensionalFourierCoefficients.set(j,nonDimensionCoef);
		}
		return new SurfaceShape(nonDimensionalPeriod, nonDimensionalShift, nonDimensionalFourierCoefficients);
	}
}
