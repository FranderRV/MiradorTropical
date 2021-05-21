package com.mirador.vo;

public class CategoriaComida {
	private int idCategoriaComida;
	private String nombre;
	public CategoriaComida(int idCategoriaComida, String nombre) { 
		this.idCategoriaComida = idCategoriaComida;
		this.nombre = nombre;
	}
	public int getIdCategoriaComida() {
		return idCategoriaComida;
	}
	public void setIdCategoriaComida(int idCategoriaComida) {
		this.idCategoriaComida = idCategoriaComida;
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
