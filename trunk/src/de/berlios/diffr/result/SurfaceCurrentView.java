package de.berlios.diffr.result;

import java.awt.BorderLayout;

import javax.swing.JTabbedPane;

import de.berlios.diffr.View;

public class SurfaceCurrentView extends View {
	private static final long serialVersionUID = 1L;
	public SurfaceCurrentView(SurfaceCurrent c) {
		JTabbedPane pane = new JTabbedPane();
		pane.add("Graphical view", new SurfaceCurrentViewImage(c));
		pane.add("Text view", new SurfaceCurrentViewText(c));
		this.setLayout(new BorderLayout());
		this.add(pane);
	}
}
