package game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class App {

	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {

		// Conexión bd
		String url = "jdbc:mysql://localhost:3306/PkmnTrainers";
		String user = "root";
		String password = "usuariodam";

		// Variables
		String opcion = "";

		try {
			Connection connection = DriverManager.getConnection(url, user, password);

			do {

				System.out.println("\n========= POKEMON TRAINERS =========\n");

				GestionEntrenadores.imprimirEntrenadores();

				System.out.println("\n====================================\n");
				
				System.out.println("1) Registrar entrenador");
				System.out.println("2) Eliminar entrenador");
				System.out.println("3) Modificar entrenador");
				System.out.println("4) Ver estuche de entrenador");
				System.out.println("5) Ver equipo de entrenador");
				System.out.println("6) Ver mochila de entrenador");
				System.out.println("7) Retar gimnasio");
				System.out.println("8) Comprar objetos");
				System.out.println("9) Mostrar entrenadores detallado");
				System.out.println("10) Datos pokédex");
				System.out.println("X) Salir");
				
				System.out.println("\n====================================");

				opcion = sc.nextLine();
				
				switch (opcion) {
				default:
					System.out.println("Opción inválida, elige otra vez");
				} 
				
			} while (opcion.toLowerCase().equals("x"));

			connection.close();

		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		}
	}
}
