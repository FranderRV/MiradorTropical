package com.mirador.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.BrokenBarrierException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import com.mirador.ln.LNCompras;
import com.mirador.ln.LNFacturas;
import com.mirador.ln.LNProducto;
import com.mirador.ln.PDFReporte;
import com.mirador.vo.Compra;
import com.mirador.vo.Detalles;
import com.mirador.vo.Proveedor;
import com.toedter.calendar.JDateChooser;

public class ViewCompras  extends View implements ActionListener{ 
	private JPanel panelArriba, panelAbajo;
	private JButton botonActualizar, botonEliminar, botonBuscarPorFecha, botonBuscarGeneral,botonExportarPDF;
	private JDateChooser calendarioUno, calendarioDos;
	private LNCompras ln;
	private GUI g;
	private int idCompra;
	private String nombre;
	private int fila;
	private int metodoBusqueda;
	private JFileChooser chooser;
	public  ViewCompras(GUI g) {
		super("Compras",0,0);
		metodoBusqueda = 0;
		this.g = g; 
		fila = -1;
		this.ln = new LNCompras();
		idCompra = -1;
		nombre = "";
		chooser = new JFileChooser();
		/*COMPONENTES*/
		panelArriba = crearPanel(0, 50, Color.white);
		panelAbajo= crearPanel(0, 50, Color.white);
		panelAbajo.setLayout(new GridLayout(1,0));

		calendarioUno = new JDateChooser();
		calendarioUno.setDateFormatString("yyyy-MM-dd");
		calendarioUno.setFont(new Font("",0,20));

		calendarioDos= new JDateChooser();
		calendarioDos.setDateFormatString("yyyy-MM-dd");
		calendarioDos.setFont(new Font("",0,20));

		botonBuscarPorFecha = new JButton("Fechas");
		botonBuscarPorFecha.setBackground(new Color(0, 148, 160));
		botonBuscarPorFecha.setForeground(Color.WHITE);
		botonBuscarPorFecha.addActionListener(this);
		botonBuscarPorFecha.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 20));

		botonBuscarGeneral = new JButton("General");
		botonBuscarGeneral.setBackground(new Color(2, 151, 0));
		botonBuscarGeneral.setForeground(Color.WHITE);
		botonBuscarGeneral.addActionListener(this);
		botonBuscarGeneral.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 20));
		//BOTON
		botonActualizar = new JButton("Actualizar");
		botonActualizar.setBackground(new Color(229, 122, 0));
		botonActualizar.setForeground(Color.white);
		botonActualizar.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 20));
		botonActualizar.addActionListener(this);

		botonEliminar = new JButton("Eliminar");
		botonEliminar.setBackground(Color.RED);
		botonEliminar.setForeground(Color.white);
		botonEliminar.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 20));
		botonEliminar.addActionListener(this);

		botonExportarPDF = new JButton("Exportar PDF");
		botonExportarPDF.setBackground(new Color(0, 148, 160));
		botonExportarPDF.setForeground(Color.WHITE);
		botonExportarPDF.addActionListener(this);
		botonExportarPDF.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 20));

		//CALENDARIOS
		JPanel  p = new JPanel();
		p.add(entradaHorizontal(calendarioUno, "Desde:"));
		p.add(entradaHorizontal(calendarioDos, "A:"));
		panelArriba.add(p, BorderLayout.LINE_START);
		JPanel  p2 = new JPanel();
		p2.setLayout(new GridLayout());
		p2.add(botonBuscarPorFecha);
		p2.add(botonBuscarGeneral);
		panelArriba.add(p2, BorderLayout.CENTER);


		panelAbajo.add(botonActualizar);
		panelAbajo.add(botonEliminar);
		panelAbajo.add(botonExportarPDF);
		panelDatos.add(panelArriba, BorderLayout.PAGE_START);
		panelDatos.add(panelAbajo, BorderLayout.PAGE_END);
		detallesTabla();
		cargarTabla();
		eventoTabla();
	}

	public void eventoTabla() {
		tabla.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				fila =  tabla.getSelectedRow();	
				idCompra = Integer.parseInt(tabla.getValueAt(fila, 0).toString());
				nombre  = tabla.getValueAt(fila, 1).toString();
			}
		});
	}

	public void cargarTabla(String dateUno, String dateDos) {
		cargarTabla(ln.getDatosCompras(ln.getComprasPorFecha(dateUno, dateDos)), ln.getColumnasCompras());
	}
	public void cargarTabla() {
		cargarTabla(ln.getDatosCompras(ln.getCompras()), ln.getColumnasCompras());
	}
	public void detallesTabla() {

		tabla.setRowHeight(30);

		tabla.setFont(new Font("",0,15));
		tabla.getTableHeader().setPreferredSize(new java.awt.Dimension(500, 60));
		tabla.getTableHeader().setBackground(new Color(215, 137, 0));
		tabla.getTableHeader().setForeground(Color.black);
		tabla.getTableHeader().setFont(new Font("", 0, 20));
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(botonBuscarGeneral == e.getSource()) {
			cargarTabla();
			metodoBusqueda = 0;
		}
		if(botonBuscarPorFecha == e.getSource()) {

			try {
				SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date date =calendarioUno.getDate();
				java.util.Date dateDos =calendarioDos.getDate();
				String fecha = formateador.format(date);
				String fechaDos = formateador.format(dateDos);
				cargarTabla(fecha, fechaDos);
				lEstado.setText("Notificaciones");
				metodoBusqueda = 1;
			}catch (Exception e2) {
				lEstado.setText("Debe insertar las dos fechas");
			}
		}

		if(botonActualizar == e.getSource()) {
			g.cargarVista(new ViewCompra(idCompra,g, nombre));
		}
		if(botonEliminar == e.getSource()) {
			if(idCompra != -1) {
				if(metodoBusqueda ==0) {
					//		ln.r
					ln.eliminarCompra(idCompra);	
					cargarTabla();
				}else {
					SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
					java.util.Date date =calendarioUno.getDate();
					java.util.Date dateDos =calendarioDos.getDate();
					String fecha = formateador.format(date);
					String fechaDos = formateador.format(dateDos);
					ln.eliminarCompra(idCompra);	
					metodoBusqueda = 1;
					cargarTabla(fecha, fechaDos);
				}
			}else {
				lEstado.setText("Seleccione un dato de la tabla");
			}
		}


		if(botonExportarPDF == e.getSource()) {
			if(idCompra != -1) {
				chooser.setDialogTitle("Guardar PDF en:");
				int userSelection = chooser.showSaveDialog(this);
				if(userSelection == JFileChooser.APPROVE_OPTION){
					Compra c = new LNCompras().getCompra(idCompra);
					c.setDetalles(new LNCompras().getDetalles(idCompra));
					new PDFReporte().crearPDFCompra(chooser.getSelectedFile().toString(), c.getDetalles(), c);
				}
			}else {
				lEstado.setText("Seleccione un dato de la tabla");
			}
		}
		if(botonEliminar == e.getSource()) {
			if(fila != -1) {
				
				ln.eliminarCompra(idCompra);
				cargarTabla();
				new LNProducto().actualizarValores();
				new LNProducto().actualizarValoresAjusteInventario();
		
			}
		}
	}
}
