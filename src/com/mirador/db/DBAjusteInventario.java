package com.mirador.db;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.mirador.vo.AjusteInventario;
import com.mirador.vo.Factura;
import com.mirador.vo.ListaAjustesInventario;
import com.mysql.fabric.xmlrpc.base.Array;

public class DBAjusteInventario extends Conexion {
	
	public int getTotalListaAjusteInventarios(int idProducto) {
		int total = 0;
		try {
			ResultSet rs = consultar("select h.cantidad from lista_ajustes h inner join productos p where h.id_producto = p.id_producto "
					+ "and h.id_producto ="+idProducto);
			while(rs.next()) {
				total += rs.getInt("h.cantidad");
			}
		} catch (Exception e) {

		}
		return total;
	}
	public DBAjusteInventario() {}
	
	public ArrayList<AjusteInventario> getAjustesInventario(){
		ArrayList<AjusteInventario>  lista = new ArrayList<AjusteInventario>();
		
		try {
			ResultSet rs = consultar("Select * from ajuste_inventario");
			while(rs.next()) {
				lista.add(new AjusteInventario(rs.getInt("id_ajuste"),
						rs.getString("fecha")));
			}
		} catch (Exception e) {
		}
		return lista;
	}
	
	public ArrayList<AjusteInventario> getAjustesPorFecha(String dateUno, String dateDos){
		ArrayList<AjusteInventario> lista = new ArrayList<AjusteInventario>();
		try {
			ResultSet rs = consultar("	select * from ajuste_inventario where fecha between '"+dateUno+"' and '"+dateDos+"';");
			while (rs.next()) {
				lista.add(new AjusteInventario(rs.getInt("id_ajuste"),
						rs.getString("fecha")));
			}
		} catch (Exception e) {
		}	
		return lista;
	}
	
	public AjusteInventario getAjusteInventario(int id){
		AjusteInventario  ajuste = null;
		
		try {
			ResultSet rs = consultar("Select * from ajuste_inventario where id_ajuste = "+id);
			while(rs.next()) {
				ajuste =new AjusteInventario(rs.getInt("id_ajuste"),
						rs.getString("fecha"));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return ajuste;
	}
	
	public void insertarAjuste(AjusteInventario a) {
		ejecutar("insert into ajuste_inventario(fecha) values('"+a.getFecha()+"')");
		insertarDetalles(getLastAjuste(), a.getDetalles());
	}
	
	public void actualizarAjuste(AjusteInventario a) {
		ejecutar("update ajuste_inventario set "+
				"fecha = '"+a.getFecha()+"' "
				+ " where id_ajuste = "+a.getIdAjuste()
				);
		eliminarDetalles(a.getIdAjuste());
		insertarDetalles(a.getIdAjuste(), a.getDetalles());
	}
	
	public void eliminarAjuste(int idAjuste) {
		
		eliminarDetalles(idAjuste);
		ejecutar("delete  from ajuste_inventario where id_ajuste = "+idAjuste);
	}
	//DETALLES


	
	public ArrayList<ListaAjustesInventario> getDetalles(int idAjuste){
		ArrayList<ListaAjustesInventario> lista = new ArrayList<ListaAjustesInventario>();
		
		try {
			ResultSet rs = consultar("select *, p.nombre as descripcion from  lista_ajustes ai inner join productos p where ai.id_producto = "
					+ "p.id_producto and ai.id_ajuste = "+idAjuste);
			
			while(rs.next()) {
				lista.add(new ListaAjustesInventario(idAjuste, rs.getInt("id_producto"),
						rs.getInt("cantidad"), rs.getString("descripcion"),rs.getInt("precio")));
			}
			
		} catch (Exception e) {
		
		}
		
		return lista;
	}
	public void eliminarDetalles(int idAjuste) {
		//recorreDetallesSumar(idAjuste);
		ejecutar("delete  from lista_ajustes where id_ajuste = "+idAjuste);
	}
	
	public void recorreDetallesSumar(int idAjuste){
		try {
			ResultSet rs = consultar("Select * from lista_ajustes where id_ajuste  = "+idAjuste);

			while(rs.next()) {
			/*	new DBProducto().sumaCantidadProducto(rs.getInt("id_producto"),
						rs.getInt("cantidad"));*/
			}
		} catch (Exception e) {
		}
	}
	
	public void insertarDetalles(int idAjuste,ArrayList<ListaAjustesInventario> lista) {
		for (ListaAjustesInventario l : lista) {
			ejecutar("insert into lista_ajustes(id_ajuste, id_producto, precio,cantidad) values("+
					"'"+idAjuste+"' , "+
					"'"+l.getIdProducto()+"' , "+
					"'"+l.getPrecio()+"' , "+
					"'"+l.getCantidad()+"'  "
					+")");
			new DBProducto().actualizarProductoEspecifico(l.getPrecio(), l.getIdProducto());
		}
	}
	
	public int getLastAjuste() {
		int i  = 0;
		try {
			ResultSet rs = consultar("select max(id_ajuste) as idAjuste from ajuste_inventario;");
			while(rs.next()) {
				i = rs.getInt("idAjuste");
			}
		} catch (Exception e) {
		}	
		return i;
	}
	
	
	
}
