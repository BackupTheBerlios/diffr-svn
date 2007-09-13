package de.berlios.diffr.inputData;

import java.awt.BorderLayout;

import javax.swing.*;
import de.berlios.diffr.*;
import de.berlios.diffr.exceptions.WrongTypeException;
import de.berlios.diffr.inputData.surface.SurfaceView;

public class InputDataView extends View {
	public InputDataView(InputData inputData) {
		JTabbedPane tabbedPane = new JTabbedPane();
		View surfaceView = new SurfaceView(inputData.getSurface());
		View impingingFieldView = new ImpingingFieldView(inputData.getImpingingField());
		tabbedPane.add("Surface", surfaceView);
		tabbedPane.add("Impinging field", impingingFieldView);
		this.setLayout(new BorderLayout());
		this.add(tabbedPane);
	}
}
