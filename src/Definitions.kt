const val DRAW_ENVIRONMENT = 0

fun Flows._init_values() {

    // ===== GLOBAl ========

    //Wall: left dump stone dump stone
    _wall(99,89,99,160);
    _wall(99,160,70,159);
    _wall(70,159,70,171);
    _wall(70,171,30,169);
    _wall(30,169,29,198);
    _wall(29,198,0,198);
    _wall(0,198,0,168);
    _wall(0,168,28,168);
    _wall(28, 168, 28, 160);
    _wall(28,160,70,160);
    _wall(70,160,70,130);
    _wall(70,130,89,130);
    _wall(89,130,89,89);
    _wall(89,89,99,89);

    //Wall: up lefter stone
    _wall(148,270,148,231);
    _wall(148,231,158,231);
    _wall(158,231,158,270);
    _wall(158,270,148,270);

    //Wall: up righter stone
    _wall(198,270,198,231);
    _wall(198,231,208,231);
    _wall(208,231,208,270);
    _wall(208,270,198,270);

    //Wall: left, stone dump stone
    _wall(0,40,40,40);
    _wall(40,40,40,30);
    _wall(40,30,70,30);
    _wall(70,30,70,40);
    _wall(70,40,108,40);
    _wall(108,40,108,50);
    _wall(108,50,70,50);
    _wall(70,50,70,60);
    _wall(70,60,40,60);
    _wall(40,60,40,50);
    _wall(40,50,0,50);
    _wall(0,50,0,40);

    // Wall: right, stone and dump
    _wall(289,159,330,159);
    _wall(330,159,330,169);
    _wall(330,169,360,169);
    _wall(360,169,360,198);
    _wall(360,198,330,198);
    _wall(330,198,330,169);
    _wall(330,169,289,169);
    _wall(289,169,289,159);

    //Wall: right three stones
    _wall(269,130,269,90);
    _wall(269,90,279,90);
    _wall(279,90,279,80);
    _wall(279,80,320,80);
    _wall(320,80,320,70);
    _wall(320,70,360,70);
    _wall(360,70,360,80);
    _wall(360,80,320,80);
    _wall(320,80,320,90);
    _wall(320,90,279,90);
    _wall(279,90,279,130);
    _wall(279,130,269,130);
    _wall(269,130,269,130);

    //Wall: right down, 1 stone
    _wall(310,0,320,0);
    _wall(320,0,320,40);
    _wall(320,40,310,40);
    _wall(310,40,310,0);

    //Wall: tower in the swamp area and swamp blocker
    _wall(163,197,163,171);
    _wall(163,171,192,171);
    _wall(192,171,192,199);
    _wall(192,199,165,198);
    _wall(165,198,219,141);
    _wall(219,141,163,197);



    // ===== ENVIRONMENT: normal1
    _environment(0.6);

    _environment_route();
    _environment_route_point(74, 26, 30);

    _flowPoint(10, 165, 60, 80);

    // ===== ENVIRONMENT: deposit
    _environment(0.1);

    _environment_route();
    _environment_route_point(74, 26, 30);
    _environment_route_point(174, 48, 20);

}
