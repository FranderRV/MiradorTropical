package com.mirador.db;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.mirador.vo.CategoriaComida;
import com.mirador.vo.Producto;
import com.mirador.vo.Unidad;
import com.mysql.jdbc.PreparedStatement;

public class DBProducto  extends Conexion{
	private String alerta; 
	public DBProducto() {
		this.alerta = "Notificación";
	} 
	
  
	public void actualizarValores() {
		for (Producto p  : getProductosMixtos()) {
			int a = new DBCompra().getTotalListasCompras(p.getIdProducto());
			int b = new DBFacturas().getTotalListasFacturas(p.getIdProducto());
			
			int resultado = a-b;
			System.out.println(p.toString()+" ID PRO: "+p.getIdProducto()+"\nA: "+a+"\nB: "+b+"\nResultad: "+resultado);
			ejecutar("update productos set cantidad = "+resultado+" where id_producto = "+p.getIdProducto());
			
		}
	}
	
	public void actualizarValoresAjusteInventario() {
		for (Producto p  : getProductosAjustes()) {
			int a = new DBCompra().getTotalListasCompras(p.getIdProducto());
			int b = new DBAjusteInventario().getTotalListaAjusteInventarios(p.getIdProducto());
				
			int resultado = a-b;
			System.out.println(p.toString()+" ID PRO: "+p.getIdProducto()+"\nA: "+a+"\nB: "+b+"\nResultad: "+resultado);
			ejecutar("update productos set cantidad = "+resultado+" where id_producto = "+p.getIdProducto());
			
		}
	}
	public ArrayList<Producto> getProductosAjustes(){
		ArrayList<Producto> lista = new ArrayList<Producto>();

		try {
			ResultSet rs = consultar(
					"select p.id_producto as id_producto, p.nombre as nombre, p.cantidad as cantidad, p.categoria as categoria,p.precio as precio ,"
					+ " c.nombre as nombre_categoria, p.codigo_producto \r\n" + 
					"as codigo_producto, p.ganancia as ganancia from productos p  inner join categorias c  where   p.categoria"
					+ "  = c.id_categoria and p.categoria = 3 and c.id_categoria =  3;"//categorias
					);
			while(rs.next()) {
				lista.add(new Producto(rs.getInt("id_producto"), 
						rs.getString("nombre"),
						rs.getString("codigo_producto"),rs.getInt("cantidad"), 
						new CategoriaComida(rs.getInt("categoria"), rs.getString("nombre_categoria")),
						rs.getInt("precio"),rs.getInt("ganancia")));
				
			}
		} catch (Exception e) {
			alerta = e.getMessage();
		}
		return lista;	
	}
	
	public ArrayList<Producto> getProductosMixtos(){
		ArrayList<Producto> lista = new ArrayList<Producto>();

		try {
			ResultSet rs = consultar(
					"select p.id_producto as id_producto, p.nombre as nombre, p.cantidad as cantidad, p.categoria as categoria,p.precio as precio ,"
					+ " c.nombre as nombre_categoria, p.codigo_producto \r\n" + 
					"as codigo_producto, p.ganancia as ganancia from productos p  inner join categorias c  where   p.categoria"
					+ "  = c.id_categoria and p.categoria = 1 and c.id_categoria =  1;"//categorias
					);
			while(rs.next()) {
				lista.add(new Producto(rs.getInt("id_producto"), 
						rs.getString("nombre"),
						rs.getString("codigo_producto"),rs.getInt("cantidad"), 
						new CategoriaComida(rs.getInt("categoria"), rs.getString("nombre_categoria")),
						rs.getInt("precio"),rs.getInt("ganancia")));
				
			}
		} catch (Exception e) {
			alerta = e.getMessage();
		}
		return lista;	
	}
	
	
	
	public ArrayList<Producto> getProductosComprasProveedor(){
		ArrayList<Producto> lista = new ArrayList<Producto>();

		try {
			ResultSet rs = consultar(
					"select p.id_producto as id_producto, p.nombre as nombre, p.cantidad as cantidad, p.categoria as categoria,p.precio as precio ,"
					+ " c.nombre as nombre_categoria, p.codigo_producto \r\n" + 
					"as codigo_producto, p.ganancia as ganancia from productos p  inner join categorias c  where   p.categoria"
					+ "  = c.id_categoria and p.categoria = 3 and c.id_categoria =  3 or p.categoria = 1 and c.id_categoria =  1;"//categorias
					);
			while(rs.next()) {
				lista.add(new Producto(rs.getInt("id_producto"), 
						rs.getString("nombre"),
						rs.getString("codigo_producto"),rs.getInt("cantidad"), 
						new CategoriaComida(rs.getInt("categoria"), rs.getString("nombre_categoria")),
						rs.getInt("precio"),rs.getInt("ganancia")));
				
			}
		} catch (Exception e) {
			alerta = e.getMessage();
		}
		return lista;	
	}
	public ArrayList<Producto> getProductosVentas(){
		ArrayList<Producto> lista = new ArrayList<Producto>();

		try {
			ResultSet rs = consultar(
					"select p.id_producto as id_producto, p.nombre as nombre, p.cantidad as cantidad, p.categoria as categoria,p.precio as precio ,"
					+ " c.nombre as nombre_categoria, p.codigo_producto \r\n" + 
					"as codigo_producto, p.ganancia as ganancia from productos p  inner join categorias c  where   p.categoria"
					+ "  = c.id_categoria and p.categoria = 1 and c.id_categoria =  1 or p.categoria = 2 and c.id_categoria =  2 or "
					+ "p.categoria = 4 and c.id_categoria =  4"//categorias
					);
			while(rs.next()) {
				lista.add(new Producto(rs.getInt("id_producto"), 
						rs.getString("nombre"),
						rs.getString("codigo_producto"),rs.getInt("cantidad"), 
						new CategoriaComida(rs.getInt("categoria"), rs.getString("nombre_categoria")),
						rs.getInt("precio"),rs.getInt("ganancia")));
				
			}
		} catch (Exception e) {
			alerta = e.getMessage();
		}
		return lista;	
	}
	public ArrayList<Producto> getProductosCompra(){
		ArrayList<Producto> lista = new ArrayList<Producto>();

		try {
			ResultSet rs = consultar(
					"select p.id_producto as id_producto, p.nombre as nombre, p.cantidad as cantidad, p.categoria as categoria,p.precio as precio ,"
					+ " c.nombre as nombre_categoria, p.codigo_producto \r\n" + 
					"as codigo_producto, p.ganancia as ganancia from productos p  inner join categorias c  where   p.categoria"
					+ "  = c.id_categoria and p.categoria = 1 and c.id_categoria =  1;"//categorias
					);
			while(rs.next()) {
				lista.add(new Producto(rs.getInt("id_producto"), 
						rs.getString("nombre"),
						rs.getString("codigo_producto"),rs.getInt("cantidad"), 
						new CategoriaComida(rs.getInt("categoria"), rs.getString("nombre_categoria")),
						rs.getInt("precio"),rs.getInt("ganancia")));
				
			}
		} catch (Exception e) {
			alerta = e.getMessage();
		}
		return lista;	
	}

	
	public ArrayList<Producto> getProductos(String buscar){
		ArrayList<Producto> lista = new ArrayList<Producto>();

		try {
			ResultSet rs = consultar(
					"select p.id_producto as id_producto, p.nombre as nombre, p.cantidad as cantidad, p.categoria as categoria, " + 
							"p.precio as precio , c.nombre as nombre_categoria, p.codigo_producto as codigo_producto, p.ganancia as ganancia " + 
							" from productos p " + 
							" inner join categorias c where p.nombre like '%"+buscar+"%'  and p.categoria  = c.id_categoria;"
					);
			while(rs.next()) {
				lista.add(new Producto(rs.getInt("id_producto"), 
						rs.getString("nombre"),
						rs.getString("codigo_producto"),rs.getInt("cantidad"), 
						new CategoriaComida(rs.getInt("categoria"), rs.getString("nombre_categoria")),
						rs.getInt("precio"),rs.getInt("ganancia")));
				
			}
		} catch (Exception e) {
			alerta = e.getMessage();
		}
		return lista;	
	}

	public Producto getProducto(int id){
		Producto p = null;

		try {

			ResultSet rs = consultar(
					"select p.id_producto as id_producto, p.nombre as nombre, p.cantidad as cantidad,  p.categoria as categoria , " + 
							"p.precio as precio , c.nombre as nombre_categoria, p.codigo_producto as codigo_producto, "
							+ "p.ganancia as ganancia " + 
							" from productos p " + 
							" inner join categorias c where id_producto = "+id+" and p.categoria  = c.id_categoria;");
			while(rs.next()) {
				p = new Producto(rs.getInt("id_producto"), 
					rs.getString("nombre"),
						rs.getString("codigo_producto"),rs.getInt("cantidad"), 
						new CategoriaComida(rs.getInt("categoria"), rs.getString("nombre_categoria")),
						rs.getInt("precio"),rs.getInt("ganancia")); 

			}
		} catch (Exception e) {
			alerta = e.getMessage();
		}
		return p;	
	}

	public void insertar(Producto p) {
		String sql = "insert into productos (nombre, codigo_producto, "
				+ "cantidad,categoria, precio, ganancia) values ("
				+ "'"+p.getNombre()+ "', "
				+ "'"+p.getCodigo()+ "', "
				+ "'"+p.getCantidad()+ "', "
				+ "'"+p.getCategoria().getIdCategoriaComida()+ "' ,"
				+ "'"+p.getPrecio()+ "' ,"
				+ "'"+p.getGanancia()+ "' "
				+ ")";
		
		ejecutar(sql);
	}
	public void eliminar(int id) {
		ejecutar("delete from productos where id_producto = "+id);
	}
	public void actualizar(Producto p) {
		ejecutar("update productos set "
				+ "nombre = '"+p.getNombre()+ "' , "
				+ "codigo_producto = '"+p.getCodigo()+ "' , "
				+ "cantidad = '"+p.getCantidad()+ "' , "
				+ "precio = '"+p.getPrecio()+ "' , "
				+ "categoria = '"+p.getCategoria().getIdCategoriaComida()+ "' ,"
				+ "ganancia = '"+p.getGanancia()+ "' "
				+ " where id_producto = "+p.getIdProducto());
	}
	public void actualizarProductoEspecifico(int precio, int id) {
		ejecutar("update productos set "
				+ "precio = '"+precio+ "' "
			+ " where id_producto = "+id);
	}
	
	public void actualizarProductoCantidad(int cantidad, int id) {
		ejecutar("update productos set "
				+ "cantidad = '"+cantidad+ "' "
			+ " where id_producto = "+id);
	}
}
