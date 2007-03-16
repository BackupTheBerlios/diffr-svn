package de.berlios.diffr.task;

import java.awt.*;
import java.awt.event.*;
import de.berlios.diffr.*;
import de.berlios.diffr.inputData.*;
import de.berlios.diffr.result.*;
import de.berlios.diffr.exceptions.*;

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
			case Task.resultIsCalculateState: state.setText("Result is calculate");
			break;
			case Task.resultIsnotCalculateState: state.setText("Result isn`t calculate");
			break;
			case Task.taskIsSolvingState: state.setText("Task is solving");
			break;
			case Task.taskStoppedState: state.setText("Task stopped");
			break;
		}
	}
}
