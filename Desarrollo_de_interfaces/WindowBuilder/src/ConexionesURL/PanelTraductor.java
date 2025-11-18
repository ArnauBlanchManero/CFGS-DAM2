package ConexionesURL;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;

public class PanelTraductor extends JPanel{
	public PanelTraductor() {
		setForeground(new Color(0, 0, 255));
		setBackground(new Color(0, 128, 255));
		setLayout(null);
		
		JLabel textoIntroductor = new JLabel("Bienvenido al tradutor de español a inglés");
		textoIntroductor.setBounds(89, 69, 305, 14);
		add(textoIntroductor);
	}
}
