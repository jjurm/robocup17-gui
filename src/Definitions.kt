const val DRAW_ENVIRONMENT = 0
const val STD_RANDOMNESS = 0.1

fun Flows._init_values() {

    // ===== GLOBAL =========

    //Wall: left stone
    _wall(0, 94, 40, 94);
    _wall(40, 94, 39, 104);
    _wall(39, 104, 0, 104);
    _wall(0, 104, 0, 94);

    //Wall: left down stone strap trap
    _wall(40, 0, 50, 0);
    _wall(50, 0, 50, 40);
    _wall(50, 40, 70, 40);
    _wall(70, 40, 70, 60);
    _wall(70, 60, 100, 60);
    _wall(100, 60, 100, 90);
    _wall(100, 90, 70, 90);
    _wall(70, 90, 70, 70);
    _wall(70, 70, 40, 70);
    _wall(40, 70, 40, 0);

    //Wall: down doublestone stone
    _wall(140, 60, 220, 60);
    _wall(220, 60, 220, 30);
    _wall(220, 30, 230, 30);
    _wall(230, 30, 230, 70);
    _wall(230, 70, 140, 70);
    _wall(140, 70, 140, 60);

    //Wall: down stone
    _wall(310, 0, 320, 0);
    _wall(320, 0, 320, 40);
    _wall(320, 40, 310, 40);
    _wall(310, 40, 310, 0);

    //Wall: down swamp cross
    _wall(231, 30, 250, 0);

    //Wall: tower stone trap trap stones around up right deposite)
    _wall(170, 200, 175, 187);
    _wall(175, 187, 195, 187);
    _wall(195, 187, 195, 195);
    _wall(195, 195, 258, 195);
    _wall(258, 195, 282, 158);
    _wall(282, 158, 291, 165);
    _wall(291, 165, 275, 188);
    _wall(275, 188, 297, 206);
    _wall(297, 206, 314, 181);
    _wall(314, 181, 324, 187);
    _wall(324, 187, 305, 216);
    _wall(305, 216, 295, 244);
    _wall(295, 244, 265, 244);
    _wall(265, 244, 265, 228);
    _wall(265, 228, 234, 227);
    _wall(234, 227, 234, 207);
    _wall(234, 207, 195, 207);
    _wall(195, 207, 190, 209);
    _wall(190, 209, 177, 213);
    _wall(177, 213, 170, 200);

    //Wall: up swamp cross
    _wall(127, 181, 170, 200);

    //Wall: right trap

    _wall(330, 70, 360, 70);
    _wall(360, 70, 360, 100);
    _wall(360, 100, 330, 100);
    _wall(330, 100, 330, 70);

    //Wall: up stone
    _wall(110, 230, 120, 230);
    _wall(120, 230, 121, 270);
    _wall(121, 270, 110, 270);
    _wall(110, 270, 110, 230);

    //Quick areas
    _area(0, 175, 140, 110);
    _area(240, 143, 324, 45);
    _area(320, 175, 357, 99);

    _area(112, 114, 137, 73);

    // ===== ENVIRONMENT: deposit
    _environment(0.0);

    _environment_route(true);
    _environment_route_point(61, 220, 50);
    _environment_route_point(66, 108, 10);
    _environment_route_point(52, 86, 10);
    _environment_route_point(18, 71, 5);
    _environment_route_point(12, 21, 5);

    _environment_route(false);
    _environment_route_point(213, 33, 20);
    _environment_route_point(117, 35, 20);
    _environment_route_point(119, 108, 10);
    _environment_route_point(66, 108, 10);
    _environment_route_point(51, 87, 10);

    _environment_route(true);
    _environment_route_point(140, 221, 4);
    _environment_route_point(163, 249, 15);
    _environment_route_point(281, 262, 4);
    _environment_route_point(340, 248, 4);
    _environment_route_point(344, 180, 6);
    _environment_route_point(327, 163, 10);
    _environment_route_point(312, 161, 4);
    _environment_route_point(295, 184, 4);

    _environment_route(true);
    _environment_route_point(149, 155, 30);
    _environment_route_point(241, 120, 30);
    _environment_route_point(299, 144, 10);
    _environment_route_point(312, 161, 4);
    _environment_route_point(295, 184, 4);

    _environment_route(true);
    _environment_route_point(253, 61, 20);
    _environment_route_point(339, 55, 10);
    _environment_route_point(342, 19, 5);

    _flowPoint(110, 234, 15, -135);
    _flowPoint(111, 147, 40, -135);
    _flowPoint(25, 120, 40, 15);
    _flowPoint(75, 40, 30, 0);
    _flowPoint(65, 20, 30, 0);
    _flowPoint(97, 60, 20, -10);
    _flowPoint(70, 91, 12, 175);
    _flowPoint(65, 77, 25, 170);
    _flowPoint(78, 60, 20, -20);
    _flowPoint(92, 258, 30, -135);
    _flowPoint(121, 190, 35, 160);
    _flowPoint(125, 198, 20, 135);
    _flowPoint(144, 61, 30, -145);
    _flowPoint(179, 59, 30, -135);
    _flowPoint(217, 59, 30, -135);
    _flowPoint(177, 31, 50, 180);
    _flowPoint(226, 12, 30, 180);

    _flowPoint(311, 35, 20, 120);
    _flowPoint(316, 90, 18, 80);
    _flowPoint(327, 108, 25, 112);
    _flowPoint(174, 216, 25, 75);
    _flowPoint(222, 222, 30, 85);
    _flowPoint(255, 235, 43, 105);
    _flowPoint(289, 240, 24, 55);
    _flowPoint(314, 229, 30, 0);
    _flowPoint(326, 189, 20, -50);
    _flowPoint(231, 176, 40, -55);
    _flowPoint(267, 161, 30, -80);
    _flowPoint(323, 208, 28, 0);
    _flowPoint(255, 187, 20, -105);
    _flowPoint(315, 72, 25, -75);
    _flowPoint(270, 216, 30, 135);
    _flowPoint(267, 247, 25,65);
    _flowPoint(270, 247, 25,50);
    _flowPoint(305, 244, 35, 45);
    _flowPoint(234, 235, 40, 95);
    _flowPoint(113, 175, 20, -135);
    _flowPoint(99, 215, 30, -135);
    _flowPoint(99, 91, 13, 45);

    // ===== ENVIRONMENT: normal 1
    _environment(STD_RANDOMNESS);

    _environment_route(true);
    _environment_route_point(14, 252, 20);
    _environment_route_point(75, 220, 25);
    _environment_route_point(100, 216, 6);
    _environment_route_point(140, 216, 15);
    _environment_route_point(176, 257, 21);
    _environment_route_point(260, 260, 10);
    _environment_route_point(322, 257, 10);
    _environment_route_point(342, 211, 12);
    _environment_route_point(343, 172, 6);
    _environment_route_point(271, 127, 25);
    _environment_route_point(147, 127, 40);
    _environment_route_point(120, 95, 8);
    _environment_route_point(117, 44, 15);
    _environment_route_point(139, 17, 15);
    _environment_route_point(212, 14, 19);

    _environment_route(false);
    _environment_route_point(12, 22, 6);
    _environment_route_point(18, 73, 4);
    _environment_route_point(53, 85, 5);
    _environment_route_point(67, 108, 12);
    _environment_route_point(108, 106, 18);

    _environment_route(false);
    _environment_route_point(346, 18, 8);
    _environment_route_point(339, 52, 8);
    _environment_route_point(278, 72, 8);
    _environment_route_point(246, 122, 14);

    _flowPoint(115, 195, 22, 135);
    _flowPoint(116, 176, 20, -115);
    _flowPoint(173, 178, 27, -70);
    _flowPoint(25, 110, 15, 40);
    _flowPoint(46, 65, 18, 135);
    _flowPoint(75, 84, 14, 145);
    _flowPoint(90, 83, 15, 33);
    _flowPoint(68, 31, 23, -30);
    _flowPoint(146, 74, 12, 150);
    _flowPoint(252, 42, 60, 58);
    _flowPoint(285, 39, 63, 80);
    _flowPoint(233, 73, 20, 60);
    _flowPoint(336, 98, 14, 135);
    _flowPoint(337, 77, 14, -135);
    _flowPoint(88, 60, 17, -60);
    _flowPoint(233, 220, 22, 105);
    _flowPoint(265, 239, 24, 110);
    _flowPoint(264, 218, 30, 123);
    _flowPoint(83, 182, 40, 85);
    _flowPoint(26, 185, 35, 70);
    _flowPoint(318, 191, 18, 10);
    _flowPoint(101, 244, 28, -115);
    _flowPoint(143, 176, 19, -98);
    _flowPoint(153, 197, 33, 115);
    _flowPoint(35, 95, 12, -45);
    _flowPoint(332, 20, 20, 78);
    _flowPoint(283, 167, 18, -85);
    _flowPoint(301, 180, 24, -50);
    _flowPoint(293, 229, 20, 20);
    _flowPoint(203, 46, 20, -127);
    _flowPoint(150, 49, 18, -110);
    _flowPoint(302, 113, 35, 160);
    _flowPoint(285, 245, 16, 50);
    _flowPoint(248, 52, 35, 40);

    // ===== ENVIRONMENT: normal 2
    _environment(STD_RANDOMNESS);

    //flow from down left deposit to upper part
    _environment_route(false);
    _environment_route_point(12, 22, 10);
    _environment_route_point(18, 72, 8);
    _environment_route_point(53, 85, 7);
    _environment_route_point(58, 114, 12);
    _environment_route_point(30, 191, 18);


    //flow from down bonus to up
    _environment_route(true);
    _environment_route_point(212, 14, 20);
    _environment_route_point(194, 35, 15);
    _environment_route_point(129, 46, 10);
    _environment_route_point(120, 112, 10);
    _environment_route_point(30, 191, 25);
    _environment_route_point(9, 259, 20);

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
    _environment_route_point(9, 259, 20);

    //flow from down right deposit to up left
    _environment_route(false);
    _environment_route_point(340, 22, 10);
    _environment_route_point(337, 53, 10);
    _environment_route_point(256, 107, 30);
    _environment_route_point(121, 130, 40);

    _flowPoint(297, 181, 30, -53);
    _flowPoint(182, 178, 20, -90);
    _flowPoint(84, 50, 18, -18);
    _flowPoint(62, 30, 20, -10);
    _flowPoint(97, 63, 18, -18);
    _flowPoint(230, 12, 20, 180);
    _flowPoint(260, 20, 20, 90);
    _flowPoint(248, 51, 40, 65);
    _flowPoint(258, 49, 35, 75);
    _flowPoint(237, 74, 20, 80);
    _flowPoint(125, 194, 22, 160);
    _flowPoint(152, 179, 20, -80);
    _flowPoint(166, 179, 20, -65);
    _flowPoint(132, 176, 10, -80);
    _flowPoint(129, 252, 30, -65);
    _flowPoint(128, 235, 20, -75);
    _flowPoint(331, 70, 18, -160);
    _flowPoint(293, 238, 18, 82);
    _flowPoint(155, 60, 19, -137);
    _flowPoint(45, 62, 16, 125);
    _flowPoint(72, 84, 14, 120);
    _flowPoint(28, 95, 18, -40);
    _flowPoint(284, 167, 18, -65);
    _flowPoint(248, 183, 30, -115);
    _flowPoint(207, 180, 25, -110);
    _flowPoint(245, 217, 18, 100);
    _flowPoint(321, 185, 12, -10);
    _flowPoint(318, 152, 28, 30);
    _flowPoint(116, 170, 10, -155);
    _flowPoint(338, 126, 30, 75);
    _flowPoint(196, 59, 24, -145);
    _flowPoint(96, 87, 18, 35);

}
