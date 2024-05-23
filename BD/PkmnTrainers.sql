DROP DATABASE IF EXISTS PkmnTrainers;
CREATE DATABASE IF NOT EXISTS PkmnTrainers CHARACTER SET utf8mb4;
USE PkmnTrainers;

CREATE TABLE Entrenador (
    idEntrenador INT AUTO_INCREMENT PRIMARY KEY,
    nombreEntrenador VARCHAR(45) NOT NULL,
    fechaCreacion DATETIME 
);

CREATE TABLE Equipo (
    idEquipo INT AUTO_INCREMENT PRIMARY KEY,
    idEntrenador INT NOT NULL,
    FOREIGN KEY (idEntrenador)
        REFERENCES Entrenador (idEntrenador)
);

CREATE TABLE Pokemon (
    idPokemon INT PRIMARY KEY,
    nombrePokemon VARCHAR(45) NOT NULL,
    altura DOUBLE,
    peso DOUBLE,
    descripcion VARCHAR(200) NOT NULL,
    popularidad INT NOT NULL,
    tipoPrimario VARCHAR(45) NOT NULL,
    tipoSecundario VARCHAR(45)
);

CREATE TABLE EquipoContienePokemons (
    idEquipo INT,
    idPokemon INT,
    PRIMARY KEY (idEquipo , idPokemon),
    FOREIGN KEY (idEquipo)
        REFERENCES Equipo (idEquipo),
    FOREIGN KEY (idPokemon)
        REFERENCES Pokemon (idPokemon)
);

CREATE TABLE Medalla (
    idMedalla INT PRIMARY KEY,
    nombre VARCHAR(45) NOT NULL,
    ciudad VARCHAR(45) NOT NULL,
    lider VARCHAR(45) NOT NULL
);

CREATE TABLE Estuche (
    idEntrenador INT,
    idMedalla INT,
    PRIMARY KEY (idEntrenador , idMedalla),
    FOREIGN KEY (idEntrenador)
        REFERENCES Entrenador (idEntrenador),
    FOREIGN KEY (idMedalla)
        REFERENCES Medalla (idMedalla)
);

CREATE TABLE Objeto (
    idObjeto INT PRIMARY KEY,
    nombreObjeto VARCHAR(45) NOT NULL
);

CREATE TABLE Mochila (
    idEntrenador INT,
    idObjeto INT,
    cantidad INT NOT NULL,
    PRIMARY KEY (idEntrenador , idObjeto),
    FOREIGN KEY (idEntrenador)
        REFERENCES Entrenador (idEntrenador),
    FOREIGN KEY (idObjeto)
        REFERENCES Objeto (idObjeto)
);