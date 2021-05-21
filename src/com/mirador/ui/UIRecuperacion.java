package com.mirador.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.mirador.ln.Email;
import com.mirador.ln.LNCodigoRecuperacion;


public class UIRecuperacion extends JFrame implements ActionListener {
	
	private JLabel lUsuario, lCodigo, lTitulo,lFondo;
	private JPanel pPrincipal;
	private JTextField tUsuario, tCodigo;
	private JButton bAceptar, bPedirCodigo;
	private LNCodigoRecuperacion ln;
	public UIRecuperacion() {
		this.ln = new LNCodigoRecuperacion();
		lFondo= new JLabel();
		lFondo.setBounds(0,0, 400, 600);
		lFondo.setIcon(icono("/images/recuperacion.jpg", 400, 600));
		
		pPrincipal = new JPanel();
		pPrincipal.setPreferredSize(new Dimension(400,600));
		pPrincipal.setBackground(Color.white);
		pPrincipal.setLayout(null);
		
		tUsuario = new JTextField();
		tUsuario.setBackground(new Color(225, 225, 225));
		tUsuario.setBounds(25,190,350,60);
		tUsuario.setFont(new Font("",1,30));
		tUsuario.setHorizontalAlignment(JLabel.CENTER);
		tUsuario.setBorder(new EmptyBorder(0, 0, 0, 0));
		
		
		tCodigo = new JTextField();
		tCodigo.setBackground(new Color(225, 225, 225));
		tCodigo.setBounds(25,325,350,60);
		tCodigo.setFont(new Font("",1,30));
		tCodigo.setHorizontalAlignment(JLabel.CENTER);
		tCodigo.setBorder(new EmptyBorder(0, 0, 0, 0));
		
		bPedirCodigo = new JButton("Solicitar código");
		bPedirCodigo.addActionListener(this);
		bPedirCodigo.setForeground(Color.white);
		bPedirCodigo.setBorder(null);
		bPedirCodigo.setFont(new Font("",1,30));
		bPedirCodigo.setFocusable(false);
		bPedirCodigo.setBounds(50, 420, 300,60);
		bPedirCodigo.setBackground(new Color(232, 112, 0 ));
		
		bAceptar = new JButton("Aceptar");
		bAceptar.addActionListener(this);
		bAceptar.setForeground(Color.white	);
		bAceptar.setBorder(null);
		bAceptar.setFont(new Font("",1,30));
		bAceptar.setFocusable(false);
		bAceptar.setBounds(75, 500, 250,60);
		bAceptar.setBackground(new Color(37, 181, 37  ));
		
		pPrincipal.add(bPedirCodigo);
		pPrincipal.add(bAceptar);
		pPrincipal.add(tUsuario);
		pPrincipal.add(tCodigo);
		pPrincipal.add(lFondo);
		add(pPrincipal);
	}
	
	public ImageIcon icono(String url, int w, int h){
		return new ImageIcon(new ImageIcon(getClass().getResource(url)).getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT));
	}
	public void init(){ 
	this.setVisible(true);
	this.pack();
	this.setLocationRelativeTo(null);
	this.setResizable(false);
	}
	


	@Override
	public void actionPerformed(ActionEvent e) {
		if(bAceptar==e.getSource()) {
			if(!tCodigo.getText().equalsIgnoreCase("")) {
				int caso = ln.verificarCodigo(tUsuario.getText(), tCodigo.getText());
 
				if(caso==0) {
					JOptionPane.showMessageDialog(null, "Código Incorrecto");
				}else if(caso == 1) {
					this.dispose();
					new UICambioContrasena(tUsuario.getText()).init();
					ln.eliminar(tUsuario.getText());
				}else if(caso == 2) {
					JOptionPane.showMessageDialog(null, "Nickname Inexistente");
				}
			}else {
				JOptionPane.showMessageDialog(null, "Digite el código de recuperación", "Falta un dato", JOptionPane.ERROR_MESSAGE, null);
			}
		}
		
		if(bPedirCodigo==e.getSource()) {
			if(!tUsuario.getText().equalsIgnoreCase("")) {
				if(ln.insertar(tUsuario.getText())) {
					bPedirCodigo.setEnabled(false);
					JOptionPane.showMessageDialog(null, "Código de recuperación enviado, por favor revisar, y no cerrar la pestaña");
				}else {
					JOptionPane.showMessageDialog(null, "Usuario Inexistente");
				}
				
			}else {
				JOptionPane.showMessageDialog(null, "Digite el nombre de usuario", "Falta un dato", JOptionPane.ERROR_MESSAGE, null);
			}
		}
		
	}
	
}
