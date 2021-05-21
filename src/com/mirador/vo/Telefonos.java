package com.mirador.vo;

import java.util.ArrayList;

public class Telefonos {
	private int idTelefono;
	private int idProveedor;
	private int numero;
	
	public Telefonos() {}
	public Telefonos(int idTelefono, int idProveedor, int numero) {
		super();
		this.idTelefono = idTelefono;
		this.idProveedor = idProveedor;
		this.numero = numero;
	}
	public int getIdTelefono() {
		return idTelefono;
	}
	public void setIdTelefono(int idTelefono) {
		this.idTelefono = idTelefono;
	}
	public int getIdProveedor() {
		return idProveedor;
	}
	public void setIdProveedor(int idProveedor) {
		this.idProveedor = idProveedor;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public boolean compare(ArrayList<Telefonos> lista, Telefonos t) {
		boolean salida = false;
		for (Telefonos tel: lista) {
			if(t.getNumero()==tel.getNumero()) {
				return true;
			}
		}
		return salida;
	}
	@Override
	public String toString() {
		return  numero+"";
	}
	
}
