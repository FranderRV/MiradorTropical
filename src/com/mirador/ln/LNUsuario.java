package com.mirador.ln;

import java.util.ArrayList;

import com.mirador.db.DBUsuario;
import com.mirador.vo.Usuario;

public class LNUsuario {
	private DBUsuario db; 

	public LNUsuario() {
		this.db = new  DBUsuario();
	}

	public ArrayList<Usuario> getUsuarios(String buscar){
		return db.getUsuarios(buscar);
	}
	
	public Usuario getUsuario(int id) {
		return db.getUsuario(id);
	}
	
	public Usuario getNickName(String nickname) {
		if(db.getNickname(nickname)!=null) {
			return db.getNickname(nickname);
		}else {
			return null;
		}
	}
	public boolean insertar(Usuario u) {
		if(getUsuario(u.getIdUsuario())==null) {
			if(!u.getNombre().equalsIgnoreCase("")&&!u.getNickname().equalsIgnoreCase("")) {
				db.insertar(u);
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}
	
	public void cambiarContrasena(String nickname, String contrasena) {
		db.actualizarContrasena(nickname, contrasena);
	}
	
	public void editar(Usuario u) {
		db.actualizar(u);
	}

	public boolean eliminar(int id) {
		if(getUsuario(id)!=null) {
			db.eliminar(id);
			return true;
		}else {
			return false;
		}
	}

	public String[] columnas() {
		return new String[] {"Id","Nombre","Primer apellido","Segundo apellido","Nickname","Correo","Contraseña"};
	}
	public Object[][] getFilas(String buscar){
		ArrayList<Usuario> filas= getUsuarios(buscar);
		Object matriz[][] = new Object[filas.size()][columnas().length];

		int i= 0;

		for (Usuario  u : filas) {
		//	if(i!=0) {
			matriz[i][0] = u.getIdUsuario();
			matriz[i][1] = u.getNombre();
			matriz[i][2] = u.getApellidoUno();
			matriz[i][3] = u.getApellidoDos();
			matriz[i][4] = u.getNickname();
			matriz[i][5] = u.getCorreo();
			matriz[i][6] = u.getContrasena();
			//}
			i++;
		}


		return matriz;
	}

}
