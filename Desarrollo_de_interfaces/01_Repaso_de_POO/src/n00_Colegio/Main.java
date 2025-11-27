package n00_Colegio;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	static Scanner entrada = new Scanner(System.in);
	public static void main(String[] args) {
		Profesor profe1 = new Profesor("123654789T", "Sergio", "Sánchez", 1800, 2, false);
		Administracion admin1 = new Administracion("987456312C", "Eva", "Ariño", 1200, "ESO", 15);
		Directivo director1 = new Directivo("147852369L", "Manuel", "Del Cerro", 2400, true, Turno.MAÑANA);
		Modulo modulo1 = new Modulo("Programación", 270, profe1, false);
		Modulo modulo2 = new Modulo("BBDD", 190, profe1, false);
		List<Modulo> modulos = new ArrayList<Modulo>();
		modulos.add(modulo1);
		modulos.add(modulo2);
		Alumno alumno1 = new Alumno("26575274Z", "Arnau", "Blanch", 0, "16/07/2006", Sexo.HOMBRE, false, modulos);
		System.out.println("Toda la información creada: ");
		System.out.println(profe1.toString());
		System.out.println(admin1.toString());
		System.out.println(director1.toString());
		System.out.println(modulo1.toString());
		System.out.println(modulo2.toString());
		System.out.println(alumno1.toString());
		System.out.println("Ahora te toca añadir datos a ti");
		Profesor profe2 = new Profesor(null, null, null, 0, 0, false);
		Administracion admin2 = new Administracion(null, null, null, 0, null, 0);
		Directivo director2 = new Directivo(null, null, null, 0, false, null);
		Modulo modulo3 = new Modulo(null, 0, profe2, false);
		Alumno alumno2 = new Alumno(null, null, null, 0, null, null, false, modulos);
		String respuesta;
		System.out.println("Datos del profesor:");
		System.out.print("DNI: ");
		profe2.setDni(entrada.next());
		entrada.nextLine();
		System.out.print("Nombre: ");
		profe2.setNombre(entrada.nextLine());
		System.out.print("Apellido: ");
		profe2.setApellidos(entrada.nextLine());
		System.out.print("Salario: ");
		profe2.setSalario(entrada.nextInt());
		System.out.print("Número de asignaturas: ");
		profe2.setN_asignaturas(entrada.nextInt());
		entrada.nextLine();
		System.out.print("¿Es tutor? (si o no): ");
		respuesta = entrada.next();
		entrada.nextLine();
		System.out.println("Respuesta: "+respuesta);
		if (respuesta.equalsIgnoreCase("si")) {
			profe2.setTutor(true);
		} else if (respuesta.equalsIgnoreCase("no")){
			profe2.setTutor(false);
		} else {
			System.out.println("Error al interpretar tu mensaje");
			System.out.println("Se pone en no tutor automaticamente");
			profe2.setTutor(false);
		}
		System.out.println("Datos del administrador:");
		System.out.print("DNI: ");
		admin2.setDni(entrada.next());
		entrada.nextLine();
		System.out.print("Nombre: ");
		admin2.setNombre(entrada.nextLine());
		System.out.print("Apellido: ");
		admin2.setApellidos(entrada.nextLine());
		System.out.print("Salario: ");
		admin2.setSalario(entrada.nextInt());
		System.out.print("Antiduedad: ");
		admin2.setAntiguedad(entrada.nextInt());
		System.out.print("Estudios: ");
		admin2.setEstudios(entrada.next());
		entrada.nextLine();
		System.out.println("Datos del director:");
		System.out.print("DNI: ");
		director2.setDni(entrada.next());
		entrada.nextLine();
		System.out.print("Nombre: ");
		director2.setNombre(entrada.nextLine());
		System.out.print("Apellido: ");
		director2.setApellidos(entrada.nextLine());
		System.out.print("Salario: ");
		director2.setSalario(entrada.nextInt());
		entrada.nextLine();
		System.out.print("¿Es salesiano? (si o no): ");
		respuesta = entrada.next();
		entrada.nextLine();
		if (respuesta.equalsIgnoreCase("si")) {
			director2.setSalesiano(true);
		} else if (respuesta.equalsIgnoreCase("no")){
			director2.setSalesiano(false);
		} else {
			System.out.println("Error al interpretar tu mensaje");
			System.out.println("Se pone en no salesiano automaticamente");
			director2.setSalesiano(false);
		}
		System.out.print("¿Qué turno tiene? (M = mañana o T = tarde): ");
		respuesta = entrada.next();
		entrada.nextLine();
		if (respuesta.equalsIgnoreCase("M")) {
			director2.setTurno(Turno.MAÑANA);
		} else if (respuesta.equalsIgnoreCase("T")){
			director2.setTurno(Turno.TARDE);
		} else {
			System.out.println("Error al interpretar tu mensaje");
			System.out.println("Se pone en turno de mañana automaticamente");
			director2.setTurno(Turno.MAÑANA);
		}
		System.out.println("Datos del modulo:");
		System.out.print("Nombre: ");
		modulo3.setNombre(entrada.nextLine());
		System.out.print("Horas: ");
		modulo3.setHoras(entrada.nextInt());
		entrada.nextLine();
		System.out.print("¿Qué profesor tiene? (1 = "+profe1.getNombre()+" o 2 = "+profe2.getNombre()+"): ");
		respuesta = entrada.next();
		entrada.nextLine();
		if (respuesta.equalsIgnoreCase("1")) {
			modulo3.setProfesor(profe1);
		} else if (respuesta.equalsIgnoreCase("2")){
			modulo3.setProfesor(profe2);
		} else {
			System.out.println("Error al interpretar tu mensaje");
			System.out.println("Se pone el profesor 1 automaticamente");
			modulo3.setProfesor(profe1);
		}
		System.out.print("¿Es convalidable? (si o no): ");
		respuesta = entrada.next();
		entrada.nextLine();
		if (respuesta.equalsIgnoreCase("si")) {
			modulo3.setConvalidable(true);
		} else if (respuesta.equalsIgnoreCase("no")){
			modulo3.setConvalidable(false);
		} else {
			System.out.println("Error al interpretar tu mensaje");
			System.out.println("Se pone en no convalidable automaticamente");
			modulo3.setConvalidable(false);
		}
		System.out.println("Datos del alumno:");
		System.out.print("DNI: ");
		alumno2.setDni(entrada.next());
		entrada.nextLine();
		System.out.print("Nombre: ");
		alumno2.setNombre(entrada.nextLine());
		System.out.print("Apellidos: ");
		alumno2.setApellidos(entrada.nextLine());
		System.out.print("Fecha de nacimiento: ");
		alumno2.setFehca_nac(entrada.nextLine());
		System.out.print("¿Qué sexo tiene? (H = hombre o M = mujer): ");
		respuesta = entrada.next();
		entrada.nextLine();
		if (respuesta.equalsIgnoreCase("M")) {
			alumno2.setSexo(Sexo.MUJER);
		} else if (respuesta.equalsIgnoreCase("H")){
			alumno2.setSexo(Sexo.HOMBRE);
		} else {
			System.out.println("Error al interpretar tu mensaje");
			System.out.println("Se pone en hombre automaticamente");
			alumno2.setSexo(Sexo.HOMBRE);
		}
		System.out.print("¿Es repetidor? (si o no): ");
		respuesta = entrada.next();
		entrada.nextLine();
		if (respuesta.equalsIgnoreCase("si")) {
			alumno2.setRepetidor(true);
		} else if (respuesta.equalsIgnoreCase("no")){
			alumno2.setRepetidor(false);
		} else {
			System.out.println("Error al interpretar tu mensaje");
			System.out.println("Se pone en no repetidor automaticamente");
			alumno2.setRepetidor(false);
		}
		List <Modulo> modulos2 = new ArrayList<Modulo>();
		for (int i = 0; i < 3; i++) {
			System.out.print("¿Qué modulos tiene? (1 = "+modulo1.getNombre()+" o 2 = "+modulo2.getNombre()+" o 3 = "+modulo3.nombre+"): ");
			respuesta = entrada.next();
			if (respuesta.equalsIgnoreCase("1")) {
				modulos2.add(modulo1);
			} else if (respuesta.equalsIgnoreCase("2")){
				modulos2.add(modulo2);
			} else if (respuesta.equalsIgnoreCase("3")){
				modulos2.add(modulo3);
			} else {
				System.out.println("Error al interpretar tu mensaje");
				System.out.println("Se pone el modulo 1 automaticamente");
				modulos2.add(modulo1);
			}
			entrada.nextLine();
				System.out.print("¿Añadir otro módulo? (si o no): ");
				respuesta = entrada.next();
				entrada.nextLine();
				if (respuesta.equalsIgnoreCase("no")) {
					if (i==0) {
						System.out.println("No has añadido ningún módulo");
					} else {
						System.out.println("No se añadirán más modulos");
						i=3;
					}
				}
		}
		alumno2.setModulos(modulos2);
		System.out.println("Toda la información creada: ");
		System.out.println(profe2.toString());
		System.out.println(admin2.toString());
		System.out.println(director2.toString());
		System.out.println(modulo3.toString());
		System.out.println(alumno2.toString());
		
		System.out.println("###########################################################################################");
		Profesor profe3 = new Profesor("54320986G", "Kevin Jose", "Arriaga", 3500, 1, false);
		List<Profesor> profesores = new ArrayList<Profesor>();
		profesores.add(profe1);
		profesores.add(profe3);
		profesores.add(profe2);
		for (Profesor profesor : profesores) {
			System.out.println(profesor.toString());
		}
		profesores.sort(null);
		System.out.println("Ordenados por salario: ");
		for (Profesor profesor : profesores) {
			System.out.println(profesor.toString());
		}
		System.out.println("El que más cobra: ");
		System.out.println(profesores.get(profesores.size()-1));
		System.out.println("El que menos cobra: ");
		System.out.println(profesores.get(0));
	}

}