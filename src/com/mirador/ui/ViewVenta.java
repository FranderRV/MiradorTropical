package com.mirador.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PRTokeniser;
import com.itextpdf.text.pdf.PdfWriter;
import com.mirador.ln.LNFacturas;
import com.mirador.ln.LNProducto;
import com.mirador.ln.PDFReporte;
import com.mirador.vo.Detalles;
import com.mirador.vo.Factura;
import com.mirador.vo.Producto;
import com.toedter.calendar.JDateChooser;

public class ViewVenta extends View implements KeyListener, ActionListener{
	private JPanel panelTabla, panelTotal, panelBotonesTabla, panelBotonesCompra, 
	panelBotonesRegistro;

	private JTextField  tPago;
	private JComboBox<Producto> cbxCodigo;
	private JSpinner sCantidad;
	private JDateChooser calendario;
	private SpinnerNumberModel modeloSpinner;
	private JFileChooser chooser;
	private JButton botonAgregarDetalles, botonRemoverDetalles, botonCrearCompra
	, botonNuevaCompra, botonActualizarDetalles, botonNuevoDetalle, botonActualizarFactura,
	botonExportarPDF,botonAgregarProducto;
	private JLabel lMonto, lTotal,lVuelto;
	private LNProducto lnProducto;
	private LNFacturas lnFactura;
	private ArrayList<Detalles> listaTemporal;
	private ArrayList<Producto> listaProductosDos;
	private ArrayList<Producto> listaProductosCombo;
	private int fila;
	protected int idFactura;
	private int casoCombo;
	private int casoEdicion;
	private int tocoAct;
	private int valorAntiguo, idAntiguo, precioAntiguo, idProducto;
	private String nombreAntiguo;
	private boolean actualizacionFlag;
	private GUI g;
	private Producto productoTemp;
	private ArrayList<Detalles> arrayValores;
	public ViewVenta(int idFactura, GUI g) {
		super("Venta",0);
		fila = -1;
		this.g= g;
		casoCombo = 1;
		casoEdicion = 1;
		precioAntiguo = 0;
		this.actualizacionFlag = true;
		valorAntiguo = 0;
		nombreAntiguo = "";
		idAntiguo = 0;
		idProducto = 0;
		tocoAct = 0;
		arrayValores = new ArrayList<Detalles>();
		this.idFactura = idFactura;
		chooser = new JFileChooser();
		lnProducto = new LNProducto();


		listaTemporal = new ArrayList<Detalles>();
		listaProductosDos = lnProducto.getProductosVentas();
		listaProductosCombo = new ArrayList<Producto>();

		lnFactura = new LNFacturas();
		modeloSpinner = new SpinnerNumberModel();
		modeloSpinner.setValue(1);
		modeloSpinner.setMinimum(1);
		cbxCodigo = new JComboBox<Producto>();
		sCantidad = new JSpinner(modeloSpinner);

		calendario = new JDateChooser();
		calendario.setDateFormatString("yyyy-MM-dd");
		calendario.setFont(new Font("",0,20));
		Date d = new Date();
		calendario.setDate(d);
		panelBotonesRegistro =  crearPanel(0, 200);
		panelBotonesRegistro.setLayout(new GridLayout(0,2));

		panelBotonesTabla = crearPanel(0, 50);
		panelTabla = crearPanel(200, 500);
		panelTabla.add(scroll, BorderLayout.CENTER);
		panelTabla.add(panelBotonesTabla, BorderLayout.PAGE_END);
		panelTabla.setBorder(new EmptyBorder(10, 10, 10, 10));

		panelTotal = crearPanel(200, 500);

		panelDatos.setLayout(new GridLayout(0,2));
		panelDatos.add(panelTabla);
		panelDatos.add(panelTotal);


		panelEntradas.add(entradaVertical(calendario, "Fecha"));
		panelEntradas.add(entradaVertical(new JSeparator(), ""));

		panelEntradas.add(entradaVertical(cbxCodigo,"Código"));
		panelEntradas.add(entradaVertical(sCantidad, "Cantidad"));
		panelBotones.add(entradaVertical(panelBotonesRegistro, ""));

		/*DETALLES*/
		botonAgregarDetalles = new JButton("+Agregar");
		botonAgregarDetalles.setBackground(new Color(2, 151, 0));
		botonAgregarDetalles.setForeground(Color.white);
		botonAgregarDetalles.addActionListener(this);
		botonAgregarDetalles.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 20));

		botonRemoverDetalles =new JButton("Remover producto");

		panelBotonesTabla.add(botonRemoverDetalles);
		botonRemoverDetalles.setBackground(Color.RED);
		botonRemoverDetalles.setForeground(Color.white);
		botonRemoverDetalles.addActionListener(this);
		botonRemoverDetalles.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 20));
		panelBotones.add(botonAgregarDetalles);

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


		modoBotonesVenta(1);

		/*PANEL TOTAL*/
		botonExportarPDF = new JButton("Exportar PDF");
		botonExportarPDF.setBackground(new Color(0, 148, 160));
		botonExportarPDF.setForeground(Color.WHITE);
		botonExportarPDF.addActionListener(this);
		botonExportarPDF.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 20));

		botonActualizarFactura = new JButton("Actualizar");
		botonActualizarFactura.setBackground(new Color(229, 122, 0));
		botonActualizarFactura.setForeground(Color.white);
		botonActualizarFactura.addActionListener(this);
		botonActualizarFactura.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 20));



		botonNuevaCompra = new JButton("Nuevo");
		botonNuevaCompra.setBackground(Color.white);
		botonNuevaCompra.setForeground(Color.black);
		botonNuevaCompra.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 20));
		botonNuevaCompra.addActionListener(this);

		botonCrearCompra = new JButton("Vender");
		botonCrearCompra.setBackground(new Color(2, 151, 0));
		botonCrearCompra.setForeground(Color.white);
		botonCrearCompra.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 20));
		botonCrearCompra.addActionListener(this);


		lMonto = new JLabel("Monto");
		lMonto.setBorder(new EmptyBorder(20, 10, 10, 10));
		lMonto.setFont(new Font("", 0, 50));
		lMonto.setOpaque(true);
		lMonto.setHorizontalAlignment(JLabel.CENTER);
		lMonto.setBackground(Color.white);


		tPago = new JTextField();
		tPago.setFont(new Font("Segoe UI", 0, 30));
		tPago.addKeyListener(this);

		lVuelto = new JLabel("\u20A1" +"0");
		lVuelto .setFont(new Font("Segoe UI", 0, 30));
		lVuelto .setHorizontalAlignment(JLabel.CENTER);
		lVuelto.setBackground(Color.black);
		lVuelto.setOpaque(true);
		lVuelto.setForeground(Color.white);

		lTotal = new JLabel("\u20A1" +"0");
		lTotal.setFont(new Font("Segoe UI", 0, 60));
		lTotal.setHorizontalAlignment(JLabel.CENTER);
		lTotal .setForeground(Color.white);
		lTotal.setBackground(Color.black);
		lTotal.setOpaque(true);

		JPanel pCentroTotal = new JPanel();
		pCentroTotal.setLayout(new GridLayout(2, 1));
		//pCentroTotal.setBackground(Color.black);
		pCentroTotal.add(lTotal);

		JPanel pCentroTotalDos = crearPanel(280, 200);
		pCentroTotalDos.setLayout(new FlowLayout());
		pCentroTotalDos.add(new JSeparator());
		pCentroTotalDos.add(entradaVertical(tPago, "Pago con:",280,90));
		pCentroTotalDos.add(entradaVertical(lVuelto, "Vuelto:",280,90));
		JScrollPane scroll = new JScrollPane(pCentroTotalDos);
		pCentroTotal.add(scroll);

		panelBotonesCompra = crearPanel(0,100);
		panelBotonesCompra.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelBotonesCompra.setLayout(new GridLayout());

		modoBotonesCompra(1);
		panelTotal.add(lMonto, BorderLayout.PAGE_START);
		panelTotal.add(pCentroTotal,BorderLayout.CENTER);
		panelTotal.add(panelBotonesCompra, BorderLayout.PAGE_END);



		cargarTabla(null, lnFactura.columnasDetalles());
		cargarComboProducto();
		AutoCompleteDecorator.decorate(cbxCodigo);
		eventoTabla();
		accionCombo();
		preCargar(idFactura);
		cargarTemporal();
		detallesTabla();

	}
	public void cargarTemporal() {

		if( lnProducto.getProductosComboVentas(listaTemporal).size()!=0) {
			productoTemp = listaProductosDos.get(0);
			
			if(productoTemp.getCategoria().getIdCategoriaComida()==1) {
				modeloSpinner.setValue(1);
				modeloSpinner.setMaximum(productoTemp.getCantidad());
				System.err.println(productoTemp.getCantidad());
			}else {
				modeloSpinner.setValue(1);
				//modeloSpinner.setMaximum(productoTemp.getCantidad());  
				System.out.println("otro");
			}
		} else {
			productoTemp = null;

		}
	}
	public void preCargar(int caso) {
		if(caso!=-1) {
			Factura  f = lnFactura.getFactura(caso);
			int fecha[] = lnFactura.vectorFecha(f.getFecha());

			calendario.setCalendar(new GregorianCalendar(fecha[0], fecha[1]-1, fecha[2]));
			listaTemporal = lnFactura.getDetalles(caso);
					
			//preCargarLista();
			cargarTabla(lnFactura.getDatosDetalles(listaTemporal),
					lnFactura.columnasDetalles());		
			modoBotonesCompra(0);
			lTotal.setText("\u20A1" +lnFactura.suma(listaTemporal)+"");
			cargarComboProducto();
		}
	}
	
	public void preCargarLista() {
		for (Detalles producto : listaTemporal) {
			lnProducto.actualizarProductoCantidad(producto.getCantidad(), producto.getIdProducto());
		}
	}
	public void modoBotonesCompra(int caso) {
		panelBotonesCompra.removeAll();
		if(caso == 1) {
			panelBotonesCompra.add(botonCrearCompra);
			panelBotonesCompra.add(botonNuevaCompra);
		}else {
			panelBotonesCompra.add(botonActualizarFactura);
			panelBotonesCompra.add(botonNuevaCompra);
			panelBotonesCompra.add(botonExportarPDF);
		}
		panelBotonesCompra.repaint();
		panelBotonesCompra.revalidate();
	}
	public void actualizarEstadoSpinner(Producto p) {
		if(lnProducto.getProductos("").size()!=0&&cbxCodigo.getItemCount()>0) {
			if(productoTemp.getCategoria().getIdCategoriaComida()==1) {
				//cargarComboProducto();
				modeloSpinner.setMaximum(p.getCantidad());
			}else {
				cbxCodigo.addItem(null);
			}
		}
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
		cbxCodigo.removeAllItems(); 
		listaProductosCombo.removeAll(listaProductosCombo);
		for (Producto p : lnProducto.getProductosComboVentas(listaTemporal)) { 
			if(p.getCategoria().getIdCategoriaComida()==1&&p.getCantidad()>0) { 
				cbxCodigo.addItem(p);
				listaProductosCombo.add(p);
			}
			else if(p.getCategoria().getIdCategoriaComida()!=1) {
				cbxCodigo.addItem(p);
				modeloSpinner.setMaximum(100);
				listaProductosCombo.add(p);
			}

		}

		cbxCodigo.repaint();
		cbxCodigo.revalidate();
	}
	public void accionCombo() {

		cbxCodigo.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				//if(fila==-1) { // CASO NUEVO
					if(lnProducto.getProductosVentas().size()!=0&&cbxCodigo.getItemCount()>0) {
						productoTemp = (Producto) cbxCodigo.getSelectedItem();
					 	//actualizarEstadoSpinner(productoTemp);
						if(productoTemp.getCategoria().getIdCategoriaComida()==1) {
							if(fila<0) {
								modeloSpinner.setValue(1);
								modeloSpinner.setMaximum(productoTemp.getCantidad()); 
								casoCombo = 1;
							}
						}
						else {
							modeloSpinner.setValue(1);
							modeloSpinner.setMaximum(200); 
							casoCombo = 1;
						}
					}
				//}
				
			
			
			}
		});
	}
	public void detallesTabla() {

		tabla.getColumnModel().getColumn(4).setMaxWidth(0);
		tabla.getColumnModel().getColumn(4).setMinWidth(0);
		tabla.getColumnModel().getColumn(4).setPreferredWidth(0);
		tabla.getColumnModel().getColumn(4).setResizable(false);	 
		tabla.setRowHeight(30);
		tabla.setFont(new Font("",0,15));
		tabla.getTableHeader().setPreferredSize(new java.awt.Dimension(500, 60));
		tabla.getTableHeader().setBackground(new Color(215, 137, 0));
		tabla.getTableHeader().setForeground(Color.black);
		tabla.getTableHeader().setFont(new Font("", 0, 15));
	}

	public void limpiar(int caso) {
		if(caso == 1) {
			sCantidad.setValue(1);
			cbxCodigo.setSelectedIndex(0);
			fila  = -1;
		}else {
			Date d = new Date();
			//calendario.setDate(d);
			sCantidad.setValue(1);
			cbxCodigo.setSelectedIndex(0);
			fila  = -1;
		}
	}
	public void labelsPrecios() {
		if(!tPago.getText().isEmpty()) {
			int a = Integer.parseInt(lTotal.getText().substring(1, lTotal.getText().length()));
			System.out.println("A: "+a+" RES: "+tPago.getText());
			int res = Integer.parseInt(tPago.getText())-a;
			if(res>0) {
				lVuelto.setForeground(Color.GREEN);
				lVuelto.setText("\u20A1" +res+"");	
			}else {
				lVuelto.setForeground(Color.RED);
				lVuelto.setText("\u20A1" +res+"");	
			}

		}else {
			lVuelto.setText("\u20A1" +0+"");
			lVuelto.setForeground(Color.white);
		}
	}
	public void eventoTabla() {
		tabla.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(idFactura==-1) {
					
				
				casoCombo = 0; //NO pone 0 de mínimo
				
				
				//new Detalles(idProducto, idFactura, cantidad, precio, descripcion)
				if(!actualizacionFlag) {
					listaTemporal.set(fila, new Detalles(productoTemp.getIdProducto(), -1,
							valorAntiguo,
							productoTemp.getPrecio(), productoTemp.getNombre()));
				}
				
				actualizacionFlag = false;
				fila =  tabla.getSelectedRow();	
				
				idProducto = 	1;
				
				valorAntiguo = Integer.valueOf(tabla.getValueAt(fila,2).toString());
				nombreAntiguo = tabla.getValueAt(fila, 1).toString();
				idAntiguo = 	Integer.parseInt(tabla.getValueAt(fila, 4).toString());
				
				productoTemp = (Producto) lnProducto.getProducto(Integer.parseInt(tabla.getValueAt(fila, 4).toString()));
			 
		 
				listaTemporal.set(fila, new Detalles(productoTemp.getIdProducto(), -1,
							0,
							productoTemp.getPrecio(), productoTemp.getNombre()));
				
				 
				cargarComboProducto();
				
				int pos = new LNProducto().getPosicionListaVentas(tabla.getValueAt(fila, 0).toString(),listaProductosCombo);
		
				cbxCodigo.setSelectedIndex(pos);
	
			 if(productoTemp.getCategoria().getIdCategoriaComida()==1) {
					modeloSpinner.setValue( Integer.valueOf(tabla.getValueAt(fila, 2).toString()));
					modeloSpinner.setMaximum(lnProducto.getProducto(Integer.parseInt(tabla.getValueAt(fila, 4).toString())).getCantidad()); 
				
			 }else { 
					modeloSpinner.setValue( Integer.valueOf(tabla.getValueAt(fila, 2).toString()));
					//modeloSpinner.setMaximum(lnProducto.getProducto(Integer.parseInt(tabla.getValueAt(fila, 4).toString())).getCantidad()); 
			
			 }
			modoBotonesVenta(0);
	 			casoEdicion = -1;
			}
				//CASO ACTUALIZAR
				
				if(idFactura !=-1) {
					
					cbxCodigo.removeAllItems();
					listaProductosCombo = new ArrayList<Producto>();
				
					for (Producto p : lnProducto.getProductosComboVentas(listaTemporal)) { 
							cbxCodigo.addItem(p);
							listaProductosCombo.add(p);
					}
				
					fila =  tabla.getSelectedRow();	
					
					productoTemp = (Producto) lnProducto.getProducto(Integer.parseInt(tabla.getValueAt(fila, 4).toString()));
					
					
					if(productoTemp.getCantidad()>Integer.valueOf(tabla.getValueAt(fila,2).toString())) {
						
						listaTemporal.set(fila, new Detalles(productoTemp.getIdProducto(), idFactura,
								Integer.valueOf(tabla.getValueAt(fila,2).toString()),
								productoTemp.getPrecio(), productoTemp.getNombre()));
						
						
						 if(productoTemp.getCategoria().getIdCategoriaComida()==1) {
								modeloSpinner.setValue( Integer.valueOf(tabla.getValueAt(fila, 2).toString()));
								modeloSpinner.setMaximum(productoTemp.getCantidad()); 				
						 }else { 
								modeloSpinner.setValue( Integer.valueOf(tabla.getValueAt(fila, 2).toString()));				
						 }
						
					}
					
					if(productoTemp.getCantidad()<Integer.valueOf(tabla.getValueAt(fila,2).toString())) {
						
						int valor= 0;
						
						if(!arrayValores.isEmpty()) {
							
							boolean prueba = false;
							int posi = 0;
							
							for(int i = 0; i<arrayValores.size(); i++) {
								
								if (arrayValores.get(i).getIdProducto() == Integer.parseInt(tabla.getValueAt(fila, 4).toString())) {
									prueba = true;
									posi = i;
									break;
								}
								
							}
							
							if(prueba) {
								
								valor = arrayValores.get(posi).getCantidad();
								
							}else {
								
								arrayValores.add(new Detalles(productoTemp.getIdProducto(), idFactura,
										Integer.valueOf(tabla.getValueAt(fila,2).toString()),
										productoTemp.getPrecio(), productoTemp.getNombre()));
								
								valor = Integer.valueOf(tabla.getValueAt(fila,2).toString());
							}
							
						}else {
							
							arrayValores.add(new Detalles(productoTemp.getIdProducto(), idFactura,
									Integer.valueOf(tabla.getValueAt(fila,2).toString()),
									productoTemp.getPrecio(), productoTemp.getNombre()));
							
							valor = Integer.valueOf(tabla.getValueAt(fila,2).toString());
						}
						
						listaTemporal.set(fila, new Detalles(productoTemp.getIdProducto(), idFactura,
								Integer.valueOf(tabla.getValueAt(fila,2).toString()),
								productoTemp.getPrecio(), productoTemp.getNombre()));
						
						 if(productoTemp.getCategoria().getIdCategoriaComida()==1) {
								modeloSpinner.setValue( Integer.valueOf(tabla.getValueAt(fila, 2).toString()));
								
								int formula = productoTemp.getCantidad()+Integer.valueOf(tabla.getValueAt(fila,2).toString())-(Integer.valueOf(tabla.getValueAt(fila,2).toString())-valor);
								modeloSpinner.setMaximum(formula); 				
						 }else { 
								modeloSpinner.setValue( Integer.valueOf(tabla.getValueAt(fila, 2).toString()));				
						 }
						 
					
					}
					
					if(productoTemp.getCantidad()==Integer.valueOf(tabla.getValueAt(fila,2).toString())) {
						
						listaTemporal.set(fila, new Detalles(productoTemp.getIdProducto(), idFactura,
								Integer.valueOf(tabla.getValueAt(fila,2).toString()),
								productoTemp.getPrecio(), productoTemp.getNombre()));
						
						 if(productoTemp.getCategoria().getIdCategoriaComida()==1) {
								modeloSpinner.setValue( Integer.valueOf(tabla.getValueAt(fila, 2).toString()));
								modeloSpinner.setMaximum(Integer.valueOf(tabla.getValueAt(fila,2).toString())+productoTemp.getCantidad()); 				
						 }else { 
								modeloSpinner.setValue( Integer.valueOf(tabla.getValueAt(fila, 2).toString()));				
						 }
					}
					

					int pos = new LNProducto().getPosicionListaVentas(tabla.getValueAt(fila, 0).toString(),listaProductosCombo);
					cbxCodigo.setSelectedIndex(pos);
					modoBotonesVenta(0);
					
					/*for (Producto p : lnProducto.getProductosComboVentas(listaTemporal)) { 
						cbxCodigo.addItem(p);
						listaProductosCombo.add(p);
				}*/
				}
			}
		});
	}

	public void casoActualizar() {
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {

		if(botonActualizarDetalles == e.getSource()) {
			casoCombo =1;
			 
			Producto p = (Producto) cbxCodigo.getSelectedItem();

			double decimal = Double.parseDouble("1."+productoTemp.getGanancia());
			
			listaTemporal.set(fila, new Detalles(p.getIdProducto(),
					-1, Integer.parseInt(sCantidad.getValue().toString()),
					(int)(p.getPrecio()*decimal), p.getNombre()));


			
			modoBotonesVenta(1);
			lTotal.setText("\u20A1" +lnFactura.suma(listaTemporal)+"");
			labelsPrecios();
			productoTemp.setCantidad(new LNProducto().getProducto(productoTemp.getIdProducto()).getCantidad()-Integer.parseInt(sCantidad.getValue().toString()));
			limpiar(1); 
			
			cargarTabla(lnFactura.getDatosDetalles(listaTemporal), lnFactura.columnasDetalles());
			fila = -1;
			cargarComboProducto();
			actualizacionFlag = true;
		}
		if(botonNuevoDetalle == e.getSource()) {
			try {
				limpiar(1);
				if(fila!=-1) {
					Producto p = (Producto) cbxCodigo.getSelectedItem();

					double decimal = Double.parseDouble("1."+productoTemp.getGanancia());
 
						listaTemporal.set(fila, new Detalles(idAntiguo,
							-1, valorAntiguo,
							(int)(p.getPrecio()*decimal), nombreAntiguo));

						productoTemp.setCantidad(p.getCantidad()-Integer.parseInt(sCantidad.getValue().toString()));

				}
				fila = -1;
				cargarComboProducto();
				modoBotonesVenta(1);
				//casoEdicion = 1; 
				lEstado.setText("Notificaciones");
				actualizacionFlag = true;
			} catch (Exception e2) {
				lEstado.setText("No hay productos disponibles");
			}
		}
		if(botonAgregarDetalles == e.getSource()) { 
			try {
				casoEdicion = 1;
				Producto p = (Producto) cbxCodigo.getSelectedItem();

				productoTemp.setCantidad(p.getCantidad()-Integer.parseInt(sCantidad.getValue().toString()));


				double decimal = Double.parseDouble("1."+p.getGanancia());

				Detalles d = new Detalles(p.getIdProducto(), 0,
						Integer.parseInt(sCantidad.getValue().toString())
						,(int) (p.getPrecio()*decimal),p.getNombre());


				new Factura().limpiarLista(listaTemporal, d);//añadir DETALLE A lista

				cargarTabla(lnFactura.getDatosDetalles(listaTemporal), lnFactura.columnasDetalles());
				limpiar(0);
				lTotal.setText("\u20A1" +lnFactura.suma(listaTemporal)+"");
				labelsPrecios();
				cargarComboProducto();


				lEstado.setText("Notificaciones");
				actualizacionFlag = true;
			} catch (Exception e2) {
				lEstado.setText("No hay productos disponibles");
			}

		}

		if(botonRemoverDetalles == e.getSource()) {
			if(fila!=-1) {
				listaTemporal.remove(fila);
				cargarTabla(lnFactura.getDatosDetalles(listaTemporal), lnFactura.columnasDetalles());
				fila = -1;
				lTotal.setText("\u20A1" +lnFactura.suma(listaTemporal)+"");
				labelsPrecios();
				modoBotonesVenta(1);
				modeloSpinner.setValue(1);
				cargarComboProducto();
				cargarTemporal();
				actualizacionFlag = true;
				accionCombo();
			}else {
				lEstado.setText("Seleccione algún dato de la tabla");
			}
		}

		/*ACCIONES DE REGISTRO PANEL 3*/

		if(botonNuevaCompra == e.getSource()) {
			g.cargarVista(g.getVista("Venta"));
		}

		if(botonCrearCompra == e.getSource()) {	
			if(listaTemporal.size()!=0) {
				try {
					SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
					java.util.Date date =calendario.getDate();
					String fecha = formateador.format(date);
					Factura f = new Factura(idFactura, "", fecha, "");
					f.setListaDetalles(listaTemporal);
					lnFactura.insertar(f);
					idFactura = lnFactura.getLastFactura();
			
					modoBotonesCompra(0);
					lEstado.setText("¡Compra registrada!");
					lnProducto.actualizarValores();
				} catch (Exception e2) {
					lEstado.setText("¡No se ha registrado la compra!");
				}
			}else {
				lEstado.setText("¡Debe registrar mínimo un producto!");
			}		

		}
		if(botonActualizarFactura == e.getSource()) {
			if(listaTemporal.size()!=0) {
				try {
					SimpleDateFormat formateador = new SimpleDateFormat("yyyy/MM/dd");
					java.util.Date date =calendario.getDate();
					String fecha = formateador.format(date);
					Factura f = new Factura(idFactura, "", fecha, "");

					f.setListaDetalles(listaTemporal);
					lnFactura.actualizarFactura(f);
					lnProducto.actualizarValores();
					lEstado.setText("¡Factura actualizada!");
				} catch (Exception e2) {
					lEstado.setText("¡No se puede actualizar!");
				}
			}else {
				lEstado.setText("¡Debe registrar mínimo un producto!");
			}		
		}

		if(botonExportarPDF == e.getSource()) {
			chooser.setSelectedFile(new File("C:\\factura"+idFactura));
			chooser.setDialogType(JFileChooser.SAVE_DIALOG);
			chooser.setDialogTitle("Guardar PDF en:"); 
			int userSelection = chooser.showSaveDialog(this);
		if(userSelection == JFileChooser.APPROVE_OPTION){
			SimpleDateFormat formateador = new SimpleDateFormat("yyyy/MM/dd");
				java.util.Date date =calendario.getDate();
				String fecha = formateador.format(date);
				Factura f = new Factura(idFactura, "", fecha, "");

				new PDFReporte().crearPDFVenta(chooser.getSelectedFile().toString(), listaTemporal, f);

			}
		}

		if(botonAgregarProducto == e.getSource()) {
			new UIAuxiliar(this, new ViewInventario(),g).init();
		}
		detallesTabla();
	}//CIERRE ACTIONPERM


	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	@Override
	public void keyPressed(KeyEvent e) {

	}
	@Override
	public void keyReleased(KeyEvent e) {
		try {
			if( e.getSource() == tPago&&!lTotal.getText().equalsIgnoreCase("\u20A1" +"0")) {
				labelsPrecios();
				lEstado.setText("Notificaciones");
			}		
		} catch (Exception e2) {
			lEstado.setText("Insertar sólo números");
			//JOptionPane.showMessageDialog(null,"Supera el limite establecido"));
		}

	}
}
