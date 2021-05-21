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

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import com.mirador.ln.LNAjuste;
import com.mirador.ln.LNCompras;
import com.mirador.ln.LNProducto;
import com.mirador.ln.PDFReporte;
import com.mirador.vo.AjusteInventario;
import com.mirador.vo.Compra;
import com.toedter.calendar.JDateChooser;

public class ViewAjustesInventario  extends View implements ActionListener{
	
	
	private JPanel panelArriba, panelAbajo;
	private JButton botonActualizar, botonEliminar, botonBuscarPorFecha, botonBuscarGeneral,botonExportarPDF;
	private JDateChooser calendarioUno, calendarioDos;
	private LNAjuste ln;
	private GUI g;
	private int idAjuste;
	private String nombre;
	private int metodoBusqueda;
	private JFileChooser chooser;
	private int fila;
	public ViewAjustesInventario(GUI g) {
		super("Ajustes Inventario",0,0);
		fila = -1;
		metodoBusqueda = 0;
		this.g = g; 
		this.ln = new LNAjuste();
		idAjuste = -1;
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

		botonBuscarPorFecha = new JButton("Buscar por fechas");
		botonBuscarPorFecha.setBackground(new Color(0, 148, 160));
		botonBuscarPorFecha.setForeground(Color.WHITE);
		botonBuscarPorFecha.addActionListener(this);
		botonBuscarPorFecha.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 20));

		botonBuscarGeneral = new JButton("Buscar desde siempre");
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
				idAjuste = Integer.parseInt(tabla.getValueAt(fila, 0).toString());
				nombre  = tabla.getValueAt(fila, 1).toString();
			}
		});
	}

	public void cargarTabla(String dateUno, String dateDos) {
		cargarTabla(ln.getDatosAjuste(ln.getAjustesPorFecha(dateUno, dateDos)), ln.getColumnasAjustes());
	}
	public void cargarTabla() {
		cargarTabla(ln.getDatosAjuste(ln.getAjustesInventario()), ln.getColumnasAjustes());
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
			g.cargarVista(new ViewAjusteInventario(g,idAjuste));
		}
		if(botonEliminar == e.getSource()) {
			if(idAjuste != -1) {
				if(metodoBusqueda ==0) {
					ln.eliminarAjuste(idAjuste);	
					cargarTabla();
					new LNProducto().actualizarValoresAjusteInventario();
					
				}else {
					SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
					java.util.Date date =calendarioUno.getDate();
					java.util.Date dateDos =calendarioDos.getDate();
					String fecha = formateador.format(date);
					String fechaDos = formateador.format(dateDos);
					ln.eliminarAjuste(idAjuste);	
					metodoBusqueda = 1;
					cargarTabla(fecha, fechaDos);
				}
			}else {
				lEstado.setText("Seleccione un dato de la tabla");
			}
		}


		if(botonExportarPDF == e.getSource()) {
			if(idAjuste != -1) {
				chooser.setDialogTitle("Guardar PDF en:");
				int userSelection = chooser.showSaveDialog(this);
				if(userSelection == JFileChooser.APPROVE_OPTION){
					AjusteInventario ai = ln.getAjusteInventario(idAjuste);
					ai.setDetalles(ln.getDetalles(idAjuste));
					new PDFReporte().crearPDFAjustes(chooser.getSelectedFile().toString(), ai.getDetalles(), ai);
				}
			}else {
				lEstado.setText("Seleccione un dato de la tabla");
			}
		}
		if(botonEliminar == e.getSource()) {
			if(fila!=-1) {
				
			}
		}
	}
}
