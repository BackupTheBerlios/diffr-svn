package de.berlios.diffr.algorithms;

import Org.netlib.math.complex.Complex;

public class DimensionData {
	
    private Complex amplitude;
    private double period;

    public Complex getAmplitude() {return amplitude;}
    public double getPeriod() {return period;}

    public void setAmplitude(Complex amplitude){
    	this.amplitude = amplitude;
    }
    public void setPeriod(double period){
    	this.period = period;
    }
    


}
