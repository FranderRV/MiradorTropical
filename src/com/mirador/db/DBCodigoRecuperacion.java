package com.mirador.db;

import java.sql.ResultSet;

public class DBCodigoRecuperacion extends Conexion{

	
	public DBCodigoRecuperacion() {
		
	}
	
	public String[] getCodigo(String nickname) {
		String codigoDos[] = new String [2];
		try {
			
			ResultSet rs = consultar("select c.codigo as codigo, u.nickname as usuarios from codigo_recuperacion c inner join usuarios u  where u.nickname = '"
					+nickname+"' "+
					" order by c.id_usuario desc LIMIT 1;");	
		
			while(rs.next()) {
			codigoDos[0]= rs.getString("codigo");
			codigoDos[1]= rs.getString("usuarios");
			}
			System.out.println(codigoDos.toString());
		} catch (Exception e) {
			System.out.println("Atención: "+e.getMessage());
		}
		return codigoDos;
	}
	
	public void insertarCodigo(String codigo, int idUsuario) {
		
		ejecutar("insert into codigo_recuperacion (codigo, id_usuario) values ( '"+codigo+"', "+idUsuario+" );");
	}
	
	public void eliminarCodigo(int idUsuario) {
		ejecutar("delete from codigo_recuperacion where id_usuario = "+idUsuario);
	}
	
	
}
