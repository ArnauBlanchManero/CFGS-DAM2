package AtrapaMosca;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class EventosRaton implements MouseListener {
	private Random rand = new Random();
	
	Mosca mosca;
	
	public EventosRaton(Mosca mosca) {
		super();
		this.mosca = mosca;
	}

	public static String posicionString() {
		return "";
	}

	public Mosca getMosca() {
		return mosca;
	}

	public void setMosca(Mosca mosca) {
		this.mosca = mosca;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("El raton ha dado un click");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
//		mosca.setBounds(rand.nextInt()/700+25,rand.nextInt()/700+25, 100,100);
		mosca.setBounds(0,0, 100,100);
		System.out.println("El raton se está moviendo");
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

}
