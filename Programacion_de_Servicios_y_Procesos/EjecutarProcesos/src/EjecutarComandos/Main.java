package EjecutarComandos;

public class Main {

	public static void main(String[] args) {
		ComandoTerminal levantar = new ComandoTerminal();
		String comando = "ipconfig";
		String[] parametros = {"/all"};
		//levantar.ejecutar("C:/WINDOWS/system32/cmd.exe");
		//levantar.ejecutar("C:/WINDOWS/system32/msinfo32.exe");
		//levantar.ejecutar("C:/WINDOWS/system32/WindowsPowerShell/v1.0/powershell.exe");
		//levantar.ejecutar("C:/WINDOWS/system32/Taskmgr.exe");
		levantar.ejecutar(comando, parametros);
		System.out.println("Proceso ejecutado.");

	}

}
