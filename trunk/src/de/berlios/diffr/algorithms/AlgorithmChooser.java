package de.berlios.diffr.algorithms;

import de.berlios.diffr.*;
import de.berlios.diffr.exceptions.WrongTypeException;
import de.berlios.diffr.task.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AlgorithmChooser extends JPanel {
	private TaskType taskType;
	private JComboBox algorithmTypes;
	private Box parametersBox = Box.createVerticalBox();
	private JLabel autor = new JLabel();
	private Algorithm currentAlgorithm;
	private ArrayList algorithmChooserListeners = new ArrayList();
	public void addAlgorithmChooserListener(AlgorithmChooserListener l) {
		algorithmChooserListeners.add(l);
	}
	public void removeAlgorithmChooserListener(AlgorithmChooserListener l) {
		algorithmChooserListeners.remove(l);
	}
	protected void tryChangeAlgorithm(Algorithm newAlgorithm) {
		if (currentAlgorithm.isEditable()) {
			Iterator i = algorithmChooserListeners.iterator();
			while (i.hasNext()) {
				AlgorithmChooserListener l = ( AlgorithmChooserListener )i.next();
				l.newAlgorithmWasChoosed(newAlgorithm);
			}
			setAlgorithm(newAlgorithm);
			algorithmTypes.setSelectedItem(currentAlgorithm.getAlgorithmType());
		} else
			JOptionPane.showMessageDialog(this, "You can`t change algorithm while task running");
		if (currentAlgorithm != null) algorithmTypes.setSelectedItem(currentAlgorithm.getAlgorithmType());
	}
	public void setAlgorithm(Algorithm algorithm) {
		currentAlgorithm = algorithm;
		autor.setText("autor : " + algorithm.getAlgorithmType().getAutor());
		parametersBox.removeAll();
		ViewFactory viewFactory = new ViewFactory();
		for (int parameterNumber = 0;
				parameterNumber < algorithm.getAlgorithmParameters().length;
				parameterNumber++) {
			try {
				View view =
					viewFactory.makeView(algorithm.getAlgorithmParameters()[parameterNumber]);
				parametersBox.add(view);
				this.add(Box.createVerticalStrut(10));
			} catch (WrongTypeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.validate();
	}
	public AlgorithmChooser(TaskType taskType, Algorithm currentAlgorithm) {
		this.taskType = taskType;
		this.currentAlgorithm = currentAlgorithm;
		taskType.addModelChangingListener(new ModelChangingListener() {
			public void modelWasChanged(Model m) {
				renewAlgorithmTypesList();
			}
		});
		
	    this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
	    this.add(Box.createVerticalStrut(20));

	    algorithmTypes = new JComboBox();
	    algorithmTypes.setMaximumSize(new Dimension(400,20));
	    this.add(algorithmTypes);  
	    
	    this.add(Box.createVerticalStrut(20));
	    this.add(autor);
	    this.add(Box.createVerticalStrut(20));
	    this.add(parametersBox);

	    renewAlgorithmTypesList();
	    
	    algorithmTypes.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
			try {
				if (algorithmTypes.getSelectedItem() != null) {
					AlgorithmType type = (AlgorithmType)algorithmTypes.getSelectedItem();
					Algorithm newAlgorithm = type.newInstance();
					tryChangeAlgorithm(newAlgorithm);
				}
			} catch (WrongTypeException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	      }
	    });
	    
	    setAlgorithm(currentAlgorithm);
	}
	private void renewAlgorithmTypesList() {
		algorithmTypes.removeAllItems();
		Iterator i = taskType.getAlgorithmTypes().iterator();
		while (i.hasNext()) {
			algorithmTypes.addItem(i.next());
		}
		if (currentAlgorithm != null) algorithmTypes.setSelectedItem(currentAlgorithm.getAlgorithmType());
	}
}
