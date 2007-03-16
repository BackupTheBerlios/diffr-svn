package de.berlios.diffr.inputData.inputDataForVerySimpleTask;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import de.berlios.diffr.*;
import de.berlios.diffr.inputData.InputDataPartView;

public class VerySimpleWaveView extends InputDataPartView {
	private VerySimpleWave wave;
	private JTextField newAngle;
	public VerySimpleWaveView(VerySimpleWave wave) {
		this.wave = wave;
		newAngle = new JTextField("" + wave.getAngle());
		newAngle.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				renewAngle();
			}
		});
		this.setLayout(new BorderLayout());
		this.add(newAngle, BorderLayout.NORTH);
		wave.addModelChangingListener(new ModelChangingListener() {
			public void modelWasChanged(Model m) {
				repaint();
			}
		});
	}
	private void renewAngle() {
		double angle;
		try {
			angle = Double.parseDouble(newAngle.getText());
		} catch (NumberFormatException e1) {
			angle = wave.getAngle();
		}
		wave.setAngle(angle);
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
		int x = width / 2;
		int y = height / 2;
		int x1 = (int) (x - 100 * Math.sin(wave.getAngle()));
		int y1 = (int) (y - 100 * Math.cos(wave.getAngle()));
		int x2 = (int) (x - 10 * Math.sin(wave.getAngle() + 0.3));
		int y2 = (int) (y - 10 * Math.cos(wave.getAngle() + 0.3));
		int x3 = (int) (x - 10 * Math.sin(wave.getAngle() - 0.3));
		int y3 = (int) (y - 10 * Math.cos(wave.getAngle() - 0.3));
		g.drawLine(x, y, x1, y1);
		g.drawLine(x, y, x2, y2);
		g.drawLine(x, y, x3, y3);
	}
}
