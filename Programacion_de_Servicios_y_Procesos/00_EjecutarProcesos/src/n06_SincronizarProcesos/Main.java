package n06_SincronizarProcesos;

public class Main {

	public static void main(String[] args) {
		GenerarProceso levantar = new GenerarProceso();
		String comando = "cmd.exe";
		String[] parametros1 = {"/C","start","dir", "/p"};
		//String[] parametros2 = {"C:/Users/DAM/Documents/Datos.txt"};
		//levantar.ejecutar("C:/WINDOWS/system32/cmd.exe");
		//levantar.ejecutar("C:/WINDOWS/system32/msinfo32.exe");
		//levantar.ejecutar("C:/WINDOWS/system32/WindowsPowerShell/v1.0/powershell.exe");
		//levantar.ejecutar("C:/WINDOWS/system32/Taskmgr.exe");
		levantar.ejecutar(comando, parametros1);
		//levantar.ejecutar("C:/windows/system32/notepad.exe");
		//levantar.ejecutar("notepad.exe", parametros2);
		System.out.println("Proceso ejecutado.");

	}

}
