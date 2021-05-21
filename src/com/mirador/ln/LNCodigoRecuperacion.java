package com.mirador.ln;

import java.util.Date;

import com.mirador.db.DBCodigoRecuperacion;
import com.mirador.vo.Usuario;

public class LNCodigoRecuperacion {
	
	private DBCodigoRecuperacion db;
	private LNUsuario lnUsuario;
	private Correo correo;
	public LNCodigoRecuperacion() {
		correo = new Correo("miradorphr@gmail.com", "miraDOOR2019");
		this.lnUsuario = new LNUsuario();
		this.db = new DBCodigoRecuperacion();
	}
	
	public String[] getCodigo(String nickname) {
			return db.getCodigo(nickname);
	}
	
	public boolean insertar(String nickname) {
		
		Usuario usuario = lnUsuario.getNickName(nickname);
		String codigo = codigoAleatorio();
  
		if(usuario!=null) {
		correo.agregarDestinatario(lnUsuario.getNickName(nickname).getCorreo());
		db.insertarCodigo(codigo, usuario.getIdUsuario());
		String fecha = new Date().toString().substring(0,10);
		correo.crearMensaje("Fecha de recuperación: "+fecha+" | Usuario:  "+nickname+" | Código de recuperación", codigo);
		correo.enviarCorreo();
		return true;
		
		}else {
			return false;
		}
	}
	
	public void eliminar(String nickname) {
		db.eliminarCodigo(lnUsuario.getNickName(nickname).getIdUsuario());
	}
	
	public String codigoAleatorio() {
		String salida = "";
		for (int i = 0; i < 8; i++) {
			salida += (int)(Math.random()*9);
		}
		return salida;
	}
	
	public int verificarCodigo(String nickname,String codigo) {
		int salida = 0;
		String codigoVector[]= getCodigo(nickname); 
		
		System.out.println(getCodigo(nickname).toString());
		if(nickname.equalsIgnoreCase(codigoVector[1])) {
			if(codigo.equalsIgnoreCase(codigoVector[0])) {
				salida = 1;
			}else {
				salida = 0;
			}
		}else {
			salida = 2;
		}
		return salida;
	}
	
}
