package com.mirador.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;

import com.mirador.ln.LNComplementosInventario;
import com.mirador.vo.CategoriaComida;
import com.mirador.vo.Unidad;

public class UICategoriasCocina extends JFrame implements ActionListener {

	private JPanel pPrincipal, pBotones, pTextfield, pCombo;
	private JTextField tNombres;
	private JButton botonRegistrar, botonNuevo, botonEliminar, botonActualizar, bCerrar;
	private JComboBox< Object> combo;
	private JLabel l;
	private int caso;
	private LNComplementosInventario ln;
	private int paso;
	private ViewInventario v;
	private int pos;
	public UICategoriasCocina(ViewInventario v,int caso) {
		this.v = v;
		this.pos = 0;
		this.paso = 0;
		this.caso = caso;
		pPrincipal = new JPanel();
		ln = new LNComplementosInventario();
		//BOTONES
		botonRegistrar = new JButton("Registrar");
		botonRegistrar.setBackground(new Color(2, 151, 0));
		botonRegistrar.setForeground(Color.white);
		botonRegistrar.setBorder(new EmptyBorder(10, 10, 10, 10));
		botonRegistrar.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 15));
		botonRegistrar.setFocusable(false);
		botonActualizar = new JButton("Actualizar");
		botonActualizar.setBackground(Color.BLUE);
		botonActualizar.setBorder(new EmptyBorder(10, 10, 10, 10));
		botonActualizar.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 15));
		botonActualizar.setForeground(Color.white);
		botonActualizar.setFocusable(false);

		botonNuevo = new JButton("Nuevo");
		botonNuevo.setBackground(Color.white);
		botonNuevo.setBorder(new EmptyBorder(10, 10, 10, 10));
		botonNuevo.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 15));
		botonNuevo.setFocusable(false);

		botonEliminar = new JButton("Eliminar");
		botonEliminar.setBackground(Color.red);
		botonEliminar.setForeground(Color.white);
		botonEliminar.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 15));
		botonEliminar.setBorder(new EmptyBorder(10, 10, 10, 10));
		botonEliminar.setFocusable(false);

		pBotones = new JPanel();
		pBotones.setLayout(new GridLayout());
		pBotones.setPreferredSize(new Dimension(0,50));
		pBotones.setBackground(Color.white);

		tNombres = new JTextField();
		tNombres.setFont(new Font("",0,20));
		tNombres.setPreferredSize(new Dimension(0,20));
		tNombres.setBackground(new Color(218, 218, 218));
		tNombres.setHorizontalAlignment(JLabel.CENTER);

		pTextfield = new JPanel();
		pTextfield.setLayout(new GridLayout(2,0));
		pTextfield.setPreferredSize(new Dimension(0,100));
		pTextfield.setBackground(Color.white);

		l = new JLabel("Inserte un nombre: ");
		l.setFont(new Font("",0,20));
		l.setHorizontalAlignment(JLabel.CENTER);
		pTextfield.add(l);
		pTextfield.add(tNombres);

		bCerrar = new JButton("X");
		bCerrar.setBackground(Color.RED);
		bCerrar.addActionListener(this);
		bCerrar.setForeground(Color.white);
		bCerrar.setPreferredSize(new Dimension(50,40));
		bCerrar.setFont(new Font("",Font.BOLD,20));
		bCerrar.setFocusable(false);

		combo = new JComboBox<Object>();
		combo.setFocusable(false);
		combo.setFont(new Font("",0,20));
		combo.setPreferredSize(new Dimension(200,40));

		pCombo = new JPanel();
		pCombo.setLayout(new BorderLayout());
		pCombo.setBackground(Color.white);
		pCombo.setPreferredSize(new Dimension(0,50));
		pCombo.add(combo, BorderLayout.CENTER);
		pCombo.add(bCerrar,BorderLayout.LINE_END);


		pPrincipal.setPreferredSize(new Dimension(400,200));
		pPrincipal.setBackground(Color.white);

		pPrincipal.setBorder(new EmptyBorder(10, 10 ,10, 10));
		pPrincipal.setLayout(new BorderLayout());

		pPrincipal.add(pCombo, BorderLayout.PAGE_START);
		pPrincipal.add(pTextfield, BorderLayout.CENTER);
		pPrincipal.add(pBotones, BorderLayout.PAGE_END);

		cargarCombo(caso);
		setBotones("registrar");
		accionCombo();
		iniciarBotones(this);
		add(pPrincipal);		
	}
	public void tittle() {
		if(caso==1) {
			this.setTitle("Añadir categorías");
		}else {
			this.setTitle("Añadir unidades");
		}
	}
	public void iniciarBotones(ActionListener al) {
		botonRegistrar.addActionListener(al);
		botonActualizar.addActionListener(al);
		botonNuevo.addActionListener(al);
		botonEliminar.addActionListener(al);

	}
	public void setBotones(String modo){
		pBotones.removeAll();
		switch (modo) {
		case "registrar":
			pBotones.add(botonRegistrar);
			pBotones.add(botonNuevo);
			break;

		case "editar":
			pBotones.add(botonActualizar);
			pBotones.add(botonEliminar);
			pBotones.add(botonNuevo);
			break;
		default:
			break;
		}
		pBotones.repaint();
		pBotones.revalidate();
	}

	public void init() {
		this.setUndecorated(true);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setLocationRelativeTo(null);	
	}
	public void accionCombo() {
		combo.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(combo.getItemCount()!=0&&paso==0) {
					tNombres.setText(combo.getSelectedItem().toString());
					setBotones("editar");
				}
			}
		});
	}
	public void cargarCombo(int caso) {
		combo.removeAllItems();
		if(caso == 1 ) {
			for (Object o : ln.getCategorias()) {
				combo.addItem(o);
			}
		} 
		}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(botonRegistrar == e.getSource()) {
			if(!tNombres.getText().isEmpty()) {
				if(caso == 1) {
					ln.insertarCategoria(new CategoriaComida(0, tNombres.getText()));
				} 
				paso = 1;
				//combo.setSelectedIndex(0);
				tNombres.setText("");
				cargarCombo(caso);
				paso = 0;
				l.setText("Inserte un nombre");
				l.setForeground(Color.black);
			}else {
				l.setText("NECESITA INSERTAR UN NOMBRE");
				l.setForeground(Color.red);
			}
		}

		if(botonActualizar== e.getSource()) {
			if(caso == 1) {
				CategoriaComida c = (CategoriaComida) combo.getSelectedItem();
				ln.actualizarCategoria(new CategoriaComida(c.getIdCategoriaComida(), tNombres.getText()));
				paso = 1;
				tNombres.setText("");
				setBotones("registrar");
				cargarCombo(caso);
				paso = 0;
			} 
		}

		if(botonNuevo == e.getSource()) {	
			combo.setSelectedIndex(0);
			tNombres.setText("");
			setBotones("registrar");
			l.setText("Inserte un nombre");
			l.setForeground(Color.black);
		}

		if(botonEliminar == e.getSource()) {
			if(caso == 1) {
				CategoriaComida c = (CategoriaComida) combo.getSelectedItem();
				ln.eliminarCategoria(c.getIdCategoriaComida());
			}
			paso = 0;
			cargarCombo(caso);
			tNombres.setText("");
			setBotones("registrar");
			paso = 1;
		}
		if(caso==1) {
			v.cargarComboCategorias();
		}
		if(bCerrar==e.getSource()) {
			this.v = null;
			this.dispose();
		}
	}

}
