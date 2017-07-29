const val DRAW_ENVIRONMENT = 0
const val STD_RANDOMNESS = 0.1

fun Flows._init_values() {

    // ===== GLOBAl ========

    // ===== WALLS =========

    //Wall: left stone
    _wall(0,94,40,94);
    _wall(40,94,39,104);
    _wall(39,104,0,104);
    _wall(0,104,0,94);

    //Wall: left down stone strap trap
    _wall(40,0,50,0);
    _wall(50,0,50,40);
    _wall(50,40,70,40);
    _wall(70,40,70,60);
    _wall(70,60,100,60);
    _wall(100,60,100,90);
    _wall(100,90,70,90);
    _wall(70,90,70,70);
    _wall(70,70,40,70);
    _wall(40,70,40,0);

    //Wall: down doublestone stone
    _wall(140,60,220,60);
    _wall(220,60,220,30);
    _wall(220,30,230,30);
    _wall(230,30,230,70);
    _wall(230,70,140,70);
    _wall(140,70,140,60);

    //Wall: down stone
    _wall(310,0,320,0);
    _wall(320,0,320,40);
    _wall(320,40,310,40);
    _wall(310,40,310,0);

    //Wall: down swamp cross
    _wall(231,30,250,0);

    //Wall: tower stone trap trap stones around up right deposite)
    _wall(170,200,175,187);
    _wall(175,187,195,187);
    _wall(195,187,195,195);
    _wall(195,195,258,195);
    _wall(258,195,282,158);
    _wall(282,158,291,165);
    _wall(291,165,275,188);
    _wall(275,188,297,206);
    _wall(297,206,314,181);
    _wall(314,181,324,187);
    _wall(324,187,300,217);
    _wall(300,217,295,244);
    _wall(295,244,265,244);
    _wall(265,244,265,228);
    _wall(265,228,234,227);
    _wall(234,227,234,207);
    _wall(234,207,195,207);
    _wall(195,207,190,209);
    _wall(190,209,177,213);
    _wall(177,213,170,200);

    //Wall: up swamp cross
    _wall(120,175,170,200);

    //Wall: right trap

    _wall(330,70,360,70);
    _wall(360,70,360,100);
    _wall(360,100,330,100);
    _wall(330,100,330,70);

    //Wall: up stone
    _wall(110,230,120,230);
    _wall(120,230,121,270);
    _wall(121,270,110,270);
    _wall(110,270,110,230);

    //Quick areas
    _area(0,175,140,110);
    _area(240,157,324,45);
    _area(320,175,357,99);

    // ===== ENVIRONMENT: deposit
    _environment(0.0);

    _environment_route(true);
    _environment_route_point(61, 220,50);
    _environment_route_point(66, 108,10);
    _environment_route_point(52, 86,10);
    _environment_route_point(18, 71,5);
    _environment_route_point(12, 21,5);

    _environment_route(false);
    _environment_route_point(179, 29,20);
    _environment_route_point(117, 35,20);
    _environment_route_point(119, 108,10);
    _environment_route_point(66, 108,10);
    _environment_route_point(51, 87,10);

    _environment_route(true);
    _environment_route_point(134, 214,20);
    _environment_route_point(163, 249,15);
    _environment_route_point(281, 257,10);
    _environment_route_point(340, 248,10);
    _environment_route_point(338, 153,10);
    _environment_route_point(308, 169,5);
    _environment_route_point(290, 187,5);

    _environment_route(true);
    _environment_route_point(149, 155,30);
    _environment_route_point(241, 120,30);
    _environment_route_point(313, 137,10);
    _environment_route_point(308, 168,5);
    _environment_route_point(290, 187,5);

    _environment_route(true);
    _environment_route_point(253, 61,20);
    _environment_route_point(339, 55,10);
    _environment_route_point(342, 19,5);

    _flowPoint(110, 234,20,-135);
    _flowPoint(111, 147,40,-135);
    _flowPoint(25, 120,40,0);
    _flowPoint(75, 40,30,0);
    _flowPoint(65, 20,30,0);
    _flowPoint(97, 60,20,-10);


    // ===== ENVIRONMENT: normal 1
    _environment(STD_RANDOMNESS);

    _environment_route(true);
    _environment_route_point(14, 252, 20);
    _environment_route_point(75, 220, 25);
    _environment_route_point(100, 216, 6);
    _environment_route_point(140, 216, 15);
    _environment_route_point(181, 252, 25);
    _environment_route_point(260, 260, 6);
    _environment_route_point(307, 260, 10);
    _environment_route_point(339, 207, 13);
    _environment_route_point(343, 172, 6);
    _environment_route_point(273, 118, 40);
    _environment_route_point(147, 127, 40);
    _environment_route_point(120, 95, 8);
    _environment_route_point(127, 25, 15);
    _environment_route_point(139, 17, 15);
    _environment_route_point(217, 12, 23);

    _environment_route(false);
    _environment_route_point(12, 22, 10);
    _environment_route_point(18, 72, 8);
    _environment_route_point(53, 85, 7);
    _environment_route_point(67, 108, 12);
    _environment_route_point(108,106, 18);

    _environment_route(false);
    _environment_route_point(346, 18, 8);
    _environment_route_point(339, 52, 8);
    _environment_route_point(278, 72, 8);
    _environment_route_point(246, 122, 14);

    _flowPoint(115, 192, 20, 135);
    _flowPoint(116, 176, 20, -145);
    _flowPoint(175, 176, 24, -80);
    _flowPoint(25, 110, 15, 40);
    _flowPoint(46, 65, 14, 135);
    _flowPoint(75, 84, 14, 145);
    _flowPoint(90, 83, 15, 33);
    _flowPoint(68, 31, 23, -30);
    _flowPoint(146, 74, 12, 150);
    //_flowPoint(240,54,30, 56);
    _flowPoint(252,42, 60, 58);
    _flowPoint(285, 39, 63, 80);
    _flowPoint(233,73,20,60);
    _flowPoint(339, 91, 14, 135);
    _flowPoint(337, 77, 14, -135);
    _flowPoint(88,60,17,-60);
    _flowPoint(241,219,16,115);
    _flowPoint(272,236,16,115);
    _flowPoint(264,218,30,123);


    // ===== ENVIRONMENT: normal 2
    _environment(STD_RANDOMNESS);

    //flow from down left deposit to upper part
    _environment_route(false);
    _environment_route_point(12, 22, 10);
    _environment_route_point(18, 72, 8);
    _environment_route_point(53, 85, 7);
    _environment_route_point(67, 108, 12);
    _environment_route_point(30, 191,  18);


    //flow from down bonus to up
    _environment_route(true);
    _environment_route_point(203,51,20);
    _environment_route_point(119,52,20);
    _environment_route_point(120,112,10);
    _environment_route_point(30,191,25);
    _environment_route_point(16,252,20);

    // /flow from very right via uppper part to up left
    _environment_route(true);
    _environment_route_point(343, 172, 6);
    _environment_route_point(339, 207, 13);
    _environment_route_point(307, 260, 10);
    _environment_route_point(260, 260, 6);
    _environment_route_point(181, 252, 25);
    _environment_route_point(140, 216, 15);
    _environment_route_point(100, 216, 6);
    _environment_route_point(75, 220, 25);
    _environment_route_point(14, 252, 20);

    //flow from down right deposit to up left
    _environment_route(false);
    _environment_route_point(340, 22,10);
    _environment_route_point(337, 53,10);
    _environment_route_point(244, 98,30);
    _environment_route_point(121, 130,40);

    _flowPoint(294, 184,30, -45);
    _flowPoint(182, 178,20,-90);
    _flowPoint(84, 50, 18,-10);
    _flowPoint(62, 30,20,-10);
    _flowPoint(99, 60, 20,-10);
    _flowPoint(230, 12,20,180);
    _flowPoint(260, 20,20,90);
    _flowPoint(248, 51,40,75);
    _flowPoint(248, 51,40,75);
    _flowPoint(237, 74,20,90);
    _flowPoint(127, 190,20,180);
    _flowPoint(152, 179,20,-80);
    _flowPoint(166, 179,20,-80);
    _flowPoint(132, 176,10,-80);
    _flowPoint(129, 252,30,-80);
    _flowPoint(128, 238,20,-80);
    _flowPoint(331, 70,18,-170);
    _flowPoint(297, 242,18,90);

}
