DROP DATABASE IF EXISTS PkmnTrainers;
CREATE DATABASE IF NOT EXISTS PkmnTrainers CHARACTER SET utf8mb4;
USE PkmnTrainers;

create table Entrenador (
idEntrenador int auto_increment primary key,
nombreEntrenador varchar(45) not null,
fechaCreacion date not null 
);

create table Equipo (
idEquipo int auto_increment primary key,
idEntrenador int not null,
foreign key Equipo(idEntrenador) references Entrenador(idEntrenador)
);

create table Pokemon (
idPokemon int primary key,
nombrePokemon varchar(45) not null,
altura double,
peso double,
descripcion varchar(200) not null,
popularidad int not null,
tipoPrimario varchar(45) not null,
tipoSecundario varchar(45)
);

create table EquipoContienePokemons (
idEquipo int,
idPokemon int,
primary key (idEquipo, idPokemon),
foreign key EquipoContienePokemons(idEquipo) references Equipo(idEquipo),
foreign key EquipoContienePokemons(idPokemon) references Pokemon(idPokemon)
);

create table Medalla (
idMedalla int primary key,
nombre varchar(45) not null,
ciudad varchar(45) not null,
lider varchar(45) not null
);

create table Estuche (
idEntrenador int,
idMedalla int,
primary key (idEntrenador, idMedalla),
foreign key Estuche(idEntrenador) references Entrenador(idEntrenador),
foreign key Estuche(idMedalla) references Medalla(idMedalla)
);

create table Objeto (
idObjeto int primary key,
nombreObjeto varchar(45) not null
);

create table Mochila (
idEntrenador int,
idObjeto int,
cantidad int not null,
primary key (idEntrenador, idObjeto),
foreign key Mochila(idEntrenador) references Entrenador(idEntrenador),
foreign key Mochila(idObjeto) references Objeto(idObjeto)
);
