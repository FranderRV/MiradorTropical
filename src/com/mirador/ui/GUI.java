package com.mirador.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
public class GUI extends JFrame implements ActionListener {

	private JPanel menu, visor;
	private JMenuBar barra;
	private JMenu  salida;
	private JMenuItem  salir,cerrarSesion;
	private JScrollPane scroll;
	public GUI() {

		
		menu = new JPanel();
		menu.setLayout(new FlowLayout(10,0,15));
		menu.setPreferredSize(new Dimension(320,700));
		menu.setBackground(Color.DARK_GRAY);
		scroll = new JScrollPane(menu);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBackground(Color.DARK_GRAY);
		visor = new JPanel();
		visor.setPreferredSize(new Dimension(800, 600) );
		visor.setLayout(new BorderLayout());

		barra = new JMenuBar();

		salida = new JMenu("Salir");
		salir = new JMenuItem("Salir");
		cerrarSesion = new JMenuItem("Cerrar Sesión");
		barra.add(salida);
		salida.add(salir);
		salir.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);	
			}
		});
		salida.add(cerrarSesion);
		cerrarSesion.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Login().init(); 
				
			}
		});
			
		setJMenuBar(barra);
		add(scroll,BorderLayout.LINE_START);
		add(visor,BorderLayout.CENTER);
		
		cargarVista(getVista("Venta"));
		crearMenu();
	}

	public void accionBarra() {

	}
	public ImageIcon icono(String url, int x , int y, int w, int h){
		ImageIcon icon = new ImageIcon(new ImageIcon(getClass().getResource(url)).getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT));
		return icon;
	}
	
	public void crearMenu() {
		final String[]  items = new String[]{"Venta","Ventas","Compra",
				"Compras","Ajuste Inventario","Ajustes Inventario",
				"Reportes","Inventario","Usuarios","Proveedores"};
		
	for (int j = 0; j < items.length; j++) {
	
			JButton b = new JButton(); 
			b.setContentAreaFilled(true);
			b.setBackground(Color.DARK_GRAY);
			b.setIcon(icono("/images/"+items[j]+".png", 0, 0, 250, 55));
			//b.setFont(new Font("", Font.LAYOUT_NO_LIMIT_CONTEXT, 20));
			b.setPreferredSize(new Dimension(300,55));
			b.setHorizontalAlignment(JLabel.CENTER);
			b.setFocusable(false);
			final int a = j;
			b.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					cargarVista(getVista(items[a]));
				}
			});
			menu.add(b);
		}
	}
	
	public View getVista(String tipo) {
		View v = null;	
		switch (tipo) {
		case "Venta":
			v = new ViewVenta(-1,this);
			break;
		case "Ventas":
			v = new ViewVentas(this);
			break;
		case "Compra":
			v = new ViewCompra(-1,this,"");
			break;
		case "Compras":
			v = new ViewCompras(this);
			break;
		case "Inventario":
			v = new ViewInventario();
			break;
		case "Proveedores":
			v = new ViewProveedores();
			break;
		case "Usuarios":
			v = new ViewUsuario();
			break;
			
		case "Ajuste Inventario":
			v = new ViewAjusteInventario(this,-1);
			break;
			
		case "Ajustes Inventario":
			v = new ViewAjustesInventario(this);
			break;
			
		case "Reportes":
			v = new ViewReportes();
			break;

		default:
			v = new ViewVenta(-1,this);
			break;
		}
		
		return v;
	}

	public void cargarVista(View v) {
		visor.removeAll();
		visor.add(v);
		visor.repaint();
		visor.revalidate();
	}



	public void init() {
		//this.setUndecorated(true);
		this.setVisible(true); 
		this.pack();
		this.setLocationRelativeTo(null);	
		this.setExtendedState(MAXIMIZED_BOTH);

	    Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/represa_20.jpg"));
	    this.setIconImage(icon);

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {


	}
}
