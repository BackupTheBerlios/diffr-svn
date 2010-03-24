package de.berlios.diffr.inputData;

import java.awt.Component;

import javax.swing.*;
import de.berlios.diffr.*;
import de.berlios.diffr.inputData.surface.SurfaceView;

public class InputDataView extends View {
	private static final long serialVersionUID = 1L;
	public InputDataView(InputData inputData, Component buttons) {
		JTabbedPane tabbedPane = new JTabbedPane();
		View surfaceView = new SurfaceView(inputData.getSurface());
		View incidentWaveView = new IncidentWaveView(inputData.getIncidentWave());
		tabbedPane.add("Surface", surfaceView);
		tabbedPane.add("Incident Wave", incidentWaveView);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(Box.createVerticalStrut(10));
		this.add(buttons);
		this.add(Box.createVerticalStrut(10));
		this.add(tabbedPane);
	}
}
