package com.mirador.vo;

import java.util.ArrayList;

public class Compra {
private int idCompra;
private String fecha;
private int idProveedor;
private ArrayList<ListaCompras> detalles;
public Compra(int idCompra, String fecha, int idProveedor) {
	super();
	this.idCompra = idCompra;
	this.fecha = fecha;
	this.idProveedor = idProveedor;
	this.detalles = new ArrayList<ListaCompras> ();
}
public int getIdCompra() {
	return idCompra;
}
public void setIdCompra(int idCompra) {
	this.idCompra = idCompra;
}
public String getFecha() {
	return fecha;
}
public void setFecha(String fecha) {
	this.fecha = fecha;
}
public int getIdProveedor() {
	return idProveedor;
}
public void setIdProveedor(int idProveedor) {
	this.idProveedor = idProveedor;
}
public ArrayList<ListaCompras> getDetalles() {
	return detalles;
}
public void setDetalles(ArrayList<ListaCompras> detalles) {
	this.detalles = detalles;
}

}
