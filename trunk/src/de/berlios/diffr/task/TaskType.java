package de.berlios.diffr.task;

import java.util.*;
import de.berlios.diffr.inputData.*;
import de.berlios.diffr.algorithms.*;

public class TaskType {
	private final String taskName;
	private InputData initialInputData;
	private Algorithm initialAlgorithm;
	private ArrayList algorithmTypes = new ArrayList();
	
	public TaskType(String taskName, InputData initialInputData, Algorithm initialAlgorithm) {
		this.taskName = taskName;
		this.initialInputData = initialInputData;
		this.initialAlgorithm = initialAlgorithm;
		algorithmTypes.add(initialAlgorithm.getClass());
	}
	
	public String getTaskName() {
		return taskName;
	}
	
	public void addAlgorithmType(Class algorithmType) {
		algorithmTypes.add(algorithmType);
	}
	
	public void removeAlgorithmType(Class algorithmType) {
		algorithmTypes.remove(algorithmType);
	}
	
	public ArrayList getAlgorithmTypes() {
		return (ArrayList)algorithmTypes.clone();
	}
	
	public Task newTask() {
		return new Task(initialInputData, initialAlgorithm);
	}
}
