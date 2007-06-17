package de.berlios.diffr.result;

import de.berlios.diffr.*;
import de.berlios.diffr.inputData.InputData;
import Org.netlib.math.complex.*;

public class SurfaceCurrent extends Model {
	private Complex[] points;
	public SurfaceCurrent(Complex[] points) {
		this.points = points;
	}
	public Complex get(int number) {
		return points[number];
	}
	public int getPointsNumber() {
		return points.length;
	}
	public  SurfaceCurrent dimensioning(InputData inputData) {
		// TODO Auto-generated method stub
		return null;
	}

}
