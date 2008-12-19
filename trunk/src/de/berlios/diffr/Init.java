package de.berlios.diffr;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import de.berlios.diffr.task.*;
import de.berlios.diffr.exceptions.TaskIsSolvingException;
import de.berlios.diffr.exceptions.TaskIsnotSolvingException;
import de.berlios.diffr.exceptions.WrongTypeException;
import de.berlios.diffr.inputData.*;
import de.berlios.diffr.algorithms.*;
import de.berlios.diffr.algorithms.addedAlgorithms.SmallPerturbationAlgorithm.SmallPerturbationAlgorithm;

import java.util.*;

public class Init {
	public static void main(String[] args) {
		new Init();
	}
	
	private JFrame frame = new JFrame("Diffr6");
	private Container cont = frame.getContentPane();
	private JMenuBar menuBar;
	private AlgorithmTypes algorithmTypes;
	private Task currentTask = null;
	private JFileChooser fileChooser = new JFileChooser();
	
	public Init() {
		algorithmTypes = loadAlgorithmTypes();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});
		cont.setLayout(new BorderLayout());
		frame.setSize(800, 600);
		frame.setVisible(true);
		addAlgorithmListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tryLoadAlgorithm();
			}
		}; 
		removeAlgorithmListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tryRemoveAlgorithm();
			}
		}; 
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
	
	private Task makeDefaultTask() {
		try {
			Algorithm algorithm = ((AlgorithmType)algorithmTypes.getAlgorithmTypes().get(0)).newInstance();
			return new Task(algorithmTypes, new InputData(), algorithm);
		} catch (WrongTypeException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private void newTask() {
		setTask(makeDefaultTask());
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
			}catch (Exception e) {
				JOptionPane.showMessageDialog(frame, "Incorrect format of file");
				e.printStackTrace();
			}
		}
	}
	
	private Task readTask(String file) throws IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
		Task task = (Task)in.readObject();
		task.restorationAfterSerialization(algorithmTypes);
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
			System.out.println("Can`t open autosave task");
			return makeDefaultTask();
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
	
	private AlgorithmTypes loadAlgorithmTypes() {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("algorithms.dat"));
			return (AlgorithmTypes)in.readObject();
		} catch (Exception e) {
			System.out.println("Can`t open algorithms list");
			
			AlgorithmType algorithmType;
			Algorithm algorithm = null;
			try {
				algorithmType = new AlgorithmType(SmallPerturbationAlgorithm.class);
				algorithm = new SmallPerturbationAlgorithm(algorithmType);
			} catch (WrongTypeException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			AlgorithmTypes types = new AlgorithmTypes(algorithm);
			return types;
		}
	}
	
	private void saveAlgorithmTypes(AlgorithmTypes t) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("algorithms.dat"));
			out.writeObject(t);
			out.close();
		} catch (Exception e) {
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
	
	public static ActionListener addAlgorithmListener;
	public static ActionListener removeAlgorithmListener;
	
	private JMenu newAlgorithmMenu() {
		JMenu menu = new JMenu("Algorithms");
		JMenuItem addAlgorithmItem = new JMenuItem("Add algorithm");
		addAlgorithmItem.addActionListener(addAlgorithmListener);
		menu.add(addAlgorithmItem);
		JMenuItem removeAlgorithmItem = new JMenuItem("Remove algorithm");
		removeAlgorithmItem.addActionListener(removeAlgorithmListener);
		menu.add(addAlgorithmItem);
		menu.add(removeAlgorithmItem);
		return menu;
	}
	
	private class RemoveAlgorithmDialog extends JDialog {
		private static final long serialVersionUID = 1L;
		private JButton okButton = new JButton("OK");
		private JButton cancelButton = new JButton("Cancel");
		private JComboBox comboBox = new JComboBox();
		RemoveAlgorithmDialog() {
			super(frame, "Remove algorithm", true);
			this.setSize(400, 200);
			this.setLocation(300, 200);
			Container c = this.getContentPane();
			c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
			c.add(Box.createVerticalStrut(30));
			
			Box chooseBox = Box.createVerticalBox();
			
			Iterator it = currentTask.getAlgorithms().getAlgorithmTypes().iterator();
			while (it.hasNext()) {
				comboBox.addItem(it.next());
			}
			comboBox.setMaximumSize(new Dimension(500, 30));
			chooseBox.add(comboBox);
			c.add(chooseBox);
			c.add(Box.createVerticalStrut(20));
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
					currentTask.getAlgorithms().removeAlgorithmType((AlgorithmType)comboBox.getSelectedItem());
					setVisible(false);
				}
			});
		}
	}
	
	private class AddAlgorithmDialog extends JDialog {
		private static final long serialVersionUID = 1L;
		private JTextField name = new JTextField();
		private JButton okButton = new JButton("OK");
		private JButton cancelButton = new JButton("Cancel");
		AddAlgorithmDialog() {
			super(frame, "Add algorithm", true);
			this.setSize(400, 200);
			this.setLocation(300, 200);
			Container c = this.getContentPane();
			c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
			c.add(Box.createVerticalStrut(10));
			c.add(new JLabel("Input name of package with algorithm:"));
			c.add(new JLabel("de.berlios.diffr.algorithms.addedAlgorithms."));
			c.add(name);
			name.setMaximumSize(new Dimension(400, 30));
			name.setMinimumSize(new Dimension(200, 30));
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
						currentTask.getAlgorithms().addAlgorithmType(
								new AlgorithmType(Class.forName("de.berlios.diffr.algorithms.addedAlgorithms." + name.getText() + "." + name.getText()))
						);
						setVisible(false);
					} catch (ClassNotFoundException e1) {
						JOptionPane.showMessageDialog(null, "Specified class doesn`t exist");
					} catch (WrongTypeException e2) {
						JOptionPane.showMessageDialog(null, "Specified class isn`t algorithm");
					}
				}
			});
		}
	}
	
	private void tryLoadAlgorithm() {
		new AddAlgorithmDialog().setVisible(true);
	}
	
	private void tryRemoveAlgorithm() {
		new RemoveAlgorithmDialog().setVisible(true);
	}
	
	private JMenu newHelpMenu() {
		JMenu menu = new JMenu("Help");
		menu.addSeparator();
		JMenuItem aboutItem = new JMenuItem("User manual (ru)");
		aboutItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().open(new File("docs/manual/rus/userManual.html"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		menu.add(aboutItem);
		aboutItem = new JMenuItem("User manual (en)");
		aboutItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().open(new File("docs/manual/en/userManual.html"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		menu.add(aboutItem);
		return menu;
	}
	
	private void exit() {
		saveCurrentTask();
		saveAlgorithmTypes(algorithmTypes);
		System.exit(0);
	}
}
