package de.berlios.diffr.result;

import de.berlios.diffr.*;
import de.berlios.diffr.inputData.IncidentWave;

import java.awt.*;

public class ReflectedFieldViewImage extends View {
	private static final long serialVersionUID = 1L;
	private ReflectedField field;
	private double energyError;
	public ReflectedFieldViewImage(Result result) {
		this.field = result.getReflectedField();
		this.energyError = result.getEnergyError();
	}
	public void paintComponent(Graphics g) {
		g.setColor(new Color(0, 0, 0));
		int width = g.getClipBounds().width;
		int height = g.getClipBounds().height;
		g.fillRect(0, 0, width, height);
		ReflectedPlaneWave[] waves = field.getWaves();
		IncidentWave incidentWave = field.getIncidentWave();
		double maxX = incidentWave.getAmplitude().abs()*Math.sin(incidentWave.getAngle())*3;
		double maxY = incidentWave.getAmplitude().abs()*Math.cos(incidentWave.getAngle())*3;
		for (int a=0;a<waves.length;a++) {
			if (waves[a] != null) {
				double sizeX = Math.abs(waves[a].getAmplitude().abs()*Math.sin(waves[a].getAngle())*3);
				double sizeY = Math.abs(waves[a].getAmplitude().abs()*Math.cos(waves[a].getAngle())*3);
				if (sizeX > maxX) maxX = sizeX;
				if (sizeY > maxY) maxY = sizeY;
			}
		}
		double scaleX;
		if (maxX !=0) scaleX = this.getWidth() / maxX;
		else scaleX = Double.MAX_VALUE;
		double scaleY;
		if (maxY !=0) scaleY = this.getHeight() / maxY;
		else scaleY = Double.MAX_VALUE;
		double scale = Math.min(scaleX, scaleY);
		if (field.isOutOfDate())
			g.setColor(Color.gray);
		else
			g.setColor(new Color(255, 0, 0));
		int x = width / 2;
		int y = height / 2;
		int x1 = (int) (x + incidentWave.getAmplitude().abs() * scale * Math.sin(incidentWave.getAngle()));
		int y1 = (int) (y - incidentWave.getAmplitude().abs() * scale * Math.cos(incidentWave.getAngle()));
		int x2 = (int) (x + 10 * Math.sin(incidentWave.getAngle() + 0.3));
		int y2 = (int) (y - 10 * Math.cos(incidentWave.getAngle() + 0.3));
		int x3 = (int) (x + 10 * Math.sin(incidentWave.getAngle() - 0.3));
		int y3 = (int) (y - 10 * Math.cos(incidentWave.getAngle() - 0.3));
		g.drawLine(x, y, x1, y1);
		g.drawLine(x, y, x2, y2);
		g.drawLine(x, y, x3, y3);
		for (int a=0;a<waves.length;a++) {
			if (field.isOutOfDate())
				g.setColor(Color.lightGray);
			else
				g.setColor(Color.green);
			if ((waves[a] != null) && (waves[a].getAmplitude().abs() > 0)) {
				x = width / 2;
				y = height / 2;
				x1 = (int) (x - waves[a].getAmplitude().abs() * scale * Math.sin(waves[a].getAngle()));
				y1 = (int) (y - waves[a].getAmplitude().abs() * scale * Math.cos(waves[a].getAngle()));
				x2 = (int) (x1 - 10 * Math.sin(waves[a].getAngle() + Math.PI + 0.3));
				y2 = (int) (y1 - 10 * Math.cos(waves[a].getAngle() + Math.PI + 0.3));
				x3 = (int) (x1 - 10 * Math.sin(waves[a].getAngle() + Math.PI - 0.3));
				y3 = (int) (y1 - 10 * Math.cos(waves[a].getAngle() + Math.PI - 0.3));
				int x4 = (int) (x - (waves[a].getAmplitude().abs() * scale + 15) * Math.sin(waves[a].getAngle()));
				int y4 = (int) (y - (waves[a].getAmplitude().abs() * scale + 15) * Math.cos(waves[a].getAngle()));
				g.drawLine(x, y, x1, y1);
				g.drawLine(x1, y1, x2, y2);
				g.drawLine(x1, y1, x3, y3);
				g.setColor(Color.blue);
				g.drawString("#"+waves[a].getNumber(), x4-3, y4+3);
			}
		}
		String error = String.format("%f%%", energyError*100);
		g.setColor(Color.white);
		g.drawString("Energy error: " + error, width/4, height/4*3);
	}
}
