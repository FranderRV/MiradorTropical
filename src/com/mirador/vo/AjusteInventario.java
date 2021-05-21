package com.mirador.vo;

import java.util.ArrayList;

public class AjusteInventario {
	private int idAjuste;
	private String fecha;
	private ArrayList<ListaAjustesInventario> detalles;
	public AjusteInventario(int idAjuste, String fecha) {
		super();
		this.idAjuste = idAjuste;
		this.fecha = fecha;
		this.detalles = new ArrayList<ListaAjustesInventario>();
	}
	public int getIdAjuste() {
		return idAjuste;
	}
	public void setIdAjuste(int idAjuste) {
		this.idAjuste = idAjuste;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public ArrayList<ListaAjustesInventario> getDetalles() {
		return detalles;
	}
	public void setDetalles(ArrayList<ListaAjustesInventario> detalles) {
		this.detalles = detalles;
	}
	
	
	
}
