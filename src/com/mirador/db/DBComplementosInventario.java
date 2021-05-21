package com.mirador.db;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.mirador.vo.CategoriaComida;
import com.mirador.vo.Unidad;

public class DBComplementosInventario extends Conexion {
	public DBComplementosInventario() {}

	public ArrayList<CategoriaComida> getCategorias(){
		ArrayList<CategoriaComida> lista = new ArrayList<CategoriaComida>();
		try {
			ResultSet rs = consultar("select * from categorias order by(id_categoria);");
			while(rs.next()) {
				lista.add(new CategoriaComida(rs.getInt("id_categoria"), rs.getString("nombre")));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return  lista;
	}

	public CategoriaComida getCategoria(int id) {
		CategoriaComida cc = null;

		try {
			ResultSet rs =  consultar("select * from categorias where id_categoria = "+id);
			try {
				cc = new CategoriaComida(rs.getInt("id_categoria"), rs.getString("nombre"));
			} catch (Exception e) {
			}
		} catch (Exception e) {
		}
		return cc;
	}
	
	public void insertarCategoria(CategoriaComida c) {
		ejecutar("insert into categorias (nombre) values('"+c.getNombre()+"')");
	}
	public void eliminarCategoria(int id) {
		ejecutar("delete from categorias where id_categoria = "+id);
	}
	public void actualizarCategoria(CategoriaComida c) {
		ejecutar("update categorias set nombre = '"+c.getNombre()+"' where id_categoria = "+c.getIdCategoriaComida());
	}
	
}
