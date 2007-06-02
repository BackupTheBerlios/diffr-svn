package de.berlios.diffr.result.resultForDiffractionOfPlaneWaveOnPriodicSurface;

import de.berlios.diffr.*;
import java.awt.*;

public class ReflectedFieldOfPlaneWavesViewImage extends View {
	private ReflectedFieldOfPlaneWaves field;
	public ReflectedFieldOfPlaneWavesViewImage(ReflectedFieldOfPlaneWaves field) {
		this.field = field;
	}
	public void paintComponent(Graphics g) {
		g.setColor(new Color(0, 0, 0));
		int width = g.getClipBounds().width;
		int height = g.getClipBounds().height;
		g.fillRect(0, 0, width, height);
		ReflectedPlaneWave[] waves = field.getWaves();
		int maxX = 0;
		int maxY = 0;
		for (int a=0;a<waves.length;a++) {
			if (waves[a] != null) {
				int sizeX = (int)Math.abs(waves[a].getAmplitude().abs()*Math.sin(waves[a].getAngle())*2.5);
				int sizeY = (int)Math.abs(waves[a].getAmplitude().abs()*Math.cos(waves[a].getAngle())*2.5);
				if (sizeX > maxX) maxX = sizeX;
				if (sizeY > maxY) maxY = sizeY;
			}
		}
		double scaleX;
		if (maxX !=0) scaleX = this.getWidth() / maxX;
		else scaleX = Double.MAX_VALUE;
		double scaleY;
		if (maxY !=0) scaleY = this.getWidth() / maxY;
		else scaleY = Double.MAX_VALUE;
		double scale = Math.min(scaleX, scaleY);
		g.setColor(new Color(100, 120, 100));
		g.drawString("Scale:" + (int)scale, 0, 20);
		for (int a=0;a<waves.length;a++) {
			g.setColor(new Color(0, 255, 0));
			if (waves[a] != null) {
				int x = width / 2;
				int y = height / 2;
				int x1 = (int) (x - waves[a].getAmplitude().abs() * scale * Math.sin(waves[a].getAngle()));
				int y1 = (int) (y - waves[a].getAmplitude().abs() * scale * Math.cos(waves[a].getAngle()));
				int x2 = (int) (x1 - 10 * Math.sin(waves[a].getAngle() + Math.PI + 0.3));
				int y2 = (int) (y1 - 10 * Math.cos(waves[a].getAngle() + Math.PI + 0.3));
				int x3 = (int) (x1 - 10 * Math.sin(waves[a].getAngle() + Math.PI - 0.3));
				int y3 = (int) (y1 - 10 * Math.cos(waves[a].getAngle() + Math.PI - 0.3));
				g.drawLine(x, y, x1, y1);
				g.drawLine(x1, y1, x2, y2);
				g.drawLine(x1, y1, x3, y3);
			}
		}
	}
}
