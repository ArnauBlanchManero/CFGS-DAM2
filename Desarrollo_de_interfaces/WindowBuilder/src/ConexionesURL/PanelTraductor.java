package ConexionesURL;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class PanelTraductor extends JPanel{
	private JTextField palabraTraducida;
	public PanelTraductor() {
		setForeground(new Color(0, 0, 255));
		setBackground(new Color(0, 128, 255));
		setLayout(null);
		
		JLabel textoIntroductor = new JLabel("Bienvenido al tradutor de español a inglés");
		textoIntroductor.setHorizontalAlignment(SwingConstants.CENTER);
		textoIntroductor.setBounds(227, 60, 305, 14);
		add(textoIntroductor);
		
		palabraTraducida = new JTextField();
		palabraTraducida.setBounds(338, 101, 86, 20);
		add(palabraTraducida);
		palabraTraducida.setColumns(10);
		
		JLabel palabraUsuario = new JLabel("");
		palabraUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		palabraUsuario.setBounds(271, 233, 227, 14);
		add(palabraUsuario);
		
		JButton btnTraducir = new JButton("Traducir");
		btnTraducir.setBounds(338, 158, 89, 23);
		EventoBoton traducir = new EventoBoton(palabraTraducida, palabraUsuario );
		add(btnTraducir);
		
		btnTraducir.addActionListener(traducir);
	}
}
