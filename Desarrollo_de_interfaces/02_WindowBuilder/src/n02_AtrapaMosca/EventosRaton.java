package n02_AtrapaMosca;

import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class EventosRaton implements MouseListener {
	private Random rand = new Random();
	
	Mosca mosca;
	PanelMosca panel;
	public EventosRaton(Mosca mosca) {
		super();
		this.mosca = mosca;
	}

	public EventosRaton(PanelMosca panelPrincipal, Mosca mosca) {
		super();
		this.panel = panelPrincipal;
		this.mosca = mosca;
	}

	public static String posicionString() {
		return MouseInfo.getPointerInfo().getLocation().getX()+" "+
				MouseInfo.getPointerInfo().getLocation().getY();
	}

	public Mosca getMosca() {
		return mosca;
	}

	public void setMosca(Mosca mosca) {
		this.mosca = mosca;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//MouseInfo.getPointerInfo().getLocation().getX();
//		System.out.println("El raton ha dado un click "+
//		MouseInfo.getPointerInfo().getLocation().getX()+" "+
//		MouseInfo.getPointerInfo().getLocation().getY());
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//System.out.println("El raton se ha pulsado");
		mosca.morir();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(mosca.getName().contains("viva"))
			mosca.getMoscaLabel().setBounds(rand.nextInt(600),rand.nextInt(600), 200,200);

	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
