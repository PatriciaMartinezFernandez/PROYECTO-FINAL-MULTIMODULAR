USE PkmnTrainers;

-- Vista para mostrar información básica de los entrenadores
CREATE VIEW VistaEntrenadores AS
SELECT idEntrenador, nombreEntrenador FROM Entrenador;

-- Vista para mostrar información básica de los Pokémon
CREATE VIEW VistaPokemons AS
SELECT idPokemon, nombrePokemon FROM Pokemon;

-- Vista para mostrar el equipo de un entrenador (ID del entrenador y ID y nombre del Pokémon)
CREATE VIEW VistaEquipoEntrenador AS
SELECT E.idEntrenador, P.idPokemon, P.nombrePokemon
FROM EquipoContienePokemons ECP
JOIN Equipo E ON ECP.idEquipo = E.idEquipo
JOIN Pokemon P ON ECP.idPokemon = P.idPokemon;

-- Vista para mostrar información sobre las medallas obtenidas por un entrenador
CREATE VIEW VistaMedallasEntrenador AS
SELECT E.idEntrenador, M.idMedalla, M.nombre AS nombreMedalla, M.ciudad, M.lider
FROM Estuche AS E
JOIN Medalla AS M ON E.idMedalla = M.idMedalla;

-- Vista para mostrar el contenido de la mochila
CREATE VIEW VistaMochila AS
SELECT Objeto.nombreObjeto, Mochila.cantidad, Mochila.idEntrenador
FROM Mochila
JOIN Objeto ON Mochila.idObjeto = Objeto.idObjeto;

-- Vista para mostrar la tienda (ID del objeto y nombre del objeto)
CREATE VIEW VistaTienda AS
SELECT idObjeto, nombreObjeto
FROM Objeto
ORDER BY idObjeto ASC;
