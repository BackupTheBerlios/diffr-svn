package de.berlios.diffr.task;

import de.berlios.diffr.*;
import de.berlios.diffr.inputData.*;
import de.berlios.diffr.result.*;
import de.berlios.diffr.algorithms.*;

public class Task extends Model {
	private InputData inputData;
	private Result result = null;
	private Algorithm algorithm;
	
	public Task(InputData initialInputData, Algorithm initialAlgorithm) {
		ModelChangingListener changingListener = new ModelChangingListener() {
			public void modelWasChanged(Model model) {
				nullResult();
			}
		};
		inputData = initialInputData;
		algorithm = initialAlgorithm;
		inputData.addModelChangingListener(changingListener);
	}
	
	public InputData getInputData() {
		return inputData;
	}
	public Result getResult() {
		return result;
	}
	
	public void start() {
		
	}
	public void stop() {
		
	}
	
	public void setAlgorithm(Algorithm algorithm) {
		this.algorithm = algorithm;
		nullResult();
	}
	
	public void nullResult() {
		result = null;
		modelWasChangedEvent();
	}
}
