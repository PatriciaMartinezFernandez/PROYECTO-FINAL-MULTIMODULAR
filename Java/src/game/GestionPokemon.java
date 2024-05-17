package game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GestionPokemon {

	static Scanner sc = new Scanner(System.in);
	public static final String AMARILLO = "\u001B[33m";
	public static final String RESET = "\u001B[0m";
	static int idPokemon;

	private static String url = "jdbc:mysql://localhost:3306/PkmnTrainers";
	private static String user = "root";
	private static String password = "usuariodam";

	private static Connection connection;

	static {
		try {
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			System.out.println("Error al conectar con la base de datos: " + e.getMessage());
		}
	}

	public static void elegirPokemon() {

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
		} catch (SQLException e) {
			e.getMessage();
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

	private static void infoPokedex(int idPokemon) {
		String queryInfo = "SELECT * FROM Pokemon WHERE idPokemon = ?";
		try (PreparedStatement stmtInfo = connection.prepareStatement(queryInfo)) {
			stmtInfo.setInt(1, idPokemon);
			ResultSet rsInfo = stmtInfo.executeQuery();
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
			e.printStackTrace();
		}
	}

	private static void fichaPokemon(int idPokemon, String nombrePokemon, String tipoPrimario, String tipoSecundario,
			double altura, double peso, String descripcion) {

		System.out.println("\n==" + AMARILLO + " POKÉDEX " + RESET
				+ "==================================================================================================\r\n" + "\n"
				+ idPokemon + ". " + nombrePokemon + "\n" + "---------------------------\n" + tipoPrimario + "   "
				+ tipoSecundario + "\nAltuta: " + altura + "m" + "\nPeso: " + peso + "kg"
				+ "\n\n=============================================================================================================\r"
				+ "\n\n" + descripcion
				+ "\n\n=============================================================================================================\r\n"
				+ "\n");

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
