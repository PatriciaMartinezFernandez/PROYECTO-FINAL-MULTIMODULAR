package game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class GestionEquipos {

	static Scanner sc = new Scanner(System.in);

	private static String url = "jdbc:mysql://localhost:3306/PkmnTrainers";
	private static String user = "root";
	private static String password = "usuariodam";

	private static Connection connection;

	static {
		try {
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		}
	}

	public static void aniadirPokemon() {
	    try {
	        System.out.println("Lista de Entrenadores:");
	        String queryEntrenadores = "SELECT idEntrenador, nombreEntrenador FROM Entrenador";
	        try (PreparedStatement stmtEntrenadores = connection.prepareStatement(queryEntrenadores)) {
	            ResultSet rsEntrenadores = stmtEntrenadores.executeQuery();
	            while (rsEntrenadores.next()) {
	                System.out.println(rsEntrenadores.getInt("idEntrenador") + ". " + rsEntrenadores.getString("nombreEntrenador"));
	            }
	        }

	        System.out.print("Introduce el ID del entrenador al que deseas añadir el Pokémon: ");
	        int idEntrenador = sc.nextInt();
	        sc.nextLine();

	        if (!tieneEquipo(idEntrenador)) {
	            crearEquipo(idEntrenador);
	        }

	        System.out.println("\nLista de Pokémon:");
	        String queryPokemon = "SELECT idPokemon, nombrePokemon FROM Pokemon";
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
	        }

	        System.out.print("\nIntroduce el ID o nombre del Pokémon que deseas añadir: ");
	        String pokemonInput = sc.nextLine().trim();

	        int idPokemon;
	        try {
	            idPokemon = Integer.parseInt(pokemonInput);
	        } catch (NumberFormatException e) {
	            idPokemon = -1;
	        }

	        if (idPokemon != -1) {
	            insertarPokemon(idEntrenador, idPokemon);
	        } else {
	            idPokemon = buscarIdPokemonPorNombre(pokemonInput);
	            if (idPokemon != -1) {
	                insertarPokemon(idEntrenador, idPokemon);
	            } else {
	                System.out.println("No se encontró ningún Pokémon con ese nombre.");
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("SQLException: " + e.getMessage());
	    }
	}

	public static void eliminarPokemon() {
	    try {
	        System.out.println("Lista de Entrenadores:");
	        String queryEntrenadores = "SELECT idEntrenador, nombreEntrenador FROM Entrenador";
	        try (PreparedStatement stmtEntrenadores = connection.prepareStatement(queryEntrenadores)) {
	            ResultSet rsEntrenadores = stmtEntrenadores.executeQuery();
	            while (rsEntrenadores.next()) {
	                System.out.println(rsEntrenadores.getInt("idEntrenador") + ". " + rsEntrenadores.getString("nombreEntrenador"));
	            }
	        }
	        
	        System.out.print("Introduce el ID del entrenador cuyo Pokémon deseas eliminar: ");
	        int idEntrenador = sc.nextInt();
	        sc.nextLine(); 

	        String nombreEntrenador = obtenerNombreEntrenador(idEntrenador);
	        if (nombreEntrenador == null) {
	            System.out.println("No se encontró ningún entrenador con el ID especificado.");
	            return;
	        }

	        System.out.println("Equipo del Entrenador " + nombreEntrenador + ":");
	        String queryEquipo = "SELECT EC.idPokemon, P.nombrePokemon " +
	                             "FROM EquipoContienePokemons EC " +
	                             "INNER JOIN Pokemon P ON EC.idPokemon = P.idPokemon " +
	                             "WHERE EC.idEquipo = ?";
	        try (PreparedStatement stmtEquipo = connection.prepareStatement(queryEquipo)) {
	            stmtEquipo.setInt(1, idEntrenador);
	            ResultSet rsEquipo = stmtEquipo.executeQuery();
	            int count = 0;
	            while (rsEquipo.next()) {
	                if (count % 10 == 0 && count != 0) {
	                    System.out.println(); 
	                }
	                System.out.print("ID " + rsEquipo.getInt("idPokemon") + ": " + rsEquipo.getString("nombrePokemon") + "\t");
	                count++;
	            }
	        }

	        System.out.print("\nIntroduce el ID del Pokémon que deseas eliminar del equipo: ");
	        int idPokemon = sc.nextInt();

	        String deletePokemon = "DELETE FROM EquipoContienePokemons WHERE idEquipo = ? AND idPokemon = ?";
	        try (PreparedStatement stmtDeletePokemon = connection.prepareStatement(deletePokemon)) {
	            stmtDeletePokemon.setInt(1, idEntrenador);
	            stmtDeletePokemon.setInt(2, idPokemon);
	            int rowsAffected = stmtDeletePokemon.executeUpdate();
	            if (rowsAffected > 0) {
	                System.out.println("Pokémon eliminado correctamente del equipo del entrenador.");
	            } else {
	                System.out.println("El Pokémon no está en el equipo del entrenador.");
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("SQLException: " + e.getMessage());
	    }
	}


	private static String obtenerNombreEntrenador(int idEntrenador) throws SQLException {
	    String queryNombreEntrenador = "SELECT nombreEntrenador FROM Entrenador WHERE idEntrenador = ?";
	    try (PreparedStatement stmtNombreEntrenador = connection.prepareStatement(queryNombreEntrenador)) {
	        stmtNombreEntrenador.setInt(1, idEntrenador);
	        ResultSet rsNombreEntrenador = stmtNombreEntrenador.executeQuery();
	        if (rsNombreEntrenador.next()) {
	            return rsNombreEntrenador.getString("nombreEntrenador");
	        }
	    }
	    return null;
	}


	private static boolean tieneEquipo(int idEntrenador) throws SQLException {
		String query = "SELECT COUNT(*) AS count FROM Equipo WHERE idEntrenador = ?";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, idEntrenador);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("count") > 0;
			}
		}
		return false;
	}

	private static void crearEquipo(int idEntrenador) throws SQLException {
		String insertEquipo = "INSERT INTO Equipo (idEntrenador) VALUES (?)";
		try (PreparedStatement stmt = connection.prepareStatement(insertEquipo)) {
			stmt.setInt(1, idEntrenador);
			int rowsAffected = stmt.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("Equipo creado correctamente para el entrenador.");
			} else {
				System.out.println("No se pudo crear el equipo para el entrenador.");
			}
		}
	}

	private static void insertarPokemon(int idEntrenador, int idPokemon) throws SQLException {
		int idEquipo = obtenerIdEquipo(idEntrenador);

		if (idEquipo != -1) {
			int cantidadPokemon = obtenerCantidadPokemonEnEquipo(idEquipo);
			if (cantidadPokemon >= 6) {
				System.out.println("El equipo ya tiene 6 Pokémon. No se puede agregar más.");
				return;
			}

			String insertPokemon = "INSERT INTO EquipoContienePokemons (idEquipo, idPokemon) VALUES (?, ?)";
			try (PreparedStatement stmtInsertPokemon = connection.prepareStatement(insertPokemon)) {
				stmtInsertPokemon.setInt(1, idEquipo);
				stmtInsertPokemon.setInt(2, idPokemon);
				int rowsAffected = stmtInsertPokemon.executeUpdate();
				if (rowsAffected > 0) {
					System.out.println("Pokémon añadido correctamente al equipo del entrenador.");
				} else {
					System.out.println("No se pudo añadir el Pokémon al equipo del entrenador.");
				}
			}
		} else {
			System.out.println("Error: No se encontró el equipo para el entrenador.");
		}
	}

	private static int obtenerCantidadPokemonEnEquipo(int idEquipo) throws SQLException {
		String query = "SELECT COUNT(*) AS cantidad FROM EquipoContienePokemons WHERE idEquipo = ?";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, idEquipo);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("cantidad");
			}
		}
		return 0;
	}

	private static int obtenerIdEquipo(int idEntrenador) throws SQLException {
		String query = "SELECT idEquipo FROM Equipo WHERE idEntrenador = ?";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, idEntrenador);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("idEquipo");
			}
		}
		return -1;
	}

	private static int buscarIdPokemonPorNombre(String nombrePokemon) throws SQLException {
		String selectIdPokemon = "SELECT idPokemon FROM Pokemon WHERE nombrePokemon = ?";
		try (PreparedStatement stmtIdPokemon = connection.prepareStatement(selectIdPokemon)) {
			stmtIdPokemon.setString(1, nombrePokemon);
			ResultSet rsIdPokemon = stmtIdPokemon.executeQuery();
			if (rsIdPokemon.next()) {
				return rsIdPokemon.getInt("idPokemon");
			}
		}
		return -1;
	}
}
