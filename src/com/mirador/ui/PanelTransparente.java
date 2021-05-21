package com.mirador.ui;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
public class PanelTransparente extends JPanel {
	
	public PanelTransparente(Color c){
	 setBackground(c);
	}

	@Override
	protected void paintComponent(Graphics g) {
	 Graphics2D g2 = (Graphics2D) g;
	 g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
	 RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	 AlphaComposite old = (AlphaComposite) g2.getComposite();
	 g2.setComposite(AlphaComposite.SrcOver.derive(0.8f));
	 super.paintComponent(g);
	 g2.setComposite(old);
	}
}
	

