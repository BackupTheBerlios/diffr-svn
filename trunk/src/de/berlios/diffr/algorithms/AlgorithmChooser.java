package de.berlios.diffr.algorithms;

import de.berlios.diffr.*;
import de.berlios.diffr.exceptions.WrongTypeException;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class AlgorithmChooser extends JPanel {
	private static final long serialVersionUID = 1L;
	private AlgorithmTypes algorithms;
	private JComboBox algorithmTypes;
	private Box parametersBox = Box.createVerticalBox();
	private JLabel autor = new JLabel();
	private Algorithm currentAlgorithm;
	private ArrayList<AlgorithmChooserListener> algorithmChooserListeners = new ArrayList<AlgorithmChooserListener>();
	private boolean notCh = false;
	private ActionListener typesListener = new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	    	  if (!notCh) {
				try {
					if (algorithmTypes.getSelectedItem() != null) {
						AlgorithmType type = (AlgorithmType)algorithmTypes.getSelectedItem();
						Algorithm newAlgorithm = type.newInstance();
						tryChangeAlgorithm(newAlgorithm);
					}
				} catch (WrongTypeException e1) {
					e1.printStackTrace();
				}
	    	  }
	      }
	};
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
		for (int parameterNumber = 0;
				parameterNumber < algorithm.getAlgorithmParameters().length;
				parameterNumber++) {
			View view = new DataStringView(algorithm.getAlgorithmParameters()[parameterNumber]);
			((DataStringView)view).listener = new ModelChangingListener() {
				public void modelWasChanged(Model m) {
					Iterator i = algorithmChooserListeners.iterator();
					while (i.hasNext()) {
						AlgorithmChooserListener l = ( AlgorithmChooserListener )i.next();
						l.algorithmParametersWereChanged();
					}		
				}
			};
			parametersBox.add(view);
			this.add(Box.createVerticalStrut(10));
		}
		this.validate();
	}
	public AlgorithmChooser(AlgorithmTypes algorithms, Algorithm currentAlgorithm, JButton startButton) {
		this.algorithms = algorithms;
		this.currentAlgorithm = currentAlgorithm;
		algorithms.addModelChangingListener(new ModelChangingListener() {
			public void modelWasChanged(Model m) {
				renewAlgorithmTypesList();
			}
		});
	    this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
	    
	    JButton addAlgorithmButton = new JButton("Add algorithm");
	    JButton removeAlgorithmButton = new JButton("Remove algorithm");
	    addAlgorithmButton.addActionListener(Init.addAlgorithmListener);
	    removeAlgorithmButton.addActionListener(Init.removeAlgorithmListener);
	    
	    this.add(Box.createVerticalStrut(20));
	    this.add(addAlgorithmButton);
	    this.add(Box.createVerticalStrut(10));
	    this.add(removeAlgorithmButton);
	    this.add(Box.createVerticalStrut(20));
	    
	    algorithmTypes = new JComboBox();
	    algorithmTypes.setMaximumSize(new Dimension(400,20));
	    this.add(algorithmTypes);  
	    
	    this.add(Box.createVerticalStrut(20));
	    this.add(autor);
	    this.add(Box.createVerticalStrut(20));
	    this.add(startButton);
	    this.add(Box.createVerticalStrut(20));
	    this.add(parametersBox);

	    renewAlgorithmTypesList();
	    
	    algorithmTypes.addActionListener(typesListener);
	    
	    setAlgorithm(currentAlgorithm);
	}
	private void renewAlgorithmTypesList() {
		notCh = true;
		algorithmTypes.removeAllItems();
		Iterator i = algorithms.getAlgorithmTypes().iterator();
		while (i.hasNext()) {
			algorithmTypes.addItem(i.next());
		}
		if (currentAlgorithm != null) algorithmTypes.setSelectedItem(currentAlgorithm.getAlgorithmType());
		notCh = false;
		if (!currentAlgorithm.getAlgorithmType().equals(algorithmTypes.getSelectedItem())) {
			//System.out.println("/");
			typesListener.actionPerformed(new ActionEvent("", 0, ""));
		}
	}
}
