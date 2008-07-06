package de.berlios.diffr.inputData.surface;

import de.berlios.diffr.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import de.berlios.diffr.exceptions.ObjectIsnotEditableException;
import de.berlios.diffr.inputData.*;
import Org.netlib.math.complex.*;

public class SurfaceView extends InputDataPartView {
	private static final long serialVersionUID = 1L;
	private Surface surface;
	private JTabbedPane tabbedPane = new JTabbedPane();
	private Box conductivityBox = Box.createVerticalBox();
	private JRadioButton perfectConductivity = new JRadioButton("Perfect conductivity");
	private JRadioButton heightConductivity = new JRadioButton("Height conductivity");
	private JRadioButton realConductivity = new JRadioButton("Real conductivity");
	private ButtonGroup conductivityType = new ButtonGroup();
	private DataString epsilon;
	private DataStringView epsilonView = null;
	private SurfaceShapeView shapeView;
	private ModelChangingListener epsilonListener;
	private ActionListener perfectConductivityListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			try {
				if (surface.getConductivity().getClass() != PerfectConductivity.class) surface.setConductivity(new PerfectConductivity());
			} catch (ObjectIsnotEditableException e1) {
				JOptionPane.showMessageDialog(null, "You can`t change this now");
			}
			renewConductivity();
		}
	};
	private ActionListener heightConductivityListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			try {
				if (surface.getConductivity().getClass() != ZeroConductivity.class) surface.setConductivity(new ZeroConductivity());
			} catch (ObjectIsnotEditableException e1) {
				JOptionPane.showMessageDialog(null, "You can`t change this now");
			}
			renewConductivity();
		}
	};
	private ActionListener realConductivityListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			try {
				if (surface.getConductivity().getClass() != Impedance.class) surface.setConductivity(new Impedance());
			} catch (ObjectIsnotEditableException e1) {
				JOptionPane.showMessageDialog(null, "You can`t change this now");
			}
			renewConductivity();
		}
	};
	public SurfaceView(Surface s) {
		surface = s;
		conductivityType.add(perfectConductivity);
		conductivityType.add(heightConductivity);
		conductivityType.add(realConductivity);
		conductivityBox.add(Box.createVerticalStrut(20));
		conductivityBox.add(perfectConductivity);
		conductivityBox.add(Box.createVerticalStrut(20));
		conductivityBox.add(heightConductivity);
		conductivityBox.add(Box.createVerticalStrut(20));
		conductivityBox.add(realConductivity);
		conductivityBox.add(Box.createVerticalStrut(20));
		renewConductivity();
		epsilonListener = new ModelChangingListener() {
			public void modelWasChanged(Model m) {
				try {
					if (surface.getConductivity().getClass() == ZeroConductivity.class) {
						surface.setConductivity(new ZeroConductivity((Complex)epsilon.getValue()));
					}
					if (surface.getConductivity().getClass() == Impedance.class) {
						surface.setConductivity(new Impedance((Complex)epsilon.getValue()));
					}
				} catch (ObjectIsnotEditableException e) {
					renewConductivity();
				}
			}
		};
		shapeView = new SurfaceShapeView(surface.getShape());
		//tabbedPane.add("Shape", shapeView);
		//tabbedPane.add("Conductivity", conductivityBox);
		this.setLayout(new BorderLayout());
		//this.add(tabbedPane);
		this.add(shapeView);
	}
	private void removeListeners() {
		perfectConductivity.removeActionListener(perfectConductivityListener);
		heightConductivity.removeActionListener(heightConductivityListener);
		realConductivity.removeActionListener(realConductivityListener);
	}
	private void addListeners() {
		perfectConductivity.addActionListener(perfectConductivityListener);
		heightConductivity.addActionListener(heightConductivityListener);
		realConductivity.addActionListener(realConductivityListener);
	}
	private void renewConductivity() {
		removeListeners();
		if (epsilonView != null) conductivityBox.remove(epsilonView);
		if (surface.getConductivity().getClass() == PerfectConductivity.class) {
			if (!perfectConductivity.isSelected()) perfectConductivity.doClick();
		}
		if (surface.getConductivity().getClass() == ZeroConductivity.class) {
			if (!heightConductivity.isSelected()) heightConductivity.doClick();
			ZeroConductivity c = (ZeroConductivity) surface.getConductivity(); 
			epsilon = new DataString("epsilon", c.getEpsilon());
			epsilon.addModelChangingListener(epsilonListener);
			epsilonView = new DataStringView(epsilon);
			conductivityBox.add(epsilonView);
		}
		if (surface.getConductivity().getClass() == Impedance.class) {
			if (!realConductivity.isSelected()) realConductivity.doClick();
			Impedance c = (Impedance) surface.getConductivity(); 
			epsilon = new DataString("epsilon", c.getEpsilon());
			epsilon.addModelChangingListener(epsilonListener);
			epsilonView = new DataStringView(epsilon);
			conductivityBox.add(epsilonView);
		}
		conductivityBox.validate();
		conductivityBox.repaint();
		addListeners();
	}
	public double getModelSizeX() {
		return shapeView.getModelSizeX();
	}
	public double getModelSizeY() {
		return shapeView.getModelSizeY();
	}
	
	public void drawImage(Graphics g, double scale) {
		shapeView.drawImage(g, scale);
	}

}
