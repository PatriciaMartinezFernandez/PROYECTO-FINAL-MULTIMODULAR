package game;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;

/**
 * La clase GestionEquipos proporciona métodos estáticos para gestionar los
 * equipos en el juego. Esto incluye añadir y eliminar pokémon de los equipos de
 * los entrenadores.
 * 
 * Esta clase extiende la clase Gestion para utilizar la conexión a la base de
 * datos.
 * 
 * @author Jaime Martínez Fernández
 */

public class GestionEquipos extends Gestion {

	/**
	 * Añade un Pokémon al equipo de un entrenador especifico.
	 */

	public static void aniadirPokemon() {
		try {
			mostrarEntrenadores();

			System.out.print("Introduce el ID del entrenador al que deseas añadir el Pokémon: ");
			int idEntrenador = obtenerIdEntrenador();

			if (!tieneEquipo(idEntrenador)) {
				crearEquipo(idEntrenador);
			}

			mostrarPokemon();

			System.out.print("\nIntroduce el ID o nombre del Pokémon que deseas añadir: ");
			String pokemonInput = sc.next().trim();

			int idPokemon;
			try {
				idPokemon = Integer.parseInt(pokemonInput);
				insertarPokemon(idEntrenador, idPokemon);
			} catch (NumberFormatException e) {
				idPokemon = buscarIdPokemonPorNombre(pokemonInput);
				if (idPokemon != -1) {
					insertarPokemon(idEntrenador, idPokemon);
				} else {
					System.out.println("No se encontró ningún Pokémon con ese nombre.");
				}
			}
		} catch (SQLException e) {
			System.out.println("Error al añadir el Pokémon: " + e.getMessage());
		}
	}

	/**
	 * Elimina un Pokémon del equipo de un entrenador especifico.
	 */

	public static void eliminarPokemon() {
		try {
			mostrarEntrenadores();

			System.out.print("Introduce el ID del entrenador cuyo Pokémon deseas eliminar: ");
			int idEntrenador = obtenerIdEntrenador();

			String nombreEntrenador = obtenerNombreEntrenador(idEntrenador);
			if (nombreEntrenador == null) {
				System.out.println("No se encontró ningún entrenador con el ID especificado.");
				return;
			}

			mostrarEquipoEntrenador(idEntrenador, nombreEntrenador);

			System.out.print("\nIntroduce el ID del Pokémon que deseas eliminar del equipo: ");
			int idPokemon = obtenerIdPokemon();

			eliminarPokemonDeEquipo(idEntrenador, idPokemon);
		} catch (SQLException e) {
			System.out.println("Error al eliminar el Pokémon: " + e.getMessage());
		}
	}

	/**
	 * Muestra una lista de entrenadores disponibles en la base de datos.
	 * 
	 * @throws SQLException Si hay error al acceder a la base de datos
	 */

	private static void mostrarEntrenadores() throws SQLException {
		System.out.println("Lista de Entrenadores:");
		String queryEntrenadores = "SELECT idEntrenador, nombreEntrenador FROM VistaEntrenadores";
		try (PreparedStatement stmtEntrenadores = connection.prepareStatement(queryEntrenadores)) {
			ResultSet rsEntrenadores = stmtEntrenadores.executeQuery();
			while (rsEntrenadores.next()) {
				System.out.println(
						rsEntrenadores.getInt("idEntrenador") + ". " + rsEntrenadores.getString("nombreEntrenador"));
			}
		}
	}

	/**
	 * Muestra una lista de Pokémons disponibles en la base de datos.
	 * 
	 * @throws SQLException Si hay un error al acceder a la base de datos
	 */

	private static void mostrarPokemon() throws SQLException {
		System.out.println("\nLista de Pokémon:");
		String queryPokemon = "SELECT idPokemon, nombrePokemon FROM VistaPokemons";
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
			System.out.println();
		}
	}

	/**
	 * Muestra el equipo de un entrenador especifico.
	 * 
	 * @param idEntrenador     ID del entrenador cuyo equipo se va a mostrar
	 * @param nombreEntrenador Nombre del entrenador cuyo equipo se va a mostrar
	 * @throws SQLException Si hay un error al acceder a la base de datos
	 */

	private static void mostrarEquipoEntrenador(int idEntrenador, String nombreEntrenador) throws SQLException {
		System.out.println("Equipo del Entrenador " + nombreEntrenador + ":");
		String queryEquipo = "SELECT idPokemon, nombrePokemon FROM VistaEquipoEntrenador WHERE idEntrenador = ?";
		try (PreparedStatement stmtEquipo = connection.prepareStatement(queryEquipo)) {
			stmtEquipo.setInt(1, idEntrenador);
			ResultSet rsEquipo = stmtEquipo.executeQuery();
			int count = 0;
			while (rsEquipo.next()) {
				if (count % 10 == 0 && count != 0) {
					System.out.println();
				}
				System.out.print(rsEquipo.getInt("idPokemon") + ". " + rsEquipo.getString("nombrePokemon") + "\n");
				count++;
			}
			System.out.println();
		}
	}

	/**
	 * Obtiene el nombre del entrenador correspondiente a un ID de entrenador
	 * especificado.
	 * 
	 * @param idEntrenador ID del entrenador del cual se desea obtener el nombre
	 * @return Nombre del entrenador, o null si no se encuentra ningún entrenador
	 *         con el ID especificado
	 * @throws SQLException Si hay un error al acceder a la base de datos
	 */

	private static String obtenerNombreEntrenador(int idEntrenador) throws SQLException {
		String queryNombreEntrenador = "SELECT nombreEntrenador FROM VistaEntrenadores WHERE idEntrenador = ?";
		try (PreparedStatement stmtNombreEntrenador = connection.prepareStatement(queryNombreEntrenador)) {
			stmtNombreEntrenador.setInt(1, idEntrenador);
			ResultSet rsNombreEntrenador = stmtNombreEntrenador.executeQuery();
			if (rsNombreEntrenador.next()) {
				return rsNombreEntrenador.getString("nombreEntrenador");
			}
		}
		return null;
	}

	/**
	 * Verifica si un entrenador especifico tiene un equipo de Pókemon.
	 * 
	 * @param idEntrenador ID del entrenador del cual se desea verificar que el
	 *                     equipo existe
	 * @return True si el entrenador tiene equipo, false si no
	 * @throws SQLException Si hay error al acceder a la base de datos
	 */

	private static boolean tieneEquipo(int idEntrenador) throws SQLException {
		String query = "SELECT tieneEquipo(?)";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, idEntrenador);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getBoolean(1);
			}
		}
		return false;
	}

	/**
	 * Crea un equipo de Pokémons para un entrenador especifico.
	 * 
	 * @param idEntrenador ID del entrendaor para el cual se va a crear el equipo
	 * @throws SQLException Si hay un error al acceder a la base de datos
	 */

	private static void crearEquipo(int idEntrenador) throws SQLException {
		String query = "{CALL crearEquipo(?)}";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, idEntrenador);
			int rowsAffected = stmt.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("Equipo creado correctamente para el entrenador.");
			} else {
				System.out.println("No se pudo crear el equipo para el entrenador.");
			}
		}
	}

	/**
	 * Inserta un Pókemon en el equipo de un entrenador especifico.
	 * 
	 * @param idEntrenador ID del entrenador en cuyo equivo se va a insertar el
	 *                     Pokémon
	 * @param idPokemon    ID del Pokémon que se va a insertar en el equipo
	 * @throws SQLException Si hay un error al acceder a la base de datos
	 */

	private static void insertarPokemon(int idEntrenador, int idPokemon) throws SQLException {
		String query = "{CALL insertarPokemonEnEquipo(?, ?)}";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, idEntrenador);
			stmt.setInt(2, idPokemon);
			stmt.executeUpdate();
			System.out.println("Pokémon añadido correctamente al equipo del entrenador.");
		} catch (SQLException e) {
			if (e.getSQLState().equals("45000")) {
				System.out.println("El equipo ya tiene 6 Pokémon. No se puede agregar más.");
			} else {
				throw e;
			}
		}
	}

	/**
	 * Elimina un Pokémon del equipo de un entrendor especifico.
	 * 
	 * @param idEntrenador ID del entrenador del cual se va a eliminar el Pokémon
	 * @param idPokemon    ID del Pokémon que se va a eliminar del equipo
	 * @throws SQLException Si hay un error al acceder a la base de datos
	 */

	private static void eliminarPokemonDeEquipo(int idEntrenador, int idPokemon) throws SQLException {
		String query = "{CALL eliminarPokemonDeEquipo(?, ?)}";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, idEntrenador);
			stmt.setInt(2, idPokemon);
			int rowsAffected = stmt.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("Pokémon eliminado correctamente del equipo del entrenador.");
			} else {
				System.out.println("El Pokémon no está en el equipo del entrenador.");
			}
		}
	}

	/**
	 * Busca el ID de un Pokémon por su nombre en la base de datos
	 * 
	 * @param nombrePokemon Nombre del Pokémon que se desea buscar
	 * @return	ID del Pokémon si se encuentra, -1 si no se encuentra
	 * @throws SQLException Si hay un error al acceder a la base de datos
	 */
	
	private static int buscarIdPokemonPorNombre(String nombrePokemon) throws SQLException {
		String query = "SELECT idPokemon FROM VistaPokemons WHERE nombrePokemon = ?";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, nombrePokemon);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("idPokemon");
			}
		}
		return -1;
	}

	/**
	 * Obtiene el ID de un entrenador ingresado por el usuario desde la entrada estándar.
	 * 
	 * @return ID del entrenador ingresado por el usuario
	 */
	
	private static int obtenerIdEntrenador() {
		while (true) {
			try {
				return sc.nextInt();
			} catch (InputMismatchException e) {
				System.out.print("Error: Se esperaba un ID válido. Inténtalo de nuevo: ");
				sc.next();
			}
		}
	}

	/**
	 * Obtiene el ID de un Pokémon ingresado por el usuario dese la entrada estándar.
	 * 
	 * @return ID del Pokémon ingresado por el usuario
	 */
	
	private static int obtenerIdPokemon() {
		while (true) {
			try {
				return sc.nextInt();
			} catch (InputMismatchException e) {
				System.out.print("Error: Se esperaba un ID válido. Inténtalo de nuevo: ");
				sc.next();
			}
		}
	}
}
