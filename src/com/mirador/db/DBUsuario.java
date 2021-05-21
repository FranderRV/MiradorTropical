package com.mirador.db;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.mirador.vo.Usuario;

public class DBUsuario extends Conexion{

	public DBUsuario() {

	}
	//AÑADIR 1
	public ArrayList<Usuario> getUsuarios(String buscar){
		ArrayList<Usuario> lista = new ArrayList<>();

		try {
			ResultSet rs = consultar("select * from usuarios u   where nombre like '%"+buscar+"%' ");

			while(rs.next()) {
				lista.add(new Usuario(rs.getInt("id_usuario"), rs.getString("nombre"), rs.getString("apellido1"), rs.getString("apellido2")
						, rs.getString("nickname"), rs.getString("contrasena"),rs.getString("correo")));
			
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return lista;
	}
	//AÑADIR 2
	public Usuario getUsuario(int id) {
		Usuario u = null;

		try {
			ResultSet rs = consultar("select *from usuarios u  where u.id_usuario = "+id);
			while(rs.next()) {

				u = new Usuario(rs.getInt("id_usuario"), rs.getString("nombre"), rs.getString("apellido1"), rs.getString("apellido2")
						, rs.getString("nickname"), rs.getString("contrasena"),rs.getString("correo"));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return u;
	}

	//NICKNAME
	public Usuario getNickname(String nickname) {
		Usuario u = null;
		try {
			ResultSet rs = consultar("select * from usuarios u    where u.nickname = '"+nickname+"'");
			while(rs.next()) {
				u = new Usuario(rs.getInt("id_usuario"), rs.getString("nombre"), rs.getString("apellido1"), rs.getString("apellido2")
						, rs.getString("nickname"), rs.getString("contrasena"),rs.getString("correo"));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return u;
	}
	//	INSERTAR
	public void insertar(Usuario u) {
		ejecutar("insert into usuarios(nombre, apellido1, apellido2, nickname, contrasena, correo) values("
				+ "'"+u.getNombre()+"', "
				+ " '"+u.getApellidoUno()+"', "
				+ "'"+u.getApellidoDos()+"', "
				+ "'"+u.getNickname()+"', "
				+ "'"+u.getContrasena()+"', "
				+ "'"+u.getCorreo()+"'"
				+ ");");
	}
	//EDITAR
	public void actualizar(Usuario u) {
		System.out.println(u.toString());
		ejecutar("update usuarios set "
				+ "nombre = '"+u.getNombre()+"' ,"
				+ "apellido1 = '"+u.getApellidoUno()+"' ,"
				+ "apellido2 = '"+u.getApellidoDos()+"' ,"
				+ "nickname = '"+u.getNickname()+"' ,"
				+ "contrasena = '"+u.getContrasena()+"' ,"
				+ "correo = '"+u.getCorreo()+"' "		
				+ "where id_usuario = "+u.getIdUsuario()+" ;");
	}
	
	public void actualizarContrasena(String nickname, String contrasena) {
		ejecutar("update usuarios set "
				+ "contrasena = '"+contrasena+"' "
				+ "where nickname = '"+nickname+"';");
	}
	//ELIMINAR

	public void eliminar(int id) {
		ejecutar("delete from usuarios where id_usuario = "+id);
	}
}
