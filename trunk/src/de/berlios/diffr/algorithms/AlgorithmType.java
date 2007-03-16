package de.berlios.diffr.algorithms;

import java.lang.reflect.InvocationTargetException;

import de.berlios.diffr.exceptions.WrongTypeException;

public class AlgorithmType {
	private final String title;
	private final String autor;
	private final String version;
	private Class algorithmClass;
	
	public AlgorithmType(String title, String autor, String version, Class c) {
		this.title = title;
		this.autor = autor;
		this.version = version;
		this.algorithmClass = c;
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
