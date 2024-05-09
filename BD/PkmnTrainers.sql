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
descripcion varchar(150) not null,
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

-- MEDALLAS --
insert into Medalla(idMedalla, nombre, ciudad, lider) values
(1, 'Medalla Roca', 'Ciudad Plateada', 'Brock'),
(2, 'Medalla Cascada', 'Ciudad Celeste', 'Misty'),
(3, 'Medalla Trueno', 'Ciudad Carmín', 'Lt Surge'),
(4, 'Medalla Arcoiris', 'Ciudad Azulona', 'Erika'),
(5, 'Medalla Alma', 'Ciudad Fucsia', 'Koga'),
(6, 'Medalla Pantáno' 'Ciudad Azafrán', 'Sabrina'),
(7, 'Medalla Volcán', 'Isla Canela', 'Blaine'),
(8, 'Medalla Tierra', 'Ciudad Verde', 'Giovanni');

-- POKEDEX --
insert into Pokemon(idPokemon, nombrePokemon, altura, peso, descripcion, popularidad, tipoPrimario, tipoSecundario) values 
(1, 'Bulbasaur', 0.7, 6.9, 'Tras nacer, crece alimentándose durante un tiempo de los nutrientes que contiene el bulbo de su lomo.', 11, 'Planta', 'Veneno'),
(2, 'Ivysaur', 1.0, 13.0, 'Cuanta más luz solar recibe, más aumenta su fuerza y más se desarrolla el capullo que tiene en el lomo.', 91, 'Planta', 'Veneno'),
(3, 'Venusaur', 2.0, 100.0, 'Puede convertir la luz del sol en energía. Por esa razón, es más poderoso en verano.', 22, 'Planta', 'Veneno'),
(4, 'Charmander', 0.6, 8.5, 'La llama de su cola indica su fuerza vital. Si está débil, la llama arderá más tenue.', 14, 'Fuego', null),
(5, 'Charmeleon', 1.1, 19.0, 'Al agitar su ardiente cola, eleva poco a poco la temperatura a su alrededor para sofocar a sus rivales.', 72, 'Fuego', null),
(6, 'Charizard', 1.7, 90.5, 'Cuando se enfurece de verdad, la llama de la punta de su cola se vuelve de color azul claro.', 2, 'Fuego', 'Volador'),
(7, 'Squirtle', 0.5, 9.0, 'Tras nacer, se le hincha el lomo y se le forma un caparazón. Escupe poderosa espuma por la boca.', 16, 'Agua', null),
(8, 'Wartortle', 1.0, 22.5, 'Tiene una cola larga y peluda que simboliza la longevidad y lo hace popular entre los mayores.', 110, 'Agua', null),
(9, 'Blastoise', 1.6, 85.5, 'Aumenta de peso deliberadamente para contrarrestar la fuerza de los chorros de agua que dispara.', 19, 'Agua', null),
(10, 'Caterpie', 0.3, 2.9, 'Para protegerse, despide un hedor horrible por las antenas con el que repele a sus enemigos.', 107, 'Bicho', null),
(11, 'Metapod', 0.7, 9.9, 'Como en este estado solo puede endurecer su coraza, permanece inmóvil a la espera de evolucionar.', 126, 'Bicho', null),
(12, 'Butterfree', 1.1, 32.0, 'Adora el néctar de las flores. Una pequeña cantidad de polen le basta para localizar prados floridos.', 61, 'Bicho', 'Volador'),
(13, 'Weedle', 0.3, 3.2, 'El aguijón de la cabeza es muy puntiagudo. Se alimenta de hojas oculto en la espesura de bosques y praderas.', 111, 'Bicho', 'Veneno'),
(14, 'Kakuna', 0.6, 10.0, 'Aunque es casi incapaz de moverse, en caso de sentirse amenazado puede envenenar a los enemigos con su aguijón.', 148, 'Bicho', 'Veneno'),
(15, 'Beedrill', 1.0, 29.5, 'Tiene tres aguijones venenosos, dos en las patas anteriores y uno en la parte baja del abdomen, con los que ataca a sus enemigos una y otra vez.', 53, 'Bicho', 'Veneno'),
(16, 'Pidgey', 0.3, 1.8, 'Su docilidad es tal que suelen defenderse levantando arena en lugar de contraatacar.', 119, 'Normal', 'Volador'),
(17, 'Pidgeotto', 1.1, 30.0, 'Su extraordinaria vitalidad y resistencia le permiten cubrir grandes distancias del territorio que habita en busca de presas.', 117, 'Normal', 'Volador'),
(18, 'Pidgeot', 1.5, 39.5, 'Este Pokémon vuela a una velocidad de 2 mach en busca de presas. Sus grandes garras son armas muy peligrosas.', 73, 'Normal', 'Volador');
