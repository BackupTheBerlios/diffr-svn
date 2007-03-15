package de.berlios.diffr.algorithms;

import de.berlios.diffr.result.*;
import de.berlios.diffr.inputData.*;
import de.berlios.diffr.exceptions.*;
import de.berlios.diffr.*;

public abstract class Algorithm extends Model {
	private final String title;
	private final String autor;
	private final String version;
	protected AlgorithmParameter[] parameters = new AlgorithmParameter[0];
	
	public Algorithm(String title, String autor, String version) {
		this.title = title;
		this.autor = autor;
		this.version = version;
	}
	
	public AlgorithmParameter[] getAlgorithmParameters() {
		return parameters;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getAutor() {
		return autor;
	}
	
	public String getVersion() {
		return version;
	}
	
	public abstract Result run(InputData inputData) throws ErrorInAlgorithmException;
}
