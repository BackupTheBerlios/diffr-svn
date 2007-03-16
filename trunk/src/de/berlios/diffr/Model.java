package de.berlios.diffr;

import java.util.*;

public abstract class Model {
	private boolean editable = true;
	private ArrayList modelChangingListeners = new ArrayList();
	public void addModelChangingListener(ModelChangingListener l) {
		modelChangingListeners.add(l);
	}
	public void removeModelChangingListener(ModelChangingListener l) {
		modelChangingListeners.remove(l);
	}
	protected void modelWasChangedEvent() {
		Iterator i = modelChangingListeners.iterator();
		while (i.hasNext()) {
			ModelChangingListener l = ( ModelChangingListener )i.next();
			l.modelWasChanged(this);
		}
	}
	public void setEditable(boolean b) {
		editable = b;
	}
	public boolean isEditable() {
		return editable;
	}
}
