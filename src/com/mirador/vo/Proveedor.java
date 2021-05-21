package com.mirador.vo;

import java.util.ArrayList;

public class Proveedor {
	private int idProveedor;
	private String nombre;
	private String direccion;
	private String cedula;
	private String correo;
	private ArrayList<Telefonos> listaTelefonos;
	
	
	
	public Proveedor(int idProveedor, String nombre, String direccion, String cedula, String correo) {
		super();
		this.idProveedor = idProveedor;
		this.nombre = nombre;
		this.direccion = direccion;
		this.cedula = cedula;
		this.correo = correo;
		this.listaTelefonos = new ArrayList<Telefonos>();
	}
	public String getCedula() {
		return cedula;
	}
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public ArrayList<Telefonos> getListaTelefonos() {
		return listaTelefonos;
	}
	public void setListaTelefonos(ArrayList<Telefonos> listaTelefonos) {
		this.listaTelefonos = listaTelefonos;
	}
	public int getIdProveedor() {
		return idProveedor;
	}
	public void setIdProveedor(int idProveedor) {
		this.idProveedor = idProveedor;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	@Override
	public String toString() {
		return nombre;
	}
	
	
}
