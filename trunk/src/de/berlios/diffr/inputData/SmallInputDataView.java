package de.berlios.diffr.inputData;

import java.awt.*;
import de.berlios.diffr.View;

public class SmallInputDataView extends View {
	public SmallInputDataView() {
		this.setMinimumSize(new Dimension(100, 100));
		this.setSize(new Dimension(100, 100));
		this.setBackground(new Color(255, 230, 230));
	}
	public void setInputData(InputData newInputData) {}
}
