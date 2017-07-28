const val DRAW_ENVIRONMENT = 0

fun Flows._init_values() {

    // ===== GLOBAl ========
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

    _wall(148,270,148,231);
    _wall(148,231,158,231);
    _wall(158,231,158,270);
    _wall(158,270,148,270);

    _wall(198,270,198,231);
    _wall(198,231,208,231);
    _wall(208,231,208,270);
    _wall(208,270,198,270);

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
