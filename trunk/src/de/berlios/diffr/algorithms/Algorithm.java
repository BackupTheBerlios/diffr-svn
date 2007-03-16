package de.berlios.diffr.algorithms;

import de.berlios.diffr.result.*;
import de.berlios.diffr.inputData.*;
import de.berlios.diffr.exceptions.*;
import de.berlios.diffr.*;

public abstract class Algorithm extends Model {
	protected AlgorithmParameter[] parameters = new AlgorithmParameter[0];
	private AlgorithmType algorithmType;
	public Algorithm(AlgorithmType algorithmType) {
		this.algorithmType = algorithmType;
	}
	public AlgorithmParameter[] getAlgorithmParameters() {
		return parameters;
	}
	
	public AlgorithmType getAlgorithmType() {
		return algorithmType;
	}
	
	public abstract Result run(InputData inputData) throws ErrorInAlgorithmException;
}
