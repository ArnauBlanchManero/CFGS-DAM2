package n02_AtrapaMosca;


import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Mosca extends JLabel{
	private String mosca;
	private JLabel moscaLabel = new JLabel();
	
	public Mosca(String ruta) {
		super();
		mosca = ruta;
		moscaLabel.setIcon(new ImageIcon(new ImageIcon(PanelMosca.class.getResource(mosca)).getImage().getScaledInstance(60, 62, java.awt.Image.SCALE_SMOOTH)));
		moscaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		moscaLabel.setBounds(315, 267, 200, 200);
	}

	public Mosca(String nombre, int ancho, int alto) {
		super();
		moscaLabel.setText(nombre);
		moscaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		moscaLabel.setBounds(0, 0, ancho, alto);
	}

	public JLabel getMoscaLabel() {
		return moscaLabel;
	}

	public void setMoscaLabel(JLabel moscaLabel) {
		this.moscaLabel = moscaLabel;
	}
	
	public String getName() {
		return mosca;
	}

	public void setName(String mosca) {
		this.mosca = mosca;
	}

	public void morir() {
		this.setName("/AtrapaMosca/mosca_muerta.png");
		moscaLabel.setIcon(new ImageIcon(new ImageIcon(PanelMosca.class.getResource(mosca)).getImage().getScaledInstance(60, 62, java.awt.Image.SCALE_SMOOTH)));
		
	}
	
}
