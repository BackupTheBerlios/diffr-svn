package de.berlios.diffr.task;

import java.io.Serializable;
import java.util.*;
import de.berlios.diffr.*;
import de.berlios.diffr.exceptions.WrongTypeException;
import de.berlios.diffr.algorithms.*;

public class AlgorithmTypes extends Model implements Serializable {
	private ArrayList algorithmTypes = new ArrayList();
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
