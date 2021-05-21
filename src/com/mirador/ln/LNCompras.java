package com.mirador.ln;

import java.util.ArrayList;

import com.mirador.db.DBCompra;
import com.mirador.vo.Compra;
import com.mirador.vo.Detalles;
import com.mirador.vo.ListaCompras;

public class LNCompras {
	private DBCompra db;
	
	public  LNCompras() {
		this.db = new DBCompra();
	}
	
	public ArrayList<Compra> getCompras(){
	return db.getCompras();	
	}
	public Compra getCompra(int id){
		return db.getCompra(id);
	}
	public void insertarCompra(Compra c) {
		db.insertarCompra(c);
	}
	public void editarCompra(Compra c) {
		db.editarCompra(c);
	}
	public void eliminarDetalles(int idCompra) {
		db.eliminarDetalles(idCompra);
	}
	public void eliminarCompra(int id) {
		db.eliminarCompra(id);
	}
	public int getTotalCompra(int id) {
		return db.getTotalCompra(id);
	}
	public ArrayList<ListaCompras> getDetalles(int idCompra){
	return db.getDetalles(idCompra)	;
	}
	public void recorrerDetallesEliminar(int idCompra) {
		db.recorrerDetallesEliminar(idCompra);
	}
	public int getLastCompra() {
		return db.getLastCompra();
	}
	public int suma(ArrayList<ListaCompras> lista) {
		int total = 0;
		for (ListaCompras d : lista) {
			total+=(d.getPrecio()*d.getCantidad());
		}
		return total;
	}
	public String getNombreProveedor(int id) {
		return db.getNombreProveedor(id);
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
	
	
	public String[] getColumnasCompras() {
		return new String[] {"#Compra","Proveedor","Fecha","Total"};
	}
	
	public Object[][] getDatosCompras(ArrayList<Compra> l){
		ArrayList<Compra> lista = l;
		Object[][] matriz = new Object[lista.size()][getColumnasCompras().length];
		int i = 0;
		System.out.println(l.size());
		for (Compra c : lista) {
			System.out.println(c.toString());
			matriz[i][0] = c.getIdCompra();
			matriz[i][1] = getNombreProveedor(c.getIdProveedor());
			String fecha =  c.getFecha().substring(0,10);
			matriz[i][2] =fecha;
			matriz[i][3] = "\u20A1" +getTotalCompra(c.getIdCompra());
			i++;
		}
		return matriz;
	}
	
	public String[] getColumnasDetalles() {
		return new String[]{"Descripción","Precio","Cantidad","Total"};
	}
	public Object[][] getDatosDetalles( ArrayList<ListaCompras> detalles){
		ArrayList<ListaCompras> lista = detalles;
		Object [][] matriz = new Object[lista.size()][getColumnasDetalles().length];

		int i = 0;
		
		for (ListaCompras d : lista) {
			matriz[i][0] = d.getDescripcion();
			matriz[i][1] = "\u20A1" +d.getPrecio();
			matriz[i][2] = d.getCantidad();
			matriz[i][3] = (d.getPrecio()*d.getCantidad());
			i++;
		}
		return matriz;
	}
	
	public ArrayList<Compra> getComprasPorFecha(String dateUno, String dateDos){
	return db.getComprasPorFecha(dateUno, dateDos);
	}
	
}
