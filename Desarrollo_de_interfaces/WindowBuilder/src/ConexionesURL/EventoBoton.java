package ConexionesURL;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class EventoBoton implements ActionListener{
	JTextField palabraES;
	JLabel palabraEN;
	public EventoBoton(JTextField palabraIN, JLabel palabraOUT) {
		this.palabraES = palabraIN;
		this.palabraEN = palabraOUT;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (control_errores()) {
			palabraEN.setText(Operaciones.traducir(palabraES.getText()));
		} else {
			palabraEN.setText("ERROR 1.\nEscribe una palabra en inglés");
		}
		
	}

	private boolean control_errores() {
		return !palabraES.getText().isBlank();
	}

}
