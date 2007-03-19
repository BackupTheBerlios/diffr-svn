package de.berlios.diffr.algorithms;

import de.berlios.diffr.result.*;
import de.berlios.diffr.inputData.*;
import de.berlios.diffr.exceptions.*;
import de.berlios.diffr.*;

public abstract class Algorithm extends Model {
	protected DataString[] parameters = new DataString[0];
	private AlgorithmType algorithmType;
	public Algorithm(AlgorithmType algorithmType) {
		this.algorithmType = algorithmType;
	}
	public DataString[] getAlgorithmParameters() {
		return parameters;
	}
	
	public AlgorithmType getAlgorithmType() {
		return algorithmType;
	}
	
	public void setEditable(boolean b) {
		super.setEditable(b);
		for (int n = 0; n < parameters.length; n++) {
			parameters[n].setEditable(b);
		}
	}
	
	public abstract Result run(InputData inputData) throws ErrorInAlgorithmException;
}
