package de.berlios.diffr;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import de.berlios.diffr.task.*;
import de.berlios.diffr.inputData.inputDataForVerySimpleTask.*;
import de.berlios.diffr.inputData.*;
import de.berlios.diffr.algorithms.*;
import de.berlios.diffr.algorithms.addedAlgorithms.AlgorithmForVerySimpleTask;
import de.berlios.diffr.algorithms.addedAlgorithms.AlgorithmForVerySimpleTask2;

public class Init {
	public static void main(String[] args) {
		new Init();
	}
	
	private JFrame frame = new JFrame("Diffr");
	private Container cont = frame.getContentPane();
	private JMenuBar menuBar = new JMenuBar();
	
	public Init() {
		
		InputData inputData = new InputData(new PlaneMirrowSurface(), new VerySimpleWave());
		AlgorithmType algorithmType =
			new AlgorithmType("Algorithm for very simple task",
					"petr_mikheev", "1.0", AlgorithmForVerySimpleTask.class);
		Algorithm algorithm = new AlgorithmForVerySimpleTask(algorithmType);
		TaskType taskType = new TaskType("Very simple task", inputData, algorithm);
		taskType.addAlgorithmType(new AlgorithmType("Algorithm for very simple task",
					"petr_mikheev", "2.0", AlgorithmForVerySimpleTask2.class));
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		cont.setLayout(new BorderLayout());
		TaskView taskView = new TaskView(taskType.newTask());
		cont.add(taskView);
		menuBar.add(taskView.getTaskMenu());
		frame.setJMenuBar(menuBar);
		frame.setSize(500, 500);
		frame.setVisible(true);
	}
}
