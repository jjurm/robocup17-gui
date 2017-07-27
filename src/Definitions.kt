const val DRAW_ENVIRONMENT = 1

fun Flows._init_values() {

    // ===== GLOBAL
    _wall(224, 49, 225, 44)
    _wall(225, 44, 320, 44)
    _wall(320, 44, 321, 74)
    _wall(321, 74, 283, 74)
    _wall(283, 74, 283, 50)
    _wall(283, 50, 224, 49)

    _wall(155, 105, 155, 115)
    _wall(155, 115, 127, 142)
    _wall(127, 142, 121, 142)
    _wall(121, 142, 123, 107)
    _wall(123, 105, 155, 105)

    _wall(38, 116, 58, 99)
    _wall(58, 99, 73, 104)
    _wall(73, 104, 77, 116)
    _wall(77, 116, 71, 143)
    _wall(71, 143, 59, 152)
    _wall(59, 152, 44, 150)
    _wall(44, 150, 28, 130)
    _wall(28, 130, 38, 116)

    _wall(227, 229, 228, 163)
    _wall(228, 163, 347, 160)

    _wall(264, 122, 254, 110);
    _wall(254, 110, 263, 100);
    _wall(263, 100, 274, 110);
    _wall(274, 110, 264, 122);

    _rule(239, 255, 348, 171, 0, 258, 203, 169);
    _rule_route_point(195, 246);
    _rule_route_point(279, 245);


    // ===== ENVIRONMENT: normal
    _environment(0.6);

    _anchor(90, 200, 35);
    _anchor(197, 244, 4);
    _anchor(280, 242, 4);
    _anchor(295, 210, 40);
    _anchor(292, 100, 20);
    _anchor(210, 65, 20);
    _anchor(175, 142, 20);

    _flowPoint(10, 165, 60, 80);

    // ===== ENVIRONMENT: deposit
    _environment(0.1);

    _environment_route();
    _environment_route_point(74, 26, 30);
    _environment_route_point(174, 48, 20);

}
