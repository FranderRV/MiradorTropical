package com.mirador.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.TableView.TableRow;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.mirador.ln.LNComplementosInventario;
import com.mirador.ln.LNProveedor;
import com.mirador.vo.Proveedor;
import com.mirador.vo.Telefonos;

public class ViewProveedores extends View implements ActionListener, KeyListener {
	private LNProveedor ln;
	private JTextField tNombre, tCorreo, tCedula, tDireccion, tNumero;
	private JButton agregarTelefono, eliminar, nuevo, actualizar;
	private JPanel panelBotones;
	private JComboBox<Telefonos> combo;
	private ArrayList<Telefonos> listaTelefonos;
	private Telefonos telefondoTemporal;
	private Proveedor proveedorTemporal;
	private UIAuxiliar ui;
	public ViewProveedores(UIAuxiliar ui) {
		super("Proveedores");
		build();
		this.ui = ui;
	}
	
	public ViewProveedores() {
		super("Proveedores");
		build();
		this.ui = null;
	}
	public void build() {
		this.telefondoTemporal = new Telefonos(0,0,0);
		this.proveedorTemporal = new Proveedor(0, "", "", "", "");
		this.listaTelefonos = new ArrayList<Telefonos>();
		this.ln = new LNProveedor();
		this.combo = new JComboBox<Telefonos>();
		combo.setBackground(Color.white);
		combo.setFont(new Font("",Font.BOLD,18));
		cargarTabla(ln.getDatosProveedor(""), ln.getColumnasProveedor());

		tNombre = new JTextField();
		tNombre.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 17));
		tCorreo = new JTextField();
		tCorreo.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 17));
		tCedula = new JTextField();
		tCedula.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 17));
		tDireccion = new JTextField();
		tDireccion.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 17));
		tNumero = new JTextField();
		tNumero.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 17));

		agregarTelefono = new JButton("Agregar");
		agregarTelefono.setBackground(new Color(2, 151, 0));
		agregarTelefono.setForeground(Color.white);
		agregarTelefono.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 15));
		agregarTelefono.setBorder(new EmptyBorder(10, 10, 10, 10));

		eliminar = new JButton("Eliminar");
		eliminar.setBackground(Color.red);
		eliminar.setForeground(Color.white);
		eliminar.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 15));
		eliminar.setBorder(new EmptyBorder(10, 10, 10, 10));

		nuevo = new JButton("Nuevo");
		nuevo.setBackground(Color.white);
		nuevo.setForeground(Color.black);
		nuevo.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 15));
		nuevo.setBorder(new EmptyBorder(10, 10, 10, 10));

		actualizar = new JButton("Actualizar");
		actualizar.setBackground(new Color(229, 104, 0));
		actualizar.setForeground(Color.white);
		actualizar.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 15));
		actualizar.setBorder(new EmptyBorder(10, 10, 10, 10));



		actualizar.addActionListener(this);
		nuevo.addActionListener(this);
		eliminar.addActionListener(this);
		agregarTelefono.addActionListener(this);

		cargarCombo();
		panelBotones = crearPanel(280, 50);
		panelBotones.add(entradaVerticalLista(new ArrayList<JComponent>(Arrays.asList(agregarTelefono,nuevo)),null));
		panelEntradas.add(entradaVertical(tNombre, "Nombre"));
		panelEntradas.add(entradaVertical(tCedula, "Cédula"));
		panelEntradas.add(entradaVertical(tCorreo, "Correo"));
		panelEntradas.add(entradaVertical(tDireccion, "Direccion"));
		panelEntradas.add(entradaVertical(new JSeparator(), "Teléfonos"));
		panelEntradas.add(entradaVertical(tNumero, "Número"));
		panelEntradas.add(panelBotones);

		panelEntradas.add(entradaVertical(combo, "Números guardados",null));		
		AutoCompleteDecorator.decorate(combo);
		cargarTabla("");
		placeHolder();
		setBotones("registrar");
		iniciarBotones(this);
		iniciarBuscar(this);
		eventoTabla();
		detallesTabla();
		comboEvento();
	}

	private JComboBox<Telefonos> comboCelda( ArrayList<Telefonos> lista){
		System.out.println(lista.size());
		JComboBox<Telefonos> combo = new JComboBox<Telefonos>();
		for (Telefonos t : lista) {
			System.out.println(t.toString());
			combo.addItem(t);
		}
		return combo;
	}

	public void recorrerTablaCombo( JTable tabla,TableColumn columna,ArrayList<Telefonos> lista ) {
		columna.setCellEditor(new DefaultCellEditor(comboCelda(lista)));
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setToolTipText("Lista de Números");
		columna.setCellRenderer(r);
	}
	public void cargarCombo() {
		combo.removeAllItems();
		for (Telefonos telefonos : listaTelefonos) {
			combo.addItem(telefonos);
		}
	}
	public void setBotonesTelefono(int caso) {
		panelBotones.removeAll();
		if(caso==1) {
			panelBotones.add(entradaVerticalLista(new ArrayList<JComponent>(Arrays.asList(agregarTelefono,nuevo)),null));

		}else {
			panelBotones.add(entradaVerticalLista(new ArrayList<JComponent>(Arrays.asList(actualizar,eliminar,nuevo)),null));
		}
		panelBotones.repaint();
		panelBotones.revalidate();
	}
	public void eventoTabla() {
		tabla.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				setBotones("editar");
				int fila = tabla.getSelectedRow();
				/**/
				tNombre.setText(tabla.getValueAt(fila, 1).toString());
				tCedula.setText(tabla.getValueAt(fila, 2).toString());
				tCorreo.setText(tabla.getValueAt(fila, 3).toString());
				tDireccion.setText(tabla.getValueAt(fila, 4).toString());
				proveedorTemporal = new Proveedor(Integer.parseInt(tabla.getValueAt(fila, 0).toString())
						, tNombre.getText(), tDireccion.getText(), tCedula.getText(), tCorreo.getText());

				if(ln.getTelefonos(proveedorTemporal.getIdProveedor()).size()>0){
					listaTelefonos = ln.getTelefonos(proveedorTemporal.getIdProveedor());

					cargarCombo();
				}else { 
					tNumero.setText("");
					listaTelefonos = new ArrayList<Telefonos>();
					cargarCombo();
				}


				//recorrerTablaCombo(tabla, tabla.getColumnModel().getColumn(5),listaTelefonos);


			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub			
			}
		});
	}
	public void placeHolder() {
		tBuscar.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				tBuscar.setText("Buscar...");
				cargarTabla("");
			}

			@Override
			public void focusGained(FocusEvent e) {
				tBuscar.setText("");
				cargarTabla("");
			}
		});
	}
	public void cargarTabla(String buscar) {		
		modelo.setDataVector(ln.getDatosProveedor(buscar),ln.getColumnasProveedor());
		detallesTabla();
	}

	public void comboEvento() {
		combo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(listaTelefonos.size()>0) {
					if(combo.getItemCount()!=0) {
						telefondoTemporal = (Telefonos) combo.getSelectedItem();		
						tNumero.setText(telefondoTemporal.getNumero()+"");
						setBotonesTelefono(2);
					}
				}else {
					System.out.println("else");
					setBotonesTelefono(1);
				}
			}
		});
	}

	public void detallesTabla() {

		tabla.setRowHeight(30);
		//OCULTAR COLUMNAS
		tabla.getColumnModel().getColumn(0).setMaxWidth(0);
		tabla.getColumnModel().getColumn(0).setMinWidth(0);
		tabla.getColumnModel().getColumn(0).setPreferredWidth(0);
		tabla.getColumnModel().getColumn(0).setResizable(false);	

		tabla.getColumnModel().getColumn(5).setMaxWidth(0);
		tabla.getColumnModel().getColumn(5).setMinWidth(0);
		tabla.getColumnModel().getColumn(5).setPreferredWidth(0);
		tabla.getColumnModel().getColumn(5).setResizable(false);	
		
		tabla.setFont(new Font("",0,15));
		tabla.getTableHeader().setPreferredSize(new java.awt.Dimension(500, 60));
		tabla.getTableHeader().setFont(new Font("", 0, 15));
	}

	public void setBox(JTable tabla) {
		ArrayList<Proveedor> lista =ln.getProveedores("");
		JComboBox<Telefonos> telefonos = new JComboBox<Telefonos>();

		for (int i = 0; i < lista.size(); i++) { 
			for (int j = 0; j < lista.get(i).getListaTelefonos().size(); j++) {
				tabla.getModel().setValueAt(telefonos, j, i);

			}
		} 
		tabla.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(telefonos));
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setToolTipText("Seleccione un numero");
		tabla.getColumnModel().getColumn(5).setCellRenderer(render);

	}
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
		cargarTabla(tBuscar.getText());
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(agregarTelefono == e.getSource()) {
			if(!tNumero.getText().isEmpty()) {
				telefondoTemporal = new Telefonos(0, 0
						, Integer.parseInt(tNumero.getText()));
				
				if(!new Telefonos().compare(listaTelefonos, telefondoTemporal)) {
				listaTelefonos.add(telefondoTemporal);
				cargarCombo();
				telefondoTemporal = null;
				tNumero.setText("");

				lEstado.setText("Notificaciones");
				}else {
					lEstado.setText("Número ya registrado");
				}
			}else {
				lEstado.setText("Necesita ingresar algún número");
			}
			setBotonesTelefono(1);
		}

		if(nuevo==e.getSource()) { 
			setBotonesTelefono(1);
			tNumero.setText("");
		}
		if(actualizar == e.getSource()) {
			if(!tNumero.getText().isEmpty()) {
					listaTelefonos.set(combo.getSelectedIndex(),new Telefonos(0, 0, Integer.parseInt(tNumero.getText())));
				}
			cargarCombo();
		}
		if(eliminar==e.getSource()){
			listaTelefonos.remove(combo.getSelectedIndex());
			cargarCombo();
			tNumero.setText("");
		}
		//NUEVO GENERAL
		if(botonNuevo==e.getSource()) {
			telefondoTemporal = null;
			proveedorTemporal = null;
			limpiar();
			cargarCombo();
			setBotonesTelefono(1);
			lEstado.setText("Notificaciones");
		}

		//general
		if(botonActualizar==e.getSource()) {
			try {
				Proveedor p = new Proveedor(proveedorTemporal.getIdProveedor(), tNombre.getText() 
						,tDireccion.getText(), tCedula.getText(), tCorreo.getText());
				p.setListaTelefonos(listaTelefonos);
				ln.actualizar(p, p.getIdProveedor());
				lEstado.setText("Actualizado");
				cargarTabla("");
			} catch (Exception e2) {
				lEstado.setText("No se ha actualizar");
			}

		}
		if(botonEliminar == e.getSource()) {
			try {
				ln.eliminar(proveedorTemporal.getIdProveedor());
				lEstado.setText("Eliminado");
				limpiar();
			} catch (Exception e2) {
				lEstado.setText("No se ha podido eliminar");
			}

		}
		if(botonRegistrar == e.getSource()) {
			try {
				if(! tNombre.getText().isEmpty()&&!tDireccion.getText().isEmpty()&&!tCedula.getText().isEmpty()) {
					Proveedor  p = new Proveedor(0, tNombre.getText(), tDireccion.getText(), tCedula.getText(), tCorreo.getText());
					p.setListaTelefonos(listaTelefonos);
					ln.insertar(p);
					lEstado.setText("Insertado");
					proveedorTemporal = null;
					limpiar();
					if(ui!=null) {
						ui.cargarComboProveedores();
					}
				}else {
					lEstado.setText("Complete todos los campos");
				}
			}catch (Exception e2) {
				lEstado.setText("No se ha podido registrar");
			}
		}

	}
	public void limpiar(){
		cargarTabla("");
		tNombre.setText("");
		tCedula.setText("");
		tCorreo.setText("");
		tDireccion.setText("");
		tNumero.setText("");
		setBotones("registrar");
		listaTelefonos = new ArrayList<Telefonos>();
		cargarCombo();
	}
}
