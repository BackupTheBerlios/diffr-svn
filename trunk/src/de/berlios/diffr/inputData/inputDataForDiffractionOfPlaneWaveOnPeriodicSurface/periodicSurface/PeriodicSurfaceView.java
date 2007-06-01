package de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface.periodicSurface;

import de.berlios.diffr.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import de.berlios.diffr.exceptions.ObjectIsnotEditableException;
import de.berlios.diffr.inputData.InputDataPartView;
import Org.netlib.math.complex.*;

public class PeriodicSurfaceView extends InputDataPartView {
	private PeriodicSurface surface;
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
	public PeriodicSurfaceView(PeriodicSurface s) {
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
		perfectConductivity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					surface.setConductivity(new PerfectConductivity());
				} catch (ObjectIsnotEditableException e1) {}
				renewConductivity();
			}
		});
		heightConductivity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					surface.setConductivity(new HeightConductivity());
				} catch (ObjectIsnotEditableException e1) {}
				renewConductivity();
			}
		});
		realConductivity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					surface.setConductivity(new RealConductivity());
				} catch (ObjectIsnotEditableException e1) {}
				renewConductivity();
			}
		});
		epsilonListener = new ModelChangingListener() {
			public void modelWasChanged(Model m) {
				try {
					if (surface.getConductivity().getClass() == HeightConductivity.class) {
						surface.setConductivity(new HeightConductivity((Complex)epsilon.getValue()));
					}
					if (surface.getConductivity().getClass() == RealConductivity.class) {
						surface.setConductivity(new RealConductivity((Complex)epsilon.getValue()));
					}
				} catch (ObjectIsnotEditableException e) {
					renewConductivity();
				}
			}
		};
		shapeView = new SurfaceShapeView(surface.getShape());
		tabbedPane.add("Shape", shapeView);
		tabbedPane.add("Conductivity", conductivityBox);
		this.setLayout(new BorderLayout());
		this.add(tabbedPane);
	}
	private void renewConductivity() {
		if (epsilonView != null) conductivityBox.remove(epsilonView);
		if (surface.getConductivity().getClass() == PerfectConductivity.class) {
			if (!perfectConductivity.isSelected()) perfectConductivity.doClick();
		}
		if (surface.getConductivity().getClass() == HeightConductivity.class) {
			if (!heightConductivity.isSelected()) heightConductivity.doClick();
			HeightConductivity c = (HeightConductivity) surface.getConductivity(); 
			epsilon = new DataString("epsilon", c.getEpsilon());
			epsilon.addModelChangingListener(epsilonListener);
			epsilonView = new DataStringView(epsilon);
			conductivityBox.add(epsilonView);
		}
		if (surface.getConductivity().getClass() == RealConductivity.class) {
			if (!realConductivity.isSelected()) realConductivity.doClick();
			RealConductivity c = (RealConductivity) surface.getConductivity(); 
			epsilon = new DataString("epsilon", c.getEpsilon());
			epsilon.addModelChangingListener(epsilonListener);
			epsilonView = new DataStringView(epsilon);
			conductivityBox.add(epsilonView);
		}
		conductivityBox.validate();
		conductivityBox.repaint();
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
