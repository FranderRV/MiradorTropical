package com.mirador.ln;

import java.util.ArrayList;

import com.mirador.db.DBProducto;
import com.mirador.vo.Detalles;
import com.mirador.vo.ListaAjustesInventario;
import com.mirador.vo.Producto;

public class LNProducto {
	private DBProducto db;

	public LNProducto() {
		this.db = new DBProducto();
	}
	public void actualizarValores() {
		db.actualizarValores();
	}
	public void actualizarValoresAjusteInventario() {
		db.actualizarValoresAjusteInventario();
	}
	public ArrayList<Producto> getProductos(String buscar){
		return db.getProductos(buscar);
	}
	public Producto getProducto(int id) {
		return db.getProducto(id);
	}
	public void insertarProducto(Producto p) {
		db.insertar(p);
	}
	public void eliminar(int id) {
		db.eliminar(id);
	}
	public void actualizar(Producto p) {
		db.actualizar(p);
	}
	
	public ArrayList<Producto> getProductosComboAjustes( ArrayList<ListaAjustesInventario> listaDetalles){
		ArrayList<Producto> lista = getProductosAjustes();
		
		for (int i = 0; i < lista.size(); i++) {
			for (int j = 0; j < listaDetalles.size(); j++) {
				if(lista.get(i).getIdProducto()==listaDetalles.get(j).getIdProducto()) {
					lista.get(i).setCantidad(lista.get(i).getCantidad()-listaDetalles.get(j).getCantidad());
				}
			}
		}
		return lista;
	}
	
	public ArrayList<Producto> getProductosComboVentas( ArrayList<Detalles> listaDetalles){
		ArrayList<Producto> lista = getProductosVentas();
		
		for (int i = 0; i < lista.size(); i++) {
			for (int j = 0; j < listaDetalles.size(); j++) {
				
				if(lista.get(i).getIdProducto() == listaDetalles.get(j).getIdProducto()) {
					
					lista.get(i).setCantidad(lista.get(i).getCantidad()-listaDetalles.get(j).getCantidad());
				}
			}
		}
		return lista;
	}
	
	public ArrayList<Producto> getProductosComboVentasDos( ArrayList<Detalles> listaDetalles){
		ArrayList<Producto> lista = getProductosVentas();
		
		for (int i = 0; i < lista.size(); i++) {
			for (int j = 0; j < listaDetalles.size(); j++) {
				
				if(lista.get(i).getIdProducto() == listaDetalles.get(j).getIdProducto()) {
					
					lista.get(i).setCantidad(lista.get(i).getCantidad()-listaDetalles.get(j).getCantidad());
				}
			}
		}
		return lista;
	}
	
	public ArrayList<Producto> getProductosVentas(){
	return db.getProductosVentas();
	}
	
	public ArrayList<Producto> getProductosComprasProveedor(){
		return db.getProductosComprasProveedor();
		}
	public ArrayList<Producto> getProductosAjustes(){
		return db.getProductosAjustes();
	}
	public int getPosicionListaAjustes(String  nombre, ArrayList<Producto> ajustes) {
		int pos = 0;
		for (int i = 0; i <ajustes.size(); i++) {

			if(ajustes.get(i).getNombre().equalsIgnoreCase(nombre) ) {
				pos  = i; 
				break;
			}
		}
		return pos;
	}
	public int getPosicionListaVentas(String  nombre, ArrayList<Producto> lista) {
		int pos = 0;
		for (int i = 0; i < lista.size(); i++) {
			if(lista.get(i).getNombre().equalsIgnoreCase(nombre) ) {
				pos  = i;

				break;
			}
		}
		return pos;
	}
	
	public int getPosicionListaCompras(String  nombre) {
		int pos = 0;
		for (int i = 0; i < getProductosComprasProveedor().size(); i++) {
			System.out.println("Nombre: "+i);
			if(getProductosComprasProveedor().get(i).getNombre().equalsIgnoreCase(nombre) ) {
				pos  = i;
				break;
			}
		}
		return pos;
	}
	
	public void actualizarProductoEspecifico(int precio, int id) {
		 db.actualizarProductoEspecifico(precio, id);
	}
	public void actualizarProductoCantidad(int cantidad, int id) {
		db.actualizarProductoCantidad(cantidad, id);
	}
	
	public String[] getColumnas() {
		return new String[] {"Id","Nombre","Código","Cantidad","Categoria","idCategoria",
				"Precio","Ganancia"};
	}
	
	public Object[][] getDatos(String buscar){
		ArrayList<Producto > lista = getProductos(buscar);
		Object[][] matriz = new Object[lista.size()][getColumnas().length];
		int i = 0;	
		for (Producto p: lista) {
			matriz[i][0] = p.getIdProducto();
			matriz[i][1] = p.getNombre();
			matriz[i][2] = p.getCodigo();
			matriz[i][3] = p.getCantidad();
			matriz[i][4] = p.getCategoria();
			matriz[i][5] = p.getCategoria().getIdCategoriaComida();
			matriz[i][6] = "\u20A1" +p.getPrecio();
			matriz[i][7] = p.getGanancia();
			
			i++;
		}
		return matriz;
	}
	public String convertirRuta(String ruta) {
		char a = '*';
		String vec[] = ruta.split("'\'");
		String salida = "";
		for (int i = 0; i < vec.length; i++) {
			if(i!=vec.length-1) {
				salida+=vec[i]+"|";
			}else {
				salida+=vec[i];
			}
		}
		return salida;
	}
}
