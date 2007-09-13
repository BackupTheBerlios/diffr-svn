package de.berlios.diffr.result;

import java.awt.BorderLayout;

import de.berlios.diffr.*;

import javax.swing.*;

public class ReflectedFieldView extends View {
	public ReflectedFieldView(ReflectedField field) {
		JTabbedPane pane = new JTabbedPane();
		pane.add("Text view", new ReflectedFieldViewText(field));
		pane.add("Graphical view", new ReflectedFieldViewImage(field));
		this.setLayout(new BorderLayout());
		this.add(pane);
	}
}
