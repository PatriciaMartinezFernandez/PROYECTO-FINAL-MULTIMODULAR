package game;

import java.util.ArrayList;

public class GestionEntrenadores {

	private static ArrayList<Entrenador> entrenadores = new ArrayList<Entrenador>();

	public static void imprimirEntrenadores() {

		if (entrenadores.isEmpty()) {
			System.out.println("No hay entrenadores registrados");
		} else {
			for (Entrenador entrenador : entrenadores) {
				System.out.println(entrenador.toString());
			}
		}
	}

}
