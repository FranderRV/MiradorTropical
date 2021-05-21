package com.mirador.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BrokenBarrierException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import com.mirador.ln.LNFacturas;
import com.mirador.ln.LNProducto;
import com.mirador.ln.PDFReporte;
import com.mirador.vo.Factura;
import com.toedter.calendar.JDateChooser;

public class ViewVentas  extends View implements ActionListener{ 
	private JPanel panelArriba, panelAbajo;
	private JButton botonActualizar, botonEliminar, botonBuscar, botonBuscarGeneral,botonExportarPDF;
	private JDateChooser calendarioUno, calendarioDos;
	private LNFacturas ln;
	private GUI g;
	private int idFactura;
	private int metodoBusqueda;
	private JFileChooser chooser;
	public ViewVentas(GUI g) {
		super("Ventas",0,0);
		this.g = g; 
		metodoBusqueda = 0;
		chooser = new JFileChooser();
	
		this.ln = new LNFacturas();
		idFactura = -1;
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

		botonBuscar = new JButton("Fechas");
		botonBuscar.setBackground(new Color(0, 148, 160));
		botonBuscar.setForeground(Color.WHITE);
		botonBuscar.addActionListener(this);
		botonBuscar.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 20));

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
		p2.add(botonBuscar);
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
				int dato =  tabla.getSelectedRow();	
				idFactura = Integer.parseInt(tabla.getValueAt(dato, 0).toString());
			}
		});
	}

	public void cargarTabla(String dateUno, String dateDos) {
		cargarTabla(ln.getDatos(ln.getFacturasPorFecha(dateUno, dateDos)), ln.columnas());
	}
	public void cargarTabla() {
		cargarTabla(ln.getDatos(ln.getFacturas()), ln.columnas());
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
		if(botonBuscar == e.getSource()) {

			SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date date =calendarioUno.getDate();
			java.util.Date dateDos =calendarioDos.getDate();
			String fecha = formateador.format(date);
			String fechaDos = formateador.format(dateDos);
			metodoBusqueda = 1;
			cargarTabla(fecha, fechaDos);
		}
		if(botonActualizar == e.getSource()) {
			g.cargarVista(new ViewVenta(idFactura,g));
		}
		if(botonEliminar == e.getSource()) { 
			if(idFactura!=-1) {
				try {
				 	if(metodoBusqueda ==0) {

						ln.eliminarFactura(idFactura);
						cargarTabla();
						new LNProducto().actualizarValores();
					}else {
						SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
						java.util.Date date =calendarioUno.getDate();
						java.util.Date dateDos =calendarioDos.getDate();
						String fecha = formateador.format(date);
						String fechaDos = formateador.format(dateDos);
						ln.eliminarFactura(idFactura);
						metodoBusqueda = 1;
						cargarTabla(fecha, fechaDos);
					}
				} catch (Exception e2) {
					lEstado.setText("No se pudo eliminar");
				}
			}
		}
		
		
		if(botonExportarPDF == e.getSource()) {
			if(idFactura!=-1) {
				chooser.setSelectedFile(new File("C:\\factura"+idFactura));
				chooser.setDialogType(JFileChooser.SAVE_DIALOG);
				chooser.setDialogTitle("Guardar PDF en:"); 
				int userSelection = chooser.showSaveDialog(this);
			if(userSelection == JFileChooser.APPROVE_OPTION){
				Factura f = new LNFacturas().getFactura(idFactura);
				f.setListaDetalles(new LNFacturas().getDetalles(idFactura));
				new PDFReporte().crearPDFVenta(chooser.getSelectedFile().toString(), 
						f.getListaDetalles(), f);
			}
			}else {
				lEstado.setText("Seleccione un dato de la tabla");
			}
		}
	}
}
