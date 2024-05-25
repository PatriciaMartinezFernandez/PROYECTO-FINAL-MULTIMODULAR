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

public class AniadirEntrenadorTest {

	private static Connection connection;

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

	@Test
	// Test de un entrenador con un nombre válido
	public void testAniadirEntrenador_NombreValido() {
		Entrenador entrenador = new Entrenador("Ash");
		assertDoesNotThrow(() -> {
			GestionEntrenadores.aniadirEntrenador(entrenador);
		});
	}

	@Test
	// Test de un entrenador con un nombre nulo
	public void testAniadirEntrenador_NombreNulo() {
	    Entrenador entrenador = new Entrenador(null);
	    assertThrows(IllegalArgumentException.class, () -> {
	        GestionEntrenadores.aniadirEntrenador(entrenador);
	    });
	}


	@Test
	// Test de un entrenador con un nombre vacio
	public void testAniadirEntrenador_NombreVacio() {
        Entrenador entrenador = new Entrenador("");
        assertThrows(IllegalArgumentException.class, () -> {
            GestionEntrenadores.aniadirEntrenador(entrenador);
        });
    }

}
