package de.berlios.diffr.result;

import de.berlios.diffr.*;

public class Result extends Model {
	private ReflectedField reflectedField;
	public Result(ReflectedField field) {
		this.reflectedField = field;
	}
	public ReflectedField getReflectedField() {
		return reflectedField;
	}
}
