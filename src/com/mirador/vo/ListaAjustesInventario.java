package com.mirador.vo;

import java.util.ArrayList;

public class ListaAjustesInventario {
	private int idAjuste;
	private int idProducto;
	private int cantidad;
	private String descripcion;
	private int precio;
	public ListaAjustesInventario() {}
	public ListaAjustesInventario(int idAjuste, int idProducto, int cantidad, String descripcion,int precio) {
		super();
		this.idAjuste = idAjuste;
		this.idProducto = idProducto;
		this.cantidad = cantidad;
		this.descripcion = descripcion;
		this.precio = precio;
	}
	public int getIdAjuste() {
		return idAjuste;
	}
	public void setIdAjuste(int idAjuste) {
		this.idAjuste = idAjuste;
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
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	

	public int getPrecio() {
		return precio;
	}
	public void setPrecio(int precio) {
		this.precio = precio;
	}
	public boolean compare(ArrayList<ListaAjustesInventario> lista, ListaAjustesInventario d) {
		boolean salida = false;
		for (ListaAjustesInventario detalles : lista) {
			if(d.getIdProducto()==detalles.getIdProducto()) {
				return true;
			}
		}
		return salida;
	}
	public ArrayList<ListaAjustesInventario> limpiarLista(ArrayList<ListaAjustesInventario> lista, ListaAjustesInventario d) {
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
	@Override
	public String toString() {
		return "ListaAjustesInventario [idAjuste=" + idAjuste + ", idProducto=" + idProducto + ", cantidad=" + cantidad
				+ ", descripcion=" + descripcion + ", precio=" + precio + "]";
	}

	
}
