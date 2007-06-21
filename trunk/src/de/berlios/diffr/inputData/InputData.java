package de.berlios.diffr.inputData;

import de.berlios.diffr.*;
import de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface.ImpingingPlaneWave;
import de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface.NonDimensionInputData;
import de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface.periodicSurface.PeriodicSurface;

public class InputData extends Model {
	private Surface surface;
	private ImpingingField impingingField;
	public InputData(Surface surface, ImpingingField field) {
		this.surface = surface;
		this.impingingField = field;
		surface.addModelChangingListener(new ModelChangingListener() {
			public void modelWasChanged(Model model) {
				modelWasChangedEvent();
			}
		});
		impingingField.addModelChangingListener(new ModelChangingListener() {
			public void modelWasChanged(Model model) {
				modelWasChangedEvent();
			}
		});
	}
	public InputData clone() {
		return new InputData(surface.clone(), impingingField.clone());
	}
	public void restorationAfterSerialization() {
		surface.restorationAfterSerialization();
		impingingField.restorationAfterSerialization();
		surface.addModelChangingListener(new ModelChangingListener() {
			public void modelWasChanged(Model model) {
				modelWasChangedEvent();
			}
		});
		impingingField.addModelChangingListener(new ModelChangingListener() {
			public void modelWasChanged(Model model) {
				modelWasChangedEvent();
			}
		});
	}
	public Surface getSurface() {
		return surface;
	}
	public ImpingingField getImpingingField() {
		return impingingField;
	}
	public void setEditable(boolean b) {
		super.setEditable(b);
		surface.setEditable(b);
		impingingField.setEditable(b);
	}

	public NonDimensionInputData nonDimensioning(){

		PeriodicSurface nonDimensionalSurface = ((PeriodicSurface)surface).nonDimensioning(); 
		ImpingingPlaneWave nonDimensionalWave = ((ImpingingPlaneWave) impingingField).nonDimensioning(((PeriodicSurface)surface).getShape().getPeriod());
		
		return new NonDimensionInputData(nonDimensionalSurface, nonDimensionalWave);
	}
}
