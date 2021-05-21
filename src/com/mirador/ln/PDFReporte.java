package com.mirador.ln;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.util.ArrayList;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mirador.vo.AjusteInventario;
import com.mirador.vo.Compra;
import com.mirador.vo.Detalles;
import com.mirador.vo.Factura;
import com.mirador.vo.ListaAjustesInventario;
import com.mirador.vo.ListaCompras;

public class PDFReporte {

	public PDFReporte() {

	}

	public void crearPDFVenta(String direccion, ArrayList<Detalles> lista, Factura factura) {
		try {
			System.out.println("DIRECCION: "+direccion);
			FileOutputStream o = new FileOutputStream(direccion+".pdf");
			Document doc = new Document();
			PdfWriter.getInstance(doc, o);
			doc.open();
			
			doc.addAuthor("Mirador PH Reventazón Siquirres "); 
			doc.addCreator("Software PH");
			doc.addTitle("Factura#"+factura.getIdFactura()); 
		
			Paragraph titulo = new Paragraph();
			titulo.setAlignment(Paragraph.ALIGN_CENTER);
			titulo.setFont(FontFactory.getFont("Times New Roman", 24, Font.BOLD, BaseColor.BLACK));
			titulo.add("Mirador PH Reventazón");
			
			doc.add(titulo);
	
			Paragraph fecha = new Paragraph();
			fecha.setAlignment(Paragraph.ALIGN_CENTER);
			fecha.setFont(FontFactory.getFont("Times New Roman", 10, Font.BOLD, BaseColor.DARK_GRAY));
			fecha.add("Fecha: "+factura.getFecha());
			
			doc.add(fecha);
			
			doc.add(new Chunk());

			PdfPTable table = new PdfPTable(4);
			
			PdfPCell celdas = new PdfPCell(new Paragraph("Lista de compra",FontFactory.getFont("Times New Roman", 15, Font.BOLD, BaseColor.WHITE)));
			celdas.setColspan(4);
			celdas.setHorizontalAlignment(Element.ALIGN_CENTER);
			celdas.setBackgroundColor(BaseColor.BLACK);
			table.addCell(celdas);
			
			PdfPCell desc = new PdfPCell(new Paragraph("Descripción",FontFactory.getFont("Times New Roman", 13, Font.BOLD, BaseColor.WHITE)));
			desc.setBackgroundColor(new BaseColor(0, 180, 139));
			
			PdfPCell precio =  new PdfPCell(new Paragraph("Precio",FontFactory.getFont("Times New Roman", 13, Font.BOLD, BaseColor.WHITE)));
			precio.setBackgroundColor(new BaseColor(0, 180, 139));
			
			PdfPCell cant = new PdfPCell(new Paragraph("Cantidad",FontFactory.getFont("Times New Roman", 13, Font.BOLD, BaseColor.WHITE)));
			cant.setBackgroundColor(new BaseColor(0, 180, 139));
			
			PdfPCell total = new PdfPCell(new Paragraph("Total",FontFactory.getFont("Times New Roman", 13, Font.BOLD, BaseColor.WHITE)));
			total.setBackgroundColor(new BaseColor(0, 180, 139));
			
			
			table.addCell(desc);
			table.addCell(precio);
			table.addCell(cant);
			table.addCell(total);
			
			int totalV = 0;
			for (Detalles d : lista) {
				
				table.addCell(d.getDescripcion());
				
				table.addCell(d.getPrecio()+"");
				table.addCell(d.getCantidad()+"");
				
				int totalC = d.getCantidad()*d.getPrecio();
				table.addCell("₡"+totalC+"");
				totalV +=totalC;
			}
			
			 
			PdfPCell totolMonto = new PdfPCell(new Paragraph("Total:  ₡"+totalV,FontFactory.getFont("Times New Roman", 15, Font.BOLD, BaseColor.WHITE)));
			totolMonto.setColspan(4);
			totolMonto.setHorizontalAlignment(Element.ALIGN_CENTER);
			totolMonto.setBackgroundColor(BaseColor.BLACK);
			table.addCell(totolMonto);
		 
			
			doc.add(totolMonto);
			
			
			doc.add(table);

			doc.close();
		} catch (Exception e) {
		}
	}
	
	/*COMPRA*/
	
	public void crearPDFCompra(String direccion, ArrayList<ListaCompras> lista, Compra compra) {
		try {
			FileOutputStream o = new FileOutputStream(direccion+".pdf");
			Document doc = new Document();
			PdfWriter.getInstance(doc, o);
			doc.open();
			
			doc.addAuthor("Mirador PH Reventazón Siquirres "); 
			doc.addCreator("Software PH");
			doc.addTitle("Compra#"+compra.getIdCompra()); 
		
			Paragraph titulo = new Paragraph();
			titulo.setAlignment(Paragraph.ALIGN_CENTER);
			titulo.setFont(FontFactory.getFont("Times New Roman", 24, Font.BOLD, BaseColor.BLACK));
			titulo.add("Mirador PH Reventazón");
			
			doc.add(titulo);
			
			
			Paragraph fecha = new Paragraph();
			fecha.setAlignment(Paragraph.ALIGN_CENTER);
			fecha.setFont(FontFactory.getFont("Times New Roman", 10, Font.BOLD, BaseColor.DARK_GRAY));
			fecha.add("Fecha: "+compra.getFecha().substring(0,10));
			
			doc.add(fecha);  
			
			Paragraph numeroCompra = new Paragraph();
			numeroCompra.setAlignment(Paragraph.ALIGN_CENTER);
			numeroCompra.setFont(FontFactory.getFont("Times New Roman", 10, Font.BOLD, BaseColor.RED)); 
 
			doc.add(numeroCompra);
			

			doc.add(new Chunk());
			
			PdfPTable table = new PdfPTable(4);
			
			PdfPCell celdas = new PdfPCell(new Paragraph("Lista de compra",FontFactory.getFont("Times New Roman", 15, Font.BOLD, BaseColor.WHITE)));
			celdas.setColspan(4);
			celdas.setHorizontalAlignment(Element.ALIGN_CENTER);
			celdas.setBackgroundColor(BaseColor.BLACK);
			table.addCell(celdas);
			
			PdfPCell desc = new PdfPCell(new Paragraph("Descripción",FontFactory.getFont("Times New Roman", 13, Font.BOLD, BaseColor.WHITE)));
			desc.setBackgroundColor(new BaseColor(0, 180, 139));
			
			PdfPCell precio =  new PdfPCell(new Paragraph("Precio",FontFactory.getFont("Times New Roman", 13, Font.BOLD, BaseColor.WHITE)));
			precio.setBackgroundColor(new BaseColor(0, 180, 139));
			
			PdfPCell cant = new PdfPCell(new Paragraph("Cantidad",FontFactory.getFont("Times New Roman", 13, Font.BOLD, BaseColor.WHITE)));
			cant.setBackgroundColor(new BaseColor(0, 180, 139));
			
			PdfPCell total = new PdfPCell(new Paragraph("Total",FontFactory.getFont("Times New Roman", 13, Font.BOLD, BaseColor.WHITE)));
			total.setBackgroundColor(new BaseColor(0, 180, 139));
			
			
			
			table.addCell(desc);
			table.addCell(precio);
			table.addCell(cant);
			table.addCell(total);
			
			int monto = 0;
			for (ListaCompras c : lista) {
				table.addCell(c.getDescripcion()	);
				table.addCell(c.getPrecio()+"");
				table.addCell(c.getCantidad()+"");
				int totalC = c.getCantidad()*c.getPrecio();
				table.addCell("₡"+totalC+"");
				monto+=totalC;
			}
			 
			PdfPCell totolMonto = new PdfPCell(new Paragraph("Total: "+monto,FontFactory.getFont("Times New Roman", 15, Font.BOLD, BaseColor.WHITE)));
			totolMonto.setColspan(4);
			totolMonto.setHorizontalAlignment(Element.ALIGN_CENTER);
			totolMonto.setBackgroundColor(BaseColor.BLACK);
			table.addCell(totolMonto);
		 
			
			doc.add(table);
			doc.close();

		} catch (Exception e) {
		e.printStackTrace();
		}
	}

	
	public void crearReporteEntradas(String direccion, int datos[], String[] fechas) {
		
	
		try {
			FileOutputStream o = new FileOutputStream(direccion+".pdf");
			Document doc = new Document();
			PdfWriter.getInstance(doc, o);
			doc.open();
			
			doc.addAuthor("Mirador PH Reventazón Siquirres "); 
			doc.addCreator("Software PH"); 
		
			Paragraph titulo = new Paragraph();
			titulo.setAlignment(Paragraph.ALIGN_CENTER);
			titulo.setFont(FontFactory.getFont("Times New Roman", 24, Font.BOLD, BaseColor.BLACK));
			titulo.add("Mirador PH Reventazón");
			
			doc.add(titulo);
			
			doc.add(new Chunk());
			
			Paragraph fecha = new Paragraph();
			fecha.setAlignment(Paragraph.ALIGN_CENTER);
			fecha.setFont(FontFactory.getFont("Times New Roman", 10, Font.BOLD, BaseColor.DARK_GRAY));
			fecha.add("Método de reporte: "+fechas[2]);
			
			doc.add(fecha); 
			
			doc.add(new Chunk());
			
			//RANGO DE FECHAS
			if(fechas[2].equalsIgnoreCase("Por fecha específica")) {
				Paragraph textoFechas = new Paragraph();
				textoFechas.setAlignment(Paragraph.ALIGN_CENTER);
				textoFechas.setFont(FontFactory.getFont("Times New Roman", 10, Font.BOLD, BaseColor.RED));
				textoFechas.add("Rango de fecha(s): "+fechas[0]);
				
				doc.add(textoFechas); 
				
				doc.add(new Chunk());
			}
			if(fechas[2].equalsIgnoreCase("Por fechas específicas")) {
				Paragraph textoFechas = new Paragraph();
				textoFechas.setAlignment(Paragraph.ALIGN_CENTER);
				textoFechas.setFont(FontFactory.getFont("Times New Roman", 10, Font.BOLD, BaseColor.RED));
				textoFechas.add("Rango de fecha(s): "+fechas[0]+"  a  "+fechas[1]);
				
				doc.add(textoFechas); 
				
				doc.add(new Chunk());
			}
			
			
			
			
			PdfPTable table = new PdfPTable(2);
			
			PdfPCell celdas = new PdfPCell(new Paragraph("Lista de entradas",FontFactory.getFont("Times New Roman", 15, Font.BOLD, BaseColor.WHITE)));
			celdas.setColspan(4);
			celdas.setHorizontalAlignment(Element.ALIGN_CENTER);
			celdas.setBackgroundColor(BaseColor.BLACK);
			table.addCell(celdas);
			
			
			PdfPCell personas =  new PdfPCell(new Paragraph("Personas",FontFactory.getFont("Times New Roman", 13, Font.BOLD, BaseColor.WHITE)));
			personas.setBackgroundColor(new BaseColor(0, 180, 139));
			
			PdfPCell cant = new PdfPCell(new Paragraph("Ganancia",FontFactory.getFont("Times New Roman", 13, Font.BOLD, BaseColor.WHITE)));
			cant.setBackgroundColor(new BaseColor(0, 180, 139));
			
	 	
			
			 
			table.addCell(personas);
			table.addCell(cant); 
		 
				table.addCell(datos[0]+"");
				table.addCell(datos[1]+""); 
	 
			doc.add(table);
			doc.close();
		} catch (Exception e) {
			 
		
	}
	}
	public void crearPDFAjustes(String direccion,ArrayList<ListaAjustesInventario> lista,AjusteInventario ajuste ) {
		try {
			FileOutputStream o = new FileOutputStream(direccion+".pdf");
			Document doc = new Document();
			PdfWriter.getInstance(doc, o);
			doc.open();
			
			doc.addAuthor("Mirador PH Reventazón Siquirres "); 
			doc.addCreator("Software PH"); 
		
			Paragraph titulo = new Paragraph();
			titulo.setAlignment(Paragraph.ALIGN_CENTER);
			titulo.setFont(FontFactory.getFont("Times New Roman", 24, Font.BOLD, BaseColor.BLACK));
			titulo.add("Mirador PH Reventazón");
			
			doc.add(titulo);
			

			doc.add(new Chunk());
			
			Paragraph fecha = new Paragraph();
			fecha.setAlignment(Paragraph.ALIGN_CENTER);
			fecha.setFont(FontFactory.getFont("Times New Roman", 10, Font.BOLD, BaseColor.DARK_GRAY));
			fecha.add("Fecha: "+ajuste.getFecha().substring(0,10));
			
			doc.add(fecha); 
			 
			
			//TABLA
			
			PdfPTable table = new PdfPTable(4);
			
			PdfPCell celdas = new PdfPCell(new Paragraph("Lista de Ajustes de Inventario",FontFactory.getFont("Times New Roman", 15, Font.BOLD, BaseColor.WHITE)));
			celdas.setColspan(4);
			celdas.setHorizontalAlignment(Element.ALIGN_CENTER);
			celdas.setBackgroundColor(BaseColor.BLACK);
			table.addCell(celdas);
			
			PdfPCell producto = new PdfPCell(new Paragraph("Producto",FontFactory.getFont("Times New Roman", 13, Font.BOLD, BaseColor.WHITE)));
			producto.setBackgroundColor(new BaseColor(0, 180, 139));
			
			PdfPCell precio =  new PdfPCell(new Paragraph("Precio",FontFactory.getFont("Times New Roman", 13, Font.BOLD, BaseColor.WHITE)));
			precio.setBackgroundColor(new BaseColor(0, 180, 139));
			
			PdfPCell cant = new PdfPCell(new Paragraph("Cantidad",FontFactory.getFont("Times New Roman", 13, Font.BOLD, BaseColor.WHITE)));
			cant.setBackgroundColor(new BaseColor(0, 180, 139));
			
			PdfPCell total = new PdfPCell(new Paragraph("Total",FontFactory.getFont("Times New Roman", 13, Font.BOLD, BaseColor.WHITE)));
			total.setBackgroundColor(new BaseColor(0, 180, 139));
			
			
			
			table.addCell(producto);
			table.addCell(precio);
			table.addCell(cant);
			table.addCell(total);
			

			int totalVar = 0;
			for (ListaAjustesInventario l  : lista) {
				table.addCell(l.getDescripcion());
				table.addCell(l.getPrecio()+"");
				table.addCell(l.getCantidad()+"");
				int totalMonto = l.getPrecio()*l.getCantidad();
				table.addCell(totalMonto+"");
				totalVar += totalMonto;
			}
			
			PdfPCell totolMonto = new PdfPCell(new Paragraph("Total:   ₡"+totalVar,FontFactory.getFont("Times New Roman", 15, Font.BOLD, BaseColor.WHITE)));
			totolMonto.setColspan(4);
			totolMonto.setHorizontalAlignment(Element.ALIGN_CENTER);
			totolMonto.setBackgroundColor(BaseColor.BLACK);
			table.addCell(totolMonto);
		 	
			doc.add(table);
			doc.close();
		} catch (Exception e) {
		}
	}
}
