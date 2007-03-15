package de.berlios.diffr;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import de.berlios.diffr.task.*;
import de.berlios.diffr.inputData.inputDataForVerySimpleTask.*;
import de.berlios.diffr.inputData.*;
import de.berlios.diffr.algorithms.*;

public class Init {
	public static void main(String[] args) {
		new Init();
	}
	
	private JFrame frame = new JFrame("Diffr");
	private Container cont = frame.getContentPane(); 
	
	public Init() {
		
		InputData inputData = new InputData(new PlaneMirrowSurface(), new VerySimpleWave());
		TaskType taskType = new TaskType("Very simple task", inputData, new AlgorithmForVerySimpleTask());
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		cont.setLayout(new BorderLayout());
		TaskView taskView = new TaskView(taskType.newTask());
		cont.add(taskView);
		frame.setSize(500, 500);
		frame.setVisible(true);
	}
}
