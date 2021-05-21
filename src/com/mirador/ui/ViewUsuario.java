package com.mirador.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.mirador.ln.LNUsuario;
import com.mirador.vo.Usuario;

public class ViewUsuario extends View implements ActionListener, KeyListener{
	private LNUsuario lnUsuario;
	private JTextField tNombre, tApellido1, tApellido2, tNickname, tCorreo;
	private JPasswordField tContrasena;
	public ViewUsuario() {
		super("Usuarios");

		lnUsuario = new LNUsuario();

		tNombre = new JTextField();
		tApellido1 = new JTextField();
		tApellido2 = new JTextField();
		tNickname = new JTextField();
		tContrasena = new JPasswordField();
		tCorreo = new JTextField();

		cargarTabla(lnUsuario.getFilas(""), lnUsuario.columnas());
		panelEntradas.add(entradaVertical(tNombre,"Nombre"));
		panelEntradas.add(entradaVertical(tApellido1,"Primer apellido"));
		panelEntradas.add(entradaVertical(tApellido2,"Segundo apellido"));
		panelEntradas.add(entradaVertical(tNickname,"Nickname"));
		panelEntradas.add(entradaVertical(tContrasena,"Contraseña"));
		panelEntradas.add(entradaVertical(tCorreo,"Correo"));

		setBotones("registrar");
		detallesTabla();
		placeHolder();
		eventoTabla();
		iniciarBuscar(this);
		iniciarBotones(this);
	}

	public void eventoTabla() {
		tabla.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				setBotones("editar");
				int fila = tabla.getSelectedRow();
				tNombre.setText(tabla.getValueAt(fila, 1).toString());
				tApellido1.setText(tabla.getValueAt(fila, 2).toString());
				tApellido2.setText(tabla.getValueAt(fila, 3).toString());
				tNickname.setText(tabla.getValueAt(fila, 4).toString());
				tCorreo.setText(tabla.getValueAt(fila, 5).toString());
				tContrasena.setText(tabla.getValueAt(fila, 6).toString()); 
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	public void placeHolder() {
		tBuscar.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
					tBuscar.setText("Buscar...");
					cargarTabla("");
			}

			@Override
			public void focusGained(FocusEvent e) {
				tBuscar.setText("");
				cargarTabla("");
			}
		});
	}

	public void cargarTabla(String buscar) {		
		modelo.setDataVector(lnUsuario.getFilas(buscar),lnUsuario.columnas());
		detallesTabla();
	}



	public void detallesTabla() {

		tabla.setRowHeight(30);
		//OCULTAR COLUMNAS
		tabla.getColumnModel().getColumn(0).setMaxWidth(0);
		tabla.getColumnModel().getColumn(0).setMinWidth(0);
		tabla.getColumnModel().getColumn(0).setPreferredWidth(0);
		tabla.getColumnModel().getColumn(0).setResizable(false);	
		
		tabla.getColumnModel().getColumn(6).setMaxWidth(0);
		tabla.getColumnModel().getColumn(6).setMinWidth(0);
		tabla.getColumnModel().getColumn(6).setPreferredWidth(0);
		tabla.getColumnModel().getColumn(6).setResizable(false);	
		
		
		tabla.setFont(new Font("",0,15));
		tabla.getTableHeader().setPreferredSize(new java.awt.Dimension(500, 60));
		tabla.getTableHeader().setBackground(Color.DARK_GRAY);
		tabla.getTableHeader().setForeground(Color.black);

		tabla.getTableHeader().setFont(new Font("", 0, 15));
	}

		public void setear() {
			tNombre.setText("");
			tApellido1.setText("");
			tApellido2.setText("");
			tNickname.setText("");
			tCorreo.setText("");
			tContrasena.setText("");
		}
	@Override
	public void actionPerformed(ActionEvent e) {
		int fila = tabla.getSelectedRow();
		if(botonNuevo==e.getSource()) {
			setear();
			setBotones("registrar");
			lEstado.setText("Notificaciones");
		}
		if(botonRegistrar==e.getSource()) {
			try {
				if(!tNombre.getText().isEmpty()&& !tApellido1.getText().isEmpty()
						&&! tApellido2.getText().isEmpty()&&!tNickname.getText().isEmpty()&&!tContrasena.getText().isEmpty()) {
				lnUsuario.insertar(new Usuario(0, tNombre.getText(), tApellido1.getText()
						, tApellido2.getText(), tNickname.getText(), tContrasena.getText(), tCorreo.getText()));
				setear();
				lEstado.setText("Registrado");
				}else {
					lEstado.setText("Debe completar todos los campos");
				}
			} catch (Exception e2) {
				lEstado.setText("No se ha podido Registrar");
			}
		
		}
		
		if(botonActualizar==e.getSource()) {
			try {
				lnUsuario.editar(new Usuario(Integer.parseInt(tabla.getValueAt(fila, 0).toString()) , tNombre.getText(), tApellido1.getText()
						, tApellido2.getText(), tNickname.getText(), tContrasena.getText(),tCorreo.getText()));
				lEstado.setText("Actualizado");
				setBotones("registrar");
				fila = -1;
				setear();
			} catch (Exception e2) {
				lEstado.setText("No se ha podido");
			}
	
		}
		
		if(botonEliminar==e.getSource()) {
			try {
				lnUsuario.eliminar(Integer.parseInt(tabla.getValueAt(fila, 0).toString()));
				setear();
				lEstado.setText("Eliminado");
			} catch (Exception e2) {
				lEstado.setText("No se ha podido eliminar");
			}

		}
		cargarTabla("");
	}


	@Override
	public void keyTyped(KeyEvent e) {

		
	}


	@Override
	public void keyPressed(KeyEvent e) {


	}


	@Override
	public void keyReleased(KeyEvent e) {
		if(tBuscar == e.getSource()) {
			cargarTabla(tBuscar.getText());
		}

	}


}
