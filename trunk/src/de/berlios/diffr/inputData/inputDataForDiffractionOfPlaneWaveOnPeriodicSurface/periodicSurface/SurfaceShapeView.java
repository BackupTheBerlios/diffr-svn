package de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface.periodicSurface;

import java.awt.*;
import de.berlios.diffr.View;

public class SurfaceShapeView extends View {
	private SurfaceShape surfaceShape;
	public SurfaceShapeView(SurfaceShape shape) {
		surfaceShape = shape;
	}
	public double getModelSizeX() {
		return surfaceShape.getPeriod();
	}
	public double getModelSizeY() {
		return 0;
	}
	public void drawImage(Graphics g, double scale) {
		g.setColor(new Color(50, 50, 50));
		int centerX = g.getClipBounds().width / 2;
		int centerY = g.getClipBounds().height / 2;
		g.drawLine((int)(centerX - surfaceShape.getPeriod() / 2 * scale), centerY, (int)(centerX + surfaceShape.getPeriod() / 2 * scale), centerY);
	}
}
