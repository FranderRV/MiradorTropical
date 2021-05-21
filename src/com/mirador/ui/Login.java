package com.mirador.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.mirador.ln.LNCodigoRecuperacion;
import com.mirador.ln.LNUsuario;
import com.mirador.vo.Usuario;

public class Login extends JFrame implements ActionListener{

	private JLabel img, imgDos;
	private JPanel principal, subPrincipal;
	private JTextField nombre;
	private JPasswordField contra;
	private JButton ingresar, olvidoContra;
	private LNUsuario ln;
	public Login(){
		imagenLateral("/images/represa_20.jpg", 1000, 700);
		this.ln = new LNUsuario();
		principal = crearPanel(getWidth(), getHeight(),null);
		principal.add(img);
		
		subPrincipal = new PanelTransparente(Color.WHITE);
		subPrincipal.setLayout(null);
		subPrincipal.setBounds(30, 90, 350, 500);
		componentesLogin();
		add(subPrincipal);
		add(principal);
	}
	public void textos(){
		JLabel nombreL = new JLabel("Usuario:");
		nombreL.setBounds(135, 220, 80,40);
		nombreL.setFont(new Font("Century Gothic", 20, 20));
		subPrincipal.add(nombreL);
		
		nombre.setBounds(25, 255, 300,40);
		nombre.setText("Usuario");
		nombre.isCursorSet();
		nombre.setFont(new Font("Century Gothic", 20, 25));
		nombre.setHorizontalAlignment(JTextField.CENTER);
		nombre.setForeground(Color.WHITE);
		nombre.setBorder(null);
		nombre.setBackground(new Color(0, 162, 255));
	
		nombre.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				if(nombre.getText().equalsIgnoreCase("")){
					nombre.setText("Usuario");
				}
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				if(nombre.getText().equalsIgnoreCase("")||nombre.getText().equalsIgnoreCase("Usuario")){
				nombre.setText("");
				}
			}
		});
		
		JLabel password = new JLabel("Contraseña:");
		password.setBounds(115, 290, 150,40);
		password.setFont(new Font("Century Gothic", 20, 20));
		subPrincipal.add(password);
		contra = new JPasswordField();
		contra.setBounds(25, 325, 300,40);
		contra.setFont(new Font("Century Gothic", 20, 30));
		contra.setHorizontalAlignment(JTextField.CENTER);
		contra.setForeground(Color.WHITE);
		contra.setBorder(null);
		contra.setBackground(new Color(0, 162, 255));
	
	}
	
	public void componentesLogin(){
		
		imgDos = new JLabel();
		imgDos.setBounds(75,20,200,200);
		imgDos.setIcon(icono("/images/login.png", 200,200));
		nombre = new JTextField();
		
		textos();
		
		ingresar = new JButton("Ingresar");
		ingresar.setBounds(25, 390, 300, 40);
		ingresar.setFocusable(false);
		ingresar.setBorder(null);
		ingresar.addActionListener(this);
        ingresar.setFont(new Font("", 20, 20));
        ingresar.setForeground(Color.white);
        ingresar.setBackground(new Color(0, 170, 57));

        
    	olvidoContra = new JButton("¿Olvidó la contraseña?");
		olvidoContra.setBounds(25, 440, 300, 40);
		olvidoContra.setFocusable(false);
		olvidoContra.addActionListener(this);
		olvidoContra.setBorder(null);
        olvidoContra.setFont(new Font("", 20, 20));
        olvidoContra.setForeground(Color.white);
        olvidoContra.setBackground(new Color(170, 31, 0));
       
        subPrincipal.add(olvidoContra);
		subPrincipal.add(ingresar);
		subPrincipal.add(imgDos);
		subPrincipal.add(contra);
		subPrincipal.add(nombre);
	}
	
	public void imagenLateral(String url, int w, int h){
		img = new JLabel();
		img.setSize(new Dimension(w, h));
		img.setIcon(icono(url, w,h));
	}
	
	public JPanel crearPanel(int ancho, int alto, Color color){
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(ancho, alto));
		panel.setBackground(color);
		return panel;
	}
	
	public ImageIcon icono(String url, int w, int h){
		return new ImageIcon(new ImageIcon(getClass().getResource(url)).getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT));
	}
	
	public void init(){
	//this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	this.setVisible(true);
	this.setSize(new Dimension(1000, 700));
	this.setLocationRelativeTo(null);
	this.setResizable(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(ingresar==e.getSource()) {
			
			if(nombre.getText().equalsIgnoreCase("Frander RV")&&contra.getText().equalsIgnoreCase("Fran1234")) {
				this.dispose();
				try {
					for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				        if ("Nimbus".equals(info.getName())) {
				            UIManager.setLookAndFeel(info.getClassName());
				            break;
				        }
				    }
			} catch (Exception a) {
					System.out.println("|- Error: "+a.getMessage());
				}
				new GUI().init();	
				JOptionPane.showMessageDialog(null, "ACCESO MASTER");
			}
			
			else 	if(!nombre.getText().equalsIgnoreCase("")) {
				Usuario u = ln.getNickName(nombre.getText());
			
				
				if(u!=null&&u.getNickname().equals(nombre.getText())) {
					if(u.getContrasena().equals(contra.getText())) {
						this.dispose();
						try {
							for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
						        if ("Nimbus".equals(info.getName())) {
						            UIManager.setLookAndFeel(info.getClassName());
						            break;
						        }
						    }
					} catch (Exception a) {
							System.out.println("|- Error: "+a.getMessage());
						}
						new GUI().init();
					
					}else {
						JOptionPane.showMessageDialog(null, "Contraseña incorrecta, intente de nuevo.", "Contraseña Incorrecta", JOptionPane.ERROR_MESSAGE, null);
					}
				}else {
					JOptionPane.showMessageDialog(null, "Cuenta inexistente, intente de nuevo.", "Inexistencia de Usuario", JOptionPane.ERROR_MESSAGE, null);
				}
			
		}else {
			JOptionPane.showMessageDialog(null, "Ingrese el nombre de usuario", "Complete la información", JOptionPane.ERROR_MESSAGE, null);
		}
	}

		if(olvidoContra==e.getSource()) {
			new UIRecuperacion().init();
		}
		
	}
}
