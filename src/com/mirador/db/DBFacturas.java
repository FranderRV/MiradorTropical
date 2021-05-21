package com.mirador.db;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.mirador.vo.Detalles;
import com.mirador.vo.Factura;
import com.mirador.vo.Proveedor;

public class DBFacturas extends Conexion{

	public DBFacturas() {}

	
	public int getTotalListasFacturas(int idProducto) {
		int total = 0;
		try {
			ResultSet rs = consultar("select h.cantidad from detalles h inner join productos p where h.id_producto = p.id_producto "
					+ "and h.id_producto ="+idProducto);
			while(rs.next()) {
				total += rs.getInt("h.cantidad");
			}
		} catch (Exception e) {

		}
		return total;
	}
	
	
	public int[] getDatosFactura(int caso,String fechaUno, String fechaDos) {
		int[] datos = new int[2];
		SimpleDateFormat formateador = new SimpleDateFormat("yyyy/MM/dd");
		java.util.Date date =new Date();
		String fechaActual = formateador.format(date);
		String consulta = "";
		switch (caso) {
	
		
		case 0:
			consulta = "select sum(d.cantidad) as total, sum(d.precio*d.cantidad) as ingreso from detalles d inner join productos p inner join facturas f"
					+ " where f.fecha = '"+fechaActual+"'  and  d.id_producto = p.id_producto \r\n" + 
					"and p.categoria = 2   and d.id_factura = f.id_factura";
			break;
		case 1:
			consulta = "select sum(d.cantidad) as total, sum(d.precio*d.cantidad) as ingreso from detalles d inner join productos p inner join facturas f"
					+ " where f.fecha = '"+fechaUno+"'  and  d.id_producto = p.id_producto \r\n" + 
					"and p.categoria = 2   and d.id_factura = f.id_factura";
			break;
		case 2:
			consulta = "select sum(d.cantidad) as total, sum(d.precio*d.cantidad) as ingreso from detalles d inner join productos p inner join facturas f where f.fecha >= '"+fechaUno+"' and f.fecha "
					+ "<= '"+fechaDos+"'" + 
					"and  d.id_producto = p.id_producto and p.categoria = 2  " + 
					"and d.id_factura = f.id_factura;";
			break;
		default:
			consulta = "select sum(d.cantidad) as total, sum(d.precio*d.cantidad) as ingreso from detalles d inner join productos p inner join facturas f"
					+ " where f.fecha = '"+fechaActual+"'  and  d.id_producto = p.id_producto \r\n" + 
					"and p.categoria = 2   and d.id_factura = f.id_factura";
			break;
		}
		try {
			System.out.println("CASO: "+caso+" | "+consulta);
			ResultSet rs = consultar(consulta);
			while(rs.next()) {
				datos[0] = rs.getInt("total");
				datos[1] = rs.getInt("ingreso");

			}
		} catch (Exception e) {
		}
		System.out.println(datos[0]+" | "+datos[1]);
		return datos;
	}
	public ArrayList<Factura> getFacturas(){
		ArrayList<Factura> lista = new ArrayList<Factura>();
		try {
			ResultSet rs = consultar("select * from facturas");
			while (rs.next()) {
				lista.add(new Factura(rs.getInt("id_factura"),rs.getString("nombre"), 
						rs.getString("fecha"), rs.getString("correo")));		
			}
		} catch (Exception e) {
		}	
		return lista;
	}

	public Factura getFactura(int id){
		Factura f= null;

		try {
			ResultSet rs = consultar("select * from facturas where id_factura = "+id);
			System.out.println("paso"+rs.getFetchSize());
			while (rs.next()) {
				f= new Factura(rs.getInt("id_factura"),rs.getString("nombre"), 
						rs.getString("fecha"), rs.getString("correo"));	

				System.out.println("pro: "+f.toString());
			}
		} catch (Exception e) {
		}
		return f;
	}

	public void insertarFactura(Factura f) {
		ejecutar("insert into facturas (nombre, fecha, correo) values ("+
				"'"+f.getCliente()+"', "+
				"'"+f.getFecha()+"', "+
				"'"+f.getCorreo()+"' "
				+")");

		insertarDetalles(getLastFactura(), f.getListaDetalles());
	}
	public void actualizarFactura(Factura f ) {
		ejecutar("update facturas set " 
				+ "fecha =  '"+f.getFecha()+"' "
				+ "where id_factura = "+f.getIdFactura());
		eliminarDetalles(f.getIdFactura());
		insertarDetalles(f.getIdFactura(), f.getListaDetalles());
	}
	public void eliminarFactura(int id) {
		recorreDetallesSumar(id);
		eliminarDetalles(id);
		ejecutar("delete from facturas where id_factura = "+id);
	}

	//DETALLES

	public void eliminarDetalles(int idFactura) {
		ejecutar("delete  from detalles where id_factura = "+idFactura);
	}


	public void recorreDetallesSumar(int idFactura){
		try {
			ResultSet rs = consultar("select d.precio as precio, \r\n" + 
					"d.cantidad as cantidad, d.id_factura as \r\n" + 
					"id_factura, d.id_producto as id_producto,p.nombre as descripcion \r\n" + 
					"from detalles d inner join productos p \r\n" + 
					"where p.id_producto = d.id_producto  \r\n" + 
					"and  d.id_factura = "+idFactura);

			while(rs.next()) {
				/*new DBProducto().sumaCantidadProducto(rs.getInt("id_producto"),
						rs.getInt("cantidad"));*/
			}
		} catch (Exception e) {
		}
	}




	public void insertarDetalles(int idFactura, ArrayList<Detalles>lista) {
		for (int i = 0; i < lista.size(); i++) {
			ejecutar("insert into detalles( precio,cantidad,id_producto,id_factura) values("
					+ "'"+lista.get(i).getPrecio()+"', "
					+ " "+lista.get(i).getCantidad()+", "
					+ ""+lista.get(i).getIdProducto()+" , "
					+ " "+idFactura+" "		
					+ ")");
			
		}
	}

	public ArrayList<Detalles> getDetalles(int idFactura){
		ArrayList<Detalles> detalles = new ArrayList<Detalles>();

		try {
			ResultSet rs = consultar("select d.precio as precio, \r\n" + 
					"d.cantidad as cantidad, d.id_factura as \r\n" + 
					"id_factura, d.id_producto as id_producto,p.nombre as descripcion \r\n" + 
					"from detalles d inner join productos p \r\n" + 
					"where p.id_producto = d.id_producto  \r\n" + 
					"and  d.id_factura = "+idFactura);

			while(rs.next()) {
				detalles.add(new Detalles(rs.getInt("id_producto"), idFactura, 
						rs.getInt("cantidad"), rs.getInt("precio"),rs.getString("descripcion")));
			}
		} catch (Exception e) {
		}
		return detalles;
	}

	public int getLastFactura() {
		int i  = 0;
		try {
			ResultSet rs = consultar("select max(id_factura) as idFactura from facturas;");
			while(rs.next()) {
				i = rs.getInt("idFactura");
			}
		} catch (Exception e) {
		}	
		return i;
	}

	public int getTotalFactura(int id) {
		int a = 0;
		try {
			ResultSet s = consultar("select sum(precio*cantidad) as total from detalles where id_factura ="+id);
			while(s.next()) {
				a = s.getInt("total");
			}
		} catch (Exception e) {
		}
		return a;
	}

	public ArrayList<Factura> getFacturasPorFecha(String dateUno, String dateDos){
		ArrayList<Factura> lista = new ArrayList<Factura>();
		try {
			ResultSet rs = consultar("select * from facturas where fecha between '"+dateUno+"' and '"+dateDos+"';");
			while (rs.next()) {
				lista.add(new Factura(rs.getInt("id_factura"),rs.getString("nombre"), 
						rs.getString("fecha"), rs.getString("correo")));		
			}
		} catch (Exception e) {
		}	
		return lista;
	}


}
