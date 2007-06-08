package de.berlios.diffr.inputData;

import java.awt.BorderLayout;

import javax.swing.*;
import de.berlios.diffr.*;
import de.berlios.diffr.exceptions.WrongTypeException;

public class InputDataView extends View {
	public InputDataView(InputData inputData) {
		JTabbedPane tabbedPane = new JTabbedPane();
		ViewFactory viewFactory = new ViewFactory();
		View surfaceView = null;
		try {
			surfaceView = viewFactory.makeView(inputData.getSurface());
		} catch (WrongTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		View impingingFieldView = null;
		try {
			impingingFieldView = viewFactory.makeView(inputData.getImpingingField());
		} catch (WrongTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tabbedPane.add("Surface", surfaceView);
		tabbedPane.add("Impinging field", impingingFieldView);
		this.setLayout(new BorderLayout());
		this.add(tabbedPane);
	}
}
