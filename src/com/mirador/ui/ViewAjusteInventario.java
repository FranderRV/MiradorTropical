package com.mirador.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.mirador.ln.LNAjuste;
import com.mirador.ln.LNProducto;
import com.mirador.ln.PDFReporte;
import com.mirador.vo.AjusteInventario;
import com.mirador.vo.Compra;
import com.mirador.vo.Detalles;
import com.mirador.vo.ListaAjustesInventario;
import com.mirador.vo.Producto;
import com.mirador.vo.Proveedor;
import com.toedter.calendar.JDateChooser;

public class ViewAjusteInventario extends View  implements ActionListener{
	private JSpinner spinnerCantidad;
	private SpinnerNumberModel modeloSpinner;
	private JComboBox<Producto> comboProductos;
	private JDateChooser calendario;
	private JButton crearAjuste;
	private JTextField tPrecio;
	private JFileChooser chooser;
	private JButton botonAgregarDetalles, 
	botonRemoverDetalles,botonNuevoDetalle
	,botonActualizarDetalles,botonActualizarCompra,
	botonNuevaAjuste,botonCrearAjuste,botonExportarPDF;
	private LNAjuste ln;
	private int idAjuste;
	private int fila;
	private GUI g;
	private JPanel panelBotonesRegistro, panelBotonesCrearAjuste;
	private ArrayList<ListaAjustesInventario> listaTemporal;
	private ArrayList<Producto> listaProductosDos, listaProductosCombo;
	private ArrayList<ListaAjustesInventario> arrayValores;
	private Producto productoTemp;
	private int valorAntiguo, idAntiguo, precioAntiguo;
	private String nombreAntiguo;
	private boolean actualizacionFlag;
	public ViewAjusteInventario (GUI g,int idAjuste) {
		super("Ajuste Inventario",0);
		this.g = g;
		this.idAjuste = idAjuste;
		this.fila = -1;
		this.actualizacionFlag = true;
		this.listaTemporal = new ArrayList<ListaAjustesInventario>();
		listaProductosDos = new LNProducto().getProductosAjustes();
		listaProductosCombo = new ArrayList<Producto>();
		valorAntiguo = 0;
		nombreAntiguo = "";
		idAntiguo = 0;
		precioAntiguo = 0;
		arrayValores = new ArrayList<ListaAjustesInventario>();
		this.ln = new LNAjuste();
		calendario = new JDateChooser(new Date());
		calendario.setDateFormatString("yyyy-MM-dd");
		calendario.setFont(new Font("",0,20));
		modeloSpinner = new SpinnerNumberModel();
		modeloSpinner.setValue(1);
		modeloSpinner.setMinimum(1);
		spinnerCantidad = new JSpinner(modeloSpinner);
		comboProductos = new JComboBox<Producto>();
		AutoCompleteDecorator.decorate(comboProductos);
		chooser = new JFileChooser();

		tPrecio = new JTextField();
		tPrecio.setFont(new Font("Segoe UI", 0, 20));

		//PANELS
		panelBotonesRegistro =  crearPanel(0, 200);
		panelBotonesRegistro.setLayout(new GridLayout(1,0));
		panelBotonesCrearAjuste = crearPanel(0, 60);
		panelBotonesCrearAjuste.setLayout(new GridLayout(1,0));

		botones();
		modoBotonesCrearAjuste(1);
		modoBotonesRegistro(1);
		panelEntradas.add(entradaVertical(calendario, "Fecha"));
		panelEntradas.add(entradaVertical(new JLabel(), ""));
		panelEntradas.add(entradaVertical(new JSeparator(), "Añadir ajustes"));
		panelEntradas.add(entradaVertical(comboProductos, "Productos"));
		panelEntradas.add(entradaVertical(tPrecio, "Precio"));
		panelEntradas.add(entradaVertical(spinnerCantidad, "Cantidad"));
		panelEntradas.add(entradaVertical(panelBotonesRegistro,""));


		cargarTabla(null, ln.getColumnasDetalles());
		panelDatos.add(scroll,BorderLayout.CENTER);
		panelDatos.add(panelBotonesCrearAjuste,BorderLayout.PAGE_END);

		detallesTabla();
		cargarComboProducto();
		eventoTabla();
		preCargar();
		eventoCombo();
		cargarTemporal();
	}
	public void cargarTemporal() {

		if( new LNProducto().getProductosComboAjustes(listaTemporal).size()!=0) {
			productoTemp = listaProductosDos.get(0);
			System.out.println("AJUSTE: "+productoTemp.impre());
			tPrecio.setText(productoTemp.getPrecio()+"");
			modeloSpinner.setValue(1);
			Producto p = (Producto) comboProductos.getSelectedItem();
			modeloSpinner.setMaximum(p.getCantidad());  
		}else {
			productoTemp = null;
		}
	}
	public void eventoCombo() {

		comboProductos.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(comboProductos.getItemCount()>0) {
					productoTemp  = (Producto)comboProductos.getSelectedItem();
					tPrecio.setText(productoTemp.getPrecio()+"");
					if(fila<0) {
						modeloSpinner.setValue(1);
					}
					modeloSpinner.setMaximum(productoTemp.getCantidad()); 
					System.out.println("TOQUE: "+productoTemp.getCantidad());
				}
			}
		});
	}
	public void preCargar() {
		if(idAjuste!=-1) {
			modoBotonesCrearAjuste(0);
			listaTemporal = ln.getDetalles(idAjuste); 
			
			int fecha[] = new LNAjuste().vectorFecha(new LNAjuste().getAjusteInventario(idAjuste).getFecha());
			calendario.setCalendar(new GregorianCalendar(fecha[0], fecha[1]-1, fecha[2]));
			cargarTabla(ln.getDatosDetalles(listaTemporal), ln.getColumnasDetalles());
			detallesTabla();
		}
	}
	public void cargarComboProducto() {
	 	comboProductos.removeAllItems();
	 	listaProductosCombo.removeAll(listaProductosCombo);
		for (Producto p : new LNProducto().getProductosComboAjustes(listaTemporal)) {
			if(p.getCantidad()>0) {
				System.out.println(p.impre());
		 		comboProductos.addItem(p);
		 		listaProductosCombo.add(p);
				//modeloSpinner.setMinimum(1); 
				//modeloSpinner.setMaximum(p.getCantidad());
			}
		}
		comboProductos.repaint();
		comboProductos.revalidate();
	}


	public void detallesTabla() {
		tabla.getColumnModel().getColumn(0).setMaxWidth(0);
		tabla.getColumnModel().getColumn(0).setMinWidth(0);
		tabla.getColumnModel().getColumn(0).setPreferredWidth(0);
		tabla.getColumnModel().getColumn(0).setResizable(false);	 
		
		tabla.setRowHeight(30);
		tabla.setFont(new Font("",0,15));
		tabla.getTableHeader().setPreferredSize(new java.awt.Dimension(500, 60));
		tabla.getTableHeader().setBackground(new Color(215, 137, 0));
		tabla.getTableHeader().setForeground(Color.black);
		tabla.getTableHeader().setFont(new Font("", 0, 15));
	}
	public void eventoTabla() {
		tabla.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(idAjuste==-1) {
				if(!actualizacionFlag) {
					listaTemporal.set(fila, new ListaAjustesInventario(idAjuste,
							idAntiguo, 
							valorAntiguo, 
							nombreAntiguo, 
							Integer.parseInt(tabla.getValueAt(fila, 2).toString())));
				}
				
				fila =  tabla.getSelectedRow();	
				
				valorAntiguo = Integer.valueOf(tabla.getValueAt(fila, 3).toString());
				nombreAntiguo = tabla.getValueAt(fila, 1).toString();
				idAntiguo = 	Integer.parseInt(tabla.getValueAt(fila, 0).toString());
				precioAntiguo = Integer.parseInt(tabla.getValueAt(fila, 2).toString());
				
				
				productoTemp = new LNProducto().getProducto(
						Integer.parseInt(tabla.getValueAt(fila, 0).toString()));

				listaTemporal.set(fila, new ListaAjustesInventario(idAjuste,
						Integer.parseInt(tabla.getValueAt(fila, 0).toString()), 
						0, 
						tabla.getValueAt(fila, 1).toString(), 
						Integer.parseInt(tabla.getValueAt(fila, 2).toString())));

				
				tPrecio.setText(tabla.getValueAt(fila, 2).toString());
				
				cargarComboProducto();
				
				modeloSpinner.setValue( Integer.valueOf(tabla.getValueAt(fila, 3).toString()));
				
				int pos = new LNProducto().getPosicionListaAjustes(tabla.getValueAt(fila, 1).toString(), listaProductosCombo);
				
				comboProductos.setSelectedIndex(pos);
	 
				modeloSpinner.setMaximum(
						new LNProducto().getProducto(
								Integer.parseInt(
										tabla.getValueAt(fila, 0).toString())).getCantidad());
		
				modoBotonesRegistro(0);
				actualizacionFlag =false;
			}
				
				/***************2******************/
				if(idAjuste !=-1) {
					
					
					comboProductos.removeAllItems();
					listaProductosCombo = new ArrayList<Producto>();
					
					
					for (Producto p : new LNProducto().getProductosComboAjustes(listaTemporal)) {
					 		comboProductos.addItem(p);
					 		listaProductosCombo.add(p);
						}
					
					fila =  tabla.getSelectedRow();	
					productoTemp = new LNProducto().getProducto(Integer.parseInt(tabla.getValueAt(fila, 0).toString()));
					
					if(productoTemp.getCantidad()>Integer.valueOf(tabla.getValueAt(fila,3).toString())) {
						
						listaTemporal.set(fila, new ListaAjustesInventario(idAjuste,
								Integer.parseInt(tabla.getValueAt(fila, 0).toString()), 
								Integer.valueOf(tabla.getValueAt(fila,3).toString()), 
								tabla.getValueAt(fila, 1).toString(), 
								Integer.parseInt(tabla.getValueAt(fila, 2).toString().substring(1,tabla.getValueAt(fila, 2).toString().length()))));
						
						modeloSpinner.setValue( Integer.valueOf(tabla.getValueAt(fila, 3).toString()));

						modeloSpinner.setMaximum(productoTemp.getCantidad()); 				

					}
					
					if(productoTemp.getCantidad()<Integer.valueOf(tabla.getValueAt(fila,3).toString())) {
						int valor= 0;
						
						if(!arrayValores.isEmpty()) {
							
						boolean prueba = false;
						int posi = 0;
						
						for(int i = 0; i<arrayValores.size(); i++) {
							if (arrayValores.get(i).getIdProducto() == Integer.parseInt(tabla.getValueAt(fila, 0).toString())) {
								prueba = true;
								posi = i;
								break;
							}
						}
						
						if(prueba) {
							
							valor = arrayValores.get(posi).getCantidad();
							
						}else {
						
						arrayValores.add(new ListaAjustesInventario(idAjuste,
									Integer.parseInt(tabla.getValueAt(fila, 0).toString()), 
									Integer.valueOf(tabla.getValueAt(fila,3).toString()), 
									tabla.getValueAt(fila, 1).toString(), 
									Integer.parseInt(tabla.getValueAt(fila, 2).toString())));
						
						valor = Integer.valueOf(tabla.getValueAt(fila,3).toString());		
						}		
						
						}else {
							
							arrayValores.add(new ListaAjustesInventario(idAjuste,
									Integer.parseInt(tabla.getValueAt(fila, 0).toString()), 
									Integer.valueOf(tabla.getValueAt(fila,3).toString()), 
									tabla.getValueAt(fila, 1).toString(), 
									Integer.parseInt(tabla.getValueAt(fila, 2).toString().substring(1,tabla.getValueAt(fila, 2).toString().length()))));
							
						valor = Integer.valueOf(tabla.getValueAt(fila,3).toString());		
						
						}
						
						listaTemporal.set(fila, new ListaAjustesInventario(idAjuste,
								Integer.parseInt(tabla.getValueAt(fila, 0).toString()), 
								Integer.valueOf(tabla.getValueAt(fila,3).toString()), 
								tabla.getValueAt(fila, 1).toString(), 
								Integer.parseInt(tabla.getValueAt(fila, 2).toString().substring(1,tabla.getValueAt(fila, 2).toString().length()))));
						
						modeloSpinner.setValue( Integer.valueOf(tabla.getValueAt(fila, 3).toString()));
						
						int formula = productoTemp.getCantidad()+Integer.valueOf(tabla.getValueAt(fila,3).toString())-(Integer.valueOf(tabla.getValueAt(fila,3).toString())-valor);
						System.out.println("FORMULA: "+formula);
						
						modeloSpinner.setMaximum(formula); 				
		
					}
					if(productoTemp.getCantidad()==Integer.valueOf(tabla.getValueAt(fila,3).toString())) {
						
						listaTemporal.set(fila, new ListaAjustesInventario(idAjuste,
								Integer.parseInt(tabla.getValueAt(fila, 0).toString()), 
								Integer.valueOf(tabla.getValueAt(fila,3).toString()), 
								tabla.getValueAt(fila, 1).toString(), 
								Integer.parseInt(tabla.getValueAt(fila, 2).toString().substring(1,tabla.getValueAt(fila, 2).toString().length()))));
						
						modeloSpinner.setValue( Integer.valueOf(tabla.getValueAt(fila, 3).toString()));
						modeloSpinner.setMaximum(Integer.valueOf(tabla.getValueAt(fila,3).toString())+productoTemp.getCantidad()); 				
		
					}
					
					int pos = new LNProducto().getPosicionListaAjustes(tabla.getValueAt(fila, 1).toString(), listaProductosCombo);	
					comboProductos.setSelectedIndex(pos);
					modoBotonesRegistro(0);
			
				}
			}
		});
	}

	public void botones( ) {
		crearAjuste = new JButton("Registrar Ajuste");
		crearAjuste.setBackground(new Color(2, 151, 0));
		crearAjuste.setForeground(Color.white);
		crearAjuste.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 20));
		crearAjuste.addActionListener(this);

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

		botonActualizarCompra = new JButton("Actualizar");
		botonActualizarCompra.setBackground(new Color(229, 122, 0));
		botonActualizarCompra.setForeground(Color.white);
		botonActualizarCompra.addActionListener(this);
		botonActualizarCompra.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 20));

		botonNuevaAjuste = new JButton("Nuevo");
		botonNuevaAjuste.setBackground(Color.white);
		botonNuevaAjuste.setForeground(Color.black);
		botonNuevaAjuste.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 20));
		botonNuevaAjuste.addActionListener(this);

		botonCrearAjuste = new JButton("Crear ajuste");
		botonCrearAjuste.setBackground(new Color(2, 151, 0));
		botonCrearAjuste.setForeground(Color.white);
		botonCrearAjuste.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 20));
		botonCrearAjuste.addActionListener(this);

		botonExportarPDF = new JButton("Exportar PDF");
		botonExportarPDF.setBackground(new Color(0, 148, 160));
		botonExportarPDF.setForeground(Color.WHITE);
		botonExportarPDF.addActionListener(this);
		botonExportarPDF.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 20));

		botonRemoverDetalles =new JButton("Quitar");
		botonRemoverDetalles.setBackground(Color.RED);
		botonRemoverDetalles.setForeground(Color.white);
		botonRemoverDetalles.addActionListener(this);
		botonRemoverDetalles.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 20));


	}

	public void modoBotonesCrearAjuste(int caso) {
		panelBotonesCrearAjuste.removeAll();
		if(caso== 1) {
			panelBotonesCrearAjuste.add(botonCrearAjuste);
			panelBotonesCrearAjuste.add(botonNuevaAjuste);
		}else {
			panelBotonesCrearAjuste.add(botonActualizarCompra);
			panelBotonesCrearAjuste.add(botonNuevaAjuste);
			panelBotonesCrearAjuste.add(botonExportarPDF);
		}
		panelBotonesCrearAjuste.repaint();
		panelBotonesCrearAjuste.revalidate();
	}
	public void modoBotonesRegistro(int caso) {
		panelBotonesRegistro.removeAll();
		if(caso == 1) {
			panelBotonesRegistro.add(botonAgregarDetalles);
			panelBotonesRegistro.add(botonNuevoDetalle);
		}else {
			panelBotonesRegistro.add(botonActualizarDetalles);
			panelBotonesRegistro.add(botonNuevoDetalle);
			panelBotonesRegistro.add(botonRemoverDetalles);

		}
		panelBotonesRegistro.repaint();
		panelBotonesRegistro.revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(botonAgregarDetalles == e.getSource()) {
			if(Integer.parseInt(spinnerCantidad.getValue().toString()) !=0) {

				Producto p = (Producto) comboProductos.getSelectedItem();
				productoTemp.setCantidad(p.getCantidad()-Integer.parseInt(spinnerCantidad.getValue().toString()));
				ListaAjustesInventario lista = new ListaAjustesInventario(idAjuste, 
						p.getIdProducto(), 
						Integer.parseInt(spinnerCantidad.getValue().toString()), p.getNombre(),
						Integer.parseInt(tPrecio.getText()));

				new ListaAjustesInventario().limpiarLista(listaTemporal,  lista);

				cargarTabla(ln.getDatosDetalles(listaTemporal), ln.getColumnasDetalles());

				modeloSpinner.setValue(1);
				comboProductos.setSelectedIndex(0);
				tPrecio.setText("");
				cargarComboProducto();
				lEstado.setText("Notificaciones");
			}else {
				lEstado.setText("Debe insertar mínimo una cantidad");
			}
		}
		if(botonNuevoDetalle == e.getSource()) {
			//spinnerCantidad.setValue(0);

			modoBotonesRegistro(1);
			if(fila!=-1) {
			Producto p = (Producto) comboProductos.getSelectedItem();

			listaTemporal.set(fila, new ListaAjustesInventario(idAjuste, 
					idAntiguo, 
					valorAntiguo, 
					nombreAntiguo,
					Integer.parseInt(tPrecio.getText())));
			
			productoTemp.setCantidad(
					new LNProducto().getProducto(
							productoTemp.getIdProducto()).getCantidad()-Integer.parseInt(spinnerCantidad.getValue().toString()));
			actualizacionFlag =true;
			}
			cargarTabla(ln.getDatosDetalles(listaTemporal), ln.getColumnasDetalles());
			cargarComboProducto();
			fila = -1;
		}
		if(botonActualizarDetalles == e.getSource()) {
			Producto p = (Producto) comboProductos.getSelectedItem();

			listaTemporal.set(fila, new ListaAjustesInventario(idAjuste, 
					p.getIdProducto(), 
					Integer.parseInt(spinnerCantidad.getValue().toString()), 
					p.getNombre(),
					Integer.parseInt(tPrecio.getText())));
		
			productoTemp.setCantidad(
					new LNProducto().getProducto(
							productoTemp.getIdProducto()).getCantidad()-Integer.parseInt(spinnerCantidad.getValue().toString()));
			
			cargarComboProducto();
			 
			cargarTabla(ln.getDatosDetalles(listaTemporal), ln.getColumnasDetalles());
			fila = -1;
			actualizacionFlag =true;
		}
		//REGISTRAR AJUSTE
		if(botonCrearAjuste == e.getSource()) {
			if(listaTemporal.size()!=0) {
				SimpleDateFormat formateador = new SimpleDateFormat("yyyy/MM/dd");
				java.util.Date date =calendario.getDate();
				String fecha = formateador.format(date);
				//AJUSTE:
				AjusteInventario a = new AjusteInventario(idAjuste, fecha);
				a.setDetalles(listaTemporal);
				//new LNProducto().actualizarProductoEspecifico(Integer.parseInt(tPrecio.getText()), id);
				ln.insertarAjuste(a);
				modoBotonesCrearAjuste(0);
				modoBotonesRegistro(1);	
				new LNProducto().actualizarValoresAjusteInventario();
				lEstado.setText("Ajuste realizado");
				idAjuste = ln.getLastAjuste();
			}else {
				lEstado.setText("¡Debe insertar mínimo un producto!");
			}
		}
		if(botonNuevaAjuste ==e.getSource()) {
			g.cargarVista(g.getVista("Ajuste Inventario"));
		}

		if(botonRemoverDetalles == e.getSource()) {
			if(fila!=-1) {
				listaTemporal.remove(fila);	
				modoBotonesRegistro(1);	
			 	modeloSpinner.setValue(1);
				cargarComboProducto();
				cargarTemporal();
				fila = -1;
				cargarTabla(ln.getDatosDetalles(listaTemporal), ln.getColumnasDetalles());
			}else {
				lEstado.setText("Seleccione algún dato de la tabla");
			}

		}
		if(botonActualizarCompra == e.getSource()) {
			if(listaTemporal.size()!=0) {
				try {
					SimpleDateFormat formateador = new SimpleDateFormat("yyyy/MM/dd");
					java.util.Date date =calendario.getDate();
					String fecha = formateador.format(date);

					AjusteInventario ajuste = new AjusteInventario(idAjuste, fecha);
					ajuste.setDetalles(listaTemporal);
					ln.actualizarAjuste(ajuste);
					new LNProducto().actualizarValoresAjusteInventario();
					lEstado.setText("¡Actualizado!");
				} catch (Exception e2) {
					lEstado.setText("¡No se puede actualizar!");

				}
			}
		}
		if(botonExportarPDF == e.getSource()) {
			System.out.println("IDAJUSTE: "+idAjuste);
			if(idAjuste!=-1) {
				chooser.setDialogTitle("Guardar PDF en:");
				int userSelection = chooser.showSaveDialog(this);
				if(userSelection == JFileChooser.APPROVE_OPTION){
					AjusteInventario ai = ln.getAjusteInventario(idAjuste);
					ai.setDetalles(listaTemporal);
					new PDFReporte().crearPDFAjustes(chooser.getSelectedFile().toString(), ai.getDetalles(), ai);
				}else {
					lEstado.setText("Seleccione un dato de la tabla");
				}
			}
		}
		detallesTabla();
	}
}
