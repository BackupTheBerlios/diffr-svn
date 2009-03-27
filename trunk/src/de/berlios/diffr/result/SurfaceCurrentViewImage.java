package de.berlios.diffr.result;

import java.awt.*;

import de.berlios.diffr.View;

public class SurfaceCurrentViewImage extends View {
	private static final long serialVersionUID = 1L;
	private SurfaceCurrent surfaceCurrent;
	public SurfaceCurrentViewImage(SurfaceCurrent surfaceCurrent) {
		this.surfaceCurrent = surfaceCurrent;
	}
	public void paintComponent(Graphics g) {
		g.setColor(new Color(0, 0, 0));
		int width = g.getClipBounds().width;
		int height = g.getClipBounds().height;
		g.fillRect(0, 0, width, height);
		double scaleX = (width - 80) / surfaceCurrent.getSurfacePeriod();
		double phaseScale = height / 5 / Math.PI;
		double absScale = Double.MAX_VALUE;
		double maxAbs = 0;
		double minAbs = surfaceCurrent.get(0).abs();
		for (int a=0;a<surfaceCurrent.getPointsNumber();a++) {
			maxAbs = Math.max(maxAbs, surfaceCurrent.get(a).abs());
			minAbs = Math.min(minAbs, surfaceCurrent.get(a).abs());
		}
		if (maxAbs==minAbs) maxAbs+=0.001;
		absScale = height / 3 / (maxAbs-minAbs);
		g.setColor(Color.white);
		g.drawString("Phase", width-50, 20);
		g.drawString("Abs", width-50, height/2 + 20);
		
		g.setColor(Color.green);
		int xb=(int)(-(surfaceCurrent.getSurfacePeriod()/2) * scaleX + width/2);
		int xe=(int)((surfaceCurrent.getSurfacePeriod()/2) * scaleX + width/2);
		int yp = (int)(height / 4);
		int ypu = (int)(height / 4 - Math.PI * phaseScale);
		int ypd = (int)(height / 4 + Math.PI * phaseScale);
		int ya = (int)(height - 40);
		g.drawLine(xb, ya, xe, ya);
		g.drawLine(xb, yp, xe, yp);
		g.drawLine(xb, ypu, xb, ypd);
		g.drawLine(xb, ya, xb, ya - (int)(maxAbs*absScale));
		for (double a=-Math.PI;a<=Math.PI;a+=Math.PI/2) {
			int y = yp-(int)(a*phaseScale);
			g.drawLine(xb-2, y, xb+2, y);
			double v = (int)(a*100); v/=100;
			g.drawString(""+v, xb-30, y);
		}
		for (double a=minAbs;a<=maxAbs;a+=(maxAbs-minAbs)/4.1) {
			int y = ya-(int)((a-minAbs)*absScale);
			g.drawLine(xb-2, y, xb+2, y);
			double v = (int)(a*100); v/=100;
			g.drawString(""+v, xb-30, y);
		}
		
		for (double a=surfaceCurrent.getSurfacePeriod()/4;a<=surfaceCurrent.getSurfacePeriod();a+=surfaceCurrent.getSurfacePeriod()/4) {
			int x = xb+(int)(a*scaleX);
			g.drawLine(x, ya-2, x, ya+2);
			g.drawLine(x, yp-2, x, yp+2);
			double v = (int)(a*100); v/=100;
			g.drawString(""+v, x-10, ya+15);
			g.drawString(""+v, x-10, yp+15);
		}
		
		g.setColor(new Color(255, 0, 0));
		double lastPhase = surfaceCurrent.get(0).arg();
		double lastAbs = surfaceCurrent.get(0).abs();
		int num = surfaceCurrent.getPointsNumber();
		double dx = surfaceCurrent.getSurfacePeriod() / (num-1);
		double center = surfaceCurrent.getSurfacePeriod() / 2;
		for (int a=1;a<num;a++) {
			int x1=(int)(((a-1)*dx - center) * scaleX + width/2);
			int x2=(int)((a*dx - center) * scaleX + width/2);
			int y1p = (int)(height / 4 - lastPhase * phaseScale);
			int y2p = (int)(height / 4 - surfaceCurrent.get(a).arg() * phaseScale);
			int y1a = (int)(height - 40 - (lastAbs-minAbs) * absScale);
			int y2a = (int)(height - 40 - (surfaceCurrent.get(a).abs()-minAbs) * absScale);
			g.drawLine(x1, y1p, x2, y2p);
			g.drawLine(x1, y1a, x2, y2a);
			lastPhase = surfaceCurrent.get(a).arg();
			lastAbs = surfaceCurrent.get(a).abs();
		}
	}
}
