package com.mirador.db;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.mirador.vo.Compra;
import com.mirador.vo.Factura;
import com.mirador.vo.ListaCompras;

public class DBCompra extends Conexion{

	public DBCompra() {}
	
	public int getTotalListasCompras(int idProducto) {
		int total = 0;
		try {
			ResultSet rs = consultar("select h.cantidad from historial_compra h inner join productos p where h.id_producto ="
					+ " p.id_producto and h.id_producto = "+idProducto);
			while(rs.next()) {
				total += rs.getInt("h.cantidad");
			}
		} catch (Exception e) {

		}
		return total;
	}
	public ArrayList<Compra> getCompras(){
		ArrayList<Compra> lista = new ArrayList<Compra>();

		try {
			ResultSet rs = consultar("select * from compras_proveedor");
			while(rs.next()) {
				lista.add(new Compra(rs.getInt("id_compra"),
						rs.getString("fecha"), rs.getInt("id_proveedor")));
			}
		} catch (Exception e) {

		}		
		return lista;
	}
	public Compra getCompra(int id){
		Compra c = null;

		try {
			ResultSet rs = consultar("select * from compras_proveedor where id_compra = "+id);
			
			while(rs.next()) {
				c =new Compra(rs.getInt("id_compra"),
						rs.getString("fecha"), rs.getInt("id_proveedor"));
			}
		} catch (Exception e) {

		}		
		System.out.println("GETCOMROA: "+c.toString());
		return c;
	}
	public void insertarCompra(Compra c) {
		ejecutar("insert into compras_proveedor (fecha,id_proveedor) values("+
				"'"+c.getFecha()+"', '"+c.getIdProveedor()+"' "	
				+")");
		insertarDetalles(getLastCompra(), c.getDetalles());
	}

	public void editarCompra(Compra c) {
		ejecutar("update  compras_proveedor set"
				+" fecha = '"+c.getFecha()+"' , "
				+" id_proveedor = '"+c.getIdProveedor()+"' "
				+"where id_compra = "+c.getIdCompra()
				);
		
		eliminarDetalles(c.getIdCompra());
		insertarDetalles(c.getIdCompra(), c.getDetalles());
	}

	public void eliminarCompra(int id) {
		//recorrerDetallesEliminar(id);
		eliminarDetalles(id);
		ejecutar("delete from compras_proveedor where id_compra = "+id);
	}

	//DETALLES

	public void eliminarDetalles(int idCompra) {
		ejecutar("delete  from historial_compra where id_compra = "+idCompra);
	}
	
	public void recorrerDetallesEliminar(int idCompra){
		try {
			ResultSet rs =  consultar("select *, p.nombre as descripcion "
					+ "from historial_compra h inner join productos p where p.id_producto = h.id_producto "
					+ "and h.id_compra = "+idCompra);
			while(rs.next()) {
				//new DBProducto().restaCantidadProducto(rs.getInt("h.id_producto"),rs.getInt("h.cantidad"));
				System.out.println("ID COMPRA"+idCompra+"ID PRODUCTO: "+rs.getInt("h.id_producto")+" |  CANTIDAD"+rs.getInt("h.cantidad"));
			}
		} catch (Exception e) {
		}
	}
	
	public void insertarDetalles(int idCompra, ArrayList<ListaCompras> l) {
		for (int i = 0; i < l.size(); i++) {
			ejecutar("insert into historial_compra(id_compra,id_producto,precio, cantidad) values ("
					+"'"+idCompra+"' ,"
					+"'"+l.get(i).getIdProducto()+"' ,"
					+"'"+l.get(i).getPrecio()+"' ,"
					+"'"+l.get(i).getCantidad()+"' "
					+")"
					);
			new DBProducto().actualizarProductoEspecifico( l.get(i).getPrecio(),l.get(i).getIdProducto());
		}
	}

	public ArrayList<ListaCompras> getDetalles(int idCompra){
		ArrayList<ListaCompras> l = new ArrayList<ListaCompras>();

		try {
			ResultSet rs =  consultar("select *, p.nombre as descripcion "
					+ "from historial_compra h inner join productos p where p.id_producto = h.id_producto "
					+ "and h.id_compra = "+idCompra);
			while(rs.next()) {
				l.add(new ListaCompras(rs.getString("descripcion"),rs.getInt("id_compra")
						, rs.getInt("id_producto"), rs.getInt("cantidad"),
						rs.getInt("precio")));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return l;
	}

	public int getTotalCompra(int id) {
		int a = 0;
		try {
			ResultSet s = consultar("select sum(precio*cantidad) as total from historial_compra where id_compra = "+id);
			while(s.next()) {
				a = s.getInt("total");
			}
		} catch (Exception e) {
		}
		return a;
	}


	public int getLastCompra() {
		int i  = 0;
		try {
			ResultSet rs = consultar("select max(id_compra) as idCompra from compras_proveedor;");
			while(rs.next()) {
				i = rs.getInt("idCompra");
			}
		} catch (Exception e) {
		}	
		return i;
	}

	public String getNombreProveedor(int id) {
		String nombre = "";
		try {
			ResultSet rs = consultar("select * from proveedores  where id_proveedor = "+id);
			while(rs.next()) {
				nombre = rs.getString("nombre");
			}
		} catch (Exception e) {
		}		

		return nombre;
	}



	public ArrayList<Compra> getComprasPorFecha(String dateUno, String dateDos){
		ArrayList<Compra> lista = new ArrayList<Compra>();
		try {
			ResultSet rs = consultar("select * from compras_proveedor  where fecha between '"+dateUno+"' and  '"+dateDos+"';");
			while(rs.next()) {
				System.out.println("PASO DE CONSILTA: "+rs.getFetchSize());
				lista.add(new Compra(rs.getInt("id_compra"),
						rs.getString("fecha"), rs.getInt("id_proveedor")));
			}
		} catch (Exception e) {

		}		
		System.out.println("select * from compras_proveedor  where fecha between '"+dateUno+"' and  '"+dateDos+"';");
		return lista;
	}
}
