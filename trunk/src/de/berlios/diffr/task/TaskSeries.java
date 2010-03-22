package de.berlios.diffr.task;

import de.berlios.diffr.Model;
import de.berlios.diffr.ModelChangingListener;
import de.berlios.diffr.algorithms.Algorithm;
import de.berlios.diffr.algorithms.AlgorithmTypes;
import de.berlios.diffr.exceptions.TaskIsSolvingException;
import de.berlios.diffr.inputData.SeriesInputData;
import de.berlios.diffr.result.SeriesResult;

public class TaskSeries extends Model {
	private static final long serialVersionUID = 1L;
	private SeriesInputData inputData;
	private SeriesResult result = null;
	private Algorithm algorithm;
	
	private int state = 0;
	private transient AlgorithmTypes algorithms;
	private transient boolean taskIsSolving = false;

	public TaskSeries(AlgorithmTypes algorithms, SeriesInputData initialInputData, Algorithm initialAlgorithm) {
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
	
	public SeriesInputData getInputData() {
		return inputData;
	}
	
	public SeriesResult getResult() {
		return result;
	}
	
	public Algorithm getAlgorithm() {
		return algorithm;
	}
	
	public void setAlgorithm(Algorithm algorithm) throws TaskIsSolvingException {
		if (taskIsSolving) throw new TaskIsSolvingException();
		this.algorithm = algorithm;
		nullResult();
	}
	
	public int getState() {
		return state;
	}
	
	public void nullResult() {
		if (result!=null) {
			state = Task.resultIsOutOfDateState;
			result.outOfDate();
		} else
			state = Task.resultIsnotCalculateState;
		modelWasChangedEvent();
	}
}
