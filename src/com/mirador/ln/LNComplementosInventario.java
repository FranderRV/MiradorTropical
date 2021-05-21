package com.mirador.ln;

import java.util.ArrayList;

import com.mirador.db.DBComplementosInventario;
import com.mirador.vo.CategoriaComida;
import com.mirador.vo.Unidad;

public class LNComplementosInventario {
	private DBComplementosInventario db;
	
	public LNComplementosInventario() {
		this.db = new DBComplementosInventario();
	}
	
	/*		CATEGORIAS	*/
	public ArrayList<CategoriaComida> getCategorias(){
		return db.getCategorias();
	}
	public CategoriaComida getCategoria(int id) {
		return db.getCategoria(id);
	}
	public void insertarCategoria(CategoriaComida c) {
		db.insertarCategoria(c);
	}
	public void eliminarCategoria(int id) {
		db.eliminarCategoria(id);
	}
	public void actualizarCategoria(CategoriaComida c) {
		db.actualizarCategoria(c);
	}
	public int getIndexCategoria(int id) {
		int n = 0;
		for (int i = 0; i < getCategorias().size(); i++) {
			if(id==getCategorias().get(i).getIdCategoriaComida()) {
				n = i;
				break;
			}
		}
		return n; 
	}
	
}
