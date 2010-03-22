package de.berlios.diffr.result;

import de.berlios.diffr.*;

import java.awt.*;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class SeriesAmplitudesViewImage extends View {
	private static final long serialVersionUID = 1L;
	private SeriesResult result;
	public SeriesAmplitudesViewImage(SeriesResult result) {
		this.result = result;
		//amplitude = result.getResult(0).getReflectedField().getIncidentWave().getAmplitude().abs();
	}
	public void paintComponent(Graphics g) {
		int num = result.getPointsNumber();
		g.setColor(new Color(0, 0, 0));
		int width = g.getClipBounds().width;
		int height = g.getClipBounds().height;
		g.fillRect(0, 0, width, height);
		double max = 0;
		for (int a=0;a<num;a++)
			max = Math.max(max, result.getResult(a).getReflectedField().getIncidentWave().getAmplitude().abs());
		//double scaleX = (width - 80) / surfaceCurrent.getSurfacePeriod();
//		g.setColor(Color.white);
		
		boolean outOfDate = result.getResult(0).getReflectedField().isOutOfDate();
		if (outOfDate)
			g.setColor(Color.darkGray);
		else
			g.setColor(Color.lightGray);
		int xb = 40;
		int xe = width - 30;
		int ye = 50;
		int yb = height - 50;
		double scale = (double)(yb-ye) / max;
		
		g.drawLine(xb, yb, xe, yb);
		g.drawLine(xb, yb, xb, 0);
		g.drawLine(xe, yb-5, xe, yb+5);
		
		g.setColor(Color.white);
		for (double a=0;a<=max*1.1;a+=max/4) {
			int y = yb-(int)(a*scale);
			g.drawLine(xb-2, y, xb+2, y);
			g.drawString(""+a, xb-30, y);
		}
		g.drawString("Reflected waves amplitudes", width/2-70, yb+20);
		if (num==1) {
			g.setColor(Color.red);
			g.drawString("Need more descritization points to draw graphic", xb+10, height/2);
			return;
		}
		
		if (outOfDate)
			g.setColor(Color.darkGray);
		else
			g.setColor(new Color(100, 0, 0));
		for (int i=1; i<num; i++) {
			int y1 = (int)(result.getResult(i-1).getReflectedField().getIncidentWave().getAmplitude().abs()*scale);
			int y2 = (int)(result.getResult(i).getReflectedField().getIncidentWave().getAmplitude().abs()*scale);
			g.drawLine(xb + (xe-xb)*(i-1)/(num-1), yb-y1, xb + (xe-xb)*i/(num-1), yb-y2);
			if (i==num-1) {
				g.setColor(Color.blue);
				g.drawString("Incident wave", xe-100, yb-y2-10);
			}
		}
		
		Map<Integer, Integer> map = new TreeMap<Integer, Integer>();
		for (ReflectedPlaneWave w : result.getResult(0).getReflectedField().getWaves()) {
			int y = (int)(w.getAmplitude().abs() * scale);
			map.put(w.getNumber(), y);
		}
		for (int i=1; i<num; i++) {
			Set<Integer> set = new TreeSet<Integer>();
			set.addAll(map.keySet());
			for (ReflectedPlaneWave w : result.getResult(i).getReflectedField().getWaves()) {
				Integer Y1 = map.get(w.getNumber());
				int y1 = Y1==null ? 0 : Y1;
				int y2 = (int)(w.getAmplitude().abs() * scale);
				set.remove(w.getNumber());
				map.put(w.getNumber(), y2);
				if (outOfDate)
					g.setColor(Color.lightGray);
				else
					g.setColor(new Color(0, 100, 0));
				g.drawLine(xb + (xe-xb)*(i-1)/(num-1), yb-y1, xb + (xe-xb)*i/(num-1), yb-y2);
				if (i==num-1) {
					g.setColor(Color.blue);
					g.drawString("#"+w.getNumber(), xe+5, yb-y2);
				}
			}
			for (int j : set) {
				int y1 = map.get(j);
				map.remove(j);
				int y2 = 0;
				if (outOfDate)
					g.setColor(Color.lightGray);
				else
					g.setColor(new Color(0, 100, 0));
				g.drawLine(xb + (xe-xb)*(i-1)/(num-1), yb-y1, xb + (xe-xb)*i/(num-1), yb-y2);
				g.setColor(Color.blue);
				g.drawString("#"+j, xb + (xe-xb)*i/(num-1), yb+10);
			}
		}
	}
}
