package test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import game.Entrenador;
import game.GestionEntrenadores;

/**
 * La clase AniadirEntrenadorTest contiene pruebas unitarias para el método
 * {@code aniadirEntrenador} de la clase {@code GestionEntrenadores}.
 * 
 * Las pruebas se ejecutan usando JUnit y verifican el comportamiento del método
 * al añadir un entrenador con diferentes tipos de nombres (válidos, nulos y
 * vacíos).
 */

public class AniadirEntrenadorTest {

	private static Connection connection;

	/**
	 * Establece una conexión a la base de datos antes de ejecutar las pruebas.
	 */

	@BeforeAll
	public static void conectar() {
		String url = "jdbc:mysql://localhost:3306/PkmnTrainers";
		String user = "root";
		String pwd = "usuariodam";

		try {
			connection = DriverManager.getConnection(url, user, pwd);
		} catch (SQLException e) {
			fail("Error al establecer la conexión a la base de datos: " + e.getMessage());
		}

	}

	/**
	 * Cierra la conexión a la base de datos después de ejecutar todas las pruebas.
	 */

	@AfterAll
	public static void cerrar() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			fail("Error al cerrar la conexión a la base de datos: " + e.getMessage());
		}
	}

	/**
	 * Prueba que el método {@code aniadirEntrenador} no lanza excepciones cuando se
	 * añade un entrenador con un nombre válido.
	 */

	@Test
	public void testAniadirEntrenador_NombreValido() {
		Entrenador entrenador = new Entrenador("Ash");
		assertDoesNotThrow(() -> {
			GestionEntrenadores.aniadirEntrenador(entrenador);
		});
	}

	/**
	 * Prueba que el método {@code aniadirEntrenador} lanza un
	 * {@code IllegalArgumentException} cuando se intenta añadir un entrenador con
	 * un nombre nulo.
	 */

	@Test
	public void testAniadirEntrenador_NombreNulo() {
		Entrenador entrenador = new Entrenador(null);
		assertThrows(IllegalArgumentException.class, () -> {
			GestionEntrenadores.aniadirEntrenador(entrenador);
		});
	}

	/**
	 * Prueba que el método {@code aniadirEntrenador} lanza un
	 * {@code IllegalArgumentException} cuando se intenta añadir un entrenador con
	 * un nombre vacío.
	 */

	@Test
	public void testAniadirEntrenador_NombreVacio() {
		Entrenador entrenador = new Entrenador("");
		assertThrows(IllegalArgumentException.class, () -> {
			GestionEntrenadores.aniadirEntrenador(entrenador);
		});
	}

}
