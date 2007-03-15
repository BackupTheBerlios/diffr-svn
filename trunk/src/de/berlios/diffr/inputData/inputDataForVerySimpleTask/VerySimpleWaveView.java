package de.berlios.diffr.inputData.inputDataForVerySimpleTask;

import java.awt.Color;
import java.awt.Graphics;

import de.berlios.diffr.inputData.InputDataPartView;

public class VerySimpleWaveView extends InputDataPartView {
	private VerySimpleWave wave;
	public VerySimpleWaveView(VerySimpleWave wave) {
		this.wave = wave;
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
		g.setColor(new Color(255, 0, 0));
		g.drawLine((int) (width / 2 - 100 * Math.sin(wave.getAngle())),
				(int) (height / 2 - 100 * Math.cos(wave.getAngle())),
				width / 2, height / 2);
	}
}
