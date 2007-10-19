package de.berlios.diffr.task;

import java.io.Serializable;
import java.util.*;
import de.berlios.diffr.*;
import de.berlios.diffr.algorithms.*;

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
	
	public ArrayList getAlgorithmTypes() {
		return (ArrayList) algorithmTypes.clone();
	}
}
