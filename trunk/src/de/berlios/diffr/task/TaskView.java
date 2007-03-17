package de.berlios.diffr.task;

import java.awt.*;
import java.awt.event.*;
import de.berlios.diffr.*;
import de.berlios.diffr.inputData.*;
import de.berlios.diffr.result.*;
import de.berlios.diffr.exceptions.*;
import de.berlios.diffr.algorithms.*;

import javax.swing.*;

public class TaskView extends View {
	
	private Task task = null;
	private SmallInputDataView smallInputDataView;
	private InputDataView inputDataView;
	private AlgorithmChooser algorithmChooser;
	private ResultView resultView = new ResultView();
	private JLabel stateLabel = new JLabel("State:");
	private JLabel state = new JLabel();
	private JTabbedPane tabbedPane = new JTabbedPane();
	
	private void tryChangeAlgorithm(Algorithm algorithm) {
		try {
			task.setAlgorithm(algorithm);
			algorithmChooser.setAlgorithm(algorithm);
		} catch (TaskIsSolvingException e) {
			JOptionPane.showMessageDialog(this, "You can`t change algorithm while task running");
		}
	}
	
	public TaskView(Task task) {
		this.task = task;
		task.addModelChangingListener(changingListener);
		smallInputDataView = new SmallInputDataView(task.getInputData());
		inputDataView = new InputDataView (task.getInputData());
		algorithmChooser = new AlgorithmChooser(task.getTaskType());
		algorithmChooser.addAlgorithmChooserListener(new AlgorithmChooserListener() {
			public void newAlgorithmWasChoosed(Algorithm algorithm) {
				tryChangeAlgorithm(algorithm);
			}
		});
		resultView.setResult(task.getResult());
		renewState();
		
		JPanel statePanel = new JPanel();
		statePanel.setLayout(new BorderLayout());
		statePanel.add(state, BorderLayout.SOUTH);
		statePanel.add(stateLabel, BorderLayout.NORTH);
		
		tabbedPane.add("Input data", inputDataView);
		tabbedPane.add("Result", resultView);
		tabbedPane.add("Algorithm", algorithmChooser);
		
		this.setLayout(new BorderLayout());
		this.add(statePanel, BorderLayout.SOUTH);
		this.add(smallInputDataView, BorderLayout.NORTH);
		this.add(tabbedPane, BorderLayout.CENTER);
		
		startItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start();
			}
		});
		
		stopItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stop();
			}
		});
		
		taskMenu.add(startItem);
		taskMenu.add(stopItem);
		
		renewTaskView();
	}
	
	private void start() {
		try {
			task.start();
		} catch (TaskIsSolvingException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(this, "Task is already solving");
		}
	}
	
	private void stop() {
		try {
			task.stop();
		} catch (TaskIsnotSolvingException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(this, "Task isn`t solving");
		}
	}
	
	private JMenu taskMenu = new JMenu("Task");
	private JMenuItem startItem = new JMenuItem("Start");
	private JMenuItem stopItem = new JMenuItem("Stop");
	
	public JMenu getTaskMenu() {
		return taskMenu;
	}
	
	private ModelChangingListener changingListener = new ModelChangingListener() {
		public void modelWasChanged(Model task) {
			renewTaskView();
		}
	};
	
	private void renewTaskView() {
		renewState();
		renewResult();
		changeMenuItemsEnabled();
	}
	
	private void renewResult() {
		if ((task.getState() == Task.resultIsCalculateState) ||
				(task.getState() == Task.resultIsnotCalculateState))
			resultView.setResult(task.getResult());
	}
	
	private void changeMenuItemsEnabled() {
		if (task.getState() == Task.taskIsSolvingState) {
			startItem.setEnabled(false);
			stopItem.setEnabled(true);
		} else {
			startItem.setEnabled(true);
			stopItem.setEnabled(false);
		}
	}
	
	private void renewState() {
		switch (task.getState()) {
			case Task.errorInAlgorithmState: state.setText("Error");
			break;
			case Task.resultIsCalculateState: state.setText("Result is calculated");
			break;
			case Task.resultIsnotCalculateState: state.setText("Result isn`t calculated");
			break;
			case Task.taskIsSolvingState: state.setText("Task is solving...");
			break;
			case Task.taskStoppedState: state.setText("Task is stopped");
			break;
		}
	}
}
