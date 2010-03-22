package de.berlios.diffr.task;

import de.berlios.diffr.Model;
import de.berlios.diffr.ModelChangingListener;
import de.berlios.diffr.algorithms.Algorithm;
import de.berlios.diffr.algorithms.AlgorithmTypes;
import de.berlios.diffr.exceptions.TaskIsSolvingException;
import de.berlios.diffr.exceptions.TaskIsnotSolvingException;
import de.berlios.diffr.inputData.IncidentWave;
import de.berlios.diffr.inputData.InputData;
import de.berlios.diffr.inputData.SeriesInputData;
import de.berlios.diffr.result.Result;
import de.berlios.diffr.result.SeriesResult;

public class TaskSeries extends Model {
	private static final long serialVersionUID = 1L;
	private SeriesInputData inputData;
	private SeriesResult result = null;
	private Algorithm algorithm;
	
	private int state = 0;
	private transient AlgorithmTypes algorithms;
	private transient boolean taskIsSolving = false;
	private transient Thread solveThread = null;

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
	
	public void start() throws TaskIsSolvingException {
		if (taskIsSolving) throw new TaskIsSolvingException();
		solveThread = new Thread() {
			public void run() {
				taskIsSolving = true;
				algorithm.setEditable(false);
				try {
					int points = inputData.getIncidentWaveSeries().getPointsNumber();
					Result[] results = new Result[points];
					for (int i=0; i<points; ++i) {
						IncidentWave wave = inputData.getIncidentWaveSeries().getIncidentWave(i);
						InputData input = new InputData(inputData.getSurface(), wave);
						results[i] = algorithm.run(input);
					}
					result = new SeriesResult(results);
					state = Task.resultIsCalculateState;
				} catch (Exception e) {
					e.printStackTrace();
					state = Task.errorInAlgorithmState;
				}
				algorithm.setEditable(true);
				taskIsSolving = false;
				inputData.setEditable(true);
				modelWasChangedEvent();
			}
		};
		state = Task.taskIsSolvingState;
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
		state = Task.taskStoppedState;
		modelWasChangedEvent();
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
