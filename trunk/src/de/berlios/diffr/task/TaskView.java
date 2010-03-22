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
	private static final long serialVersionUID = 1L;
	private Task task = null;
	private SmallInputDataView smallInputDataView;
	private JButton[] startButtons = new JButton[3];
	private InputDataView inputDataView;
	private AlgorithmChooser algorithmChooser;
	private ResultView resultView;
	private JLabel stateLabel = new JLabel("State:");
	private JLabel state = new JLabel();
	private JTabbedPane tabbedPane = new JTabbedPane();
	private boolean active = true;
	
	public void setActive(boolean active) {
		this.active = active;
	}

	private ActionListener startListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (active) start();
		}
	};
	private ActionListener stopListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (active) stop();
		}
	};
	
	private void changeAlgorithm(Algorithm algorithm) {
		try {
			task.setAlgorithm(algorithm);
		} catch (TaskIsSolvingException e) {
			e.printStackTrace();
		}
	}
	
	public TaskView(Task t, JMenuItem startItem, JMenuItem stopItem) {
		this.task = t;
		this.startItem = startItem;
		this.stopItem = stopItem;
		for (int a=0;a<3;a++) startButtons[a] = new JButton();
		task.addModelChangingListener(changingListener);
		smallInputDataView = new SmallInputDataView(task.getInputData());
		inputDataView = new InputDataView (task.getInputData(), startButtons[0]);
		resultView = new ResultView(startButtons[1]);
		algorithmChooser = new AlgorithmChooser(task.getAlgorithms(), task.getAlgorithm(), startButtons[2]);
		algorithmChooser.addAlgorithmChooserListener(new AlgorithmChooserListener() {
			public void newAlgorithmWasChoosed(Algorithm algorithm) {
				changeAlgorithm(algorithm);
			}
			public void algorithmParametersWereChanged() {
				task.nullResult();
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
		
		JPanel taskBox = new JPanel();
		taskBox.setLayout(new GridLayout(1, 2));
		taskBox.add(tabbedPane);
		taskBox.add(smallInputDataView);
		
		this.setLayout(new BorderLayout());
		this.add(statePanel, BorderLayout.SOUTH);
		this.add(taskBox, BorderLayout.CENTER);
		
		startItem.addActionListener(startListener);
		stopItem.addActionListener(stopListener);
		
		renewTaskView();
	}
	
	private void start() {
		try {
			task.start();
		} catch (TaskIsSolvingException e1) {
			JOptionPane.showMessageDialog(this, "Task is already solving");
		}
	}
	
	private void stop() {
		try {
			task.stop();
		} catch (TaskIsnotSolvingException e1) {
			JOptionPane.showMessageDialog(this, "Task isn`t solving");
		}
	}
	
	private JMenuItem startItem;
	private JMenuItem stopItem;
	
	private ModelChangingListener changingListener = new ModelChangingListener() {
		public void modelWasChanged(Model task) {
			renewTaskView();
		}
	};
	
	private void renewTaskView() {
		renewState();
		renewResult();
		changeMenuItemsEnabled();
		if (task.getState() == Task.resultIsCalculateState) tabbedPane.setSelectedComponent(resultView);
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
		for (JButton startButton : startButtons) {
			if (task.getState() == Task.taskIsSolvingState) {
				for (ActionListener l : startButton.getActionListeners())
					startButton.removeActionListener(l);
				startButton.setText("Stop calculation");
				startButton.addActionListener(stopListener);
			} else {
				for (ActionListener l : startButton.getActionListeners())
					startButton.removeActionListener(l);
				startButton.setText("Start calculation");
				startButton.addActionListener(startListener);
			}
		}
	}
	
	private void renewState() {
		state.setForeground(Color.black);
		switch (task.getState()) {
			case Task.errorInAlgorithmState: state.setText("Input data not supported");
			break;
			case Task.resultIsCalculateState: state.setText("Result is calculated");
				state.setForeground(Color.red);
			break;
			case Task.resultIsnotCalculateState: state.setText("Result isn`t calculated");
			break;
			case Task.taskIsSolvingState: state.setText("Task is being solved ...");
			break;
			case Task.taskStoppedState: state.setText("Task is stopped");
			break;
			case Task.resultIsOutOfDateState: state.setText("Result is out of date");
			break;
		}
	}
}
