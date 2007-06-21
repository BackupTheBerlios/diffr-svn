package de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface.periodicSurface;

import de.berlios.diffr.*;
import de.berlios.diffr.inputData.*;
import de.berlios.diffr.exceptions.ObjectIsnotEditableException;

public class PeriodicSurface extends Surface {
	public PeriodicSurface() {
		addListenerToShape();
	}
	public PeriodicSurface(SurfaceShape shape, SurfaceConductivity conductivity) {
	    this.shape = shape;
	    this.conductivity = conductivity;
	    addListenerToShape();
	}
	
	public Surface clone() {
		return new PeriodicSurface(shape.clone(), conductivity);
	}
	
	public void restorationAfterSerialization() {
		addListenerToShape();
	}
	
	private void addListenerToShape() {
		shape.addModelChangingListener(new ModelChangingListener() {
			public void modelWasChanged(Model m) {
				modelWasChangedEvent();
			}
		});
	}

	public SurfaceShape getShape() {
		return shape;
	}

	public SurfaceConductivity getConductivity() {
		return conductivity;
	}
	
	public void setConductivity(SurfaceConductivity c) throws ObjectIsnotEditableException {
		if (!isEditable()) throw new ObjectIsnotEditableException();
		conductivity = c;
		modelWasChangedEvent();
	}
	
	public void setEditable(boolean b) {
		super.setEditable(b);
		shape.setEditable(b);
	}

	private SurfaceShape shape = new SurfaceShape();
	private SurfaceConductivity conductivity = new PerfectConductivity();

	public PeriodicSurface nonDimensioning() {
		SurfaceShape nonDimensionalShape = shape.nonDimensioning(); 
		SurfaceConductivity nonDimensionalConductivity = conductivity.nonDimensioning(); 
		return new PeriodicSurface(nonDimensionalShape, nonDimensionalConductivity);
	}
}
