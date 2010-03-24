package de.berlios.diffr.result;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import de.berlios.diffr.View;

public class SeriesResultView extends View {
	private static final long serialVersionUID = 1L;
	
	private SeriesAmplitudesViewImage amplitudes = null;
	private int currentPoint = -1;
	public SeriesResultView(JButton startButton) {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(Box.createVerticalStrut(10));
		this.add(startButton);
		this.add(Box.createVerticalStrut(10));
	}
	
	public void setResult(SeriesResult newResult) {
		if (amplitudes != null) this.remove(amplitudes);
		if (newResult != null) {
			amplitudes = new SeriesAmplitudesViewImage(newResult) {
				public void currentPointChanged(int point) {
					currentPoint = point;
					currentTaskChanged(point);
				}
			};
			this.add(amplitudes);
		}
		validate();
	}
	
	public void currentTaskChanged(int point) {}
}
