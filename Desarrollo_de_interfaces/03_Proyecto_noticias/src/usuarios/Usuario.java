package usuarios;

import java.util.ArrayList;

public class Usuario {
	private String nombre;
	private String correo;
	private String passwd;
	private boolean admin;
	private int vecesLogueado;
	
	public Usuario(String nombre, String correo, String passwd, boolean admin, int vecesLogueado) {
		super();
		this.nombre = nombre;
		this.correo = correo;
		this.passwd = passwd;
		this.admin = admin;
		this.vecesLogueado = vecesLogueado;
	}
	
	public int comprobarCredenciales(ArrayList<Usuario> listaUsuarios) {
		boolean nombreCorrecto = false;
		boolean contraseñaCorrecta = false;
		for (Usuario usuario : listaUsuarios) {
			if (usuario.nombre.equals(this.nombre) && usuario.passwd.equals(this.passwd)) {
				nombreCorrecto = true;
				contraseñaCorrecta = true;
				this.correo = usuario.correo;
				this.vecesLogueado = usuario.vecesLogueado;
				this.admin = usuario.admin;
			}
		}
		if (nombreCorrecto && contraseñaCorrecta) {
			if (this.admin) {
				return 1;
			}
			return 0;
		}
		return -1;
	}
}
