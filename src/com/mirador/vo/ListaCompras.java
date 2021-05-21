package com.mirador.vo;

import java.util.ArrayList;

public class ListaCompras {
private String descripcion;
private int idCompra;
private int idProducto;
private int cantidad;
private int precio;



public ListaCompras() {}
public ListaCompras(String descripcion, int idCompra, int idProducto, int cantidad, int precio) {
	super();
	this.descripcion = descripcion;
	this.idCompra = idCompra;
	this.idProducto = idProducto;
	this.cantidad = cantidad;
	this.precio = precio;
}

public String getDescripcion() {
	return descripcion;
}

public void setDescripcion(String descripcion) {
	this.descripcion = descripcion;
}

public int getIdCompra() {
	return idCompra;
}
public void setIdCompra(int idCompra) {
	this.idCompra = idCompra;
}
public int getIdProducto() {
	return idProducto;
}
public void setIdProducto(int idProducto) {
	this.idProducto = idProducto;
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

public boolean compare(ArrayList<ListaCompras> lista, ListaCompras d) {
	boolean salida = false;
	for (ListaCompras detalles : lista) {
		if(d.getIdProducto()==detalles.getIdProducto()) {
			return true;
		}
	}
	return salida;
}
public ArrayList<ListaCompras> limpiarLista(ArrayList<ListaCompras> lista, ListaCompras d) {
	boolean primera = false;
	
		if(!compare(lista, d)) {
			lista.add(d);
			primera = true;		
	}
	for (int i = 0; i < lista.size(); i++) {
		if(!primera) {
		if(d.getIdProducto() == lista.get(i).getIdProducto()) {
			int a = d.getCantidad();
			lista.get(i).setCantidad( lista.get(i).getCantidad()+a);
		}
		}
	}
return lista;
}

}
