package de.berlios.diffr.algorithms;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import de.berlios.diffr.exceptions.WrongTypeException;

public class AlgorithmType implements Serializable {
	private static final long serialVersionUID = 1L;
	private final String title;
	private final String autor;
	private final String version;
	private Class algorithmClass;
	
	public boolean equals(Object o) {
		if (!AlgorithmType.class.isInstance(o)) return false;
		AlgorithmType t = (AlgorithmType)o;
		if (t.algorithmClass != algorithmClass) return false;
		return true;
	}
	
	public AlgorithmType(Class c) throws WrongTypeException {
		this.algorithmClass = c;
		Algorithm alg = newInstance();
		this.title = alg.getTitle();
		this.autor = alg.getAutor();
		this.version = alg.getVersion();
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getAutor() {
		return autor;
	}
	
	public String getVersion() {
		return version;
	}
	
	public String toString() {
		return title + " " + version;
	}
	
	public Algorithm newInstance() throws WrongTypeException {
		try {
			Class[] parameters = {this.getClass()};
			return (Algorithm)algorithmClass.getConstructor(parameters).newInstance(this);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		throw new WrongTypeException();
	}
}
