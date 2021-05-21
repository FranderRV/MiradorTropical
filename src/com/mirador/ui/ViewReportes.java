package com.mirador.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import com.mirador.ln.LNFacturas;
import com.mirador.ln.PDFReporte;
import com.mysql.fabric.xmlrpc.base.Array;
import com.toedter.calendar.JDateChooser;

public class ViewReportes  extends View implements ActionListener{
	private JPanel panelPrincipal, ppCentroP,ppIngresoPersonas,ppGananciaPersonas;
	private JComboBox<String> comboOpciones;
	private JTabbedPane contenedor;
	private JPanel panelVentas, panelEntradasV;
	private JDateChooser calendarioUno;
	private JDateChooser calendarioDos;
	private JButton botonBuscarPorFechas, botonExportarPDF;
	private JLabel labelIngresoTexto, labelIngresoNumero,labelGananciaTexto, labelGananciaNumero; 
	private LNFacturas ln;
	private int[] listaDatos;
	private JFileChooser chooser;
	private int metodoBusqueda;
	private String metodoBusquedaTexto;
	//DOS

	public ViewReportes() {
		super("Reportes",0,0);
		metodoBusqueda = 0;
		listaDatos = new int[0];
		metodoBusquedaTexto = "Día de hoy";
		chooser = new JFileChooser();
		chooser.setDialogType(0);
		this.ln = new LNFacturas();
		panelVentas = crearPanel(400, 500);
		panelVentas.setName("Ventas");
		
		panelEntradas();
		
		contenedor = new JTabbedPane();
		contenedor.add(panelEntradasV);
		//contenedor.add(panelVentas); 
		panelDatos.add(panelEntradasV);
		cargarInfo(0,"","");
		accionCombo();
		calendarioDos.setEnabled(false);
		calendarioUno.setEnabled(false);
		botonBuscarPorFechas.setEnabled(false);
	}
	public void accionCombo() {
		comboOpciones.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String tex = (String)comboOpciones.getSelectedItem();
	
				if(tex.equalsIgnoreCase("hoy")) {
					metodoBusqueda = 0;
					cargarInfo(0,"","");
					metodoBusquedaTexto = "Día de hoy";
					calendarioDos.setEnabled(false);
					calendarioUno.setEnabled(false);
					botonBuscarPorFechas.setEnabled(false);
				}
				if(tex.equalsIgnoreCase("Fecha específica")) {
					metodoBusqueda = 1;
					metodoBusquedaTexto = "Por fecha específica";
						calendarioUno.setEnabled(true);
						calendarioDos.setEnabled(false);
						botonBuscarPorFechas.setEnabled(true);	
				}
				if(tex.equalsIgnoreCase("Por fechas")) {
					metodoBusqueda = 2;
					calendarioDos.setEnabled(true);
					metodoBusquedaTexto = "Por fechas específicas";
					calendarioUno.setEnabled(true);
					botonBuscarPorFechas.setEnabled(true);
					cargarInfo(0,"","");
				}
			
			}
		});
	}
	public void cargarInfo(int caso,String fechaUno, String fechaDos) {
		int[] datos = ln.getDatosFactura(caso,fechaUno,fechaDos);
		listaDatos = datos;
		labelGananciaNumero.setText("\u20A1" +datos[1]+"");
		labelIngresoNumero.setText(datos[0]+"");

	}
	private void panelEntradas() {
		panelEntradasV  = crearPanel(0, 500);
		ppCentroP = crearPanel(0, 500);
		ppCentroP.setBackground(Color.cyan);
		ppCentroP.setLayout(new GridLayout());
		panelEntradasV.setName("Entradas");
		ppIngresoPersonas =  crearPanel(0, 50); 
		ppGananciaPersonas = crearPanel(0, 0);
		ppGananciaPersonas.setBackground(Color.blue);
		ppIngresoPersonas.setBackground(Color.green);
		
	
		comboOpciones = new JComboBox<String>();
		comboOpciones.addItem("Hoy");
		comboOpciones.addItem("Fecha específica");
		comboOpciones.addItem("Por fechas");
		
		
		comboOpciones.setFont(new Font("Segoe UI",0, 15));
		calendarioUno = new JDateChooser();
		calendarioUno.setDateFormatString("yyyy-MM-dd");
		calendarioUno.setFont(new Font("",0,20));
		calendarioUno.setDate(new Date());
		
		calendarioDos = new JDateChooser();
		calendarioDos.setDateFormatString("yyyy-MM-dd");
		calendarioDos.setFont(new Font("",0,20));
		calendarioDos.setDate(new Date());
		
		botonBuscarPorFechas = new JButton("Buscar");
		botonBuscarPorFechas.setBackground(new Color(2, 151, 0));
		botonBuscarPorFechas.setForeground(Color.WHITE);
		botonBuscarPorFechas.addActionListener(this);
		botonBuscarPorFechas.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 20));
		
		botonExportarPDF = new JButton("Exportar PDF");
		botonExportarPDF.setBackground(new Color(0, 148, 160));
		botonExportarPDF.setForeground(Color.WHITE);
		botonExportarPDF.addActionListener(this);
		botonExportarPDF.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 20));
		
		JPanel p = new JPanel();
		p.setBackground(Color.white);
		p.setLayout(new FlowLayout());
		p.setPreferredSize(new Dimension(200,120));
		p.add(entradaHorizontal(comboOpciones, "Fecha"));
		p.add(entradaHorizontal(calendarioUno, "Desde: "));
		p.add(entradaHorizontal(calendarioDos, "A: "));
		p.add(botonBuscarPorFechas);
		p.add(botonExportarPDF);
		//P_INGRESO
		 Border border = BorderFactory.createLineBorder(Color.white, 5);
		 Border border2 = BorderFactory.createMatteBorder(0, 0, 0, 2, Color.darkGray);
		labelIngresoTexto = new JLabel("Ingreso: ");
		labelIngresoTexto.setBorder(border);
		labelIngresoTexto.setFont(new Font("Segoe UI", 0, 45));
		labelIngresoTexto.setOpaque(true);
		labelIngresoTexto.setHorizontalAlignment(JLabel.CENTER);
		labelIngresoTexto.setBackground(new Color(0, 158, 129 ));
		labelIngresoTexto.setForeground(Color.white);
		//P_INGRESO
		labelIngresoNumero = new JLabel("0");
		labelIngresoNumero.setBorder(border2);
		labelIngresoNumero.setFont(new Font("Segoe UI", 0, 50));
		labelIngresoNumero.setOpaque(true);
		labelIngresoNumero.setHorizontalAlignment(JLabel.CENTER);
		labelIngresoNumero.setBackground(Color.white);
		
		//P_GANANCIA
		labelGananciaTexto = new JLabel("Ganancia:");
		labelGananciaTexto.setBorder(border);
		labelGananciaTexto.setFont(new Font("Segoe UI", 0, 45));
		labelGananciaTexto.setOpaque(true);
		labelGananciaTexto.setHorizontalAlignment(JLabel.CENTER);
		labelGananciaTexto.setBackground(new Color(0, 158, 129 ));
		labelGananciaTexto.setForeground(Color.white);
		//P_GANANCIA
		labelGananciaNumero = new JLabel("0");
		labelGananciaNumero.setBorder(border2);
		labelGananciaNumero.setFont(new Font("Segoe UI", 0, 50));
		labelGananciaNumero.setOpaque(true);
		labelGananciaNumero.setHorizontalAlignment(JLabel.CENTER);
		labelGananciaNumero.setBackground(Color.white);

		ppIngresoPersonas.add(labelIngresoTexto, BorderLayout.PAGE_START);
		ppIngresoPersonas.add(labelIngresoNumero,BorderLayout.CENTER);
		ppGananciaPersonas.add(labelGananciaTexto, BorderLayout.PAGE_START);
		ppGananciaPersonas.add(labelGananciaNumero,BorderLayout.CENTER);
		
		panelEntradasV.add(p, BorderLayout.PAGE_START);
		panelEntradasV.add(ppCentroP, BorderLayout.CENTER);
		
		
		ppCentroP.add(ppIngresoPersonas);
		ppCentroP.add(ppGananciaPersonas);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(botonBuscarPorFechas == e.getSource()) {
			
			try {
				SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date date =calendarioUno.getDate();
				String fecha = formateador.format(date);
				if(metodoBusqueda==1) {
					cargarInfo(metodoBusqueda,fecha,"");
				}else {
					SimpleDateFormat formateadorDos = new SimpleDateFormat("yyyy-MM-dd");
					java.util.Date dateDos =calendarioDos.getDate();
					String fechaDos = formateadorDos.format(dateDos);
					cargarInfo(metodoBusqueda,fecha, fechaDos);
				}
			} catch (Exception e2) {
			lEstado.setText("¡Insertar fecha!");
			}
		}
		if(botonExportarPDF == e.getSource()) {
			chooser.setSelectedFile(new File("Reporte entradas"));
			chooser.setDialogType(JFileChooser.SAVE_DIALOG);
			chooser.setDialogTitle("Guardar PDF en:"); 
			int userSelection = chooser.showSaveDialog(this);
		if(userSelection == JFileChooser.APPROVE_OPTION){
		SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date date =calendarioUno.getDate();
				String fecha = formateador.format(date);
				
				SimpleDateFormat formateadorDos = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date dateDos =calendarioDos.getDate();
				String fechaDos = formateadorDos.format(dateDos);
	
				
				if(fecha.isEmpty()&&fechaDos.isEmpty()) {
					fecha = "";
					fechaDos = "";
				}
				else if (fechaDos.isEmpty()) {
					fechaDos = "";
				}
				
				
				new PDFReporte().crearReporteEntradas(chooser.getSelectedFile().toString(),
						listaDatos, new String[] {fecha, fechaDos,metodoBusquedaTexto});
					
			}
		}
	}
 
}
