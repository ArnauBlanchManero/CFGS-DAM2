package ventanas;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Panel extends JPanel{
	
	public Panel(String mensaje) {
		super();
		setBackground(new Color(255, 118, 114));
		setLayout(null);
		JLabel info = new JLabel();
		add(info);
		info.setBounds(10, 10, 500, 10);
		info.setText(mensaje);
//		info.setHorizontalAlignment(SwingConstants.CENTER);
//		info.setVerticalAlignment(SwingConstants.CENTER);
	}

	public Panel() {
		super();
		setBackground(new Color(133, 239, 237));
		setLayout(null);
	}

}
