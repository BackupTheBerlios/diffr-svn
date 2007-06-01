package de.berlios.diffr.inputData;

import java.awt.Graphics;

import de.berlios.diffr.View;

public abstract class InputDataPartView extends View {
	public abstract void drawImage(Graphics g, double scale);
	public double getModelSizeX() {
		return 1;
	}
	public double getModelSizeY() {
		return 1;
	}
}
