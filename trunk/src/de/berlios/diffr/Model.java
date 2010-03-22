package de.berlios.diffr;

import java.io.Serializable;
import java.util.*;

public abstract class Model implements Serializable {
	private static final long serialVersionUID = 1L;
	private boolean editable = true;
	private transient ArrayList<ModelChangingListener> modelChangingListeners = new ArrayList<ModelChangingListener>();
	public void addModelChangingListener(ModelChangingListener l) {
		if (modelChangingListeners == null) modelChangingListeners = new ArrayList<ModelChangingListener>(); 
		modelChangingListeners.add(l);
	}
	public void removeModelChangingListener(ModelChangingListener l) {
		if (modelChangingListeners == null) modelChangingListeners = new ArrayList<ModelChangingListener>();
		modelChangingListeners.remove(l);
	}
	protected void modelWasChangedEvent() {
		if (modelChangingListeners == null) modelChangingListeners = new ArrayList<ModelChangingListener>();
		Iterator<ModelChangingListener> i = modelChangingListeners.iterator();
		while (i.hasNext())
			i.next().modelWasChanged(this);
	}
	public void setEditable(boolean b) {
		editable = b;
	}
	public boolean isEditable() {
		return editable;
	}
	public void restorationAfterSerialization() {}
}
