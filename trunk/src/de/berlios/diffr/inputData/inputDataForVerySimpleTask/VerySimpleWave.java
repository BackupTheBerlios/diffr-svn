package de.berlios.diffr.inputData.inputDataForVerySimpleTask;

import de.berlios.diffr.inputData.*;
import de.berlios.diffr.exceptions.ObjectIsnotEditableException;

public class VerySimpleWave extends ImpingingField {
	private double angle = 1;
	public void setAngle(double angle) throws ObjectIsnotEditableException {
		if (isEditable()) {
			this.angle = angle;
			modelWasChangedEvent();
		} else
			throw new ObjectIsnotEditableException();
	}
	public double getAngle() {
		return angle;
	}
}
