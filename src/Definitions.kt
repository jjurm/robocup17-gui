const val DRAW_ENVIRONMENT = 1

fun Flows._init_values() {

    // ===== GLOBAL
    _wall(100, 200, 300, 150);

    _rule(117, 201, 201, 156, 23, 239, 80, 124);
    _rule_route_point(79, 195);
    _rule_route_point(120, 194);


    // ===== ENVIRONMENT: normal
    _environment(0.6);

    _anchor(88, 58, 20);
    _anchor(102, 145, 10);
    _anchor(90, 200, 35);
    _anchor(197, 244, 4);
    _anchor(280, 242, 4);
    _anchor(292, 134, 20);
    _anchor(337, 90, 4);
    _anchor(337, 24, 4);
    _anchor(190, 26, 20);

    _flowPoint(10, 165, 60, 80);

    // ===== ENVIRONMENT: deposit
    _environment(0.1);

    _environment_route();
    _environment_route_point(74, 26, 30);
    _environment_route_point(174, 48, 20);

}
