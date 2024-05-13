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

-- MEDALLAS --
insert into Medalla(idMedalla, nombre, ciudad, lider) values
(1, 'Medalla Roca', 'Ciudad Plateada', 'Brock'),
(2, 'Medalla Cascada', 'Ciudad Celeste', 'Misty'),
(3, 'Medalla Trueno', 'Ciudad Carmín', 'Lt Surge'),
(4, 'Medalla Arcoiris', 'Ciudad Azulona', 'Erika'),
(5, 'Medalla Alma', 'Ciudad Fucsia', 'Koga'),
(6, 'Medalla Pantáno', 'Ciudad Azafrán', 'Sabrina'),
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
(18, 'Pidgeot', 1.5, 39.5, 'Este Pokémon vuela a una velocidad de 2 mach en busca de presas. Sus grandes garras son armas muy peligrosas.', 73, 'Normal', 'Volador'),
(19, 'Rattata', 0.3, 3.5, 'Cautiva a su presa con sus grandes ojos para después morderla con sus incisivos afilados.', 93, 'Normal', null),
(20, 'Raticate', 0.7, 18.5, 'Sus colmillos crecen sin cesar. Para evitar que se hagan demasiado largos, roe objetos duros.', 138, 'Normal', null),
(21, 'Spearow', 0.3, 2.0, 'Siempre vuela a gran altura para mantener vigilado su territorio. Cuando divisa a un enemigo, se lanza en picado.', 145, 'Normal', 'Volador'),
(22, 'Fearow', 1.2, 38.0, 'Vuela a gran altura. Si descubre algún enemigo en tierra, lo ataca desde lo alto a toda velocidad.', 141, 'Normal', 'Volador'),
(23, 'Ekans', 2.0, 6.9, 'Se desplaza lentamente husmeando por el suelo. Puede esconderse en la maleza en cualquier momento.', 115, 'Veneno', null),
(24, 'Arbok', 3.5, 65.0, 'Posee una vista excelente. Incluso puede localizar presas que se ocultan detrás de una pared.', 105, 'Veneno', null),
(25, 'Pikachu', 0.4, 6.0, 'Cuanto más carga eléctrica tiene en las mejillas, mejor. Almacena electricidad en las bolsas de sus mejillas.', 1, 'Eléctrico', null),
(26, 'Raichu', 0.8, 30.0, 'Almacena electricidad en su cuerpo. Puede descargarla con más potencia de la que generó originalmente.', 18, 'Eléctrico', null),
(27, 'Sandshrew', 0.6, 12.0, 'Se enrolla para protegerse de los enemigos. Puede rodar a gran velocidad para desplazarse o huir.', 80, 'Tierra', null),
(28, 'Sandslash', 1.0, 29.5, 'Cuando se enrolla, se cubre de pinchos afilados. Si un depredador lo ataca, se defiende con furia.', 96, 'Tierra', null),
(29, 'Nidoran♀', 0.4, 7.0, 'Aunque no es venenoso, tiene un cuerno muy puntiagudo en la cabeza que le sirve para inyectar veneno a sus enemigos.', 151, 'Veneno', null),
(30, 'Nidorina', 0.8, 20.0, 'Si está protegiendo su territorio o su cría, puede volverse muy agresivo y no dudará en atacar.', 139, 'Veneno', null),
(31, 'Nidoqueen', 1.3, 60.0, 'Es muy veloz. Carga contra su objetivo y lo derriba con su cuerno dorsal antes de atacarlo con sus afiladas garras.', 102, 'Veneno', 'Tierra'),
(32, 'Nidoran♂', 0.5, 9.0, 'Posee una extraordinaria agilidad y reflejos. Si se siente amenazado, pincha a sus enemigos con las púas venenosas de su cabeza.', 150, 'Veneno', null),
(33, 'Nidorino', 0.9, 19.5, 'Cada vez que se enfrenta a un rival, la púa de su cabeza crece. Después se la lima luchando contra otros Pokémon.', 129, 'Veneno', null),
(34, 'Nidoking', 1.4, 62.0, 'Carga contra su objetivo a toda velocidad y lo embiste con su cuerno dorsal. Su velocidad puede alcanzar los 60 km/h.', 42, 'Veneno', 'Tierra'),
(35, 'Clefairy', 0.6, 7.5, 'Es un Pokémon muy popular entre las chicas. Tiene una naturaleza dócil y se le puede ver jugando en cualquier prado.', 56, 'Hada', null),
(36, 'Clefable', 1.3, 40.0, 'Su cuerpo está completamente cubierto de pelaje. Siempre ha sido objeto de admiración y se le considera un símbolo de buena suerte.', 55, 'Hada', null),
(37, 'Vulpix', 0.6, 9.9, 'Se considera un símbolo de longevidad. Vulpix vive en el interior de los volcanes y se alimenta de bayas que maduran bajo tierra.', 21, 'Fuego', null),
(38, 'Ninetales', 1.1, 19.9, 'Cuando la luna llena brilla en el cielo nocturno, la cola de Ninetales brilla con todos los colores del arco iris.', 43, 'Fuego', null),
(39, 'Jigglypuff', 0.5, 5.5, 'Si se le enfada, canta una dulce melodía que hace dormir al enemigo. Luego lo dibuja en la cara con suavidad.', 23, 'Normal', 'Hada'),
(40, 'Wigglytuff', 1.0, 12.0, 'A pesar de su aspecto bonachón, puede ser muy agresivo si se le molesta. Se infla para intimidar a los enemigos.', 94, 'Normal', 'Hada'),
(41, 'Zubat', 0.8, 7.5, 'Se le conoce por desplazarse a gran velocidad entre la oscuridad. No le molestan los obstáculos.', 78, 'Veneno', 'Volador'),
(42, 'Golbat', 1.6, 55.0, 'Con la boca llena de afilados colmillos, se lanza sobre su presa y la muerde sin soltarla hasta que la venza.', 108, 'Veneno', 'Volador'),
(43, 'Oddish', 0.5, 5.4, 'Hace la fotosíntesis durante el día usando los rayos del sol. La luna llena le proporciona la energía necesaria para evolucionar.', 79, 'Planta', 'Veneno'),
(44, 'Gloom', 0.8, 8.6, 'Desprende un hedor tan insoportable que no hay nada ni nadie que se le acerque. Por su bien, vive solo.', 35, 'Planta', 'Veneno'),
(45, 'Vileplume', 1.2, 18.6, 'Cuando está furioso, lanza un polen tóxico que hace que la gente y los Pokémon huyan aterrorizados.', 81, 'Planta', 'Veneno'),
(46, 'Paras', 0.3, 5.4, 'Paras tiene setas rojas y blancas en la espalda llamadas tochukaso. Parece que las setas le roban nutrientes al Pokémon host.', 75, 'Bicho', 'Planta'),
(47, 'Parasect', 1.0, 29.5, 'Parasect es conocido por tener setas que crecen en su espalda y le roban energía al Pokémon host.', 140, 'Bicho', 'Planta'),
(48, 'Venonat', 1.0, 30.0, 'Venonat posee grandes ojos que nunca descansan. Por la noche, es atraído por la luz y puede ver hasta en la oscuridad.', 147, 'Bicho', 'Veneno'),
(49, 'Venomoth', 1.5, 12.5, 'La luz de la luna llena aumenta la toxicidad de Venomoth. Los bosques donde habita están llenos de polvo venenoso.', 136, 'Bicho', 'Veneno'),
(50, 'Diglett', 0.2, 0.8, 'Diglett es un Pokémon dócil que se alimenta de raíces y tubérculos que excava bajo tierra.', 45, 'Tierra', null),
(51, 'Dugtrio', 0.7, 33.3, 'El cabello metálico de Dugtrio brilla con un brillo misterioso. Se dice que cada uno tiene un temperamento diferente.', 120, 'Tierra', null),
(52, 'Meowth', 0.4, 4.2, 'Meowth es atraído por objetos brillantes y monedas. Hay quienes creen que trae buena suerte si aparece.', 28, 'Normal', null),
(53, 'Persian', 1.0, 32.0, 'Persian tiene un carácter extremadamente orgulloso. No dudará en arañar a quienquiera que osara tocarle un pelo.', 31, 'Normal', null),
(54, 'Psyduck', 0.8, 19.6, 'Siempre le duele la cabeza. Cuando se enfada, le sube la fiebre y desarrolla un poder psíquico extraordinario.', 41, 'Agua', null),
(55, 'Golduck', 1.7, 76.6, 'Golduck es un experto nadador que puede nadar sin fatigarse durante todo un día gracias a su físico aerodinámico.', 121, 'Agua', null),
(56, 'Mankey', 0.5, 28.0, 'Cuando está enfadado, Mankey se convierte en un peligroso rival. Se enfurece por cualquier cosa.', 122, 'Lucha', null),
(57, 'Primeape', 1.0, 32.0, 'Primeape se enfada con gran facilidad. Si le irrito, puede resultar muy peligroso y violento.', 128, 'Lucha', null),
(58, 'Growlithe', 0.7, 19.0, 'Growlithe tiene un olfato extraordinario. Se dice que puede seguir el olor de algo a más de una milla de distancia.', 36, 'Fuego', null),
(59, 'Arcanine', 1.9, 155.0, 'Arcanine es conocido por su velocidad increíble. Se dice que puede correr 6.200 millas en 24 horas.', 24, 'Fuego', null),
(60, 'Poliwag', 0.6, 12.4, 'Poliwag tiene una piel suave y lisa. Puede absorber agua y mantenerla en su cuerpo.', 103, 'Agua', null),
(61, 'Poliwhirl', 1.0, 20.0, 'Poliwhirl tiene una piel suave y lisa. Puede absorber agua y mantenerla en su cuerpo.', 113, 'Agua', null),
(62, 'Poliwrath', 1.3, 54.0, 'Poliwrath tiene un físico robusto y musculoso. Puede nadar perfectamente incluso en aguas turbulentas.', 92, 'Agua', 'Lucha'),
(63, 'Abra', 0.9, 19.5, 'Abra duerme dieciocho horas al día. También tiene un gran poder psíquico, aunque apenas lo utiliza.', 17, 'Psíquico', null),
(64, 'Kadabra', 1.3, 56.5, 'Kadabra posee un gran poder psíquico. Se dice que puede crear ilusiones tan reales que te resultará difícil distinguirlas de la realidad.', 82, 'Psíquico', null),
(65, 'Alakazam', 1.5, 48.0, 'Alakazam tiene un coeficiente intelectual de más de 5.000. Es uno de los Pokémon más inteligentes que existen.', 34, 'Psíquico', null),
(66, 'Machop', 0.8, 19.5, 'Machop tiene un físico extremadamente fuerte. Es capaz de levantar pesas que sobrepasan su propio peso.', 85, 'Lucha', null),
(67, 'Machoke', 1.5, 70.5, 'Machoke se pasa todo el día ejercitándose. De esta manera, mantiene su musculatura siempre en óptimas condiciones.', 84, 'Lucha', null),
(68, 'Machamp', 1.6, 130.0, 'Machamp es conocido por su fuerza increíble. Puede mover montañas de un solo golpe.', 29, 'Lucha', null),
(69, 'Bellsprout', 0.7, 4.0, 'Bellsprout tiene una raíz muy larga que le sirve para absorber agua y nutrientes del suelo. Vive en lugares pantanosos.', 106, 'Planta', 'Veneno'),
(70, 'Weepinbell', 1.0, 6.4, 'Weepinbell tiene un apéndice que le cuelga del cuello y libera un aroma fuerte y penetrante para atraer presas.', 143, 'Planta', 'Veneno'),
(71, 'Victreebel', 1.7, 15.5, 'Victreebel libera un aroma a muerte que atrae a sus presas. La trampa de su boca se cierra en cuanto se acercan.', 127, 'Planta', 'Veneno'),
(72, 'Tentacool', 0.9, 45.5, 'Tentacool flota a la deriva en los océanos. Los rayos del sol brillan a través de su cuerpo, produciendo un bello resplandor.', 137, 'Agua', 'Veneno'),
(73, 'Tentacruel', 1.6, 55.0, 'Tentacruel tiene tentáculos que segregan un líquido tóxico. Estos tentáculos pueden alcanzar hasta 80 pies de largo.', 131, 'Agua', 'Veneno'),
(74, 'Geodude', 0.4, 20.0, 'Geodude es conocido por sus durezas. Se dice que fue creado por la unión de materiales rocosos y tierra bajo presión.', 71, 'Roca', 'Tierra'),
(75, 'Graveler', 1.0, 105.0, 'Graveler es muy fuerte, incluso para un Pokémon Roca. Puede levantar piedras de gran tamaño con facilidad.', 132, 'Roca', 'Tierra'),
(76, 'Golem', 1.4, 300.0, 'Golem tiene una piel extremadamente dura y pesada. Se dice que puede derribar un edificio con un solo golpe.', 13, 'Roca', 'Tierra'),
(77, 'Ponyta', 1.0, 30.0, 'Ponyta tiene una constitución ligera y ágil. Es muy rápido, incluso al correr sobre terreno irregular.', 49, 'Fuego', null),
(78, 'Rapidash', 1.7, 95.0, 'Rapidash es capaz de correr a grandes velocidades. Su melena arde con un fuego tan intenso que parece la estela de una estrella fugaz.', 58, 'Fuego', null),
(79, 'Slowpoke', 1.2, 36.0, 'Slowpoke es muy tranquilo y relajado, siempre va a su propio ritmo. Es lento en todos los aspectos.', 20, 'Agua', 'Psíquico'),
(80, 'Slowbro', 1.6, 78.5, 'Slowbro tiene un shellder pegado a su cola que le muerde cuando Slowbro siente peligro. Esto le hace alcanzar un estado de frenesí.', 44, 'Agua', 'Psíquico'),
(81, 'Magnemite', 0.3, 6.0, 'Magnemite flota gracias a la energía magnética que produce. Es atraído por la electricidad y puede ser encontrado cerca de las centrales eléctricas.', 70, 'Eléctrico', 'Acero'),
(82, 'Magneton', 1.0, 60.0, 'Magneton es el resultado de tres Magnemite que se han unido por el magnetismo. Se mueve formando un triángulo y emite un zumbido constante.', 76, 'Eléctrico', 'Acero'),
(83, 'Farfetchd', 0.8, 15.0, 'Farfetchd siempre lleva una rama de apio en la mano. Es su comida favorita y la considera un tesoro.', 63, 'Normal', 'Volador'),
(84, 'Doduo', 1.4, 39.2, 'Doduo puede correr a velocidades de hasta 60 mph, pero se desorienta si tiene que girar en una dirección.', 144, 'Normal', 'Volador'),
(85, 'Dodrio', 1.8, 85.2, 'Los cerebros de los tres cabezas de Dodrio se conectan entre sí, por lo que pueden reaccionar ante cualquier amenaza más rápido que cualquier otro Pokémon.', 146, 'Normal', 'Volador'),
(86, 'Seel', 1.1, 90.0, 'Seel se alimenta de plantas marinas que crecen en los fondos marinos. Es muy hábil nadando y buceando.', 99, 'Agua', null),
(87, 'Dewgong', 1.7, 120.0, 'Dewgong se traslada a latitudes más al norte en invierno. Su capa de grasa le permite vivir en aguas gélidas.', 114, 'Agua', 'Hielo'),
(88, 'Grimer', 0.9, 30.0, 'Grimer descompone y digiere cualquier cosa que entra en contacto con él. Esta inmunidad a los venenos se atribuye a su cuerpo compuesto por materiales tóxicos.', 52, 'Veneno', null),
(89, 'Muk', 1.2, 30.0, 'Muk produce constantemente fluidos pegajosos y tóxicos de todo su cuerpo. Es capaz de disolver cualquier cosa que toque.', 51, 'Veneno', null),
(90, 'Shellder', 0.3, 4.0, 'Shellder es un molusco bivalvo. Es comestible y tiene un sabor delicioso, pero es difícil de atrapar.', 118, 'Agua', null),
(91, 'Cloyster', 1.5, 132.5, 'Cloyster se cierra con fuerza y usa su concha dura para protegerse de los depredadores. Su ataque es tan fuerte que puede romper rocas.', 64, 'Agua', 'Hielo'),
(92, 'Gastly', 1.3, 0.1, 'Gastly es un gas tóxico que puede flotar en el aire. Causa miedo a los humanos y Pokémon.', 57, 'Fantasma', 'Veneno'),
(93, 'Haunter', 1.6, 0.1, 'Por la noche, se pone a absorber el calor corporal de la gente y a robarles el alma.', 59, 'Fantasma', 'Veneno'),
(94, 'Gengar', 1.5, 40.5, 'Para ocultarse, se camufla como sombra de las personas. Le encanta asustar.', 6, 'Fantasma', 'Veneno'),
(95, 'Onix', 8.8, 210.0, 'Nace a partir de piedras que se encuentran en la montaña. Alcanza gran tamaño en poco tiempo.', 3, 'Roca', 'Tierra'),
(96, 'Drowzee', 1.0, 32.4, 'Si se te aparece en sueños para absorber tus pesadillas, ¡te costará volver a conciliar el sueño!', 124, 'Psíquico', null),
(97, 'Hypno', 1.6, 75.6, 'Aprovecha la oscilación del péndulo de su mano para hipnotizar a su enemigo y así comerse sus sueños.', 77, 'Psíquico', null),
(98, 'Krabby', 0.4, 6.5, 'Es muy agresivo y siempre está dispuesto a luchar. Se le considera la mascota oficial de los entrenadores Pokémon.', 135, 'Agua', null),
(99, 'Kingler', 1.3, 60.0, 'Es muy duro, tanto que no se le daña aunque le caigan rocas y bloques de hormigón.', 109, 'Agua', null),
(100, 'Voltorb', 0.5, 10.4, 'Cuando intentas atraparlo, puede explotar en cualquier momento. Se dice que es la evolución fallida de un Poké Ball.', 123, 'Eléctrico', null),
(101, 'Electrode', 1.2, 66.6, 'Cuando almacena demasiada electricidad, este Pokémon puede estallar en cualquier momento sin previo aviso.', 83, 'Eléctrico', null),
(102, 'Exeggcute', 0.4, 2.5, 'Se formó a partir de seis huevos. Parece ser que está relacionado con algunos Pokémon tipo Planta.', 125, 'Planta', 'Psíquico'),
(103, 'Exeggutor', 2.0, 120.0, 'Es capaz de utilizar el rayo solar con los cien núcleos de su cabeza para arrasar una montaña.', 39, 'Planta', 'Psíquico'),
(104, 'Cubone', 0.4, 6.5, 'Usa los huesos que encuentra como arma y también como herramienta de comunicación entre sus congéneres.', 48, 'Tierra', null),
(105, 'Marowak', 1.0, 45.0, 'Golpea con su garrote de hueso. Tiene la costumbre de bailar con él en la pata trasera.', 38, 'Tierra', null),
(106, 'Hitmonlee', 1.5, 49.8, 'Extiende las piernas elásticas para dar patadas a una velocidad increíblemente alta. Solo necesita su cuerpo para atacar.', 87, 'Lucha', null),
(107, 'Hitmonchan', 1.4, 50.2, 'Mientras despliega una velocidad increíble, lanza golpes tan rápidos que son indetectables para el ojo humano.', 90, 'Lucha', null),
(108, 'Lickitung', 1.2, 65.5, 'Lame sin cesar todo lo que encuentra a su paso. Si se encuentra algo desconocido, lo prueba con su lengua.', 97, 'Normal', null),
(109, 'Koffing', 0.6, 1.0, 'Inhala gases venenosos por la boca y los expulsa por los orificios de su piel. Los gases que expulsa pueden ser de diferentes colores.', 89, 'Veneno', null),
(110, 'Weezing', 1.2, 9.5, 'Los gases que acumula en su interior hacen que se hinche hasta alcanzar un tamaño enorme. Es más resistente de lo que parece.', 86, 'Veneno', null),
(111, 'Rhyhorn', 1.0, 115.0, 'Corre en línea recta para embestir a su enemigo con el cuerno que lleva en el hocico.', 74, 'Tierra', 'Roca'),
(112, 'Rhydon', 1.9, 120.0, 'Parece ser que está relacionado con los antiguos reptiles. Posee un cuerno gigantesco en el morro.', 54, 'Tierra', 'Roca'),
(113, 'Chansey', 1.1, 34.6, 'Siempre lleva un huevo en la bolsa que tiene en el vientre. Si ve a alguien malherido, lo cura con una pequeña porción del huevo.', 67, 'Normal', null),
(114, 'Tangela', 1.0, 35.0, 'La masa de vides de este Pokémon se enreda y desenreda constantemente para atrapar a su presa.', 100, 'Planta', null),
(115, 'Kangaskhan', 2.2, 80.0, 'Lleva siempre a su cría en la bolsa ventral. Siempre que se siente en peligro, protege a su hijo con la vida.', 65, 'Normal', null),
(116, 'Horsea', 0.4, 8.0, 'Se lanza al agua y busca agujeros de roca donde meterse. Se han encontrado fosiles de Horsea en montañas.', 133, 'Agua', null),
(117, 'Seadra', 1.2, 25.0, 'Posee una cola poderosa que puede desgarrar hasta un bloque de hierro. Se dice que su cola es una auténtica joya.', 130, 'Agua', null),
(118, 'Goldeen', 0.6, 15.0, 'Nada elegantemente por los ríos y lagos. Cuando está en peligro, despliega sus aletas dorsal y pectoral para intimidar', 142, 'Agua', null),
(119, 'Seaking', 1.3, 39.0, 'Su bello cuerno es una maravilla. Se cree que este cuerno tiene propiedades curativas.', 134, 'Agua', null),
(120, 'Staryu', 0.8, 34.5, 'En noches de luna llena, se agrupan flotando sobre el agua para observar las estrellas.', 112, 'Agua', null),
(121, 'Starmie', 1.1, 80.0, 'Dicen que está formado por dos estrellas, pero nadie sabe si es cierto. Brilla tanto que puede cegar.', 104, 'Agua', 'Psíquico'),
(122, 'Mr. Mime', 1.3, 54.5, 'Le gusta hacer gestos y bailar para distraer a su enemigo mientras le lanza ataques de energía psíquica.', 149, 'Psíquico', 'Hada'),
(123, 'Scyther', 1.5, 56.0, 'Con sus afiladas garras, es capaz de cercenar una columna de acero con un solo golpe.', 40, 'Bicho', 'Volador'),
(124, 'Jynx', 1.4, 40.6, 'Lanza besos a sus objetivos para confundirlos y luego golpearlos con sus puños y pies de hielo.', 88, 'Hielo', 'Psíquico'),
(125, 'Electabuzz', 1.1, 30.0, 'Genera electricidad caminando, y la almacena en su pelo para atacar con descargas eléctricas.', 68, 'Eléctrico', null),
(126, 'Magmar', 1.3, 44.5, 'Lanza llamas de más de 1.700 °C desde su boca y del lomo. Su fuego puede prender ropa y pelo al contacto.', 69, 'Fuego', null),
(127, 'Pinsir', 1.5, 55.0, 'Su potente mandíbula puede partir troncos y bloques de hierro sin ningún esfuerzo.', 66, 'Bicho', null),
(128, 'Tauros', 1.4, 88.4, 'Este Pokémon ataca embistiendo con todo su cuerpo. Es muy testarudo y terco.', 101, 'Normal', null),
(129, 'Magikarp', 0.9, 10.0, 'Se dice que este Pokémon no sirve para nada, así que no se molesta en luchar. Apenas se mueve, y eso es lo más que hace.', 33, 'Agua', null),
(130, 'Gyarados', 6.5, 235.0, 'Cuando se enfada, se vuelve un ser violento y despiadado que arrasa todo lo que encuentra a su paso.', 10, 'Agua', 'Volador'),
(131, 'Lapras', 2.5, 220.0, 'Es tan gentil y noble que hasta se le puede subir un niño pequeño. Dicen que lleva a las personas en su lomo a nado.', 9, 'Agua', 'Hielo'),
(132, 'Ditto', 0.3, 4.0, 'Puede transformarse en cualquier cosa. Si se le ve transformado en algo, enseguida se convierte en algo diferente.', 12, 'Normal', null),
(133, 'Eevee', 0.3, 6.5, 'Puede evolucionar a una de ocho formas diferentes de Pokémon según el método de evolución.', 5, 'Normal', null),
(134, 'Vaporeon', 1.0, 29.0, 'Su estructura celular se similar a la de una esponja, lo que le permite ocultarse en el agua sin ser detectado.', 27, 'Agua', null),
(135, 'Jolteon', 0.8, 24.5, 'Los finísimos filamentos de su pelaje pueden generar hasta 20.000 voltios.', 50, 'Eléctrico', null),
(136, 'Flareon', 0.9, 25.0, 'Cuando estalla una erupción volcánica, dicen que surge un Flareon de entre las llamas.', 62, 'Fuego', null),
(137, 'Porygon', 0.8, 36.5, 'Es capaz de moverse en el mundo virtual y de convertir en realidad sus propios movimientos.', 26, 'Normal', null),
(138, 'Omanyte', 0.4, 7.5, 'Este Pokémon se extinguió hace millones de años. Cada uno de los fósiles encontrados está congelado.', 98, 'Roca', 'Agua'),
(139, 'Omastar', 1.0, 35.0, 'Una vez que captura a su presa con sus tentáculos, le inyecta un veneno que la paraliza para luego devorarla.', 116, 'Roca', 'Agua'),
(140, 'Kabuto', 0.5, 11.5, 'Vivió hace millones de años. Cada vez que muda la concha, su tamaño aumenta.', 25, 'Roca', 'Agua'),
(141, 'Kabutops', 1.3, 40.5, 'Se cree que era un cazador ágil que capturaba a sus presas usando sus afiladas guadañas.', 95, 'Roca', 'Agua'),
(142, 'Aerodactyl', 1.8, 59.0, 'Es capaz de volar tan rápido que parece que ni siquiera se mueve las alas.', 46, 'Roca', 'Volador'),
(143, 'Snorlax', 2.1, 460.0, 'Este Pokémon se pasa todo el día durmiendo. Para comer, solo se despierta un ratito.', 8, 'Normal', null),
(144, 'Articuno', 1.7, 55.4, 'Este Pokémon vive en una tundra congelada. Es capaz de crear un copo de nieve con solo aletear las alas.', 32, 'Hielo', 'Volador'),
(145, 'Zapdos', 1.6, 52.6, 'Este Pokémon vive en la atmósfera. Es capaz de lanzar rayos eléctricos con una precisión asombrosa.', 30, 'Eléctrico', 'Volador'),
(146, 'Moltres', 2.0, 60.0, 'Es un Pokémon legendario. Dicen que su resplandor puede ser visto a kilómetros de distancia en una noche oscura.', 37, 'Fuego', 'Volador'),
(147, 'Dratini', 1.8, 3.3, 'Es un Pokémon que vive en ríos de aguas limpias. Su largo cuerpo le permite nadar con gran elegancia.', 47, 'Dragón', null),
(148, 'Dragonair', 4.0, 16.5, 'Entra en un estado de éxtasis al emerger de su capullo y se pone a danzar con una majestuosidad asombrosa.', 60, 'Dragón', null),
(149, 'Dragonite', 2.2, 210.0, 'Es capaz de cambiar el clima mediante la energía que acumula en sus alas. Se dice que vive en las montañas más remotas.', 15, 'Dragón', 'Volador'),
(150, 'Mewtwo', 2.0, 122.0, 'Es un Pokémon creado mediante manipulación genética. Es muy poderoso y su inteligencia es comparable a la humana.', 7, 'Psíquico', null),
(151, 'Mew', 0.4, 4.0, 'Es capaz de aprender cualquier movimiento. Su ADN es muy similar al de todos los Pokémon.', 4, 'Psíquico', null);
