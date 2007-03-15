package de.berlios.diffr.task;

import java.awt.BorderLayout;
import java.awt.LayoutManager;

import de.berlios.diffr.*;
import de.berlios.diffr.inputData.*;
import de.berlios.diffr.result.*;
import de.berlios.diffr.exceptions.TaskIsnotSolvingException;
import javax.swing.*;

public class TaskView extends View {
	
	private Task task = null;
	private SmallInputDataView smallInputDataView = new SmallInputDataView();
	private InputDataView inputDataView = new InputDataView();
	private ResultView resultView = new ResultView();
	private JLabel stateLabel = new JLabel("State:");
	private JLabel state = new JLabel();
	private JTabbedPane tabbedPane = new JTabbedPane();
	
	public TaskView() {
		JPanel statePanel = new JPanel();
		statePanel.setLayout(new BorderLayout());
		statePanel.add(state, BorderLayout.SOUTH);
		statePanel.add(stateLabel, BorderLayout.NORTH);
		
		tabbedPane.add("Input data", inputDataView);
		tabbedPane.add("Result", resultView);
		
		this.setLayout(new BorderLayout());
		this.add(statePanel, BorderLayout.SOUTH);
		this.add(smallInputDataView, BorderLayout.NORTH);
		this.add(tabbedPane, BorderLayout.CENTER);
		renewState();
	}
	
	private ModelChangingListener changingListener = new ModelChangingListener() {
		public void modelWasChanged(Model task) {
			
		}
	};
	public void setTask(Task newTask) {
		if (task != null) {
			task.removeModelChangingListener(changingListener);
			try {
				task.stop();
			} catch (TaskIsnotSolvingException e) {}
		}
		task = newTask;
		task.addModelChangingListener(changingListener);
		smallInputDataView.setInputData(task.getInputData());
		inputDataView.setInputData(task.getInputData());
		resultView.setResult(task.getResult());
		renewState();
	}
	private void renewState() {
		if (task != null) {
			switch (task.getState()) {
				case Task.errorInAlgorithmState: state.setText("Error");
				break;
				case Task.resultIsCalculateState: state.setText("Result is calculate");
				break;
				case Task.resultIsnotCalculateState: state.setText("Result isn`t calculate");
				break;
				case Task.taskIsSolvingState: state.setText("Task is solving");
				break;
				case Task.taskStoppedState: state.setText("TaskStopped");
				break;
			}
		} else
			state.setText("Task isn`t choosed");
	}
}
