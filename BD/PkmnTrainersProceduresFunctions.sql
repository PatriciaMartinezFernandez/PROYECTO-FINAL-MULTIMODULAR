USE PkmnTrainers;

-- Añadir entrenador
DELIMITER //
CREATE PROCEDURE aniadirEntrenador(IN nombre VARCHAR(45))
BEGIN
    INSERT INTO Entrenador(nombreEntrenador) VALUES (nombre);
END //
DELIMITER ;

-- Eliminar entrenador
DELIMITER //
CREATE PROCEDURE eliminarEntrenadorPorID(IN entrenadorID INT)
BEGIN
    DELETE FROM equipocontienepokemons WHERE idEquipo IN (SELECT idEquipo FROM Equipo WHERE idEntrenador = entrenadorID);
    DELETE FROM Equipo WHERE idEntrenador = entrenadorID;
    DELETE FROM Estuche WHERE idEntrenador = entrenadorID;
    DELETE FROM Mochila WHERE idEntrenador = entrenadorID;
    DELETE FROM Entrenador WHERE idEntrenador = entrenadorID;
END //
DELIMITER ;

-- Modificar entrenador
DELIMITER //
CREATE PROCEDURE modificarEntrenador(IN entrenadorID INT, IN nuevoNombre VARCHAR(45))
BEGIN
    UPDATE Entrenador SET nombreEntrenador = nuevoNombre WHERE idEntrenador = entrenadorID;
END //
DELIMITER ;

-- Crear equipo
DELIMITER //
CREATE PROCEDURE crearEquipo(IN entrenadorId INT)
BEGIN
    INSERT INTO Equipo (idEntrenador) VALUES (entrenadorId);
END //
DELIMITER ;

-- Insertar pokemon en equipo especificado
DELIMITER //
CREATE PROCEDURE insertarPokemonEnEquipo(IN entrenadorId INT, IN pokemonId INT)
BEGIN
    DECLARE equipoId INT;
    DECLARE cantidadPokemon INT;

    SELECT idEquipo INTO equipoId FROM Equipo WHERE idEntrenador = entrenadorId;
    SELECT COUNT(*) INTO cantidadPokemon FROM EquipoContienePokemons WHERE idEquipo = equipoId;
    
    IF cantidadPokemon < 6 THEN
        INSERT INTO EquipoContienePokemons (idEquipo, idPokemon) VALUES (equipoId, pokemonId);
    ELSE
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El equipo ya tiene 6 Pokémon.';
    END IF;
END //
DELIMITER ;

-- Eliminar pokemon de equipo especificado
DELIMITER //
CREATE PROCEDURE eliminarPokemonDeEquipo(IN entrenadorId INT, IN pokemonId INT)
BEGIN
    DECLARE equipoId INT;
    
    SELECT idEquipo INTO equipoId FROM Equipo WHERE idEntrenador = entrenadorId;
    
    DELETE FROM EquipoContienePokemons WHERE idEquipo = equipoId AND idPokemon = pokemonId;
END //
DELIMITER ;

-- Verificar si tiene equipo
DELIMITER //
CREATE FUNCTION tieneEquipo(entrenadorId INT) RETURNS BOOLEAN
DETERMINISTIC
BEGIN
    DECLARE count INT;
    SELECT COUNT(*) INTO count FROM Equipo WHERE idEntrenador = entrenadorId;
    RETURN count > 0;
END //
DELIMITER ;

-- Comprar en tienda
DELIMITER //
CREATE PROCEDURE ComprarTienda(
    IN p_idEntrenador INT,
    IN p_idObjeto INT,
    IN p_cantidad INT
)
BEGIN
    DECLARE cantidadExistente INT DEFAULT 0;

    SELECT cantidad INTO cantidadExistente
    FROM Mochila
    WHERE idEntrenador = p_idEntrenador AND idObjeto = p_idObjeto;

    IF cantidadExistente > 0 THEN
        UPDATE Mochila
        SET cantidad = cantidadExistente + p_cantidad
        WHERE idEntrenador = p_idEntrenador AND idObjeto = p_idObjeto;
    ELSE
        INSERT INTO Mochila (idEntrenador, idObjeto, cantidad)
        VALUES (p_idEntrenador, p_idObjeto, p_cantidad);
    END IF;
END //
DELIMITER ;

-- Mostrar mochila
DELIMITER //
CREATE PROCEDURE MostrarMochila(
    IN p_idEntrenador INT
)
BEGIN
    SELECT Objeto.nombreObjeto, Mochila.cantidad
    FROM Mochila
    JOIN Objeto ON Mochila.idObjeto = Objeto.idObjeto
    WHERE Mochila.idEntrenador = p_idEntrenador;
END //
DELIMITER ;

-- Obtener información de Pokémon por ID
DELIMITER //
CREATE PROCEDURE ObtenerInfoPokemonPorID (
    IN p_idPokemon INT
)
BEGIN
    SELECT * FROM Pokemon WHERE idPokemon = p_idPokemon;
END //
DELIMITER ;

-- Obtener ID de Pokémon por nombre
DELIMITER //
CREATE PROCEDURE ObtenerIDPokemonPorNombre (
    IN p_nombrePokemon VARCHAR(255)
)
BEGIN
    SELECT idPokemon FROM Pokemon WHERE nombrePokemon = p_nombrePokemon;
END //
DELIMITER ;