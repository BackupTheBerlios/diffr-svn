package de.berlios.diffr.task;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import de.berlios.diffr.Model;
import de.berlios.diffr.ModelChangingListener;
import de.berlios.diffr.View;
import de.berlios.diffr.algorithms.Algorithm;
import de.berlios.diffr.algorithms.AlgorithmChooser;
import de.berlios.diffr.algorithms.AlgorithmChooserListener;
import de.berlios.diffr.exceptions.TaskIsSolvingException;
import de.berlios.diffr.exceptions.TaskIsnotSolvingException;
import de.berlios.diffr.inputData.IncidentWave;
import de.berlios.diffr.inputData.InputData;
import de.berlios.diffr.inputData.SeriesInputDataView;
import de.berlios.diffr.inputData.SmallSeriesInputDataView;
import de.berlios.diffr.result.ReflectedFieldView;
import de.berlios.diffr.result.SeriesResultView;

public class TaskSeriesView extends View {
	private static final long serialVersionUID = 1L;
	private TaskSeries series = null;
	private JButton[] startButtons = new JButton[3];
	private AlgorithmChooser algorithmChooser;
	private JLabel stateLabel = new JLabel("State:");
	private JLabel state = new JLabel();
	private JTabbedPane tabbedPane = new JTabbedPane();
	private boolean active = true;
	private SeriesInputDataView inputDataView;
	private SeriesResultView resultView;
	private SmallSeriesInputDataView smallInputDataView;
	private ReflectedFieldView currentReflectedFieldView = null;
	
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
	
	JPanel rightPanel = null;
	public TaskSeriesView(TaskSeries t, JMenuItem startItem, JMenuItem stopItem) {
		this.series = t;
		this.startItem = startItem;
		this.stopItem = stopItem;
		for (int a=0;a<3;a++) startButtons[a] = new JButton();
		series.addModelChangingListener(changingListener);
		smallInputDataView = new SmallSeriesInputDataView(series.getInputData());
		inputDataView = new SeriesInputDataView (series.getInputData(), startButtons[0]);
		resultView = new SeriesResultView(startButtons[1]) {
			private static final long serialVersionUID = 1L;
			public void currentTaskChanged(int point) {
				smallInputDataView.setCurrentWave(point);
				if (currentReflectedFieldView!=null)
					rightPanel.remove(currentReflectedFieldView);
				if (series.getResult()!=null) {
					currentReflectedFieldView = new ReflectedFieldView(series.getResult().getResult(point));
					rightPanel.add(currentReflectedFieldView);
					rightPanel.validate();
				}
			}
			public void switchMode(int point) {
				switchToAloneTask(point);
			}
		};
		algorithmChooser = new AlgorithmChooser(series.getAlgorithms(), series.getAlgorithm(), startButtons[2]);
		algorithmChooser.addAlgorithmChooserListener(new AlgorithmChooserListener() {
			public void newAlgorithmWasChoosed(Algorithm algorithm) {
				changeAlgorithm(algorithm);
			}
			public void algorithmParametersWereChanged() {
				series.nullResult();
			}
		});
		resultView.setResult(series.getResult());
		renewState();
		
		JPanel statePanel = new JPanel();
		statePanel.setLayout(new BorderLayout());
		statePanel.add(state, BorderLayout.SOUTH);
		statePanel.add(stateLabel, BorderLayout.NORTH);
	
		tabbedPane.add("Input data", inputDataView);
		tabbedPane.add("Result", resultView);
		tabbedPane.add("Algorithm", algorithmChooser);
		
		rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(2, 1));
		rightPanel.add(smallInputDataView);
		
		JPanel taskBox = new JPanel();
		taskBox.setLayout(new GridLayout(1, 2));
		taskBox.add(tabbedPane);
		taskBox.add(rightPanel);
		
		this.setLayout(new BorderLayout());
		this.add(statePanel, BorderLayout.SOUTH);
		this.add(taskBox, BorderLayout.CENTER);
		
		startItem.addActionListener(startListener);
		stopItem.addActionListener(stopListener);		
		renewTaskView();
		
		resultView.currentTaskChanged(0);
	}
	
	private void changeAlgorithm(Algorithm algorithm) {
		try {
			series.setAlgorithm(algorithm);
		} catch (TaskIsSolvingException e) {
			e.printStackTrace();
		}
	}
	
	private void start() {
		try {
			series.start();
		} catch (TaskIsSolvingException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(this, "Task is already solving");
		}
	}
	
	private void stop() {
		try {
			series.stop();
		} catch (TaskIsnotSolvingException e1) {
			e1.printStackTrace();
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
		if (series.getState() == Task.resultIsCalculateState) tabbedPane.setSelectedComponent(resultView);
	}
	
	private void renewResult() {
		if ((series.getState() == Task.resultIsCalculateState) ||
				(series.getState() == Task.resultIsnotCalculateState))
			resultView.setResult(series.getResult());
	}
	
	private void changeMenuItemsEnabled() {
		if (series.getState() == Task.taskIsSolvingState) {
			startItem.setEnabled(false);
			stopItem.setEnabled(true);
		} else {
			startItem.setEnabled(true);
			stopItem.setEnabled(false);
		}
		for (JButton startButton : startButtons) {
			if (series.getState() == Task.taskIsSolvingState) {
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
		switch (series.getState()) {
			case Task.errorInAlgorithmState: state.setText("Input data not supported");
			break;
			case Task.resultIsCalculateState: state.setText("Result is calculated");
				state.setForeground(Color.red);
			break;
			case Task.resultIsnotCalculateState: state.setText("Result isn`t calculated");
			break;
			case Task.taskIsSolvingState: state.setText("Task series is being solved ...");
			break;
			case Task.taskStoppedState: state.setText("Task series is stopped");
			break;
			case Task.resultIsOutOfDateState: state.setText("Result is out of date");
			break;
		}
	}
	
	private void switchToAloneTask(int point) {
		IncidentWave wave = series.getInputData().getIncidentWaveSeries().getIncidentWave(point);
		InputData input = new InputData(series.getInputData().getSurface(), wave);
		Task task = new Task(series.getAlgorithms(), input, series.getAlgorithm());
		switchMode(task);
	}
	
	public void switchMode(Task task) {}
}
