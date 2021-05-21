package com.mirador.vo;

import java.util.ArrayList;

public class Producto {
	private int idProducto;
	private String nombre;
	private String codigo;
	private int cantidad;
	private int precio;
	private int impuesto;
	private CategoriaComida categoria;
	

	public Producto(int idProducto,String nombre, String codigo, int cantidad,
			CategoriaComida categoria, int precio, int ganancia) {

		this.idProducto = idProducto;
		this.nombre = nombre;
		this.codigo = codigo;
		this.cantidad = cantidad;
		this.precio = precio;
		this.impuesto = ganancia;
		this.categoria = categoria;
	}
	public int getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public CategoriaComida getCategoria() {
		return categoria;
	}
	public void setCategoria(CategoriaComida categoria) {
		this.categoria = categoria;
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
	public int getGanancia() {
		return impuesto;
	}
	public void setGanancia(int impuesto) {
		this.impuesto = impuesto;
	}

	@Override
	public String toString() {
		return  codigo+" - "+nombre;
	}
	
	public String impre() {
		return "Producto [idProducto=" + idProducto + ", nombre=" + nombre + ", codigo=" + codigo + ", cantidad="
				+ cantidad + ", precio=" + precio + ", ganancia=" + impuesto + ", categoria=" + categoria + "]";
	}
	
	
}
