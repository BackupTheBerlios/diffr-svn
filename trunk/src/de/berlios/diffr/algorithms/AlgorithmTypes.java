package de.berlios.diffr.algorithms;

import java.io.Serializable;
import java.util.*;
import de.berlios.diffr.*;

public class AlgorithmTypes extends Model implements Serializable {
	private static final long serialVersionUID = 1L;
	private ArrayList<AlgorithmType> algorithmTypes = new ArrayList<AlgorithmType>();
	public AlgorithmTypes(Algorithm initialAlgorithm) {
		algorithmTypes.add(initialAlgorithm.getAlgorithmType());
	}

	public void addAlgorithmType(AlgorithmType algorithmType) {
		algorithmTypes.add(algorithmType);
		modelWasChangedEvent();
	}
	
	public void removeAlgorithmType(AlgorithmType algorithmType) {
		algorithmTypes.remove(algorithmType);
		modelWasChangedEvent();
	}
	
	public ArrayList<AlgorithmType> getAlgorithmTypes() {
		return (ArrayList<AlgorithmType>) algorithmTypes.clone();
	}
}
