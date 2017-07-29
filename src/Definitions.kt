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
    _area(104, 205, 157, 54);
    _area(104, 80, 302, 0);
    _area(236, 226, 270, 66);

    // ===== ENVIRONMENT: deposit
    _environment(0.0);

    _environment_route(false);
    _environment_route_point(23, 144, 8);
    _environment_route_point(72, 76, 8);
    _environment_route_point(130, 68, 12);
    _environment_route_point(127, 27, 10);
    _environment_route_point(20, 15, 4);

    _environment_route(false);
    _environment_route_point(133, 118, 10);
    _environment_route_point(282, 59, 6);
    _environment_route_point(338, 53, 4);
    _environment_route_point(342, 20, 4);

    _environment_route(false);
    _environment_route_point(47, 224, 15);
    _environment_route_point(180, 219, 8);
    _environment_route_point(179, 252, 4);

    _environment_route(false);
    _environment_route_point(337, 116, 6);
    _environment_route_point(255, 164, 6);
    _environment_route_point(255, 218, 8);
    _environment_route_point(174, 223, 8);
    _environment_route_point(178, 249, 6);

    _flowPoint(20, 66, 20, 60);
    _flowPoint(85, 102, 23, -125);
    _flowPoint(69, 144, 15, -135);
    _flowPoint(135, 242, 35, -120);
    _flowPoint(222, 238, 33, -70);
    _flowPoint(296, 29, 20, 100);
    _flowPoint(321, 228, 38, -170);
    _flowPoint(285, 122, 16, 70);
    _flowPoint(160, 49, 24, -165);
    _flowPoint(73, 35, 15, -90);
    _flowPoint(228, 137, 24, -10);
    _flowPoint(177, 144, 24, -130);
    _flowPoint(333, 68, 16, -80);
    _flowPoint(169, 167, 20, -140);
    _flowPoint(337, 190, 24, 135);
    _flowPoint(298,174, 20, 140);
    _flowPoint(23, 192, 20, 40);

    // ===== ENVIRONMENT: normal 1
    _environment(STD_RANDOMNESS);

    _environment_route(false);
    _environment_route_point(13, 154, 18);
    _environment_route_point(66, 77, 8);
    _environment_route_point(123, 71, 12);
    _environment_route_point(115, 173, 20);
    _environment_route_point(22, 230, 34);
    _environment_route_point(26, 245, 40);
    _environment_route_point(125, 250, 30);

    _environment_route(false);
    _environment_route_point(38, 18, 10);
    _environment_route_point(118, 22, 8);
    _environment_route_point(135, 57, 18);

    _flowPoint(55, 53, 25, 60);
    _flowPoint(180, 252, 50, -110);
    _flowPoint(332, 24, 30, 85);
    _flowPoint(302, 112, 35, 85);
    _flowPoint(80, 105, 23, -135);

    _flowPoint(222, 188, 26, 80);
    _flowPoint(210, 148, 35, -60);
    _flowPoint(173, 198, 24, 130);
    _flowPoint(29, 189, 16, 70);

    // ===== ENVIRONMENT: normal 2
    _environment(STD_RANDOMNESS);

    _environment_route(false);
    _environment_route_point(121, 217, 10);
    _environment_route_point(226, 215, 13);
    _environment_route_point(268, 259, 27);
    _environment_route_point(333, 244, 28);
    _environment_route_point(323, 214, 14);
    _environment_route_point(261, 188, 10);
    _environment_route_point(252, 154, 6);
    _environment_route_point(303, 143, 4);
    _environment_route_point(340, 118, 35);
    _environment_route_point(344, 100, 10);

    _flowPoint(166, 166, 19, 150);
    _flowPoint(180, 240, 35, -110);
    _flowPoint(133, 240, 35, -110);
    _flowPoint(129, 147, 60, 90);
    _flowPoint(303, 177, 25, 140);
    _flowPoint(21, 183, 20, 40);
    _flowPoint(62, 115, 32, -103);
    _flowPoint(54, 20, 37, -30);
    _flowPoint(92, 154, 20, 45);
    _flowPoint(191, 145, 20, -100);
    _flowPoint(216, 189, 22, 70);
    _flowPoint(165, 135, 20, 125);
    _flowPoint(305, 102, 23, 40);
    _flowPoint(170, 192, 30, 130);
    _flowPoint(284, 233, 15, 40);
    _flowPoint(336, 192, 12, 140);
    _flowPoint(220, 134, 13, -80);
    _flowPoint(80, 93, 18, -120);
    _flowPoint(289, 165, 15, -135);
    _flowPoint(245, 224, 25, 95);

    _environment_route(false);
    _environment_route_point(340, 28, 6);
    _environment_route_point(336, 51, 6);
    _environment_route_point(242, 70, 10);
    _environment_route_point(239, 103, 15);
    _environment_route_point(258, 162, 6);

    _environment_route(false);
    _environment_route_point(21, 75, 6);
    _environment_route_point(113, 75, 10);
    _environment_route_point(248, 103, 13);

    _environment_route(false);
    _environment_route_point(20, 16, 10);
    _environment_route_point(125, 17, 8);
    _environment_route_point(146, 88, 16);

    // ===== ENVIRONMENT: normal 3
    _environment(STD_RANDOMNESS);

    _environment_route(false);
    _environment_route_point(343, 102, 18);
    _environment_route_point(311, 140, 20);
    _environment_route_point(295, 147, 4);
    _environment_route_point(255, 154, 6);
    _environment_route_point(240, 108, 20);
    _environment_route_point(142, 104, 30);
    _environment_route_point(103, 68, 10);
    _environment_route_point(47, 96, 15);
    _environment_route_point(11, 158, 20);

    _environment_route(false);
    _environment_route_point(340, 24, 10);
    _environment_route_point(337, 51, 6);
    _environment_route_point(238, 75, 10);
    _environment_route_point(140, 87, 15);

    _environment_route(false);
    _environment_route_point(21, 12, 5);
    _environment_route_point(130, 16, 6);
    _environment_route_point(139, 62, 10);
    _environment_route_point(110, 74, 10);

    _environment_route(false);
    _environment_route_point(50, 240, 15);
    _environment_route_point(132, 183, 10);
    _environment_route_point(138, 124, 10);

    _flowPoint(220, 188, 25, 20);
    _flowPoint(217, 145, 25, -60);
    _flowPoint(64, 126, 30, 175);
    _flowPoint(36, 182, 30, 40);
    _flowPoint(294, 116, 30, 60);
    _flowPoint(84, 91, 20, -140);
    _flowPoint(94, 41, 24, -50);
    _flowPoint(302, 182, 24, 135);
    _flowPoint(339, 190, 20, 140);
    _flowPoint(341, 160, 18, -130);
    _flowPoint(46, 35, 15, -50);

}
