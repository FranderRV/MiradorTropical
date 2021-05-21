package com.mirador.ln;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;

import com.mirador.db.DBFacturas;
import com.mirador.vo.Detalles;
import com.mirador.vo.Factura;
import com.mirador.vo.Producto;
import com.toedter.components.UTF8ResourceBundle;


public class LNFacturas{
	
	private DBFacturas db;
	
	public LNFacturas() {
		db = new DBFacturas();
	}
	
	public ArrayList<Factura> getFacturas(){
		return db.getFacturas();
	}
	
	public Factura getFactura(int id) {
		return db.getFactura(id);
	}
	
	public void insertar(Factura f ) {
		db.insertarFactura(f);
	}
	public void eliminarListaDetalles(int id) {
		db.eliminarDetalles(id);
	}
	
	public void actualizarFactura(Factura f ) {
		db.actualizarFactura(f);
	}
	
	public ArrayList<Detalles> getDetalles(int idFactura){
		return db.getDetalles(idFactura);
	}
	
	public void eliminarDetalles(int idFactura) {
		db.eliminarDetalles(idFactura);
	}
	
	public int getLastFactura() {
		return db.getLastFactura();
	}
	
	public int getTotalFactura(int id) {
		return db.getTotalFactura(id);
	}
	public ArrayList<Factura> getFacturasPorFecha(String dateUno, String dateDos){
	return db.getFacturasPorFecha(dateUno, dateDos);
	}
	public void eliminarFactura(int id) {
		db.eliminarFactura(id);
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
	 public int[] getDatosFactura(int caso,String fechaUno, String fechaDos) {
		 return db.getDatosFactura(caso,fechaUno,fechaDos);
	 }
	public String[] columnas() {
		return new String[]{"#Factura","Fecha","Total"};
	}
	public Object[][] getDatos(ArrayList<Factura> listaUno ){
		ArrayList<Factura> lista = listaUno;
		Object [][] matriz = new Object[lista.size()][columnas().length];

		int i = 0;
		for (Factura f : lista) {
			matriz[i][0] = f.getIdFactura();
			String fecha = f.getFecha().substring(0, 10);
			matriz[i][1] = fecha;
			matriz[i][2] = "\u20A1" +getTotalFactura(f.getIdFactura());
			i++;
		}
		return matriz;
	}
	
	public String[] columnasDetalles() {
		return new String[]{"Descripción","Precio","Cantidad","Sub Total","ID"};
	}
	
	public Object[][] getDatosDetalles( ArrayList<Detalles> detalles){
		ArrayList<Detalles> lista = detalles;
		Object [][] matriz = new Object[lista.size()][columnasDetalles().length];

		int i = 0;
		
		for (Detalles d : lista) {
			matriz[i][0] = d.getDescripcion();
			matriz[i][1] = d.getPrecio();
			matriz[i][2] = d.getCantidad();
			matriz[i][3] =  	"\u20A1" + (d.getPrecio()*d.getCantidad());
			matriz[i][4] = d.getIdProducto();
			i++;
		}
		return matriz;
	}
	
	public int suma(ArrayList<Detalles> detalles) {
		int total = 0;
		for (Detalles d : detalles) {
			total+=(d.getPrecio()*d.getCantidad());
		}
		return total;
	}
	
}
