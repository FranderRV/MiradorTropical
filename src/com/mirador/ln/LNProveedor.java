package com.mirador.ln;

import java.util.ArrayList;

import com.mirador.db.DBProveedor;
import com.mirador.ui.ViewProveedores;
import com.mirador.vo.Proveedor;
import com.mirador.vo.Telefonos;

public class LNProveedor {
	private DBProveedor db;	
	public LNProveedor() {

		this.db = new DBProveedor();
	}
	public ArrayList<Proveedor> getProveedores(String buscar){
		return db.getProveedores(buscar);
	}
	public Proveedor getProveedor(int id) {
		return db.getProveedor(id);
	}
	public void insertar(Proveedor p ) {
		db.insertarProveedor(p);
	}
	public void eliminar(int id) {
		db.eliminarProveedor(id);
	}
	public void actualizar(Proveedor p, int caso) {
		db.actualizar(p,caso);
	}
	
	public int getPosicionLista(String  nombre) {
		int pos = 0;
		System.out.println("arriba");
		for (int i = 0; i < getProveedores("").size(); i++) {
			System.out.println(getProveedores("").get(i).getNombre()+"=="+nombre);
			if(getProveedores("").get(i).getNombre().equalsIgnoreCase(nombre) ) {
				pos  = i;
				break;
			}
		}
		return pos;
	}
	
	public String[] getColumnasProveedor() {
		return new String[] {"ID","Nombre","Cedula","Correo","Direccion","Telefonos" };
		
	}
	public Object[][] getDatosProveedor(String buscar){
		ArrayList<Proveedor> lista = getProveedores(buscar);
		Object[][]	 matriz = new Object[lista.size()][getColumnasProveedor().length];
		int i = 0;
		for (Proveedor p : lista) {
			matriz[i][0] = p.getIdProveedor();
			matriz[i][1] = p.getNombre();
			matriz[i][2] = p.getCedula();
			matriz[i][3] = p.getCorreo();
			matriz[i][4] = p.getDireccion();	
			matriz[i][5] ="Numeros";	
			i++;
		}
				return matriz;
	}
	
	// TELEFONOS
	
	public ArrayList<Telefonos> getTelefonos(int id){
		return db.getTelefonos(id);
	}
	
	public void insertarTelefono(int id, ArrayList<Telefonos> lista) {
		db.insertarTelefonos(id, lista);
	}
	
	public void eliminarDetalles(int id) {
		db.eliminarTelefonos(id);
	 }


}
