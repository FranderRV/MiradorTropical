package com.mirador.vo;

public class Unidad { 
	private int idUnidad;
	private String nombre;
	public Unidad(int idUnidad, String nombre) {
		super();
		this.idUnidad = idUnidad;
		this.nombre = nombre;
	}
	public int getIdUnidad() {
		return idUnidad;
	}
	public void setIdUnidad(int idUnidad) {
		this.idUnidad = idUnidad;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@Override
	public String toString() {
		return nombre;
	}
	
}
