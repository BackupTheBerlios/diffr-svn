package de.berlios.diffr;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import de.berlios.diffr.task.*;

public class Init {
	public static void main(String[] args) {
		new Init();
	}
	
	private JFrame frame = new JFrame("Diffr");
	private Container cont = frame.getContentPane(); 
	
	public Init() {
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		cont.setLayout(new BorderLayout());
		TaskView taskView = new TaskView();
		//taskView.setTask(new Task(new InputData()));
		cont.add(taskView);
		frame.setSize(500, 500);
		frame.setVisible(true);
	}
}
