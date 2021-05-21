package com.mirador.db;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.mirador.vo.Proveedor;
import com.mirador.vo.Telefonos;

public class DBProveedor extends Conexion {

	public DBProveedor() {

	}

	public ArrayList<Proveedor> getProveedores(String buscar){
		ArrayList<Proveedor> lista = new ArrayList<Proveedor>();
		try {
			ResultSet rs = consultar("select * from proveedores where nombre like '%"+buscar+"%'");
			while (rs.next()) {
				lista.add(new Proveedor(rs.getInt("id_proveedor"), rs.getString("nombre")
						, rs.getString("direccion"),rs.getString("cedula"),rs.getString("correo")));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return lista;
	}

	public Proveedor getProveedor(int id) {
		Proveedor p = null;

		try {

			ResultSet rs = consultar("select * from proveedores where id_proveedor = "+id);
			p  = new Proveedor(rs.getInt("id_proveedor"), rs.getString("nombre")
					, rs.getString("direccion"),rs.getString("cedula"),rs.getString("correo"));

		} catch (Exception e) { 
		}

		return p;
	}
	public void insertarProveedor(Proveedor p ) {
		ejecutar("insert into proveedores(nombre, direccion, cedula, correo) values("
				+"'"+p.getNombre()+"',"
				+ "'"+p.getDireccion()+"',"
				+ "'"+p.getCedula()+"',"
				+ "'"+p.getCorreo()+"'"
				+ ");");
		insertarTelefonos(getLastProveedor(0), p.getListaTelefonos());
	}

	public void actualizar(Proveedor p , int caso) {
		eliminarTelefonos(caso);
		System.out.println(p.getNombre());
		ejecutar("update proveedores set "
				+ "nombre = '"+p.getNombre()+"',"
				+ "direccion = '"+p.getDireccion()+"' ,"
				+ "cedula = '"+p.getCedula()+"',"
				+ "correo = '"+p.getCorreo()+"' "
				+ " where id_proveedor = "+caso);
		insertarTelefonos(caso, p.getListaTelefonos());
	}

	public void eliminarProveedor(int id) {
		eliminarTelefonos(id);
		ejecutar("delete from proveedores where id_proveedor = "+id);
	}


	//TELEFONOS

	public ArrayList<Telefonos> getTelefonos(int id){
		ArrayList<Telefonos> lista = new ArrayList<Telefonos>();

		try {
			ResultSet rs = consultar("select * from telefonos_proveedor where id_proveedor = "+id);
			while(rs.next()) {
				lista.add(new Telefonos(rs.getInt("id_telefono"), rs.getInt("id_proveedor")
						, rs.getInt("numero")));

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return lista;
	}

	public void insertarTelefonos(int id, ArrayList<Telefonos> lista) {
		for (int i = 0; i < lista.size(); i++) {
			try {
				Thread.sleep(100);
				ejecutar("insert into telefonos_proveedor (numero,id_proveedor) values("
						+"'"+lista.get(i).getNumero()+"',"
						+"'"+id+"'"
						+");");
			} catch (Exception e) {	}

		}
	}

	public void eliminarTelefonos(int id) {
		ejecutar("delete from telefonos_proveedor where id_proveedor = "+id);
	}

	public int getLastProveedor(int caso) {
		int i  = 0;
		try {

			switch (caso) {
			case 0:
				ResultSet rs = consultar("select max(id_proveedor) as idProveedor from"
						+ " proveedores;");
				while(rs.next()) {
					i = rs.getInt("idProveedor");
				}
				break;

			default:
				ResultSet rs2 = consultar("select * from"
						+ " proveedores where id_proveedor = "+caso+";");
				while(rs2.next()) {
					i = rs2.getInt("idProveedor");
					break;
				}
			}
		} catch (Exception e) {
		}	
		System.out.println("I: "+i);
		return i;
	}


}
