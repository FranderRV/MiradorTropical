package com.mirador.ln;

import java.util.ArrayList;

import org.jdesktop.swingx.table.DatePickerCellEditor;

import com.mirador.db.DBAjusteInventario;
import com.mirador.db.DBCompra;
import com.mirador.vo.AjusteInventario;
import com.mirador.vo.ListaAjustesInventario;

public class LNAjuste {
	private DBAjusteInventario db;
	
	public LNAjuste() {
		this.db = new DBAjusteInventario();
	}
	
	public ArrayList<AjusteInventario> getAjustesInventario(){
	return db.getAjustesInventario();
	}
	
	public ArrayList<AjusteInventario> getAjustesPorFecha(String dateUno, String dateDos){
		return db.getAjustesPorFecha(dateUno, dateDos);
	}
	
	public AjusteInventario getAjusteInventario(int id){
		return db.getAjusteInventario(id);
	}
	
	public void insertarAjuste(AjusteInventario a) {
	db.insertarAjuste(a);	
	}
	
	public void actualizarAjuste(AjusteInventario a) {
		db.actualizarAjuste(a);
	}
	
	public void eliminarAjuste(int idAjuste) {
	db.eliminarAjuste(idAjuste);
	}
	public int getLastAjuste() {
	return 	db.getLastAjuste();
	}
	
	public int[] vectorFecha(String fechaBses){ 
		int vector[] = new int[3];
		String fecha = fechaBses.substring(0,10);
		String vectorS[] = fecha.split("-");
		for (int i = 0; i < vector.length; i++) {
			vector[i] = Integer.parseInt(vectorS[i]);
		} 
		return vector;
	}
	//DETALLES
	
	public ArrayList<ListaAjustesInventario> getDetalles(int idAjuste){
		return db.getDetalles(idAjuste);	
	}
	public void insertarDetalles(int idAjuste,ArrayList<ListaAjustesInventario> lista) {
		db.insertarDetalles(idAjuste, lista);
	}
	
	public String[] getColumnasDetalles() {
		return new String[] {"IDProducto","Producto","Precio","Cantidad" };
	}
	
	public Object[][] getDatosDetalles(ArrayList<ListaAjustesInventario> lista){
		ArrayList<ListaAjustesInventario> listaAjus = lista;
		Object [][] matriz = new Object[listaAjus.size()][getColumnasDetalles().length];
		int i = 0;
		for (ListaAjustesInventario l  : listaAjus) {
			
			matriz[i][0] = l.getIdProducto();
			matriz[i][1] = l.getDescripcion();
			matriz[i][2] = "\u20A1" +l.getPrecio();
			matriz[i][3] = l.getCantidad(); 
			i++;
		}
		return matriz;
	}
	
	public String[] getColumnasAjustes() {
		return new String[] {"#Ajuste","Fecha"};
	}
	
	public Object[][] getDatosAjuste(ArrayList<AjusteInventario> lista){
		ArrayList<AjusteInventario> listaAjus = lista;
		Object [][] matriz = new Object[listaAjus.size()][getColumnasDetalles().length];
		int i = 0;
		for (AjusteInventario l  : listaAjus) {
			matriz[i][0] = l.getIdAjuste();
			String fecha = l.getFecha().substring(0,10);
			matriz[i][1] = fecha;
			i++;
		}
		return matriz;
	}
	
	
	
}
