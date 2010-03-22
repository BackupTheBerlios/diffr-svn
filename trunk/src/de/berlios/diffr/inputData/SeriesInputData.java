package de.berlios.diffr.inputData;

import de.berlios.diffr.Model;
import de.berlios.diffr.ModelChangingListener;

public class SeriesInputData extends Model {
	private static final long serialVersionUID = 1L;
	private Surface surface;
	private IncidentWaveSeries waveSeries;
	public SeriesInputData(Surface surface, IncidentWaveSeries waveSeries) {
		this.surface = surface;
		this.waveSeries = waveSeries;
		surface.addModelChangingListener(new ModelChangingListener() {
			public void modelWasChanged(Model model) {
				modelWasChangedEvent();
			}
		});
		waveSeries.addModelChangingListener(new ModelChangingListener() {
			public void modelWasChanged(Model model) {
				modelWasChangedEvent();
			}
		});
	}
	
	public SeriesInputData() {
		this(new Surface(), new IncidentWaveSeries());
	}
	
	public void restorationAfterSerialization() {
		surface.restorationAfterSerialization();
		waveSeries.restorationAfterSerialization();
		surface.addModelChangingListener(new ModelChangingListener() {
			public void modelWasChanged(Model model) {
				modelWasChangedEvent();
			}
		});
		waveSeries.addModelChangingListener(new ModelChangingListener() {
			public void modelWasChanged(Model model) {
				modelWasChangedEvent();
			}
		});
	}
	public Surface getSurface() {
		return surface;
	}
	public IncidentWaveSeries getIncidentWaveSeries() {
		return waveSeries;
	}
	public void setEditable(boolean b) {
		super.setEditable(b);
		surface.setEditable(b);
		waveSeries.setEditable(b);
	}
}
