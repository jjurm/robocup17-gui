const val DRAW_ENVIRONMENT = 0
const val STD_RANDOMNESS = 0.1

fun Flows._init_values() {

    // ===== GLOBAl ========

    //Wall: left dump stone dump stone
    _wall(99, 89, 99, 160);
    _wall(99, 160, 70, 159);
    _wall(70, 159, 70, 171);
    _wall(70, 171, 30, 169);
    _wall(30, 169, 29, 198);
    _wall(29, 198, 0, 198);
    _wall(0, 198, 0, 168);
    _wall(0, 168, 28, 168);
    _wall(28, 168, 28, 160);
    _wall(28, 160, 70, 160);
    _wall(70, 160, 70, 130);
    _wall(70, 130, 89, 130);
    _wall(89, 130, 89, 89);
    _wall(89, 89, 99, 89);

    //Wall: up lefter stone
    _wall(148, 270, 148, 231);
    _wall(148, 231, 158, 231);
    _wall(158, 231, 158, 270);
    _wall(158, 270, 148, 270);

    //Wall: up righter stone
    _wall(198, 270, 198, 231);
    _wall(198, 231, 208, 231);
    _wall(208, 231, 208, 270);
    _wall(208, 270, 198, 270);

    //Wall: left, stone dump stone
    _wall(0, 40, 40, 40);
    _wall(40, 40, 40, 30);
    _wall(40, 30, 70, 30);
    _wall(70, 30, 70, 40);
    _wall(70, 40, 108, 40);
    _wall(108, 40, 108, 50);
    _wall(108, 50, 70, 50);
    _wall(70, 50, 70, 60);
    _wall(70, 60, 40, 60);
    _wall(40, 60, 40, 50);
    _wall(40, 50, 0, 50);
    _wall(0, 50, 0, 40);

    // Wall: right, stone and dump
    _wall(289, 159, 330, 159);
    _wall(330, 159, 330, 169);
    _wall(330, 169, 360, 169);
    _wall(360, 169, 360, 198);
    _wall(360, 198, 330, 198);
    _wall(330, 198, 330, 169);
    _wall(330, 169, 289, 169);
    _wall(289, 169, 289, 159);

    //Wall: right three stones
    _wall(269, 130, 269, 90);
    _wall(269, 90, 279, 90);
    _wall(279, 90, 279, 80);
    _wall(279, 80, 320, 80);
    _wall(320, 80, 320, 70);
    _wall(320, 70, 360, 70);
    _wall(360, 70, 360, 80);
    _wall(360, 80, 320, 80);
    _wall(320, 80, 320, 90);
    _wall(320, 90, 279, 90);
    _wall(279, 90, 279, 130);
    _wall(279, 130, 269, 130);
    _wall(269, 130, 269, 130);

    //Wall: right down, 1 stone
    _wall(310, 0, 320, 0);
    _wall(320, 0, 320, 40);
    _wall(320, 40, 310, 40);
    _wall(310, 40, 310, 0);

    //Wall: tower in the swamp area and swamp blocker
    _wall(163, 197, 163, 171);
    _wall(163, 171, 192, 171);
    _wall(192, 171, 192, 199);
    _wall(192, 199, 165, 198);
    _wall(165, 198, 219, 141);
    _wall(219, 141, 163, 197);

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
    _flowPoint(85, 110, 23, -125);
    _flowPoint(69, 144, 15, -135);
    _flowPoint(129, 238, 30, -95);
    _flowPoint(234, 240, 30, -80);
    _flowPoint(296, 29, 20, 100);
    _flowPoint(321, 228, 38, -170);
    _flowPoint(285, 122, 16, 70);
    _flowPoint(160, 49, 24, -165);
    _flowPoint(73, 35, 15, -90);
    _flowPoint(228, 137, 24, 0);
    _flowPoint(177, 144, 24, -130);
    _flowPoint(333, 68, 16, -80);
    _flowPoint(169,167,20, -140);

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
    _environment_route_point(232,221,13);
    _environment_route_point(276,251,30);
    _environment_route_point(333,244,20);
    _environment_route_point(261,188,10);
    _environment_route_point(258, 162, 6);
    _environment_route_point(303, 143, 4);
    _environment_route_point(340, 118, 35);
    _environment_route_point(344, 100, 10);

    _flowPoint(166, 166, 19, -100);
    _flowPoint(177, 240, 35, -90);
    _flowPoint(133, 243, 35, -90);
    _flowPoint(129, 147, 60, -90);
    _flowPoint(308, 179, 27, 140);
    _flowPoint(21, 183, 20, 40);
    _flowPoint(62, 115, 32, -103);
    _flowPoint(82, 25, 37, -25);
    _flowPoint(86, 154, 20, 45);
    _flowPoint(191, 145, 20, -100);
    _flowPoint(216, 189, 22, 70);
    _flowPoint(165, 142, 20, -130);
    _flowPoint(305, 102, 23, 40);
    _flowPoint(178, 192, 29, 140);

    _environment_route(false);
    _environment_route_point(340, 28, 6);
    _environment_route_point(336, 51, 6);
    _environment_route_point(242, 70, 10);
    _environment_route_point(239,103, 15);
    _environment_route_point(258,162, 6);

    _environment_route(false);
    _environment_route_point(21, 75, 6);
    _environment_route_point(145, 75, 10);
    _environment_route_point(238, 103, 13);

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
