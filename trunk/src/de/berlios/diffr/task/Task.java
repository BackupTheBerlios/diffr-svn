package de.berlios.diffr.task;

import java.util.*;
import de.berlios.diffr.*;
import de.berlios.diffr.inputData.*;
import de.berlios.diffr.result.*;
import de.berlios.diffr.algorithms.*;
import de.berlios.diffr.exceptions.*;

public class Task extends Model {
	private InputData inputData;
	private Result result = null;
	private Algorithm algorithm;
	
	private transient boolean taskIsSolving = false;
	private transient Thread solveThread = null;
	private transient AlgorithmTypes algorithms;
	
	private int state = 0;
	public static final int resultIsnotCalculateState = 0;
	public static final int taskIsSolvingState = 1;
	public static final int taskStoppedState = 2;
	public static final int errorInAlgorithmState = 3;
	public static final int resultIsCalculateState = 4; 
	
	public Task(AlgorithmTypes algorithms, InputData initialInputData, Algorithm initialAlgorithm) {
		this.algorithms = algorithms;
		inputData = initialInputData;
		algorithm = initialAlgorithm;
		inputData.addModelChangingListener(new ModelChangingListener() {
			public void modelWasChanged(Model model) {
				nullResult();
			}
		});
	}
	
	public AlgorithmTypes getAlgorithms() {
		return algorithms;
	}
	
	public void restorationAfterSerialization() {
		inputData.restorationAfterSerialization();
		inputData.addModelChangingListener(new ModelChangingListener() {
			public void modelWasChanged(Model model) {
				nullResult();
			}
		});
	}
	
	public void restorationAfterSerialization(AlgorithmTypes algorithms) {
		this.algorithms = algorithms;
		restorationAfterSerialization();
	}
	
	public InputData getInputData() {
		return inputData;
	}
	public Result getResult() {
		return result;
	}
	public Algorithm getAlgorithm() {
		return algorithm;
	}
	
	public int getState() {
		return state;
	}
	
	public void start() throws TaskIsSolvingException {
		if (taskIsSolving) throw new TaskIsSolvingException();
		solveThread = new Thread() {
			public void run() {
				taskIsSolving = true;
				algorithm.setEditable(false);
				try {
					result = algorithm.run(inputData);
					state = resultIsCalculateState;
				} catch (Exception e) {
					state = errorInAlgorithmState;
				}
				algorithm.setEditable(true);
				taskIsSolving = false;
				inputData.setEditable(true);
				modelWasChangedEvent();
			}
		};
		state = taskIsSolvingState;
		inputData.setEditable(false);
		solveThread.start();
		modelWasChangedEvent();
	}
	public void stop() throws TaskIsnotSolvingException {
		if (!taskIsSolving) throw new TaskIsnotSolvingException();
		solveThread.stop();
		taskIsSolving = false;
		inputData.setEditable(true);
		algorithm.setEditable(true);
		state = taskStoppedState;
		modelWasChangedEvent();
	}
	
	public void setAlgorithm(Algorithm algorithm) throws TaskIsSolvingException {
		if (taskIsSolving) throw new TaskIsSolvingException();
		this.algorithm = algorithm;
		nullResult();
	}
	
	public void nullResult() {
		result = null;
		state = resultIsnotCalculateState;
		modelWasChangedEvent();
	}
}
