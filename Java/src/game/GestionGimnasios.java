package game;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GestionGimnasios {

	static Scanner sc = new Scanner(System.in);
	public static final String AMARILLO = "\u001B[33m";
	public static final String RESET = "\u001B[0m";
	static HashMap<String, String> preguntasRespuestas = new HashMap<>();

	public static void main(String[] args) {

		preguntasRespuestas.put("Nombre del Profesor Pokémon en Kanto", "Oak");
		preguntasRespuestas.put("Color de la Poké Ball original", "Rojo");
		preguntasRespuestas.put("Tipo de Pikachu", "Electrico");
		preguntasRespuestas.put("Evolución de Charmander", "Charmeleon");
		preguntasRespuestas.put("Tipo de Bulbasaur", "Planta");
		preguntasRespuestas.put("Tipo de Squirtle", "Agua");
		preguntasRespuestas.put("Equipo criminal en Pokémon Rojo y Azul", "Team Rocket");
		preguntasRespuestas.put("Tipo de Ekans", "Veneno");
		preguntasRespuestas.put("Evolución de Pidgey", "Pidgeotto");
		preguntasRespuestas.put("Nombre del rival en Pokémon Rojo y Azul", "Gary");
		preguntasRespuestas.put("Ciudad donde se encuentra el primer gimnasio", "Verde");
		preguntasRespuestas.put("Tipo de Mew", "Psíquico");
		preguntasRespuestas.put("Ciudad donde se encuentra la Liga Pokémon", "Meseta Añil");
		preguntasRespuestas.put("Tipo de Meowth", "Normal");
		preguntasRespuestas.put("Evolución de Oddish", "Gloom");
		preguntasRespuestas.put("Líder del gimnasio tipo Roca", "Brock");
		preguntasRespuestas.put("Evolución de Abra", "Kadabra");
		preguntasRespuestas.put("Ciudad donde se encuentra el equipo criminal", "Carmin");
		preguntasRespuestas.put("Tipo de Mankey", "Lucha");
		preguntasRespuestas.put("Líder del equipo criminal", "Giovanni");
		preguntasRespuestas.put("Evolución de Nidoran♂", "Nidorino");
		preguntasRespuestas.put("Evolución de Nidoran♀", "Nidorina");
		preguntasRespuestas.put("Evolución de Machop", "Machoke");
		preguntasRespuestas.put("Evolución de Poliwag", "Poliwhirl");
		preguntasRespuestas.put("Líder del gimnasio tipo Agua", "Misty");
		preguntasRespuestas.put("Evolución de Bellsprout", "Weepinbell");
		preguntasRespuestas.put("Evolución de Geodude", "Graveler");
		preguntasRespuestas.put("Tipo de Growlithe", "Fuego");
		preguntasRespuestas.put("Evolución de Vulpix", "Ninetales");
		preguntasRespuestas.put("Evolución de Tentacool", "Tentacruel");
		preguntasRespuestas.put("Líder del gimnasio tipo Eléctrico", "Lt Surge");
		preguntasRespuestas.put("Evolución de Ponyta", "Rapidash");
		preguntasRespuestas.put("Evolución de Slowpoke", "Slowbro");
		preguntasRespuestas.put("Tipo de Magnemite", "Eléctrico");
		preguntasRespuestas.put("Evolución de Doduo", "Dodrio");
		preguntasRespuestas.put("Evolución de Seel", "Dewgong");
		preguntasRespuestas.put("Líder del gimnasio tipo Psíquico", "Sabrina");
		preguntasRespuestas.put("Evolución de Grimer", "Muk");
		preguntasRespuestas.put("Evolución de Shellder", "Cloyster");
		preguntasRespuestas.put("Tipo de Gastly", "Fantasma");
		preguntasRespuestas.put("Evolución de Drowzee", "Hypno");
		preguntasRespuestas.put("Evolución de Krabby", "Kingler");
		preguntasRespuestas.put("Líder del gimnasio tipo Planta", "Erika");
		preguntasRespuestas.put("Evolución de Cubone", "Marowak");
		preguntasRespuestas.put("Evolución de Hitmonlee", "Hitmonchan");
		preguntasRespuestas.put("Tipo de Koffing", "Veneno");
		preguntasRespuestas.put("Evolución de Rhyhorn", "Rhydon");
		preguntasRespuestas.put("Evolución de Tangela", "Tangrowth");
		preguntasRespuestas.put("Líder del gimnasio tipo Lucha", "Chuck");
		preguntasRespuestas.put("Evolución de Horsea", "Seadra");
		preguntasRespuestas.put("Evolución de Goldeen", "Seaking");
		preguntasRespuestas.put("Tipo de Staryu", "Agua");
		preguntasRespuestas.put("Evolución de Scyther", "Scizor");

	}

	public static void retarGimnasio() {
	    System.out.println("\n== GIMNASIO ========================\n");
	    System.out.println("¡Prepárate para retar al gimnasio!");
	    System.out.println("Responde correctamente 5 preguntas para demostrar tu habilidad.\n");

	    int correctas = 0;
	    for (Map.Entry<String, String> entry : preguntasRespuestas.entrySet()) {
	        System.out.println(entry.getKey() + ": ");
	        String respuestaUsuario = sc.nextLine().trim();
	        if (respuestaUsuario.equalsIgnoreCase(entry.getValue())) {
	            correctas++;
	            System.out.println("¡Correcto!\n");
	        } else {
	            System.out.println("Respuesta incorrecta. La respuesta correcta es: " + entry.getValue() + "\n");
	        }

	        System.out.println("\n====================================\n");

	        if (correctas >= 5) {
	            System.out.println("¡Felicidades! Has derrato el gimnasio.");
	            break;
	        }
	    }
	}



}
