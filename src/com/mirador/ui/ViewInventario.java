package com.mirador.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import com.mirador.ln.LNComplementosInventario;
import com.mirador.ln.LNProducto;
import com.mirador.vo.CategoriaComida;
import com.mirador.vo.Producto;
import com.mirador.vo.Proveedor;
import com.mirador.vo.Unidad;


public class ViewInventario extends View  implements ActionListener, KeyListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JTextField tNombre, tCodigo, tPrecio;
	private JSpinner sGanancia;
	private JSpinner cantidad;
	private JComboBox<CategoriaComida> comboCategorias;
	protected JButton bCargar, bAddCategoria;
	private LNComplementosInventario lnComplementos;
	private LNProducto ln;
	private Producto producto;
	private SpinnerNumberModel modeloSpinner;
	
	private UIAuxiliar uiAuxiliar;
	
	public ViewInventario(UIAuxiliar uiAuxiliar) {
		super("Inventario");
		/*DECLARACIÓN*/

		this.uiAuxiliar = uiAuxiliar;
		
		build();

	}
	
	
	public ViewInventario() {
		super("Inventario");
		/*DECLARACIÓN*/

		this.uiAuxiliar = null;
		
		build();

	}
	
	
	private void build() {
		this.producto = new Producto(0,"","",0,null,0,0);
		this.lnComplementos = new LNComplementosInventario();
		this.ln = new LNProducto();

		modeloSpinner = new SpinnerNumberModel();
		modeloSpinner.setMinimum(0);
		modeloSpinner.setStepSize(10);
		
		bCargar = new JButton("Cargar imagen");
		bCargar.setPreferredSize(new Dimension(128,50));
		bCargar.setFont(new Font("",Font.BOLD,14));
		bCargar.addActionListener(this);

 

		tNombre = new JTextField();
		tCodigo = new JTextField();
		tPrecio = new JTextField();
		sGanancia = new JSpinner(modeloSpinner);
		sGanancia.setBackground(Color.white);
		sGanancia.setForeground(Color.black);
		
		cantidad = new JSpinner();
		cantidad .setBackground(Color.white);
		cantidad .setForeground(Color.black);
		
		
	
		sGanancia.setFont(new Font("",0,12	));
		cantidad.setFont(new Font("",0,12	));
		
		
		comboCategorias = new JComboBox<CategoriaComida>();

		//Detalles Combo
		comboCategorias.setBackground(Color.white);
		comboCategorias.setPreferredSize(new Dimension(320,40));
		comboCategorias.setFont(new Font("",Font.BOLD,18));

		panelEntradas.add(entradaVertical(tNombre, "Nombre"));
		panelEntradas.add(entradaVertical(tCodigo, "Código"));
		panelEntradas.add(entradaVertical(sGanancia, "Ganancia"));		
		//panelEntradas.add(entradaVertical(cantidad, "Cantidad"));		
		panelEntradas.add(entradaVertical(tPrecio, "Precio")); 
		
		panelEntradas.add(entradaVertical(comboCategorias, "Categoria"));
	
	
		cargarTabla(ln.getDatos(""), ln.getColumnas());
		setBotones("registrar");
		/*PANEL FORMULARIO*/

		/*	CARGA DE PÁNELES	*/
		cargarComboCategorias();
		detallesTabla();
		eventoTabla();
		iniciarBotones(this);
		placeHolder();
		iniciarBuscar(this);
	} 
	
	public void placeHolder() {
		tBuscar.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
					tBuscar.setText("Buscar...");
					//cargarTabla("");
			}

			@Override
			public void focusGained(FocusEvent e) {
				tBuscar.setText("");
				//cargarTabla("");
			}
		});
	}
	public void cargarTabla() {
		cargarTabla(ln.getDatos(""), ln.getColumnas());
		detallesTabla();
	}
	public void eventoTabla() {
		tabla.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {
				setBotones("editar");
				int fila = tabla.getSelectedRow();
				tNombre.setText(tabla.getValueAt(fila, 1).toString());
				tCodigo.setText(tabla.getValueAt(fila, 2).toString());
				sGanancia.setValue(Integer.parseInt(tabla.getValueAt(fila, 7).toString()));
				cantidad.setValue(Integer.parseInt(tabla.getValueAt(fila, 3).toString()));
				comboCategorias.setSelectedIndex(lnComplementos.getIndexCategoria(
						Integer.parseInt(tabla.getValueAt(fila, 5).toString())
						)); 
				//OBJETO PRODUCTO
				producto = new Producto(Integer.parseInt(tabla.getValueAt(fila, 0).toString()),
						tabla.getValueAt(fila, 1).toString(), 
						tabla.getValueAt(fila, 2).toString(), 
						Integer.parseInt(tabla.getValueAt(fila, 3).toString()), 
						new CategoriaComida(Integer.parseInt(tabla.getValueAt(fila, 5).toString()), tabla.getValueAt(fila, 6).toString()),
						Integer.parseInt(tabla.getValueAt(fila, 7).toString()), 
						0
						);
				tPrecio.setText(tabla.getValueAt(fila, 6).toString().substring(1,tabla.getValueAt(fila, 6).toString().length()));
			}
		});
	}
	public void detallesTabla() {

		tabla.setRowHeight(30);
		//OCULTAR COLUMNAS
		//ID PRODUCTO
		tabla.getColumnModel().getColumn(0).setMaxWidth(0);
		tabla.getColumnModel().getColumn(0).setMinWidth(0);
		tabla.getColumnModel().getColumn(0).setPreferredWidth(0);
		tabla.getColumnModel().getColumn(0).setResizable(false);	

		tabla.getColumnModel().getColumn(5).setMaxWidth(0);
		tabla.getColumnModel().getColumn(5).setMinWidth(0);
		tabla.getColumnModel().getColumn(5).setPreferredWidth(0);



		tabla.getColumnModel().getColumn(5).setResizable(false);	
		tabla.getColumnModel().getColumn(4).setResizable(false);	
		tabla.getColumnModel().getColumn(3).setResizable(false);	
		tabla.getColumnModel().getColumn(2).setResizable(false);	
		tabla.getColumnModel().getColumn(1).setResizable(false);	
		
		tabla.setFont(new Font("Segoe UI ",0,15));
		tabla.getTableHeader().setPreferredSize(new java.awt.Dimension(500, 60));
		tabla.getTableHeader().setFont(new Font("Segoe UI ", 0, 17));
	}
	public void cargarComboCategorias() {
		comboCategorias.removeAllItems();
		for (CategoriaComida  c  : lnComplementos.getCategorias()) {
			comboCategorias.addItem(c);
	 
		}
	}
	
	public void limpiar() {
		tNombre.setText("");
		tCodigo.setText("");
		tPrecio.setText("0");
		sGanancia.setValue(0);
		//imagen.setIcon(iconoExterno("", 200, 200));
		comboCategorias.setSelectedIndex(0);

	}
	@Override
	public void actionPerformed(ActionEvent e) {

		if(botonActualizar==e.getSource()) {
			try {
				ln.actualizar(new Producto(producto.getIdProducto(),
						tNombre.getText(),
						tCodigo.getText(), 
						Integer.parseInt(cantidad.getValue().toString()), 
						(CategoriaComida)comboCategorias.getSelectedItem(), 
						Integer.parseInt(tPrecio.getText()), 
						Integer.parseInt(sGanancia.getValue().toString()))
						);	
				cargarTabla();
				lEstado.setText("Actualizado");
			} catch (Exception e2) {
				lEstado.setText("No se ha podido actualizar");
			}

		}


		if(botonEliminar == e.getSource()) {
			try {
			int input =	JOptionPane.showConfirmDialog(null, "¿Está seguro? Estos datos se eliminarán de las facturas ya realizadas.",
					"Seleccion la acción", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
			if(input==0) {
				ln.eliminar(producto.getIdProducto());
				cargarTabla();
				limpiar();
				setBotones("registrar");
				lEstado.setText("Eliminado");
			}
			} catch (Exception e2) {
				lEstado.setText("No se ha podido eliminar");
			}

		}
		if(botonRegistrar == e.getSource()) {
			try {
				int n = 0;
				if(!tPrecio.getText().isEmpty()) {
					n = 	Integer.parseInt(tPrecio.getText());
				}
				
				ln.insertarProducto(new Producto(-1,
						tNombre.getText(),
						tCodigo.getText(), 
						Integer.parseInt(cantidad.getValue().toString()), 
						(CategoriaComida)comboCategorias.getSelectedItem(), 
						n, 
						Integer.parseInt(sGanancia.getValue().toString()))					
						);	
				limpiar();
				lEstado.setText("Registrado");
			
			} catch (Exception e2) {
				e2.printStackTrace();
				lEstado.setText("Llenar todos los campos antes de insertar");
			}

			cargarTabla();
			
			if(uiAuxiliar!=null) {
				uiAuxiliar.cargarComboProductos();
			}
			
		}
		if(botonNuevo == e.getSource()) {
		
			setBotones("registrar");
			limpiar();
			lEstado.setText("Notificaciones");
		}

	}
	
	// KEY LISTENER
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if(tBuscar == e.getSource()) {
			cargarTabla(ln.getDatos(tBuscar.getText()), ln.getColumnas());
			detallesTabla();
		}
		
	}
	
	
}
