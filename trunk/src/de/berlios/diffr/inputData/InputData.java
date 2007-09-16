package de.berlios.diffr.inputData;

import de.berlios.diffr.*;
import de.berlios.diffr.algorithms.DimensionData;

public class InputData extends Model {
	private Surface surface;
	private IncidentWave incidentWave;
	public InputData(Surface surface, IncidentWave field) {
		this.surface = surface;
		this.incidentWave = field;
		surface.addModelChangingListener(new ModelChangingListener() {
			public void modelWasChanged(Model model) {
				modelWasChangedEvent();
			}
		});
		incidentWave.addModelChangingListener(new ModelChangingListener() {
			public void modelWasChanged(Model model) {
				modelWasChangedEvent();
			}
		});
	}
	public InputData() {
		this(new Surface(), new IncidentWave());
	}
	public InputData clone() {
		return new InputData(surface.clone(), incidentWave.clone());
	}
	public void restorationAfterSerialization() {
		surface.restorationAfterSerialization();
		incidentWave.restorationAfterSerialization();
		surface.addModelChangingListener(new ModelChangingListener() {
			public void modelWasChanged(Model model) {
				modelWasChangedEvent();
			}
		});
		incidentWave.addModelChangingListener(new ModelChangingListener() {
			public void modelWasChanged(Model model) {
				modelWasChangedEvent();
			}
		});
	}
	public Surface getSurface() {
		return surface;
	}
	public IncidentWave getIncidentWave() {
		return incidentWave;
	}
	public void setEditable(boolean b) {
		super.setEditable(b);
		surface.setEditable(b);
		incidentWave.setEditable(b);
	}

	public InputData nonDimensioning(DimensionData data) {

		Surface nonDimensionalSurface = surface.nonDimensioning(data); 
		IncidentWave nonDimensionalWave = incidentWave.nonDimensioning(data);
		
		return new InputData(nonDimensionalSurface, nonDimensionalWave);
	}
}
