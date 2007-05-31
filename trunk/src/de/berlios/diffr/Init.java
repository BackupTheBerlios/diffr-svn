package de.berlios.diffr;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import de.berlios.diffr.task.*;
import de.berlios.diffr.inputData.inputDataForVerySimpleTask.*;
import de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface.*;
import de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface.periodicSurface.*;
import de.berlios.diffr.inputData.*;
import de.berlios.diffr.algorithms.*;
import de.berlios.diffr.algorithms.addedAlgorithms.*;
import java.util.*;

public class Init {
	public static void main(String[] args) {
		new Init();
	}
	
	private JFrame frame = new JFrame("Diffr6");
	private Container cont = frame.getContentPane();
	private JMenuBar menuBar;
	private ArrayList taskTypes = new ArrayList();
	private Task currentTask = null;
	
	public Init() {
		loadTaskTypes();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});
		cont.setLayout(new BorderLayout());
		frame.setSize(500, 500);
		frame.setVisible(true);
		setTask(loadLastSavedTask());
	}
	
	private void setTask(Task newCurrentTask) {
		cont.removeAll();
		currentTask = newCurrentTask;
		TaskView taskView = new TaskView(currentTask);
		cont.add(taskView);
		menuBar = new JMenuBar();
		menuBar.add(newFileMenu());
		menuBar.add(taskView.getTaskMenu());
		menuBar.add(newHelpMenu());
		frame.setJMenuBar(menuBar);
		cont.validate();
	}
	
	private void newTask() {
		cont.removeAll();
		Box chooseBox = Box.createVerticalBox();
		JComboBox comboBox = new JComboBox();
		Iterator it = taskTypes.iterator();
		while (it.hasNext()) {
			comboBox.addItem(it.next());
		}
		comboBox.setSelectedIndex(-1);
		comboBox.setMaximumSize(new Dimension(500, 20));
		chooseBox.add(Box.createVerticalStrut(30));
		chooseBox.add(new JLabel("Choose task type:"));
		chooseBox.add(comboBox);
		cont.add(chooseBox);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTask(((TaskType)((JComboBox)e.getSource()).getSelectedItem()).newTask());
			}
		});
		cont.validate();
	}
	
	private Task loadLastSavedTask() {
		// This code is temporarily
		return ((TaskType)taskTypes.get(1)).newTask();
	}
	
	private void saveCurrentTask() {
		
	}
	
	private void loadTaskTypes() {
		// This code is temporarily
		InputData inputData1 = new InputData(new PlaneMirrowSurface(), new VerySimpleWave());
		AlgorithmType algorithmType1 =
			new AlgorithmType("Algorithm for very simple task",
					"petr_mikheev", "1.0", AlgorithmForVerySimpleTask.class);
		Algorithm algorithm1 = new AlgorithmForVerySimpleTask(algorithmType1);
		TaskType taskType1 = new TaskType("Very simple task", inputData1, algorithm1);
		taskType1.addAlgorithmType(new AlgorithmType("Algorithm for very simple task",
					"petr_mikheev", "2.0", AlgorithmForVerySimpleTask2.class));
		taskTypes.add(taskType1);
		
		InputData inputData2 = new InputData(new PeriodicSurface(), new ImpingingPlaneWave());
		AlgorithmType algorithmType2 =
			new AlgorithmType("Small perturbation algorithm",
					"andrmikheev", "0.01", SmallPerturbationAlgorithm.class);
		Algorithm algorithm2 = new SmallPerturbationAlgorithm(algorithmType2);
		TaskType taskType2 = new TaskType("Diffraction of plane wave on periodic surface", inputData2, algorithm2);
		taskTypes.add(taskType2);
	}
	
	private JMenu newFileMenu() {
		JMenu menu = new JMenu("File");
		JMenuItem newTaskItem = new JMenuItem("New task");
		newTaskItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newTask();
			}
		});
		menu.add(newTaskItem);
		menu.addSeparator();
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		});
		menu.add(exitItem);
		return menu;
	}
	
	private JMenu newHelpMenu() {
		JMenu menu = new JMenu("Help");
		menu.addSeparator();
		JMenuItem aboutItem = new JMenuItem("About Diffr6");
		menu.add(aboutItem);
		return menu;
	}
	
	private void exit() {
		saveCurrentTask();
		System.exit(0);
	}
}
