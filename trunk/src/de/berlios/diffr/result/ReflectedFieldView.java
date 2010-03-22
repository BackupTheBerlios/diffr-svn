package de.berlios.diffr.result;

import java.awt.BorderLayout;

import de.berlios.diffr.*;

import javax.swing.*;

public class ReflectedFieldView extends View {
	private static final long serialVersionUID = 1L;
	public ReflectedFieldView(Result result) {
		JTabbedPane pane = new JTabbedPane();
		pane.setTabPlacement(3);
		pane.add("Graphical view", new ReflectedFieldViewImage(result));
		pane.add("Text view", new ReflectedFieldViewText(result.getReflectedField()));
		this.setLayout(new BorderLayout());
		this.add(pane);
	}
}
