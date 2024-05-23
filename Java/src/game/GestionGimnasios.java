package game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestionGimnasios extends Gestion {

	public static final String ROJO = "\u001B[31m";
	public static final String VERDE = "\u001B[32m";

	static HashMap<String, String> preguntasRespuestas = new HashMap<String, String>();

	public static void pregunstasRespuestasInit() {
		preguntasRespuestas.put("Nombre del Profesor Pokémon en Kanto", "Oak");
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
		preguntasRespuestas.put("Tipo de Magnemite", "Electrico");
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
		preguntasRespuestas.put("Tipo de Hitmonlee", "Lucha");
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
		int idEntrenador = GestionEntrenadores.elegirEntrenador();
		if (idEntrenador == -1) {
			System.out.println(
					ROJO + "Error al seleccionar el entrenador o no hay entrenadores disponibles. Inténtalo de nuevo."
							+ RESET);
			return;
		}

		System.out.println("\n==" + AMARILLO + " GIMNASIO " + RESET + "========================\n");

		String queryMedallas = "SELECT COUNT(*) AS medallas FROM Estuche WHERE idEntrenador = ?";
		try (Connection connection = DriverManager.getConnection(url, user, password)) {
			PreparedStatement stmtMedallas = connection.prepareStatement(queryMedallas);
			stmtMedallas.setInt(1, idEntrenador);
			ResultSet rsMedallas = stmtMedallas.executeQuery();
			if (rsMedallas.next()) {
				int numMedallas = rsMedallas.getInt("medallas");
				if (numMedallas < 8) {

					System.out.println("¡Prepárate para retar al gimnasio!");
					System.out.println("Responde correctamente 5 preguntas para demostrar tu habilidad.\n");

					List<Map.Entry<String, String>> preguntasSeleccionadas = seleccionarPreguntasAleatorias(5);

					int correctas = 0;
					for (Map.Entry<String, String> entry : preguntasSeleccionadas) {
						System.out.print(entry.getKey() + ": ");
						String respuestaUsuario = sc.nextLine().trim();
						if (respuestaUsuario.equalsIgnoreCase(entry.getValue())) {
							correctas++;
							System.out.println(VERDE + "¡Correcto!\n" + RESET);
						} else {
							System.out.println(ROJO + "Respuesta incorrecta." + RESET + " La respuesta correcta es: "
									+ entry.getValue() + "\n");
						}
					}

					if (correctas == 5) {
						System.out.println("¡Felicidades! Has derrotado al gimnasio.");
						otorgarMedalla(idEntrenador);
					} else {
						System.out.println("¡Has perdido! Sigue intentándolo.");
					}

				} else {
					System.out.println("¡Ya has conseguido todas las medallas!");
				}
			}
		} catch (SQLException e) {
			e.getMessage();
		}

	}

	private static List<Map.Entry<String, String>> seleccionarPreguntasAleatorias(int cantidad) {
		List<Map.Entry<String, String>> preguntasList = new ArrayList<>(preguntasRespuestas.entrySet());
		Collections.shuffle(preguntasList);
		return preguntasList.subList(0, cantidad);
	}

	private static void otorgarMedalla(int idEntrenador) {
		String queryMedallas = "SELECT COUNT(*) AS medallas FROM Estuche WHERE idEntrenador = ?";
		String queryInsertMedalla = "INSERT INTO Estuche (idEntrenador, idMedalla) VALUES (?, ?)";
		String queryNextMedalla = "SELECT COALESCE(MAX(idMedalla), 0) + 1 AS nextMedalla FROM Estuche WHERE idEntrenador = ?";

		try (Connection connection = DriverManager.getConnection(url, user, password)) {
			PreparedStatement stmtMedallas = connection.prepareStatement(queryMedallas);
			stmtMedallas.setInt(1, idEntrenador);
			ResultSet rsMedallas = stmtMedallas.executeQuery();
			if (rsMedallas.next()) {
				int numMedallas = rsMedallas.getInt("medallas");
				if (numMedallas < 8) {
					PreparedStatement stmtNextMedalla = connection.prepareStatement(queryNextMedalla);
					stmtNextMedalla.setInt(1, idEntrenador);
					ResultSet rsNextMedalla = stmtNextMedalla.executeQuery();
					if (rsNextMedalla.next()) {
						int nextMedalla = rsNextMedalla.getInt("nextMedalla");

						PreparedStatement stmtInsertMedalla = connection.prepareStatement(queryInsertMedalla);
						stmtInsertMedalla.setInt(1, idEntrenador);
						stmtInsertMedalla.setInt(2, nextMedalla);
						stmtInsertMedalla.executeUpdate();
						System.out.println("¡Has ganado la medalla número " + nextMedalla + "!");
					}
				} else {
					System.out.println("¡Ya has conseguido todas las medallas!");
				}
			}
		} catch (SQLException e) {
			e.getMessage();
		}
	}

	public static void mostrarEstuche(int idEntrenador) {
		System.out.println("\n==" + AMARILLO + " ESTUCHE " + RESET + "=============== Nº ID/" + idEntrenador + " ==\n");

		try {
			String query = "SELECT Medalla.* FROM Estuche JOIN Medalla ON Estuche.idMedalla = Medalla.idMedalla WHERE idEntrenador = ?";
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, idEntrenador);
			ResultSet resultSet = pstmt.executeQuery();

			while (resultSet.next()) {
				int idMedalla = resultSet.getInt("idMedalla");
				String nombreMedalla = resultSet.getString("nombre");
				String ciudad = resultSet.getString("ciudad");
				String lider = resultSet.getString("lider");
				System.out.println(
						idMedalla + ". " + nombreMedalla + "\n  Ciudad: " + ciudad + "\n  Lider: " + lider + "\n");
			}

		} catch (SQLException e) {
			System.out.println("Error al obtener el estuche: " + e.getMessage());
		}

		System.out.println("\n=====================================");
	}
}
