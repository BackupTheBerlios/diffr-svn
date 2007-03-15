package de.berlios.diffr.inputData.inputDataForVerySimpleTask;

import de.berlios.diffr.inputData.*;
import java.awt.*;

public class PlaneMirrowSurfaceView extends InputDataPartView {
	public PlaneMirrowSurfaceView(PlaneMirrowSurface surface) {}
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
		g.setColor(new Color(0, 0, 255));
		g.drawLine(0, height - 50, width, height - 50);
	}
}
