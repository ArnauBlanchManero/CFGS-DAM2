package usuarios;

import java.util.ArrayList;

import txt.LeerTxt;

public class Usuario {
	private int id;
	private String nombre;
	private String correo;
	private String passwd;
	private boolean admin;
	private boolean [] categorias;
	
	public Usuario(int id, String nombre, String correo, String passwd, boolean admin, boolean [] categorias) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.correo = correo;
		this.passwd = passwd;
		this.admin = admin;
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

	public boolean[] getCategorias() {
		return categorias;
	}

	public void setCategorias(boolean[] categorias) {
		this.categorias = categorias;
	}

	public int comprobarCredenciales(ArrayList<Usuario> listaUsuarios) {
		boolean inicioCorrecto = false;
		for (Usuario usuario : listaUsuarios) {
			if (usuario.nombre.equals(this.nombre) && usuario.passwd.equals(this.passwd)) {
				inicioCorrecto = true;
				this.id = usuario.id;
				this.correo = usuario.correo;
				this.admin = usuario.admin;
				this.categorias = LeerTxt.leerCategorias(usuario.id);
			}
		}
		if (inicioCorrecto) {
			if (this.admin) {
				return 1;
			}
			return 0;
		}
		return -1;
	}
}
