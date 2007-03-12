package de.berlios.diffr.result;

public class Result {
	private ReflectedField reflectedField;
	public Result(ReflectedField field) {
		this.reflectedField = field;
	}
	public ReflectedField getReflectedField() {
		return reflectedField;
	}
}
