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
	private SmallInputDataView smallInputDataView;
	private InputDataView inputDataView;
	private ResultView resultView = new ResultView();
	private JLabel stateLabel = new JLabel("State:");
	private JLabel state = new JLabel();
	private JTabbedPane tabbedPane = new JTabbedPane();
	
	public TaskView(Task task) {
		this.task = task;
		task.addModelChangingListener(changingListener);
		smallInputDataView = new SmallInputDataView(task.getInputData());
		inputDataView = new InputDataView (task.getInputData());
		resultView.setResult(task.getResult());
		renewState();
		
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
	}
	
	private ModelChangingListener changingListener = new ModelChangingListener() {
		public void modelWasChanged(Model task) {
			
		}
	};
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
