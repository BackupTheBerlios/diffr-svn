package de.berlios.diffr.inputData.inputDataForVerySimpleTask;

import de.berlios.diffr.inputData.*;

public class VerySimpleWave extends ImpingingField {
	private double angle = 1;
	public void setAngle(double angle) {
		if (isEditable()) {
			this.angle = angle;
			modelWasChangedEvent();
		}
	}
	public double getAngle() {
		return angle;
	}
}
