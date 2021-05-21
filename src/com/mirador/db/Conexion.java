package com.mirador.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class Conexion {

	private String host, user, pass, db, url;
	private Connection conexion;

	public Conexion() {
		host = "localhost";
		user = "root";
		pass = "";
		db = "db_mirador";
		url = "jdbc:mysql://"+host+"/"+db;
	}


	//CONECTAR
	public void conectar() {
		try {

			Class.forName("com.mysql.jdbc.Connection");
			conexion = DriverManager.getConnection(url, user, pass);

			if(conexion!=null) {
				//System.out.println("Conexión exitosa");
			}else {
				System.err.println("Conexión fallida");
			}

		} catch (Exception e) {

		}
	}

	/*
	 * OBTENER DATOS 
	 * */

	public ResultSet consultar(String consulta) {
		ResultSet rs = null;
		try {	

			conectar(); //conecto
			Statement estado = (Statement) conexion.createStatement();
			rs = estado.executeQuery(consulta);

			
		} catch (SQLException e) {
			e.printStackTrace();
		}	catch (Exception e) {
			e.printStackTrace();
		}	

		return rs;
	}
	
	

	/*
	 *  DAR ORDENES
	 * */

	public Connection getConexion() {
		return conexion;
	}


	public void setConexion(Connection conexion) {
		this.conexion = conexion;
	}


	public void  ejecutar(String sql) {
		try {
			conectar();
			Statement estado = (Statement) conexion.createStatement();
			estado.execute(sql);

		} catch (SQLException e) {
			System.out.println("Alerta: "+e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
