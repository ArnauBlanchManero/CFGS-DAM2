package usuarios;

import java.util.ArrayList;

public class Usuario {
	private int id;
	private String nombre;
	private String correo;
	private String passwd;
	private boolean admin;
	private int vecesLogueado;
	private boolean [] categorias;
	
	public Usuario(int id, String nombre, String correo, String passwd, boolean admin, int vecesLogueado, boolean [] categorias) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.correo = correo;
		this.passwd = passwd;
		this.admin = admin;
		this.vecesLogueado = vecesLogueado;
		this.categorias = categorias;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public int getVecesLogueado() {
		return vecesLogueado;
	}

	public void setVecesLogueado(int vecesLogueado) {
		this.vecesLogueado = vecesLogueado;
	}

	public boolean[] getCategorias() {
		return categorias;
	}

	public void setCategorias(boolean[] categorias) {
		this.categorias = categorias;
	}

	public int comprobarCredenciales(ArrayList<Usuario> listaUsuarios) {
		boolean nombreCorrecto = false;
		boolean contraseñaCorrecta = false;
		for (Usuario usuario : listaUsuarios) {
			if (usuario.nombre.equals(this.nombre) && usuario.passwd.equals(this.passwd)) {
				nombreCorrecto = true;
				contraseñaCorrecta = true;
				this.id = usuario.id;
				this.correo = usuario.correo;
				this.vecesLogueado = usuario.vecesLogueado;
				this.admin = usuario.admin;
				this.categorias = usuario.categorias;
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
