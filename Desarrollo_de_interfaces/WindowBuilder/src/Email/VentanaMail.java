package Email;

import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Rectangle;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class VentanaMail extends JFrame{
	private JTextField correoUsuario;
	private JTextField asuntoUsuario;
	private JTextField cuerpoUsuario;

	public VentanaMail(String title) throws HeadlessException {
		super();
		setBounds(new Rectangle(0, 0, 800, 800));
		setTitle(title);
		PanelEmail panelPrincipal = new PanelEmail();
		getContentPane().add(panelPrincipal);
		
		JLabel correoEnviar = new JLabel("Escribe tu correo");
		correoEnviar.setBounds(236, 178, 137, 14);
		panelPrincipal.add(correoEnviar);
		
		JLabel asunto = new JLabel("Asunto");
		asunto.setBounds(236, 235, 137, 14);
		panelPrincipal.add(asunto);
		
		JLabel cuerpo = new JLabel("Cuerpo");
		cuerpo.setBounds(236, 289, 46, 14);
		panelPrincipal.add(cuerpo);
		
		correoUsuario = new JTextField();
		correoUsuario.setBounds(417, 175, 170, 20);
		panelPrincipal.add(correoUsuario);
		correoUsuario.setColumns(10);
		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.setBounds(349, 398, 89, 23);
		panelPrincipal.add(btnEnviar);
		
		asuntoUsuario = new JTextField();
		asuntoUsuario.setBounds(417, 232, 170, 20);
		panelPrincipal.add(asuntoUsuario);
		asuntoUsuario.setColumns(10);
		
		cuerpoUsuario = new JTextField();
		cuerpoUsuario.setBounds(417, 286, 170, 71);
		panelPrincipal.add(cuerpoUsuario);
		cuerpoUsuario.setColumns(10);
		
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleEmail.prepararEmail(correoUsuario.getText(), asuntoUsuario.getText(), cuerpoUsuario.getText());
			}
		});
		
	}
}
