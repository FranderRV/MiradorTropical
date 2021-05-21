package com.mirador.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;

public class UIAuxiliar extends JFrame implements ActionListener {
	private ViewInventario p;
	private ViewProveedores p2;
	
	private JPanel panelPrin;
	private JButton botonCerrar;
	private JScrollPane scroll;
	private JLabel l;
	private ViewVenta v;
	private ViewCompra c;
	private ViewCompra v2;
	private GUI g;
	private int caso;
	public UIAuxiliar(ViewCompra v2,ViewProveedores p2, GUI g) {
		this.p2 = p2;
		this.g = g;
		caso = 2;
		this.v2 = v2;
		panelPrin = p2.panelEntradas;
		p2.setBackground(Color.DARK_GRAY);
		this.scroll = new JScrollPane(p2);
		this.setPreferredSize(new Dimension(400,700));
		p2.panelFormulario.setBackground(Color.DARK_GRAY);
		botonCerrar = new JButton("Guardar");
		botonCerrar.setBackground(new Color(2, 151, 0));
		botonCerrar.setForeground(Color.white);
		botonCerrar.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 20));
		botonCerrar.setBorder(new EmptyBorder(10, 10, 10, 10));
		botonCerrar.setFocusable(false);
		botonCerrar.setPreferredSize(new Dimension(100,50));
		botonCerrar.addActionListener(this);
		JPanel panel = new JPanel();
		panel.setBackground(new Color(18, 18, 18));
		panel.add(botonCerrar, BorderLayout.LINE_END);
		add(panel,BorderLayout.PAGE_START);
		add(p2, BorderLayout.CENTER);
	}
	public UIAuxiliar(ViewVenta v,ViewInventario p,GUI g) {
		this.p = p;
		caso = 1;
		this.g = g;
		p.iniciarBotones(this);
		p.setBackground(Color.DARK_GRAY);
		this.v = v;
		panelPrin = p.panelEntradas;
		l = new JLabel("Notificación");
		this.scroll = new JScrollPane(p);
		this.setPreferredSize(new Dimension(400,700));
		p.panelFormulario.setBackground(Color.DARK_GRAY);
		botonCerrar = new JButton("Cerrar");
		botonCerrar.setBackground(Color.red);
		botonCerrar.setForeground(Color.white);
		botonCerrar.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 20));
		botonCerrar.setBorder(new EmptyBorder(10, 10, 10, 10));
		botonCerrar.setFocusable(false);
		botonCerrar.setPreferredSize(new Dimension(100,50));
		botonCerrar.addActionListener(this);
		JPanel panel = new JPanel();
		panel.setBackground(new Color(18, 18, 18));
		panel.add(botonCerrar, BorderLayout.LINE_END);
		add(panel,BorderLayout.PAGE_START);
		add(p, BorderLayout.CENTER);
	}

	public UIAuxiliar(ViewCompra c,ViewInventario p, GUI g) {
		this.p = p;
		caso = 3;
		this.g = g;
		p.iniciarBotones(this);
		p.setBackground(Color.DARK_GRAY);
		this.c = c;
		panelPrin = p.panelEntradas;
		l = new JLabel("Notificación");
		this.scroll = new JScrollPane(p);
		this.setPreferredSize(new Dimension(400,700));
		p.panelFormulario.setBackground(Color.DARK_GRAY);
		botonCerrar = new JButton("Guardar");
		botonCerrar.setBackground(new Color(2, 151, 0));
		botonCerrar.setForeground(Color.white);
		botonCerrar.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 20));
		botonCerrar.setBorder(new EmptyBorder(10, 10, 10, 10));
		botonCerrar.setFocusable(false);
		botonCerrar.setPreferredSize(new Dimension(100,50));
		botonCerrar.addActionListener(this);
		JPanel panel = new JPanel();
		panel.setBackground(new Color(18, 18, 18));
		panel.add(botonCerrar, BorderLayout.LINE_END);
		add(panel,BorderLayout.PAGE_START);
		add(p, BorderLayout.CENTER);
	}
	
	public void init() {
		this.setUndecorated(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	
	
	public void cargarComboProductos() {
		v.cargarComboProducto();
	}
	public void cargarComboProveedores() {
		v2.cargarComboProveedor();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(botonCerrar == e.getSource()) {
			if(caso == 1) {
				cargarComboProductos();
			}
			if(caso == 2) {
			cargarComboProveedores();
			}
			if(caso == 3) {
				c.cargarComboProductoLast();
				this.c = null;
			}
			this.v2 = null;
			this.v  = null;

			this.dispose();
		}

	}
}
