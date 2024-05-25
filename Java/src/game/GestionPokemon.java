package game;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * La clase GestionPokemon proporciona métodos para gestionar la información de
 * los Pokémons en la Pokédex. Incluye selección, visualización de información y
 * búsqueda por nombre.
 * 
 * @author Jaime Martínez Fernández
 */

public class GestionPokemon extends Gestion {

	static int idPokemon;
	private static Scanner sc = new Scanner(System.in);

	/**
	 * Muestra la lista de Pokémon disponibles y permite al usuario elegir uno para
	 * ver su información.
	 */

	public static void elegirPokemon() {
		System.out.println("\nLista de Pokémon:");
		String queryPokemon = "SELECT * FROM VistaPokemons";
		try (PreparedStatement stmtPokemon = connection.prepareStatement(queryPokemon)) {
			ResultSet rsPokemon = stmtPokemon.executeQuery();
			int count = 0;
			while (rsPokemon.next()) {
				if (count % 10 == 0 && count != 0) {
					System.out.println();
				}
				System.out.print(rsPokemon.getInt("idPokemon") + ". " + rsPokemon.getString("nombrePokemon") + "\t");
				count++;
			}
		} catch (SQLException e) {
			System.out.println("Error en la consulta SQL: " + e.getMessage());
		}

		System.out.print("\nIntroduce el ID o nombre del Pokémon del que quieres ver información: ");
		String pokemonInput = sc.nextLine().trim();

		try {
			idPokemon = Integer.parseInt(pokemonInput);
		} catch (NumberFormatException e) {
			idPokemon = -1;
		}

		if (idPokemon != -1) {
			infoPokedex(idPokemon);
		} else {
			try {
				idPokemon = buscarIdPokemonPorNombre(pokemonInput);
			} catch (SQLException e) {
				e.getMessage();
			}
			if (idPokemon != -1) {
				infoPokedex(idPokemon);
			} else {
				System.out.println("No se encontró ningún Pokémon con ese nombre.");
			}
		}
	}

	/**
	 * Muestra la información del Pokémon especificado por su ID.
	 * 
	 * @param idPokemon ID del Pokémon cuya información se va a mostrar
	 */

	private static void infoPokedex(int idPokemon) {
		try {
			String callProcedure = "{CALL ObtenerInfoPokemonPorID(?)}";
			CallableStatement cstmt = connection.prepareCall(callProcedure);
			cstmt.setInt(1, idPokemon);
			ResultSet rsInfo = cstmt.executeQuery();
			if (rsInfo.next()) {
				idPokemon = rsInfo.getInt("idPokemon");
				String nombrePokemon = rsInfo.getString("nombrePokemon");
				String tipoPrimario = rsInfo.getString("tipoPrimario");
				String tipoSecundario = rsInfo.getString("tipoSecundario");
				double altura = rsInfo.getDouble("altura");
				double peso = rsInfo.getDouble("peso");
				String descripcion = rsInfo.getString("descripcion");

				fichaPokemon(idPokemon, nombrePokemon, tipoPrimario, tipoSecundario, altura, peso, descripcion);

			} else {
				System.out.println("No se encontró ningún Pokémon con ese ID.");
			}
		} catch (SQLException e) {
			System.out.println("Error en la consulta SQL: " + e.getMessage());
		}
	}

	/**
	 * Muestra la ficha de un Pokémon con todo su información detallada.
	 * 
	 * @param idPokemon      ID del Pokémon
	 * @param nombrePokemon  Nombre del Pokémon
	 * @param tipoPrimario   Tipo primario del Pokémon
	 * @param tipoSecundario Tipo secundario del Pokémon
	 * @param altura         Altura del Pokémon
	 * @param peso           Peso del Pokémon
	 * @param descripcion    Descripción del Pokémon
	 */

	private static void fichaPokemon(int idPokemon, String nombrePokemon, String tipoPrimario, String tipoSecundario,
			double altura, double peso, String descripcion) {
		if (tipoSecundario == null) {
			tipoSecundario = "";
		}

		System.out.println("\n==" + AMARILLO + " POKÉDEX " + RESET
				+ "==================================================================================================\r\n"
				+ "\n" + idPokemon + ". " + nombrePokemon + "\n" + "---------------------------\n" + tipoPrimario
				+ "   " + tipoSecundario + "\nAltuta: " + altura + "m" + "\nPeso: " + peso + "kg"
				+ "\n\n=============================================================================================================\r"
				+ "\n\n" + descripcion
				+ "\n\n=============================================================================================================\r\n"
				+ "\n");
	}

	/**
	 * Busca el ID de un Pokémon por su nombre.
	 * 
	 * @param nombrePokemon Nombre del Pokémon
	 * @return ID del Pokémon si existe, -1 si no
	 * @throws SQLException Si ocurre un error de SQL
	 */

	private static int buscarIdPokemonPorNombre(String nombrePokemon) throws SQLException {
		try {
			String callProcedure = "{CALL ObtenerIDPokemonPorNombre(?)}";
			CallableStatement cstmt = connection.prepareCall(callProcedure);
			cstmt.setString(1, nombrePokemon);
			ResultSet rsIdPokemon = cstmt.executeQuery();
			if (rsIdPokemon.next()) {
				return rsIdPokemon.getInt("idPokemon");
			}
		} catch (SQLException e) {
			System.out.println("Error en la consulta SQL: " + e.getMessage());
		}
		return -1;
	}
}
