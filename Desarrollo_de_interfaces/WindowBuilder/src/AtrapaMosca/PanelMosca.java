package AtrapaMosca;

import javax.swing.JPanel;
import java.awt.Color;

import javax.swing.JLabel;
import java.awt.Rectangle;

import javax.swing.SwingConstants;

public class PanelMosca extends JPanel{
	public PanelMosca() {
		setBounds(new Rectangle(0, 0, 750, 750));
		setBackground(new Color(133, 239, 237));
		setLayout(null);
		
		/*
		Mosca moscaGeneral = new Mosca("Mosca", 50, 100);
		add(moscaGeneral.getMoscaLabel());
		*/
		Mosca moscaViva = new Mosca("/AtrapaMosca/mosca_viva.png");
		add(moscaViva.getMoscaLabel());
		EventosRaton eventosMosca = new EventosRaton(moscaViva);
		moscaViva.getMoscaLabel().addMouseListener(eventosMosca);
		
		JLabel informacion = new JLabel("Posición del ratón: "+eventosMosca.posicionString());
		informacion.setHorizontalAlignment(SwingConstants.LEFT);
		informacion.setBounds(10, 723, 730, 27);
		add(informacion);
		 /*
		JLabel imgMosca = new JLabel();
		imgMosca.addMouseListener(eventosMosca);
		/*
		 * 
		JLabel imgMosca = new JLabel();
		imgMosca.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
			}
		});
		ImageIcon img1 = new ImageIcon(PanelMosca.class.getResource("/AtrapaMosca/mosca_viva.png"));
		Image img2 = img1.getImage().getScaledInstance(60, 62, java.awt.Image.SCALE_SMOOTH);
		imgMosca.setIcon(new ImageIcon(img2));
		*//*
		imgMosca.setIcon(new ImageIcon(new ImageIcon(PanelMosca.class.getResource("/AtrapaMosca/mosca_viva.png")).getImage().getScaledInstance(60, 62, java.awt.Image.SCALE_SMOOTH)));
		imgMosca.setHorizontalAlignment(SwingConstants.CENTER);
		imgMosca.setBounds(365, 317, 100, 100);
		add(imgMosca);
		
		*/
		
	}
}
