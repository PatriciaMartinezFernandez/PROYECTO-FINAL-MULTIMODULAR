USE PkmnTrainers;

-- Trigger para eliminar Pokémon relacionados cuando se elimina un entrenador
DELIMITER //
CREATE TRIGGER antesEliminarEntrenador
BEFORE DELETE ON Entrenador
FOR EACH ROW
BEGIN
    DELETE FROM equipocontienepokemons WHERE idEquipo IN (SELECT idEquipo FROM Equipo WHERE idEntrenador = old.idEntrenador);
    DELETE FROM Equipo WHERE idEntrenador = old.idEntrenador;
END //
DELIMITER ;

-- Trigger para evitar que se inserte un nuevo equipo si el entrenador ya tiene uno
DELIMITER //
CREATE TRIGGER antesInsertarEquipo
BEFORE INSERT ON Equipo
FOR EACH ROW
BEGIN
    DECLARE count INT;
    SELECT COUNT(*) INTO count FROM Equipo WHERE idEntrenador = NEW.idEntrenador;
    IF count > 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El entrenador ya tiene un equipo.';
    END IF;
END //
DELIMITER ;

-- Trigger para actualizar la fecha de creación al insertar un nuevo entrenador
DELIMITER //
CREATE TRIGGER actualizar_fecha_creacion
BEFORE INSERT ON Entrenador
FOR EACH ROW
BEGIN
    SET NEW.fechaCreacion = CURRENT_TIMESTAMP;
END //
DELIMITER ;

/*DELIMITER //
CREATE TRIGGER after_insert_entrenador
AFTER INSERT ON Entrenador
FOR EACH ROW
BEGIN
    DECLARE mensaje VARCHAR(255);
    SET mensaje = CONCAT('Se ha añadido un nuevo entrenador con el nombre: ', NEW.nombreEntrenador);
    SELECT mensaje;
END //
DELIMITER ;*/