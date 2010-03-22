package de.berlios.diffr.inputData;

import java.awt.*;
import de.berlios.diffr.*;
import de.berlios.diffr.inputData.InputDataPartView;
import de.berlios.diffr.inputData.surface.SurfaceView;

public class SmallSeriesInputDataView extends View {
	private static final long serialVersionUID = 1L;
	private InputDataPartView surfaceView;
	private InputDataPartView incidentWavesView;
	public SmallSeriesInputDataView(SeriesInputData inputData) {
		surfaceView = new SurfaceView(inputData.getSurface());
		incidentWavesView = new IncidentWaveSeriesView(inputData.getIncidentWaveSeries());
		inputData.getSurface().addModelChangingListener(new ModelChangingListener() {
			public void modelWasChanged(Model m) {
				repaint();
			}
		});
		inputData.getIncidentWaveSeries().addModelChangingListener(new ModelChangingListener() {
			public void modelWasChanged(Model m) {
				repaint();
			}
		});
	}
	public void paintComponent(Graphics g) {
		g.setColor(new Color(0, 0, 0));
		int width = g.getClipBounds().width;
		int height = g.getClipBounds().height;
		g.fillRect(0, 0, width, height);
		double scaleX = (this.getWidth()-30) / Math.max(surfaceView.getModelSizeX(), incidentWavesView.getModelSizeX());
		double scaleY = this.getHeight() / Math.max(surfaceView.getModelSizeY(), incidentWavesView.getModelSizeY());
		double scale = Math.min(scaleX, scaleY);
		g.setColor(new Color(100, 120, 100));
		surfaceView.drawImage(g, scale);
		incidentWavesView.drawImage(g, scale);
		g.setColor(new Color(255, 255, 255));
		g.drawString("Series mode", 10, height-10);
	}
}
