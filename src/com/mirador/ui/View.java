package com.mirador.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyledEditorKit.BoldAction;

public class View extends JPanel {
		
	protected String titulo;
	protected JPanel panelFormulario, panelDatos, panelEstado, panelTitulo;
	protected JPanel panelEntradas, panelBotones;
	protected  JButton botonRegistrar, botonActualizar, botonEliminar, botonNuevo;
	protected DefaultTableModel modelo;
	protected JTable tabla;
	protected JScrollPane scroll, scroll2;
	protected JTextField tBuscar;
	protected JLabel lEstado;
	
	public View(String titulo, int n,int n2) {
		this.titulo = titulo;
		this.setLayout(new BorderLayout());
		this.panelTitulo = crearPanel(0, 50, Color.darkGray);
		this.panelDatos = crearPanel(400, 500);
		this.panelEstado = crearPanel(0, 50, new Color(18, 18, 18));
		
		JLabel lTitulo = new JLabel(titulo);
		lTitulo.setHorizontalAlignment(JLabel.CENTER);
		lTitulo.setFont(new Font("Segoe UI",Font.BOLD,25));
		lTitulo.setForeground(Color.white);
		panelTitulo.add(lTitulo);	
		 
		modelo = new DefaultTableModel();
		
		tabla = new JTable(modelo);
		tabla.setDefaultEditor(Object.class,null);
		scroll= new JScrollPane(tabla);
		
		lEstado = new JLabel("Notificaciones");
		lEstado.setHorizontalAlignment(JLabel.CENTER);
		lEstado.setForeground(Color.white);
		lEstado.setFont(new Font("Segoe UI", Font.BOLD, 30));
		
		panelEstado.add(lEstado);
		
		panelDatos.add(scroll, BorderLayout.CENTER);

		add(panelEstado, BorderLayout.PAGE_END);
		add(panelTitulo, BorderLayout.PAGE_START);
		add(panelDatos,BorderLayout.CENTER);
	}
	
	public View(String titulo, int n) {
		this.titulo = titulo;
		this.setLayout(new BorderLayout());

		this.panelFormulario = crearPanel(400, 500);
		
		this.panelDatos = crearPanel(400, 500);
		this.panelTitulo = crearPanel(0, 50, Color.darkGray);
		this.panelEstado = crearPanel(0, 50, new Color(18, 18, 18));
		
		JLabel lTitulo = new JLabel(titulo);
		lTitulo.setHorizontalAlignment(JLabel.CENTER);
		lTitulo.setFont(new Font("Segoe UI",Font.BOLD,25));
		lTitulo.setForeground(Color.white);
		panelTitulo.add(lTitulo);	
		 
		modelo = new DefaultTableModel();
		tabla = new JTable(modelo);
		tabla.setDefaultEditor(Object.class,null);
		scroll= new JScrollPane(tabla);
		
		lEstado = new JLabel("Notificaciones");
		lEstado.setHorizontalAlignment(JLabel.CENTER);
		lEstado.setForeground(Color.white);
		lEstado.setFont(new Font("Segoe UI", Font.BOLD, 30));
		
		panelEstado.add(lEstado);
		panelEntradas = crearPanel(300, 450, new FlowLayout());
		JScrollPane s = new JScrollPane(panelEntradas);
		panelBotones = crearPanel(0, 100, new GridLayout());
		
		panelFormulario.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelFormulario.add(s, BorderLayout.CENTER);
		panelFormulario.add(panelBotones, BorderLayout.PAGE_END);
		
		
		add(panelDatos,BorderLayout.CENTER);
		add(panelEstado, BorderLayout.PAGE_END);
		add(panelTitulo, BorderLayout.PAGE_START);
		add(panelFormulario, BorderLayout.LINE_START);
	}
	public View(String titulo) {
		this.titulo = titulo;
		this.setLayout(new BorderLayout());
		
		this.panelFormulario = crearPanel(400, 500);
		this.panelDatos = crearPanel(400, 500);

		panelDatos.setBorder(new EmptyBorder(20, 10, 10, 10));
		this.panelTitulo = crearPanel(0, 50, Color.darkGray);
		this.panelEstado = crearPanel(0, 50, new Color(18, 18, 18));
		
		JLabel lTitulo = new JLabel(titulo);
		lTitulo.setHorizontalAlignment(JLabel.CENTER);
		lTitulo.setFont(new Font("Segoe UI",Font.BOLD,25));
		lTitulo.setForeground(Color.white);
		panelTitulo.add(lTitulo);
		
		tBuscar = new JTextField("Buscar...");
		tBuscar.setPreferredSize(new Dimension(0,40));
		tBuscar.setFont(new Font("Segoe UI", 0, 20));
		
		modelo = new DefaultTableModel();
		tabla = new JTable(modelo);
		tabla.setDefaultEditor(Object.class,null);
		scroll= new JScrollPane(tabla);
		 
		lEstado = new JLabel("Notificaciones");
		lEstado.setHorizontalAlignment(JLabel.CENTER);
		lEstado.setForeground(Color.white);
		lEstado.setFont(new Font("Segoe UI", Font.BOLD, 30));
		
		panelDatos.add(tBuscar, BorderLayout.PAGE_START);
		panelDatos.add(scroll, BorderLayout.CENTER);
		panelEstado.add(lEstado);
		
	
		creacionBtn();
		
		
		panelEntradas = crearPanel(300, 800, new FlowLayout());
		panelBotones = crearPanel(0, 50, new GridLayout());

		scroll2= new JScrollPane(panelEntradas);
		

		panelFormulario.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelFormulario.add(scroll2, BorderLayout.CENTER);
		panelFormulario.add(panelBotones, BorderLayout.PAGE_END);
		
		
		add(panelDatos,BorderLayout.CENTER);
		add(panelEstado, BorderLayout.PAGE_END);
		add(panelTitulo, BorderLayout.PAGE_START);
		add(panelFormulario, BorderLayout.LINE_START);
	
	}
public void creacionBtn() {
	//BOTONES
	botonRegistrar = new JButton("Registrar");
	botonRegistrar.setBackground(new Color(2, 151, 0));
	botonRegistrar.setForeground(Color.white);
	botonRegistrar.setBorder(new EmptyBorder(10, 10, 10, 10));
	botonRegistrar.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 15));

	
	botonActualizar = new JButton("Actualizar");
	botonActualizar.setBackground(Color.BLUE);
	botonActualizar.setBorder(new EmptyBorder(10, 10, 10, 10));
	botonActualizar.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 15));
	botonActualizar.setForeground(Color.white);
	
	botonNuevo = new JButton("Nuevo");
	botonNuevo.setBackground(Color.white);
	botonNuevo.setBorder(new EmptyBorder(10, 10, 10, 10));
	botonNuevo.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 15));
	
	botonEliminar = new JButton("Eliminar");
	botonEliminar.setBackground(Color.red);
	botonEliminar.setForeground(Color.white);
	botonEliminar.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 15));
	botonEliminar.setBorder(new EmptyBorder(10, 10, 10, 10));
}

	
	/**UTILIDADES*/
	
	public ImageIcon iconoExterno(String url, int w, int h){
		ImageIcon icon = new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT));
		return icon;
	}
	

	public ImageIcon icono(String url, int x , int y, int w, int h){
		ImageIcon icon = new ImageIcon(new ImageIcon(getClass().getResource(url)).getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT));
		return icon;
	}
	
	public JPanel entradaMultimedia(JLabel l, JComponent f) {
		JPanel p = crearPanel(400, 200);
		p.setLayout(new FlowLayout());
		p.add(l);
		p.add(f);
		return p;
	}
	
	public JPanel entradaVerticalLista( ArrayList<JComponent>lista,Color color) {
		JPanel p = new JPanel();
		p = crearPanel(360, 50, new GridLayout(1,lista.size()));
		for (JComponent c : lista) {
			p.add(c);
		}
		return p;
	}
	public JPanel entradaVertical(JComponent c, String nombre, Color color) {
		JPanel p = new JPanel();
		if(!nombre.equalsIgnoreCase("")) {
		p = crearPanel(360, 80, new GridLayout(2,1));
		p.setBackground(color);
		JLabel l = new JLabel(nombre);
		l.setFont(new Font("", 0, 15));
		p.add(l);
		p.add(c);
		}else {
		p = crearPanel(360, 80, new GridLayout(1,2));
		p.add(c);
		}

		return p;
	}
	public JPanel entradaVertical(JComponent c, String nombre, int w, int h) {
		JPanel p = new JPanel();
		if(!nombre.equalsIgnoreCase("")) {
		p = crearPanel(w, h, new GridLayout(2,1));
		p.setBackground(null);
		JLabel l = new JLabel(nombre);
		l.setFont(new Font("", 0, 20));
		p.add(l);
		p.add(c);
		}else {
		p = crearPanel(360, 80, new GridLayout(1,2));
		p.add(c);
		}

		return p;
	}
	
	public JPanel entradaVertical(JComponent c, String nombre) {
		JPanel p = crearPanel(360, 80, new GridLayout(2,1));
		JLabel l = new JLabel(nombre);
		l.setFont(new Font("", Font.LAYOUT_NO_LIMIT_CONTEXT, 20));
		p.add(l);
		c.setFont(new Font("Segoe UI", 0, 19));
		p.add(c);
		return p;
	}
	public JPanel entradaVertical(JComponent c1, JComponent c2, String nombre) {
		JPanel p = crearPanel(360, 100, new GridLayout(2,1));
		JLabel l = new JLabel(nombre);
		l.setFont(new Font("", Font.LAYOUT_NO_LIMIT_CONTEXT, 20));

		JPanel p2 = new JPanel();
		p2.setLayout(new BorderLayout());
		c1.setFont(new Font("Segoe UI", 0, 19));
		p2.add(l, BorderLayout.CENTER);
		p2.add(c2, BorderLayout.LINE_END);
		p.add(p2);
		p.add(c1);
		return p;
	}
	public JPanel entradaHorizontal(JComponent c, String nombre) {
		JPanel p = crearPanel(360, 50, new FlowLayout());
		p.setBackground(Color.white);
		JLabel l = new JLabel(nombre);
		l.setPreferredSize(new Dimension(80,40));
		c.setPreferredSize(new Dimension(190,40));
		l.setFont(new Font("", 0, 20));
		p.add(l);
		p.add(c);
		return p;
	}
	
	public JPanel entradaHorizontal(JComponent c, String nombre,int w, int h) {
		JPanel p = crearPanel(w, h, new FlowLayout());
		JLabel l = new JLabel(nombre);
		l.setFont(new Font("Segoe UI", 0, 30));
		l.setPreferredSize(new Dimension(200,60));
		c.setPreferredSize(new Dimension(200,50));
		p.add(l);
		p.add(c);
		return p;
	}
	
	public void iniciarBuscar(KeyListener k) {
		tBuscar.addKeyListener(k);
	}
	
	public void iniciarBotones(ActionListener al) {
		botonRegistrar.addActionListener(al);
		botonActualizar.addActionListener(al);
		botonNuevo.addActionListener(al);
		botonEliminar.addActionListener(al);
		
		}
	
	public void setBotones(String modo){
		panelBotones.removeAll();
		switch (modo) {
		case "registrar":
			panelBotones.add(botonRegistrar);
			panelBotones.add(botonNuevo);
			break;
		
		case "editar":
			panelBotones.add(botonActualizar);
			panelBotones.add(botonEliminar);
			panelBotones.add(botonNuevo);
			break;
		default:
			break;
		}
		panelBotones.repaint();
		panelBotones.revalidate();
	}
	
	public void cargarTabla(Object[][] data, String[] columns) {
		modelo.setDataVector(data, columns);
	}
	
	public JPanel crearPanel(int width, int height) {
	JPanel p = new JPanel();
	p.setPreferredSize(new DimensionUIResource(width, height));
	p.setLayout(new BorderLayout());
	return p;
	}
	
	public JPanel crearPanel(int width, int height, LayoutManager lay) {
	JPanel p = crearPanel(width, height);
	p.setLayout(lay);
	return p;
	}
	
	public JPanel crearPanel(int width, int height, Color c) {
		JPanel p = crearPanel(width, height);
		p.setLayout(new BorderLayout());
		p.setBackground(c);
		return p;
		}
	
	
	/** ENCAPSULAMIENTO*/

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public JPanel getPanelFormulario() {
		return panelFormulario;
	}

	public void setPanelFormulario(JPanel panelFormulario) {
		this.panelFormulario = panelFormulario;
	}

	public JPanel getPanelDatos() {
		return panelDatos;
	}

	public void setPanelDatos(JPanel panelDatos) {
		this.panelDatos = panelDatos;
	}

	public JPanel getPanelEstado() {
		return panelEstado;
	}

	public void setPanelEstado(JPanel panelEstado) {
		this.panelEstado = panelEstado;
	}

	public JPanel getPanelTitulo() {
		return panelTitulo;
	}

	public void setPanelTitulo(JPanel panelTitulo) {
		this.panelTitulo = panelTitulo;
	}

	public JPanel getPanelEntradas() {
		return panelEntradas;
	}

	public void setPanelEntradas(JPanel panelEntradas) {
		this.panelEntradas = panelEntradas;
	}

	public JPanel getPanelBotones() {
		return panelBotones;
	}

	public void setPanelBotones(JPanel panelBotones) {
		this.panelBotones = panelBotones;
	}

	public JButton getBotonRegistrar() {
		return botonRegistrar;
	}

	public void setBotonRegistrar(JButton botonRegistrar) {
		this.botonRegistrar = botonRegistrar;
	}

	public JButton getBotonActualizar() {
		return botonActualizar;
	}

	public void setBotonActualizar(JButton botonActualizar) {
		this.botonActualizar = botonActualizar;
	}

	public JButton getBotonEliminar() {
		return botonEliminar;
	}

	public void setBotonEliminar(JButton botonEliminar) {
		this.botonEliminar = botonEliminar;
	}

	public JButton getBotonNuevo() {
		return botonNuevo;
	}

	public void setBotonNuevo(JButton botonNuevo) {
		this.botonNuevo = botonNuevo;
	}

	public DefaultTableModel getModelo() {
		return modelo;
	}

	public void setModelo(DefaultTableModel modelo) {
		this.modelo = modelo;
	}

	public JTable getTabla() {
		return tabla;
	}

	public void setTabla(JTable tabla) {
		this.tabla = tabla;
	}

	public JScrollPane getScroll() {
		return scroll;
	}

	public void setScroll(JScrollPane scroll) {
		this.scroll = scroll;
	}

	public JTextField gettBuscar() {
		return tBuscar;
	}

	public void settBuscar(JTextField tBuscar) {
		this.tBuscar = tBuscar;
	}

	public JLabel getlEstado() {
		return lEstado;
	}

	public void setlEstado(JLabel lEstado) {
		this.lEstado = lEstado;
	}
	
	
}
