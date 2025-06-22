package models.marioBros.niveles;

import models.marioBros.bloques.*;
import models.marioBros.entidades.Enemigo;

public class Nivel1_1 extends Nivel {
    private final int piso= 208;

    public Nivel1_1() {
        super("/assets/marioBros/World1-1.png","/assets/marioBros/Musica_Nivel1_1.wav");
    }

    @Override
    protected void inicializarEntidades() {

        // Bordes del mapa

        for (int i = 0; i < 15; i++) {
            bloques.add(new BloqueLadrillo(-16, i*16, 16, 16));
        }
        for (int i = 0; i < 15; i++) {
            bloques.add(new BloqueLadrillo(3376, i*16, 16, 16));
        }

        // Bloques del mapa

        // Piso

        for (int i = 0; i < 69; i++) {
            bloques.add(new BloqueLadrillo(i * 16, piso, 16, 16));
        }

        for (int i = 71; i < 86; i++) {
            bloques.add(new BloqueLadrillo(i * 16, piso, 16, 16));
        }

        for (int i = 89; i < 153; i++) {
            bloques.add(new BloqueLadrillo(i * 16, piso, 16, 16));
        }

        for (int i = 155; i < 211; i++) {
            bloques.add(new BloqueLadrillo(i * 16, piso, 16, 16));
        }


        // Bloques
        crearBloqueTuberia(28,2,2);
        crearBloqueTuberia(38,2,3);
        crearBloqueTuberia(46,2,4);
        crearBloqueTuberia(57,2,4);
        crearBloquePregunta(16,4,1,1);

        crearBloqueLadrillo(20,4,1,1);
        crearBloquePregunta(21,4,1,1);
        crearBloqueLadrillo(22,4,1,1);
        crearBloquePregunta(22,8,1,1);
        crearBloquePregunta(23,4,1,1);
        crearBloqueLadrillo(24,4,1,1);

        crearBloqueLadrillo(77,4,1,1);
        crearBloquePregunta(78,4,1,1);
        crearBloqueLadrillo(79,4,1,1);

        crearBloqueLadrillo(80,8,1,1);
        crearBloqueLadrillo(81,8,1,1);
        crearBloqueLadrillo(82,8,1,1);
        crearBloqueLadrillo(83,8,1,1);
        crearBloqueLadrillo(84,8,1,1);
        crearBloqueLadrillo(85,8,1,1);
        crearBloqueLadrillo(86,8,1,1);
        crearBloqueLadrillo(87,8,1,1);

        crearBloqueLadrillo(91,8,1,1);
        crearBloqueLadrillo(92,8,1,1);
        crearBloqueLadrillo(93,8,1,1);
        crearBloquePregunta(94,8,1,1);
        crearBloqueLadrillo(94,4,1,1);

        crearBloqueLadrillo(100,4,1,1);
        crearBloqueLadrillo(101,4,1,1);

        crearBloquePregunta(106,4,1,1);
        crearBloquePregunta(109,4,1,1);
        crearBloquePregunta(109,8,1,1);
        crearBloquePregunta(112,4,1,1);

        crearBloqueLadrillo(118,4,1,1);

        crearBloqueLadrillo(121,8,1,1);
        crearBloqueLadrillo(122,8,1,1);
        crearBloqueLadrillo(123,8,1,1);

        crearBloqueLadrillo(128,8,1,1);
        crearBloquePregunta(129,8,1,1);
        crearBloquePregunta(130,8,1,1);
        crearBloqueLadrillo(131,8,1,1);

        crearBloqueLadrillo(129,4,1,1);
        crearBloqueLadrillo(130,4,1,1);

        crearBloqueLadrillo(134,1,1,1);
        crearBloqueLadrillo(135,2,1,2);
        crearBloqueLadrillo(136,3,1,3);
        crearBloqueLadrillo(137,4,1,4);

        crearBloqueLadrillo(140,4,1,4);
        crearBloqueLadrillo(141,3,1,3);
        crearBloqueLadrillo(142,2,1,2);
        crearBloqueLadrillo(143,1,1,1);

        crearBloqueLadrillo(148,1,1,1);
        crearBloqueLadrillo(149,2,1,2);
        crearBloqueLadrillo(150,3,1,3);
        crearBloqueLadrillo(151,4,1,4);
        crearBloqueLadrillo(152,4,1,4);

        crearBloqueLadrillo(155,4,1,4);
        crearBloqueLadrillo(156,3,1,3);
        crearBloqueLadrillo(157,2,1,2);
        crearBloqueLadrillo(158,1,1,1);

        crearBloqueTuberia(163,2,2);

        crearBloqueLadrillo(168,4,1,1);
        crearBloqueLadrillo(169,4,1,1);
        crearBloquePregunta(170,4,1,1);
        crearBloqueLadrillo(171,4,1,1);

        crearBloqueTuberia(179,2,2);

        crearBloqueLadrillo(181,1,1,1);
        crearBloqueLadrillo(182,2,1,2);
        crearBloqueLadrillo(183,3,1,3);
        crearBloqueLadrillo(184,4,1,4);
        crearBloqueLadrillo(185,5,1,5);
        crearBloqueLadrillo(186,6,1,6);
        crearBloqueLadrillo(187,7,1,7);
        crearBloqueLadrillo(188,8,1,8);
        crearBloqueLadrillo(189,8,1,8);

        crearBloqueVictoria(198,10,1,10);

        crearBloqueMortal(69,-1,2,1);
        crearBloqueMortal(86,-1,3,1);
        crearBloqueMortal(154,-1,3,1);

        crearBloqueTuberia(90,2,2);

        // Enemigos

        crearEnemigo(20,1);
        crearEnemigo(32,1);
        crearEnemigo(50,1);
        crearEnemigo(55,1);
        crearEnemigo(100,1);
        crearEnemigo(105,1);
        crearEnemigo(112,1);
        crearEnemigo(121,1);
        crearEnemigo(149,1);
        crearEnemigo(168,1);
        crearEnemigo(179,1);
        crearEnemigo(176,1);

    }
}