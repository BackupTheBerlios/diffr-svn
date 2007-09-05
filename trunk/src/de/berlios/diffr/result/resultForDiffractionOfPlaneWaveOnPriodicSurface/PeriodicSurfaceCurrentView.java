package de.berlios.diffr.result.resultForDiffractionOfPlaneWaveOnPriodicSurface;

import java.awt.*;

import de.berlios.diffr.View;

public class PeriodicSurfaceCurrentView extends View {
	private PeriodicSurfaceCurrent surfaceCurrent;
	public PeriodicSurfaceCurrentView(PeriodicSurfaceCurrent surfaceCurrent) {
		this.surfaceCurrent = surfaceCurrent;
	}
	public void paintComponent(Graphics g) {
		g.setColor(new Color(0, 0, 0));
		int width = g.getClipBounds().width;
		int height = g.getClipBounds().height;
		g.fillRect(0, 0, width, height);
		double scale = Math.min(width / surfaceCurrent.getSurfacePeriod(), height / 3 / 2 / Math.PI);
		for (int a=0;a<surfaceCurrent.getPointsNumber();a++) {
			scale = Math.min(height / 3 / surfaceCurrent.get(a).abs(), scale);
		}
		g.setColor(Color.white);
		g.drawString("Scale:" + (int)scale, 100, 20);
		g.drawString("Phase", 0, 20);
		g.drawString("Abs", 0, height/2 + 20);
		g.setColor(new Color(255, 0, 0));
		double lastPhase = surfaceCurrent.get(0).arg();
		double lastAbs = surfaceCurrent.get(0).abs();
		for (int a=1;a<surfaceCurrent.getPointsNumber();a++) {
			int x1=(int)(((a-1)*surfaceCurrent.getSurfacePeriod()/(surfaceCurrent.getPointsNumber()-1)-surfaceCurrent.getSurfacePeriod() / 2) * scale + width/2);
			int x2=(int)((a*surfaceCurrent.getSurfacePeriod()/(surfaceCurrent.getPointsNumber()-1)-surfaceCurrent.getSurfacePeriod() / 2) * scale + width/2);
			int y1p = (int)(height / 4 - (lastPhase - Math.PI) * scale);
			int y2p = (int)(height / 4 - (surfaceCurrent.get(0).arg() - Math.PI) * scale);
			int y1a = (int)(height * 5/6 - lastAbs * scale);
			int y2a = (int)(height * 5/6 - surfaceCurrent.get(0).abs() * scale);
			g.drawLine(x1, y1p, x2, y2p);
			g.drawLine(x1, y1a, x2, y2a);
			lastPhase = surfaceCurrent.get(a).arg();
			lastAbs = surfaceCurrent.get(a).abs();
		}
	}
}
