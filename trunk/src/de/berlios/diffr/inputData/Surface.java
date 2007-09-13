package de.berlios.diffr.inputData;

import de.berlios.diffr.*;
import de.berlios.diffr.inputData.surface.*;
import de.berlios.diffr.algorithms.DimensionData;
import de.berlios.diffr.exceptions.ObjectIsnotEditableException;

public class Surface extends Model {
	public Surface() {
		addListenerToShape();
	}
	public Surface(SurfaceShape shape, SurfaceConductivity conductivity) {
	    this.shape = shape;
	    this.conductivity = conductivity;
	    addListenerToShape();
	}
	
	public Surface clone() {
		return new Surface(shape.clone(), conductivity);
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

	public Surface nonDimensioning(DimensionData d) {
		SurfaceShape nonDimensionalShape = shape.nonDimensioning(); 
		SurfaceConductivity nonDimensionalConductivity = conductivity.nonDimensioning(); 
		return new Surface(nonDimensionalShape, nonDimensionalConductivity);
	}
}
