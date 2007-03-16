package de.berlios.diffr.inputData;

import java.awt.*;
import de.berlios.diffr.*;
import de.berlios.diffr.inputData.InputDataPartView;
import de.berlios.diffr.exceptions.WrongTypeException;

public class SmallInputDataView extends View {
	private InputDataPartView surfaceView;
	private InputDataPartView impingingFieldView;
	public SmallInputDataView(InputData inputData) {
		ViewFactory viewFactory = new ViewFactory();
		surfaceView = null;
		try {
			surfaceView = (InputDataPartView)viewFactory.makeView(inputData.getSurface());
		} catch (WrongTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		impingingFieldView = null;
		try {
			impingingFieldView = (InputDataPartView)viewFactory.makeView(inputData.getImpingingField());
		} catch (WrongTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		inputData.getSurface().addModelChangingListener(new ModelChangingListener() {
			public void modelWasChanged(Model m) {
				repaint();
			}
		});
		inputData.getImpingingField().addModelChangingListener(new ModelChangingListener() {
			public void modelWasChanged(Model m) {
				repaint();
			}
		});
	}
	public void paintComponent(Graphics g) {
		g.setColor(new Color(0, 0, 0));
		int width = g.getClipBounds().width;
		int height = g.getClipBounds().height;
		g.fillRect(0, 0, width, height);
		surfaceView.drawImage(g);
		impingingFieldView.drawImage(g);
	}
}
