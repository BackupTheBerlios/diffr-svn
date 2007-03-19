package de.berlios.diffr.algorithms;

import de.berlios.diffr.Model;
import de.berlios.diffr.exceptions.ObjectIsnotEditableException;
import de.berlios.diffr.exceptions.WrongTypeException;

public class AlgorithmParameter extends Model {
	private String description;
	private Object value;
	
	public AlgorithmParameter(String description, Object value) {
		this.description = description;
		this.value = value;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Object getValue() {
		return value;
	}
	
	public void setValue(Object newValue) throws WrongTypeException, ObjectIsnotEditableException {
		if (isEditable())
			if (value.getClass().isInstance(newValue))
				value = newValue;
			else
				throw new WrongTypeException();
		else
			throw new ObjectIsnotEditableException();
	}
}
