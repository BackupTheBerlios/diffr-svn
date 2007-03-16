package de.berlios.diffr.result.resultForVerySimpleTask;

import java.awt.Color;
import java.awt.Graphics;

import de.berlios.diffr.*;

public class ReflectedWaveView extends View {
	private ReflectedWave wave;
	public ReflectedWaveView(ReflectedWave wave) {
		this.wave = wave;
		wave.addModelChangingListener(new ModelChangingListener() {
			public void modelWasChanged(Model m) {
				repaint();
			}
		});
		validate();
	}
	public void paintComponent(Graphics g) {
		g.setColor(new Color(0, 0, 0));
		int width = g.getClipBounds().width;
		int height = g.getClipBounds().height;
		g.fillRect(0, 0, width, height);
		drawImage(g);
	}
	public void drawImage(Graphics g) {
		int width = g.getClipBounds().width;
		int height = g.getClipBounds().height;
		g.setColor(new Color(0, 255, 0));
		int x1 = width / 2;
		int y1 = height / 2;
		int x = (int) (x1 - 100 * Math.sin(wave.getAngle()));
		int y = (int) (y1 - 100 * Math.cos(wave.getAngle()));
		int x2 = (int) (x + 10 * Math.sin(wave.getAngle() + 0.3));
		int y2 = (int) (y + 10 * Math.cos(wave.getAngle() + 0.3));
		int x3 = (int) (x + 10 * Math.sin(wave.getAngle() - 0.3));
		int y3 = (int) (y + 10 * Math.cos(wave.getAngle() - 0.3));
		g.drawLine(x, y, x1, y1);
		g.drawLine(x, y, x2, y2);
		g.drawLine(x, y, x3, y3);
	}
}
