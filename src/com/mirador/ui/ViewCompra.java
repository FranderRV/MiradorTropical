package com.mirador.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.itextpdf.text.BaseColor;
import com.mirador.ln.LNCompras;
import com.mirador.ln.LNProducto;
import com.mirador.ln.LNProveedor;
import com.mirador.ln.PDFReporte;
import com.mirador.vo.Compra;
import com.mirador.vo.Detalles;
import com.mirador.vo.Factura;
import com.mirador.vo.ListaCompras;
import com.mirador.vo.Producto;
import com.mirador.vo.Proveedor;
import com.toedter.calendar.JDateChooser;

public class ViewCompra extends View  implements ActionListener{
	private LNProducto lnProducto;
	private LNProveedor lnProveedor;
	private LNCompras lnCompra;
	private JComboBox<Producto> comboProducto;
	private JComboBox<Proveedor> comboProveedores;
	private JDateChooser calendario;
	private JSpinner spinnerCantidad;
	private SpinnerNumberModel modeloSpinner;
	private JButton botonAgregarProductos, botonAgregarProveedores;
	private JButton botonAgregarDetalles, botonRemoverDetalles, botonCrearCompra
	, botonNuevaCompra, botonActualizarDetalles, botonNuevoDetalle, botonActualizarCompra,
	botonExportarPDF;
	private JLabel 	lMonto,lTotal;
	private JPanel panelBotonesRegistro,
	panelBotonesTabla,panelTabla,panelTotal,panelBotonesCompra;
	private int fila;
	private int idCompra;
	private ArrayList<ListaCompras> listaTemporal;
	private GUI g;
	private String nombre;
	private JFileChooser chooser;
	private JTextField tPrecio;
	public ViewCompra(int idCompra, GUI g,String nombre) {
		super("Compra",0);
		this.g = g;
		this.nombre = nombre;
		this.fila = -1;
		this.idCompra = idCompra;
		chooser = new JFileChooser();
		this.listaTemporal = new  ArrayList<ListaCompras>();
		this.lnProducto = new LNProducto();
		this.lnProveedor = new LNProveedor();
		this.lnCompra = new LNCompras();
		this.comboProducto = new JComboBox<Producto>();
		this.comboProveedores = new JComboBox<Proveedor>();
		comboProveedores.setFont(new Font("",0,20));
		this.calendario = new JDateChooser();
		this.modeloSpinner = new SpinnerNumberModel();
		modeloSpinner.setValue(1);
		modeloSpinner.setMinimum(1);
		this.spinnerCantidad = new JSpinner(modeloSpinner);

		tPrecio = new JTextField();
		tPrecio.setFont(new Font("Segoe UI", 0, 20));
	
		this.botonAgregarProductos = new JButton("+");
		this.botonAgregarProveedores = new JButton("+");
		botonAgregarProveedores.setForeground(Color.white);
		botonAgregarProveedores.setPreferredSize(new Dimension(45,45));
		botonAgregarProveedores.setBackground(new Color(2, 151, 0));
		botonAgregarProveedores.setFont(new Font("",Font.BOLD,12));
		botonAgregarProveedores.addActionListener(this);
		cargarComboProducto();
		cargarComboProveedor();
		AutoCompleteDecorator.decorate(comboProducto);
		//calendario
		calendario.setDateFormatString("yyyy-MM-dd");
		calendario.setFont(new Font("",0,20));
		calendario.setDate(new Date());
		//boton
		botonAgregarProductos.setForeground(Color.white);
		botonAgregarProductos.setPreferredSize(new Dimension(35,35));
		botonAgregarProductos.setBackground(new Color(2, 151, 0));
		botonAgregarProductos.setFont(new Font("",Font.BOLD,12));
		botonAgregarProductos.addActionListener(this);
		//combo
		comboProducto.setEditable(true);
		//PANELS
		panelBotonesRegistro =  crearPanel(0, 200);
		panelBotonesRegistro.setLayout(new GridLayout(0,2));
		
		cargarBotonesCompraProductos();
		modoBotonesVenta(1);
		cargarComponentesPanelTablaDetalles();
		/*PANEL ENTRADAS*/
		panelEntradas.add(entradaVertical(calendario, "Fecha"));
		panelEntradas.add(entradaVertical(new JSeparator(), ""));
		cargarTabla(null, new String[] {"Compra","Fecha"});
		JPanel  p = new JPanel();
		p.setLayout(new BorderLayout());
		p.add(new JLabel(),BorderLayout.CENTER);
		p.add(botonAgregarProductos, BorderLayout.PAGE_END);
		panelEntradas.add(entradaVertical(comboProducto, p,"Productos"));
		panelEntradas.add(entradaVertical(tPrecio, "Precio"));
		panelEntradas.add(entradaVertical(spinnerCantidad,"Cantidad"));
		panelBotones.add(entradaVertical(panelBotonesRegistro, ""));

		cargarTabla(null,lnCompra.getColumnasDetalles());
		cargarComboProducto();
		AutoCompleteDecorator.decorate(comboProveedores);
		detallesTabla();
		eventoTabla();
		preCarga();
		eventoCombo();
		cargarPrecioInicial();
	}
	public void eventoCombo() {
		
		comboProducto.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(comboProducto.getItemCount()>0) {
					Producto p = (Producto)comboProducto.getSelectedItem();
					tPrecio.setText(p.getPrecio()+"");
				}
			}
		});	
}
	public void setearPrecio(Producto p) {
		tPrecio.setText(p.getPrecio()+"");
	}
	public void preCarga() { 
		if(idCompra >-1) {

		Compra c = lnCompra.getCompra(idCompra);
		listaTemporal = lnCompra.getDetalles(idCompra);
		int fecha[] =lnCompra.vectorFecha( c.getFecha());
		
		calendario.setCalendar(new GregorianCalendar(fecha[0], fecha[1]-1, fecha[2]));
		cargarTabla(lnCompra.getDatosDetalles(listaTemporal),
				lnCompra.getColumnasDetalles());
		modoBotonesCompra(0);
		int posPro = new LNProveedor().getPosicionLista(nombre);

		comboProveedores.setSelectedIndex(posPro);
		lTotal.setText(lnCompra.suma(listaTemporal)+"");
		}
	}
	public void cargarBotonesCompraProductos() {
		botonAgregarDetalles = new JButton("+Agregar");
		botonAgregarDetalles.setBackground(new Color(2, 151, 0));
		botonAgregarDetalles.setForeground(Color.white);
		botonAgregarDetalles.addActionListener(this);
		botonAgregarDetalles.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 20));

		botonNuevoDetalle = new JButton("Nuevo");
		botonNuevoDetalle.setBackground(Color.white);
		botonNuevoDetalle.setForeground(Color.black);
		botonNuevoDetalle.addActionListener(this);
		botonNuevoDetalle.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 20));

		botonActualizarDetalles = new JButton("Actualizar");
		botonActualizarDetalles.setBackground(new Color(229, 122, 0));
		botonActualizarDetalles.setForeground(Color.white);
		botonActualizarDetalles.addActionListener(this);
		botonActualizarDetalles.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 20));

	}
	
	public void cargarComponentesPanelTablaDetalles() {
		
		panelBotonesTabla = crearPanel(0, 50);
		panelTabla = crearPanel(200, 500);
		panelTabla.add(scroll, BorderLayout.CENTER);
		panelTabla.add(panelBotonesTabla, BorderLayout.PAGE_END);
		panelTabla.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		panelTotal = crearPanel(200, 500, Color.darkGray);
		
		botonRemoverDetalles =new JButton("Remover producto");
		//BOTONES 
		
		
		panelBotonesTabla.add(botonRemoverDetalles);
		botonRemoverDetalles.setBackground(Color.RED);
		botonRemoverDetalles.setForeground(Color.white);
		botonRemoverDetalles.addActionListener(this);
		botonRemoverDetalles.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 20));
		
		panelDatos.setLayout(new GridLayout(0,2));
		panelDatos.add(panelTabla);
		
		//DATOS PANEL TOTAL
		
		//BOTONES
		botonActualizarCompra = new JButton("Actualizar");
		botonActualizarCompra.setBackground(new Color(229, 122, 0));
		botonActualizarCompra.setForeground(Color.white);
		botonActualizarCompra.addActionListener(this);
		botonActualizarCompra.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 20));

		botonNuevaCompra = new JButton("Nuevo");
		botonNuevaCompra.setBackground(Color.white);
		botonNuevaCompra.setForeground(Color.black);
		botonNuevaCompra.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 20));
		botonNuevaCompra.addActionListener(this);

		botonCrearCompra = new JButton("Comprar");
		botonCrearCompra.setBackground(new Color(2, 151, 0));
		botonCrearCompra.setForeground(Color.white);
		botonCrearCompra.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 20));
		botonCrearCompra.addActionListener(this);
		
		botonExportarPDF = new JButton("Exportar PDF");
		botonExportarPDF.setBackground(new Color(0, 148, 160));
		botonExportarPDF.setForeground(Color.WHITE);
		botonExportarPDF.addActionListener(this);
		botonExportarPDF.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 20));

		
		//PANELS
		JPanel pCentroTotal = new JPanel();
		pCentroTotal.setLayout(new GridLayout(2, 1));
		pCentroTotal.setBackground(Color.black);
		
		JPanel pCentroTotalDos = new JPanel();
		pCentroTotalDos.setLayout(new GridLayout(2, 1));

		
		JPanel p = new JPanel();
		//LABELS

		lMonto = new JLabel("Monto");
		lMonto.setBorder(new EmptyBorder(20, 10, 10, 10));
		lMonto.setFont(new Font("", 0, 50));
		lMonto.setOpaque(true);
		lMonto.setHorizontalAlignment(JLabel.CENTER);
		lMonto.setBackground(Color.white);
		
		lTotal = new JLabel("0");
		lTotal.setFont(new Font("Segoe UI", 0, 70));
		lTotal.setBackground(new Color(0, 158, 129 ));
		lTotal.setOpaque(true);
		lTotal.setHorizontalAlignment(JLabel.CENTER);
		lTotal .setForeground(Color.white);
		
		//PANEL SUB TOTOAL

		
		//COMBO PROVEEDOR
	
		p.setLayout(new FlowLayout());
		p.add(botonAgregarProveedores, BorderLayout.CENTER);
		panelBotonesCompra = crearPanel(0,100);
		panelBotonesCompra.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelBotonesCompra.setLayout(new GridLayout());
		
		
		modoBotonesCompra(1);
		
		//ADS

		pCentroTotal.add(lTotal);
		pCentroTotal.add(pCentroTotalDos);
		pCentroTotalDos.add(entradaVertical(comboProveedores, p,"Proveedor"));
		pCentroTotalDos.add(entradaVertical(new JSeparator(),"Realizar compra"));
		
		panelDatos.add(panelTotal);
		panelTotal.add(lMonto, BorderLayout.PAGE_START);
		panelTotal.add(pCentroTotal,BorderLayout.CENTER);
		panelTotal.add(panelBotonesCompra, BorderLayout.PAGE_END);
		
		
		
	}
	public void cargarPrecioInicial(){
if(lnProducto. getProductosComprasProveedor().size()!=0) {
	tPrecio.setText(lnProducto. getProductosComprasProveedor().get(0).getPrecio()+"");
}
	}
	
	public void modoBotonesCompra(int caso) {
		panelBotonesCompra.removeAll();
		if(caso == 1) {
			panelBotonesCompra.add(botonCrearCompra);
			panelBotonesCompra.add(botonNuevaCompra);
		}else {
			panelBotonesCompra.add(botonActualizarCompra);
			panelBotonesCompra.add(botonNuevaCompra);
			panelBotonesCompra.add(botonExportarPDF);
		}
		panelBotonesCompra.repaint();
		panelBotonesCompra.revalidate();
	}
	
	public void modoBotonesVenta(int caso) {
		panelBotonesRegistro.removeAll();
		if(caso == 1) {
			panelBotonesRegistro.add(botonAgregarDetalles);
			panelBotonesRegistro.add(botonNuevoDetalle);
		}else {
			panelBotonesRegistro.add(botonActualizarDetalles);
			panelBotonesRegistro.add(botonNuevoDetalle);
		}
		panelBotonesRegistro.repaint();
		panelBotonesRegistro.revalidate();
	}

	public void cargarComboProducto() {
		
		comboProducto.removeAllItems();
		for (Producto p : lnProducto. getProductosComprasProveedor()) {
			comboProducto.addItem(p);
		}
	}
	
	
	public void cargarComboProductoLast() {
		comboProducto.removeAllItems();
		for (Producto p : lnProducto.getProductos("")) {
			comboProducto.addItem(p);
		}
		int last = comboProducto.getItemCount()-1;
		comboProducto.setSelectedIndex(last);
	}

	public void cargarComboProveedor() {
		comboProveedores.removeAllItems();
		for (Proveedor p : lnProveedor.getProveedores("")) {
			comboProveedores.addItem(p);
		}
	}

	public void cargarComboProveedorLast() {
		comboProveedores.removeAllItems();
		for (Proveedor p : lnProveedor.getProveedores("")) {
			comboProveedores.addItem(p);
		}
		int last = comboProveedores.getItemCount()-1;
		comboProveedores.setSelectedIndex(last);
	}
	
	public void eventoTabla() {
		tabla.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				fila =  tabla.getSelectedRow();	
				spinnerCantidad.setValue( Integer.valueOf(tabla.getValueAt(fila, 2).toString()));
				tPrecio.setText(tabla.getValueAt(fila, 1).toString());
				int pos = new LNProducto().getPosicionListaCompras(tabla.getValueAt(fila, 0).toString());
				comboProducto.setSelectedIndex(pos);
				modoBotonesVenta(0);
			}
		});
	}
	
	public void detallesTabla() {

		tabla.setRowHeight(30);
		tabla.setFont(new Font("",0,15));
		tabla.getTableHeader().setPreferredSize(new java.awt.Dimension(500, 60));
		tabla.getTableHeader().setBackground(new Color(215, 137, 0));
		tabla.getTableHeader().setForeground(Color.black);
		tabla.getTableHeader().setFont(new Font("", 0, 15));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(botonAgregarProductos == e.getSource()) {
			new UIAuxiliar(this, new ViewInventario(),g).init();
		}
		if(botonAgregarProveedores == e.getSource()) {
			new UIAuxiliar(this, new ViewProveedores(),g).init();	
		}
		
		/*AGREGAR DETALLES*/
		
		if(botonAgregarDetalles == e.getSource()) {
			if(!tPrecio.getText().isEmpty()) {
			Producto p = (Producto) comboProducto.getSelectedItem();
			
			ListaCompras lc = new ListaCompras(p.getNombre(),
					-1, p.getIdProducto(), 
					Integer.parseInt(spinnerCantidad.getValue().toString()),
					Integer.parseInt(tPrecio.getText()));
			
			new ListaCompras().limpiarLista(listaTemporal, lc);
			
			cargarTabla(lnCompra.getDatosDetalles(listaTemporal),
					lnCompra.getColumnasDetalles());
			
			lTotal.setText(lnCompra.suma(listaTemporal)+"");
			modeloSpinner.setValue(1);
			modeloSpinner.setMinimum(1);
		}		else {
				lEstado.setText("Debe insertar mínimo una cantidad");
			}
		}
		
		if(botonNuevoDetalle == e.getSource()) {
			comboProducto.setSelectedIndex(0);
			calendario.setDate(new Date());
			modeloSpinner.setValue(1);
			modeloSpinner.setMinimum(1);
			modoBotonesVenta(1);
		}
		
		if(botonRemoverDetalles == e.getSource()) {
			if(fila!=-1) {
				listaTemporal.remove(fila);
				cargarTabla(lnCompra.getDatosDetalles(listaTemporal),
						lnCompra.getColumnasDetalles());
				fila = -1;
				lTotal.setText(lnCompra.suma(listaTemporal)+"");
				modoBotonesVenta(1);
			}else {
				lEstado.setText("Seleccione algún dato de la tabla");
			}
		}
		
		if(botonActualizarDetalles == e.getSource()) {
			Producto p = (Producto) comboProducto.getSelectedItem();
			
			listaTemporal.set(fila, new ListaCompras(p.getNombre(), 
					idCompra, p.getIdProducto(),
					Integer.parseInt(spinnerCantidad.getValue().toString()),
					Integer.parseInt(tPrecio.getText())));
			
			cargarTabla(lnCompra.getDatosDetalles(listaTemporal),
					lnCompra.getColumnasDetalles());
			
			modoBotonesVenta(1);
			lTotal.setText(lnCompra.suma(listaTemporal)+"");
		}
		
		/********************** CREAR COMPRA*****************************/
		if(botonCrearCompra == e.getSource()) {
			if(listaTemporal.size()!=0) {
				if(comboProveedores.getItemCount()!=0) {
					
				try {
				
					SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
					java.util.Date date =calendario.getDate();
					String fecha = formateador.format(date);
					Proveedor p = (Proveedor) comboProveedores.getSelectedItem();
					
				
					Compra c = new Compra(
							idCompra, 
							fecha, 
							p.getIdProveedor()
							);
					c.setDetalles(listaTemporal);		
					lnCompra.insertarCompra(c);		
					lnProducto.actualizarValores();
					lnProducto.actualizarValoresAjusteInventario();
					
					lEstado.setText("¡Compra realizada!");
					idCompra = lnCompra.getLastCompra();
					modoBotonesCompra(0);
				} catch (Exception e2) {
					lEstado.setText("¡No se ha registrado la compra!");
					e2.printStackTrace();
				}
			}else {
				lEstado.setText("¡Debe registrar algún proveedor!");
			}
			}else {
				lEstado.setText("¡Debe registrar mínimo un producto!");
			}		
			
		}
		if(botonActualizarCompra == e.getSource()) {
			if(listaTemporal.size()!=0) {
				try {
					SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
					java.util.Date date =calendario.getDate();
					String fecha = formateador.format(date);
										
					System.out.println("FECHA DE ACTUALIZAR: "+fecha);
					
					Proveedor p = (Proveedor) comboProveedores.getSelectedItem();
					Compra c = new Compra(idCompra, fecha, p.getIdProveedor());
					c.setDetalles(listaTemporal);	
					lnCompra.editarCompra(c);
					lnProducto.actualizarValores();
					lnProducto.actualizarValoresAjusteInventario();
			
					lEstado.setText("¡Compra actualizada!");
				} catch (Exception e2) {
					lEstado.setText("¡No se puede actualizar!");
				}
			}else {
				lEstado.setText("¡Debe registrar mínimo un producto!");
			}	
		}
		if(botonNuevaCompra == e.getSource()) {
			g.cargarVista(g.getVista("Compra"));
		}
		if(botonExportarPDF == e.getSource()) {
			chooser.setDialogTitle("Guardar PDF en:");
			int userSelection = chooser.showSaveDialog(this);
			if(userSelection == JFileChooser.APPROVE_OPTION){
				SimpleDateFormat formateador = new SimpleDateFormat("yyyy/MM/dd");
				java.util.Date date =calendario.getDate();
				String fecha = formateador.format(date);
				Proveedor p = (Proveedor) comboProveedores.getSelectedItem();	
				Compra c = new Compra(idCompra, fecha, p.getIdProveedor());
				
				new PDFReporte().crearPDFCompra(chooser.getSelectedFile().toString(), listaTemporal, c);
				
			}
		}
		
		
	}
}

