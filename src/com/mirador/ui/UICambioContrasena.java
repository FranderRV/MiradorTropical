package com.mirador.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.mirador.ln.LNUsuario;

public class UICambioContrasena extends JFrame implements ActionListener {
	
	private JPanel pPrincipal;
	private JPasswordField tContrasena, tConfirmarContrasena;
	private JLabel lFondo, lContrasena, lConfirmarContrasena;
	private JButton bAceptar;
	private LNUsuario ln;
	private String nickname;
	public UICambioContrasena (String nickname) {
		this.ln = new LNUsuario();
		this.nickname = nickname;
		pPrincipal = new JPanel();
		pPrincipal.setPreferredSize(new Dimension(400,350));
		pPrincipal.setBackground(Color.white);
		pPrincipal.setLayout(null);
		
		lFondo = new JLabel();
		lFondo.setBounds(0, 0, 400,350);
		lFondo.setIcon(icono("/images/panelCamcioContra.jpg",1000,700));
	
		
		lContrasena = new JLabel("Inserte la  nueva contraseña");
		lContrasena.setBounds(50, 10, 300, 60);
		lContrasena.setHorizontalAlignment(JLabel.CENTER);
		lContrasena.setFont(new Font("",0,20));
		
		lConfirmarContrasena = new JLabel("Confirme la  nueva contraseña");
		lConfirmarContrasena.setHorizontalAlignment(JLabel.CENTER);
		lConfirmarContrasena.setBounds(50, 120, 300, 60);
		lConfirmarContrasena.setFont(new Font("",0,20));
		
		tContrasena = new JPasswordField();
		tContrasena.setBounds(50, 60, 300, 60);
		tContrasena.setHorizontalAlignment(JLabel.CENTER);
		tContrasena.setFont(new Font("", 0, 30));
		tContrasena.setBackground(Color.DARK_GRAY);
		tContrasena.setForeground(Color.white);
		
		tConfirmarContrasena = new JPasswordField();
		tConfirmarContrasena.setBounds(50, 170, 300, 60);
		tConfirmarContrasena.setHorizontalAlignment(JLabel.CENTER);
		tConfirmarContrasena.setFont(new Font("", 0, 30));
		tConfirmarContrasena.setBackground(Color.DARK_GRAY);
		tConfirmarContrasena.setForeground(Color.white);
		
		bAceptar = new JButton("Aceptar");
		bAceptar.setBounds(50, 260, 300, 60);
		bAceptar.setFont(new Font("", 0, 30));
		bAceptar.setForeground(Color.white);
		bAceptar.addActionListener(this);
		bAceptar.setBackground(new Color(30, 146, 0 ));
		
		pPrincipal.add(bAceptar);
		pPrincipal.add(	lConfirmarContrasena);
		pPrincipal.add(lContrasena);
		pPrincipal.add(	tConfirmarContrasena);
		pPrincipal.add(tContrasena);
		pPrincipal.add(lFondo);
		add(pPrincipal);
	}
	
	public ImageIcon icono(String url, int w, int h){
		return new ImageIcon(new ImageIcon(getClass().getResource(url)).getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT));
	}
	
	public void init() { 
		this.setVisible(true);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setResizable(false);
	}
//panelCamcioContra
	@Override
	public void actionPerformed(ActionEvent e) {
		if(tContrasena.getText().equalsIgnoreCase(tConfirmarContrasena.getText())) {
			JOptionPane.showMessageDialog(null, "Contraseña actualizada");
			this.dispose();
			ln.cambiarContrasena(nickname, tContrasena.getText());
		}else {
			JOptionPane.showMessageDialog(null, "Contraseña no coinciden", "Error de contraseña", JOptionPane.ERROR_MESSAGE, null);
		}
	}

}
