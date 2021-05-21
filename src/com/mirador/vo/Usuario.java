package com.mirador.vo;

import java.util.Arrays;

public class Usuario {
	private int idUsuario;
	private String nombre;
	private String apellidoUno;
	private String apellidoDos;
	private String nickname;
	private String contrasena;
	private String correo;
	
	
	public Usuario(int idUsuario, String nombre, String apellidoUno, String apellidoDos, String nickname,
			String contrasena, String correo) {
		super();
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.apellidoUno = apellidoUno;
		this.apellidoDos = apellidoDos;
		this.nickname = nickname;
		this.contrasena = contrasena;
		this.correo = correo;
	}

	
	
	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidoUno() {
		return apellidoUno;
	}

	public void setApellidoUno(String apellidoUno) {
		this.apellidoUno = apellidoUno;
	}

	public String getApellidoDos() {
		return apellidoDos;
	}

	public void setApellidoDos(String apellidoDos) {
		this.apellidoDos = apellidoDos;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	
}
