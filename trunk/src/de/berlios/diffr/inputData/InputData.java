package de.berlios.diffr.inputData;

import de.berlios.diffr.*;

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
}
