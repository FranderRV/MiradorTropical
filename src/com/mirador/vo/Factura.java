package com.mirador.vo;

import java.util.ArrayList;

public class Factura {
	private int idFactura;
	private String cliente;
	private String fecha;
	private String correo;
	private ArrayList<Detalles> listaDetalles;
	
	public Factura() {
		this.listaDetalles = new ArrayList<Detalles>();
	}
	public Factura(int idFactura, String cliente, String fecha, String correo) {
		this.idFactura = idFactura;
		this.cliente = cliente;
		this.fecha = fecha;
		this.correo = correo;
		this.listaDetalles = new ArrayList<Detalles>();
	}

	public Factura(int idFactura, String cliente, String fecha,String correo, ArrayList<Detalles> listaDetalles) {
		super();
		this.idFactura = idFactura;
		this.cliente = cliente;
		this.fecha = fecha;
		this.correo = correo;
		this.listaDetalles = listaDetalles;
	}
	
	public void insertarDetalle(Detalles d) {
		listaDetalles.add(d);
	}
	public void  eliminarDetalle(Detalles d) {
		listaDetalles.remove(d);
	}

	public ArrayList<Detalles> getListaDetalles() {
		return listaDetalles;
	}


	public void setListaDetalles(ArrayList<Detalles> listaDetalles) {
		this.listaDetalles = listaDetalles;
	}


	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public int getIdFactura() {
		return idFactura;
	}
	public void setIdFactura(int idFactura) {
		this.idFactura = idFactura;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}	
	public boolean compare(ArrayList<Detalles> lista, Detalles d) {
		boolean salida = false;
		for (Detalles detalles : lista) {
			if(d.getIdProducto()==detalles.getIdProducto()) {
				return true;
			}
		}
		return salida;
	}
	public ArrayList<Detalles> limpiarLista(ArrayList<Detalles> lista, Detalles d) {
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
		return "Factura:" + idFactura + "| Cliente: " + cliente + "| Fecha: " + fecha.substring(0,10) + "| Correo: " + correo;
	}


}
