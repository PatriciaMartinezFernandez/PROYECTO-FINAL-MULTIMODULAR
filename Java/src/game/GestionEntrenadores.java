package game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class GestionEntrenadores {

	static Scanner sc = new Scanner(System.in);
	
	private static String url = "jdbc:mysql://localhost:3306/PkmnTrainers";
	private static String user = "root";
	private static String password = "usuariodam";

	static ArrayList<Entrenador> entrenadores = new ArrayList<Entrenador>();
	private static Connection connection;

	static {
		try {
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		}
	}

	public static void imprimirEntrenadores() {

		if (entrenadores.isEmpty()) {
			System.out.println("No hay entrenadores registrados");
		} else {
			for (Entrenador entrenador : entrenadores) {
				System.out.println(entrenador.toString());
			}
		}
	}

	public static void aniadirEntrenador(Entrenador entrenador) {
		entrenadores.add(entrenador);

		try {
			String insertarEntrenador = "INSERT INTO Entrenador (nombreEntrenador, fechaCreacion) VALUES (?, NOW())";
			PreparedStatement preparedStatement = connection.prepareStatement(insertarEntrenador);
			preparedStatement.setString(1, entrenador.getNombreEntrenador());
			preparedStatement.executeUpdate();
			System.out.println("Entrenador registrado correctamente.");
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		}
	}

	public static void eliminarEntrenadorPorID(int id) {
		Iterator<Entrenador> iterator = entrenadores.iterator();
		boolean encontrado = false;
		while (iterator.hasNext() && !encontrado) {
			Entrenador entrenador = iterator.next();
			if (entrenador.getIdEntrenador() == id) {
				iterator.remove();

				try {
					String eliminarEntrenador = "DELETE FROM Entrenador WHERE idEntrenador = ?";
					PreparedStatement preparedStatement = connection.prepareStatement(eliminarEntrenador);
					preparedStatement.setInt(1, id);
					preparedStatement.executeUpdate();
				} catch (SQLException e) {
					System.out.println("SQLException: " + e.getMessage());
				}

				encontrado = true;
				System.out.println("Entrenador eliminado correctamente.");
			}
		}
		if (!encontrado) {
			System.out.println("No se encontró ningún entrenador con el ID especificado.");
		}
	}

	public static void modificarEntrenador(Entrenador entrenador) {

		String nombre;
		int id = entrenador.getIdEntrenador();
		
		System.out.print("Introduce un nuevo nombre para este entrenador: ");
		nombre = sc.nextLine();
				
		entrenador.setNombreEntrenador(nombre);
		
		  try {
		        String modificarEntrenador = "UPDATE Entrenador SET nombreEntrenador = ? WHERE idEntrenador = ?";
		        PreparedStatement preparedStatement = connection.prepareStatement(modificarEntrenador);
		        preparedStatement.setString(1, nombre);
		        preparedStatement.setInt(2, id);
		        preparedStatement.executeUpdate();
		        System.out.println("Entrenador modificado correctamente.");
		    } catch (SQLException e) {
		        System.out.println("SQLException: " + e.getMessage());
		    }
		
		System.out.println("Entrenador modificado correctamente.");
			
	}

	public static Entrenador buscarEntrenadorPorID(int id) {
		for (Entrenador entrenador : entrenadores) {
			if (entrenador.getIdEntrenador() == id) {
				return entrenador;
			}
		}
		return null;
	}

}
