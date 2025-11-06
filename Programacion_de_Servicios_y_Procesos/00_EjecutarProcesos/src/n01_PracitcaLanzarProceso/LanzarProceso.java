package n01_PracitcaLanzarProceso;

import java.util.ArrayList;
import java.util.List;

public class LanzarProceso {
	public void ejecutar() {
		List <String> nombreArgumentos = new ArrayList<>();
		nombreArgumentos.add("C:/MyCode/Sum2num.exe");
		nombreArgumentos.add("18");
		nombreArgumentos.add("20");
		ProcessBuilder pb = new ProcessBuilder();
		try {
			Process proceso = pb.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
