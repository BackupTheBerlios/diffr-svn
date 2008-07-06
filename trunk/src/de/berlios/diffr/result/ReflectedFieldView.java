package de.berlios.diffr.result;

import java.awt.BorderLayout;

import de.berlios.diffr.*;

import javax.swing.*;

public class ReflectedFieldView extends View {
	private static final long serialVersionUID = 1L;
	public ReflectedFieldView(ReflectedField field) {
		JTabbedPane pane = new JTabbedPane();
		pane.setTabPlacement(3);
		pane.add("Graphical view", new ReflectedFieldViewImage(field));
		pane.add("Text view", new ReflectedFieldViewText(field));
		this.setLayout(new BorderLayout());
		this.add(pane);
	}
}
