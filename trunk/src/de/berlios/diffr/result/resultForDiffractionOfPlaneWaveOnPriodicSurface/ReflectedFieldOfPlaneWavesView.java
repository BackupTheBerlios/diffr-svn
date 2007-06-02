package de.berlios.diffr.result.resultForDiffractionOfPlaneWaveOnPriodicSurface;

import java.awt.BorderLayout;

import de.berlios.diffr.*;
import javax.swing.*;

public class ReflectedFieldOfPlaneWavesView extends View {
	public ReflectedFieldOfPlaneWavesView(ReflectedFieldOfPlaneWaves field) {
		JTabbedPane pane = new JTabbedPane();
		pane.add("Text view", new ReflectedFieldOfPlaneWavesViewText(field));
		pane.add("Graphical view", new ReflectedFieldOfPlaneWavesViewImage(field));
		this.setLayout(new BorderLayout());
		this.add(pane);
	}
}
