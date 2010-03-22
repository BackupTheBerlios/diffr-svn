package de.berlios.diffr.inputData;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTabbedPane;

import de.berlios.diffr.View;
import de.berlios.diffr.inputData.surface.SurfaceView;

public class SeriesInputDataView extends View {
	private static final long serialVersionUID = 1L;
	public SeriesInputDataView(SeriesInputData inputData, JButton startButton) {
		JTabbedPane tabbedPane = new JTabbedPane();
		View surfaceView = new SurfaceView(inputData.getSurface());
		View incidentWavesView = new IncidentWaveSeriesView(inputData.getIncidentWaveSeries());
		tabbedPane.add("Surface", surfaceView);
		tabbedPane.add("Incident Wave", incidentWavesView);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(Box.createVerticalStrut(10));
		this.add(startButton);
		this.add(Box.createVerticalStrut(10));
		this.add(tabbedPane);
	}
}