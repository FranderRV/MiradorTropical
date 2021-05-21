package com.mirador.vo;

import java.util.ArrayList;

public class Detalles {
	
	private int idProducto;
	private int idFactura;
	private String descripcion;
	private int cantidad;
	private int precio;	
	
	public Detalles(int idProducto, int idFactura,  int cantidad, int precio,String descripcion) {
		super();
		this.idProducto = idProducto;
		this.descripcion = descripcion;
		this.idFactura = idFactura;
		this.cantidad = cantidad;
		this.precio = precio;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	public int getIdFactura() {
		return idFactura;
	}
	public void setIdFactura(int idFactura) {
		this.idFactura = idFactura;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public int getPrecio() {
		return precio;
	}
	public void setPrecio(int precio) {
		this.precio = precio;
	}
	
	@Override
	public String toString() {
		return "Detalles [idDetalle=" + idProducto + ", idFactura=" + idFactura + ", cantidad=" + cantidad + ", precio="
				+ precio + "]";
	}

	
}
