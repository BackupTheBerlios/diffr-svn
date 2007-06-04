package de.berlios.diffr;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import de.berlios.diffr.task.*;
import de.berlios.diffr.exceptions.TaskIsSolvingException;
import de.berlios.diffr.exceptions.TaskIsnotSolvingException;
import de.berlios.diffr.exceptions.UnknownTaskTypeException;
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
	private ArrayList taskTypes;
	private Task currentTask = null;
	private JFileChooser fileChooser = new JFileChooser();
	
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
		menuBar.add(newAlgorithmMenu());
		menuBar.add(newHelpMenu());
		frame.setJMenuBar(menuBar);
		cont.validate();
		cont.repaint();
		frame.validate();
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
	
	private void saveTask() {
		fileChooser.showSaveDialog(frame);
		File file = fileChooser.getSelectedFile();
		if (file != null) {
			try {
				writeTask(file.getAbsolutePath(), currentTask);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TaskIsSolvingException e) {
				JOptionPane.showMessageDialog(frame, "You can`t save task when it running");
			}
		}
	}
	
	private void loadTask() {
		fileChooser.showOpenDialog(frame);
		File file = fileChooser.getSelectedFile();
		if (file != null) {
			try {
				setTask(readTask(file.getAbsolutePath()));
			} catch (UnknownTaskTypeException e) {
				JOptionPane.showMessageDialog(frame, "Unknown task type");
				e.printStackTrace();
			}catch (Exception e) {
				JOptionPane.showMessageDialog(frame, "Incorrect format of file");
				e.printStackTrace();
			}
		}
	}
	
	private Task readTask(String file) throws IOException, ClassNotFoundException, UnknownTaskTypeException {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
		Task task = (Task)in.readObject();
		task.restorationAfterSerialization(taskTypes);
		return task;
	}
	
	private void writeTask(String file, Task task) throws IOException, TaskIsSolvingException {
		if (task.getState() == Task.taskIsSolvingState) throw new TaskIsSolvingException();
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
		out.writeObject(task);
		out.close();
	}
	
	private Task loadLastSavedTask() {
		try {
			return readTask("autosave.task");
		} catch (Exception e) {
			e.printStackTrace();
			return ((TaskType)taskTypes.get(0)).newTask();
		}
	}
	
	private void saveCurrentTask() {
		try {
			writeTask("autosave.task", currentTask);
		} catch (TaskIsSolvingException e) {
			try {
				currentTask.stop();
				saveCurrentTask();
			} catch (TaskIsnotSolvingException e1) {e1.printStackTrace();}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void loadTaskTypes() {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("taskTypes.d6"));
			taskTypes = (ArrayList)in.readObject();
			Iterator i = taskTypes.iterator();
			while (i.hasNext())
				((TaskType)i.next()).getInitialInputData().restorationAfterSerialization();
		} catch (Exception e) {
			e.printStackTrace();
			
			taskTypes = new ArrayList();
			InputData inputData1 = new InputData(new PlaneMirrowSurface(), new VerySimpleWave());
			AlgorithmType algorithmType1 =
				new AlgorithmType("Algorithm for very simple task",
						"petr_mikheev", "1.0", AlgorithmForVerySimpleTask.class);
			Algorithm algorithm1 = new AlgorithmForVerySimpleTask(algorithmType1);
			TaskType taskType1 = new TaskType("Very simple task", inputData1, algorithm1);
			taskType1.addAlgorithmType(new AlgorithmType("Algorithm for very simple task",
						"petr_mikheev", "2.0", AlgorithmForVerySimpleTask2.class));
			
			InputData inputData2 = new InputDataForDiffractionOfPlaneWaveOnPeriodicSurface(new PeriodicSurface(), new ImpingingPlaneWave());
			AlgorithmType algorithmType2 =
				new AlgorithmType("Small perturbation algorithm",
						"andrmikheev", "0.01", SmallPerturbationAlgorithm.class);
			Algorithm algorithm2 = new SmallPerturbationAlgorithm(algorithmType2);
			TaskType taskType2 = new TaskType("Diffraction of plane wave on periodic surface", inputData2, algorithm2);
			
			taskTypes.add(taskType2);
			taskTypes.add(taskType1);			
		}
	}
	
	private void saveTaskTypes() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("taskTypes.d6"));
			out.writeObject(taskTypes);
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		JMenuItem loadItem = new JMenuItem("Load task");
		loadItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadTask();
			}
		});
		menu.add(loadItem);
		JMenuItem saveItem = new JMenuItem("Save task");
		saveItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveTask();
			}
		});
		menu.add(saveItem);
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
	
	private JMenu newAlgorithmMenu() {
		JMenu menu = new JMenu("Algorithms");
		JMenuItem addAlgorithmItem = new JMenuItem("Add algorithm for current task");
		addAlgorithmItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tryLoadAlgorithm();
			}
		});
		menu.add(addAlgorithmItem);
		return menu;
	}
	
	private class AddAlgorithmDialog extends JDialog {
		private JTextField title = new JTextField();
		private JTextField autor = new JTextField();
		private JTextField version = new JTextField();
		private JTextField className = new JTextField();
		private JButton okButton = new JButton("OK");
		private JButton cancelButton = new JButton("Cancel");
		AddAlgorithmDialog() {
			super(frame, "Add algorithm", true);
			this.setSize(200, 300);
			Container c = this.getContentPane();
			c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
			c.add(Box.createVerticalStrut(10));
			c.add(new JLabel("Title"));
			c.add(title);
			c.add(Box.createVerticalStrut(10));
			c.add(new JLabel("Autor"));
			c.add(autor);
			c.add(Box.createVerticalStrut(10));
			c.add(new JLabel("Version"));
			c.add(version);
			c.add(Box.createVerticalStrut(10));
			c.add(new JLabel("Class"));
			c.add(className);
			c.add(Box.createVerticalStrut(10));
			c.add(okButton);
			c.add(Box.createVerticalStrut(10));
			c.add(cancelButton);
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
				}
			});
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						currentTask.getTaskType().addAlgorithmType(
								new AlgorithmType(title.getText(), autor.getText(), version.getText(), Class.forName(className.getText()))
						);
						setVisible(false);
					} catch (ClassNotFoundException e1) {
						JOptionPane.showMessageDialog(null, "Specified class doesn`t exist");
					}
				}
			});
		}
	}
	
	private void tryLoadAlgorithm() {
		new AddAlgorithmDialog().setVisible(true);
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
		saveTaskTypes();
		System.exit(0);
	}
}
