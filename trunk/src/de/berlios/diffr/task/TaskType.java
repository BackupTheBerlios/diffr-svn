package de.berlios.diffr.task;

import java.util.*;
import de.berlios.diffr.Model;
import de.berlios.diffr.inputData.*;
import de.berlios.diffr.algorithms.*;

public class TaskType extends Model {
	private final String taskName;
	private InputData initialInputData;
	private Algorithm initialAlgorithm;
	private ArrayList algorithmTypes = new ArrayList();
	
	public boolean equals(Object o) {
		if (o == null) return false;
		if (o.getClass() == this.getClass()) {
			TaskType t = (TaskType)o;
			if (t.taskName.equals(taskName) && t.initialInputData.equals(initialInputData));
				return true;
		}
		return false;
	}
	
	public TaskType(String taskName, InputData initialInputData, Algorithm initialAlgorithm) {
		this.taskName = taskName;
		this.initialInputData = initialInputData;
		this.initialAlgorithm = initialAlgorithm;
		algorithmTypes.add(initialAlgorithm.getAlgorithmType());
	}
	
	public String getTaskName() {
		return taskName;
	}
	
	public String toString() {
		return taskName;
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
	
	public Task newTask() {
		return new Task(this, initialInputData, initialAlgorithm);
	}
	
	public Algorithm getInitialAlgorithm() {
		return initialAlgorithm;
	}
	
	public InputData getInitialInputData() {
		return initialInputData;
	}
}
