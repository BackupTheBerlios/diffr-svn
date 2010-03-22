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
		int num = surfaceCurrent.getPointsNumber();
		g.setColor(new Color(0, 0, 0));
		int width = g.getClipBounds().width;
		int height = g.getClipBounds().height;
		g.fillRect(0, 0, width, height);
		double maxAbs = 0;
		double minAbs = surfaceCurrent.get(0).abs();
		double minPhase = 0;
		double maxPhase = 0;
		double[] phase = new double[num];
		for (int a=0;a<num;a++) {
			maxAbs = Math.max(maxAbs, surfaceCurrent.get(a).abs());
			minAbs = Math.min(minAbs, surfaceCurrent.get(a).abs());
			phase[a] = surfaceCurrent.get(a).arg();
			if (a!=0) {
				while (phase[a]-phase[a-1]>1.5*Math.PI) phase[a]-=2*Math.PI;
				while (phase[a-1]-phase[a]>1.5*Math.PI) phase[a]+=2*Math.PI;
			}
			while (maxPhase<phase[a]) maxPhase += 2*Math.PI;
			while (minPhase>phase[a]) minPhase -= 2*Math.PI;
			maxPhase = Math.max(maxPhase, phase[a]);
			minPhase = Math.min(minPhase, phase[a]);
		}
		if (maxAbs==minAbs) maxAbs+=0.001;
		if (maxPhase==minPhase) maxPhase+=2*Math.PI;
		double scaleX = (width - 80) / surfaceCurrent.getSurfacePeriod();
		double phaseScale = height / (2.5 * (maxPhase-minPhase));
		double absScale = height / 3 / (maxAbs-minAbs);
		g.setColor(Color.white);
		g.drawString("Phase", width-50, 20);
		g.drawString("Abs", width-50, height/2 + 20);
		
		if (surfaceCurrent.isOutOfDate())
			g.setColor(Color.lightGray);
		else
			g.setColor(Color.green);
		int xb=(int)(-(surfaceCurrent.getSurfacePeriod()/2) * scaleX + width/2);
		int xe=(int)((surfaceCurrent.getSurfacePeriod()/2) * scaleX + width/2);
		int ypu = (int)(height / 4 - height / 5);
		int ypd = (int)(height / 4 + height / 5);
		int ya = (int)(height - 40);
		g.drawLine(xb, ya, xe, ya);
		g.drawLine(xb, ypd, xe, ypd);
		g.drawLine(xb, ypu, xb, ypd);
		g.drawLine(xb, ya, xb, ya - (int)(maxAbs*absScale));
		for (double a=minPhase;a<=maxPhase*1.1;a+=(maxPhase-minPhase)/4) {
			int y = ypd-(int)((a-minPhase)*phaseScale);
			g.drawLine(xb-2, y, xb+2, y);
			int vi = (int)((a-minPhase)/Math.PI*2);
			if (vi%2 == 0) g.drawString(""+vi/2+"Pi", xb-30, y);
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
			g.drawLine(x, ypd-2, x, ypd+2);
			double v = (int)(a*100); v/=100;
			g.drawString(""+v, x-10, ya+15);
			g.drawString(""+v, x-10, ypd+15);
		}
		
		if (surfaceCurrent.isOutOfDate())
			g.setColor(Color.gray);
		else
			g.setColor(new Color(255, 0, 0));
		double lastPhase = phase[0];
		double lastAbs = surfaceCurrent.get(0).abs();
		double dx = surfaceCurrent.getSurfacePeriod() / (num-1);
		double center = surfaceCurrent.getSurfacePeriod() / 2;
		for (int a=1;a<num;a++) {
			int x1=(int)(((a-1)*dx - center) * scaleX + width/2);
			int x2=(int)((a*dx - center) * scaleX + width/2);
			int y1p = (int)(ypd - (lastPhase-minPhase) * phaseScale);
			int y2p = (int)(ypd - (phase[a]-minPhase) * phaseScale);
			int y1a = (int)(ya - (lastAbs-minAbs) * absScale);
			int y2a = (int)(ya - (surfaceCurrent.get(a).abs()-minAbs) * absScale);
			g.drawLine(x1, y1p, x2, y2p);
			g.drawLine(x1, y1a, x2, y2a);
			lastPhase = phase[a];
			lastAbs = surfaceCurrent.get(a).abs();
		}
	}
}
